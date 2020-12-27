/* 
 * A simple web-server.  
 * 
 * The web-server performs the following tasks:
 * 
 *     1. Accepts connection from a client.
 *     2. Processes cgi-bin GET request.
 *     3. If not cgi-bin, it responds with the specific file or a 404.
 * 
 * Copyright (C) 2018 raodm@miamiOH.edu
 */

#include <ext/stdio_filebuf.h>
#include <unistd.h>
#include <sys/wait.h>
#include <boost/asio.hpp>
#include <bits/stl_vector.h>
#include <cstdlib>
#include <cmath>
#include <iostream>
#include <string>
#include <fstream>
#include <sstream>
#include <vector>
#include <memory>
#include <thread>
#include <mutex>

// Using namespaces to streamline code below
using namespace std;
using namespace boost::asio;
using namespace boost::asio::ip;


// Forward declaration for method defined further below
void serveClient(std::istream& is, std::ostream& os, bool genFlag);

// shared_ptr is a garbage collected pointer!
using TcpStreamPtr = std::shared_ptr<tcp::iostream>;

// Easy way to store runtimes (4 different int values per record
using TimeInformation = std::vector<std::vector<int>>;


/** Simple method to be run from a separate thread.
 *
 * @param client The client socket to be processed.
 */
void threadMain(TcpStreamPtr client) {
    // Call routine/regular helper method.
    serveClient(*client, *client, true);
}

/**
 * Runs the program as a server that listens to incoming connections.
 * 
 * @param port The port number on which the server should listen.
 */
void runServer(int port) {
    // Setup a server socket to accept connections on the socket
    io_service service;
    // Create end point
    tcp::endpoint myEndpoint(tcp::v4(), port);
    // Create a socket that accepts connections
    tcp::acceptor server(service, myEndpoint);
    std::cout << "Server is listening on "
              << server.local_endpoint().port()
              << " & ready to process clients...\n";
    // Process client connections one-by-one...forever
    while (true) {
        // Create garbage-collect object on heap
        TcpStreamPtr client = std::make_shared<tcp::iostream>();
        // Wait for a client to connect
        server.accept(*client->rdbuf());
        // Create a separate thread to process the client.
        std::thread thr(threadMain, client);
        thr.detach();
    }
}

// Forward declaration for method used further below.
std::string url_decode(std::string);

// Named-constants to keep pipe code readable below
const int READ = 0, WRITE = 1;

// The default file to return for "/"
const std::string RootFile = "index.html";

/**
 * This method is a convenience method that extracts file or command
 * from a string of the form: "GET <path> HTTP/1.1"
 * 
 * @param req The request from which the file path is to be extracted.
 * @return The path to the file requested
 */
std::string getFilePath(const std::string& req) {
    size_t spc1 = req.find(' '), spc2 = req.rfind(' ');
    if ((spc1 == std::string::npos) || (spc2 == std::string::npos)) {
        return "";  // Invalid or unhandled type of request.
    }
    std::string path = req.substr(spc1 + 2, spc2 - spc1 - 2);
    if (path == "") {
        return RootFile;  // default root file
    }
    return path;
}

/** Helper method to send HTTP 404 message back to the client.

    This method is called in cases where the specified file name is
    invalid.  This method uses the specified path and sends a suitable
    404 response back to client.

    \param[out] os The output stream to where the data is to be
    written.

    \param[in] path The file path that is invalid.
 */
void send404(std::ostream& os, const std::string& path) {
    const std::string msg = "The following file was not found: " + path;
    // Send a fixed message back to the client.
    os << "HTTP/1.1 404 Not Found\r\n"
       << "Content-Type: text/txt\r\n"
       << "Transfer-Encoding: chunked\r\n"        
       << "Connection: Close\r\n\r\n";
    // Send the chunked data to client.
    os << std::hex << msg.size() << "\r\n";
    // Write the actual data for the line.
    os << msg << "\r\n";
    // Send trailer out to end stream to client.
    os << "0\r\n\r\n";
}

/**
 * Obtain the mime type of data based on file extension.
 * 
 * @param path The path from where the file extension is to be determined.
 * 
 * @return The mime type associated with the contents of the file.
 */
std::string getMimeType(const std::string& path) {
    const size_t dotPos = path.rfind('.');
    if (dotPos != std::string::npos) {
        const std::string ext = path.substr(dotPos + 1);
        if (ext == "html") {
            return "text/html";
        } else if (ext == "png") {
            return "image/png";
        } else if (ext == "jpg") {
            return "image/jpeg";
        }
    }
    // In all cases return default mime type.
    return "text/plain";
}

/** Convenience method to split a given string into words.

    This method is just a copy-paste of example code from lecture
    slides.

    \param[in] str The string to be split

    \return A vector of words
 */
std::vector<std::string> split(std::string str) {
    std::istringstream is(str);
    std::string word;
    std::vector<std::string> list;
    while (is >> word) {
        list.push_back(word);
    }
    return list;
}

/** Uses execvp to run the child process.

    This method is a helper method that is used to run the child
    process using the execvp command.  This method is called onlyl on
    the child process from the exec() method in this source file.

    \param[in] argList The list of command-line arguments.  The 1st
    entry is assumed to the command to be executed.
*/
void runChild(std::vector<std::string> argList) {
    // Setup the command-line arguments for execvp.  The following
    // code is a copy-paste from lecture slides.
    std::vector<char*> args;
    for (size_t i = 0; (i < argList.size()); i++) {
        args.push_back(&argList[i][0]);
    }
    // nullptr is very important
    args.push_back(nullptr);
    // Finally run the command in child process
    execvp(args[0], &args[0]);  // Run the command!
    // If control drops here, then the command was not found!
    std::cout << "Command " << argList[0] << " not found!\n";
    // Exit out of child process with error exit code.    
    exit(0);
}

/*
 * This is the first block of HTML and JS we need to send formatted to the
 * client from the server, it takes @param additionBody as what we need to add
 */
void buildFirstJSBlock(std::string& additionBody) {
    /* A lot of string work to get the format right */
    std::string fullBlock = "";
    fullBlock += "<html>\n";
    fullBlock += "  <head>\n";
    fullBlock += "    <script type='text/javascript' src='https://www."; 
    fullBlock += "gstatic.com/charts/loader.js'></script>\n";
    fullBlock += "    <script type='text/javascript' "; 
    fullBlock += "src='/draw_chart.js'></script>\n"; 
    fullBlock += "    <link rel='stylesheet' type='text/css' ";
    fullBlock += "href='/mystyle.css'>\n";
    fullBlock += "  </head>\n\n";
    fullBlock += "  <body>\n";
    fullBlock += "    <h3>Output from program</h3>\n";
    fullBlock += "    <textarea style='width: 700px; height: 200px'>\n";
    //  Write it to the output stream
    std::ostringstream os;
    os << std::hex << (fullBlock.size()) << "\r\n" << fullBlock << "\r\n";  
    additionBody += os.str(); 
}

/* This allows us to create the full string that represents the HTML
 * of our table in @param fullBody, as well as the 2D vector, 
 * @param TimeInformation that stores all our records
 */
void printTable(std::string& fullBody, TimeInformation& ti) {
    //  The outer loop is to handle every entry
    for (size_t i = 0; i < ti[0].size(); i++) {
        //  Begin a table record
        fullBody += "       <tr>";
        //  This handles each member of the record in the form of table 
        //  elements
        for (size_t j = 0; j < ti.size(); j++) {
            fullBody += "<td>" + to_string(ti[j][i]) + "</td>";
        }
        //  Closes it off
        fullBody += "</tr>\n";
    }
}
/* 
 * Given the information in @param ti, we append array values 
 * to @param fullBody to build the graph portion of the google-based
 * javascript
 */
void buildGraph(std::string& fullBody, TimeInformation& ti) {
    for (size_t i = 0; i < ti[0].size(); i++) {
        fullBody += ",\n";
        fullBody += "          [" + to_string(ti[0][i]) + ", "; 
        fullBody += to_string(ti[1][i] + ti[2][i]) + ", ";
        fullBody += to_string(ti[3][i]) + "]";
    }
    fullBody += "\n";
}

/*  This handles the second chunk of our HTML + JS that includes the table
 *  for the necessary Google Analytics graph, with @param ti containing our
 *  data and @param additionBody as a reference to our total string
 */
void buildSecondJSBlock(TimeInformation& ti, std::string& additionBody) {
    /* @var fullBody will store the full string */
    std::string fullBody = "";
    fullBody += "     </textarea>\n";
    fullBody += "     <h2>Runtime statistics</h2>\n";
    fullBody += "     <table>\n";
    fullBody += "       <tr><th>Time (sec)</th><th>User time</th><th>";
    fullBody += "System time</th><th>Memory (MB)</th></tr>\n";
    //  This is where we inject the HTML table into our massive string
    printTable(fullBody, ti);
    fullBody += "     </table>\n";
    fullBody += "     <div id='chart' style='width: 900px; height: 500px'>";
    fullBody += "</div>\n";
    fullBody += "  </body>\n";
    fullBody += "  <script type='text/javascript'>\n";
    fullBody += "    function getChartData() {\n";
    fullBody += "      return google.visualization.arrayToDataTable(\n";
    fullBody += "        [\n";
    fullBody += "          ['Time (sec)', 'CPU Usage', 'Memory Usage']";
    buildGraph(fullBody, ti);
    fullBody += "        ]\n";
    fullBody += "      );\n";
    fullBody += "    }\n";
    fullBody += "  </script>\n";
    fullBody += "</html>\n"; std::ostringstream oss;
    oss << std::hex << (fullBody.size()) << "\r\n" << fullBody << "\r\n";
    additionBody += oss.str();
}

/*  This method gathers information from the /proc/@param pid/stat directory
 *  with a means of storing information in @param ti, 
 */

void gatherStats(TimeInformation &ti, const int pid) {
    //  To potentially be used later
    int exitCode = 0;
    //  The second we want to start at
    int timeInterval = 1;
    //  A loop that runs until our program runs
    while (waitpid(pid, &exitCode, WNOHANG) == 0) {
        //  To Prevent us from checking constantly
        sleep(1);
        std::ifstream is("/proc/" + to_string(pid) + "/stat");
        if (is) {
            //  The data is stored in a single line which we can getline
            std::string data; std::getline(is, data);
            //  Use the pre-baked split method to access specific values
            std::vector<std::string> dataVec = split(data);
            if (dataVec.size() != 0) {
                //  The second we are on
                ti[0].push_back(timeInterval);
                //  The current user time
                ti[1].push_back(std::round(
                std::stof(dataVec[14 - 1]) / sysconf(_SC_CLK_TCK)));
                //  The current system time
                ti[2].push_back(std::round(
                std::stof(dataVec[15 - 1]) / sysconf(_SC_CLK_TCK)));
                //  The current memory usage
                ti[3].push_back(std::round(
                std::stol(dataVec[23 - 1]) / 1000000));  
                //  Increment our interval 
                timeInterval++;
            }
        }
    }
}

/** Helper method to send the data to client in chunks.
    
    This method is a helper method that is used to send data to the
    client line-by-line.

    \param[in] mimeType The Mime Type to be included in the header.

    \param[in] pid An optional PID for the child process.  If it is
    -1, it is ignored.  Otherwise it is used to determine the exit
    code of the child process and send it back to the client.
*/

void sendData(const std::string& mimeType, int pid,
              std::istream& is, std::ostream& os, bool tStat = false) {
    // First write the fixed HTTP header.
    std::string totalOutput = ""; totalOutput += "HTTP/1.1 200 OK\r\n";
    totalOutput += "Content-Type: " + mimeType + "\r\n";
    totalOutput += "Transfer-Encoding: chunked\r\n";
    totalOutput += "Connection: Close\r\n\r\n";
    // Read line-by line from child-process and write results to
    // client.
    //  Variables to be used later 
    std::thread t1; TimeInformation ti(4); std::string line;
    //  Flags if we need to obtain the stats or not
    //  If the user is just GETting the page,we don't want to fire this off
    if (tStat) { 
        //  Create a thread to get the stats in our @param ti object
        t1 = std::thread(gatherStats, std::ref(ti), pid);
        //  Builds the initial block of our JS
        buildFirstJSBlock(totalOutput); 
    }
    while (std::getline(is, line)) {
        // Add required "\n" to terminate the line.
        line += "\n"; std::ostringstream oss;
        // Add size of line in hex and the actual data
        oss << std::hex << (line.size()) << "\r\n" << line << "\r\n";
        totalOutput += oss.str();
    }
    // Check if we need to end out exit code
    if (pid != -1) {
        // Wait for process to finish and get exit code.
        int exitCode = 0; std::ostringstream oss;
        waitpid(pid, &exitCode, 0);
        // Create exit code information and send to client.
        line = "\r\nExit code: " + std::to_string(exitCode) + "\r\n";
        oss << std::hex << line.size() << "\r\n" << line << "\r\n";
        totalOutput += oss.str();
    }
    //  We need to wait on stat storage before printing the tables
    if (tStat) { t1.join(); buildSecondJSBlock(ti, totalOutput); };
    // Send trailer out to end stream to client.
    totalOutput += "0\r\n\r\n"; os << totalOutput;
}
/** Run the specified command and send output back to the user.

    This method runs the specified command and sends the data back to
    the client using chunked-style response.

    \param[in] cmd The command to be executed

    \param[in] args The command-line arguments, wich each one
    separated by one or more blank spaces.

    \param[out] os The output stream to which outputs from child
    process are to be sent.
*/
void exec(std::string cmd, std::string args, std::ostream& os) {
    // Split string into individual command-line arguments.
    std::vector<std::string> cmdArgs = split(args);
    // Add command as the first of cmdArgs as per convention.
    cmdArgs.insert(cmdArgs.begin(), cmd);
    // Setup pipes to obtain inputs from child process
    int pipefd[2];
    pipe(pipefd);
    // Finally fork and exec with parent having more work to do.
    const int pid = fork();
    if (pid == 0) {
        close(pipefd[READ]);        // Close unused end.
        dup2(pipefd[WRITE], 1);     // Tie/redirect std::cout of command
        runChild(cmdArgs);
    } else {
        // In parent process. First close unused end of the pipe and
        // read standard inputs.
        close(pipefd[WRITE]);
        __gnu_cxx::stdio_filebuf<char> fb(pipefd[READ], std::ios::in, 1);
        std::istream is(&fb);
        // Have helper method process the output of child-process
        sendData("text/html", pid, is, os, true);
    }
}


/**
 * Process HTTP request (from first line & headers) and
 * provide suitable HTTP response back to the client.
 * 
 * @param is The input stream to read data from client.
 * @param os The output stream to send data to client.
 * @param genChart If this flag is true then generate data for chart.
 */
void serveClient(std::istream& is, std::ostream& os, bool genChart) {
    // Read headers from client and print them. This server
    // does not really process client headers
    std::string line;
    // Read the GET request line.
    std::getline(is, line);
    const std::string path = getFilePath(line);
    //  If we want to run programs we baked ourselves, we need to decode
    const std::string decodedPath = url_decode(path);
    // Skip/ignore all the HTTP request & headers for now.
    while (std::getline(is, line) && (line != "\r")) {}
    // Check and dispatch the request appropriately
    const std::string cgiPrefix = "cgi-bin/exec?cmd=";
    const int prefixLen         = cgiPrefix.size();
    if (decodedPath.substr(0, prefixLen) == cgiPrefix) {
        // Extract the command and parameters for exec.
        const size_t argsPos   = decodedPath.find("&args=", prefixLen);
        const std::string cmd  = 
            decodedPath.substr(prefixLen, argsPos - prefixLen);
        const std::string args = url_decode(decodedPath.substr(argsPos + 6));
        // Now run the command and return result back to client.
        exec(cmd, args, os);
    } else {
        // Get the file size (if path exists)
        std::ifstream dataFile(path);
        if (!dataFile.good()) {
            // Invalid file/File not found. Return 404 error message.
            send404(os, path);
        } else {
            // Send contents of the file to the client. 
            sendData(getMimeType(path), -1, dataFile, os, genChart);
        }
    }
}

//------------------------------------------------------------------
//  DO  NOT  MODIFY  CODE  BELOW  THIS  LINE
//------------------------------------------------------------------

/** Convenience method to decode HTML/URL encoded strings.

    This method must be used to decode query string parameters
    supplied along with GET request.  This method converts URL encoded
    entities in the from %nn (where 'n' is a hexadecimal digit) to
    corresponding ASCII characters.

    \param[in] str The string to be decoded.  If the string does not
    have any URL encoded characters then this original string is
    returned.  So it is always safe to call this method!

    \return The decoded string.
*/
std::string url_decode(std::string str) {
    // Decode entities in the from "%xx"
    size_t pos = 0;
    while ((pos = str.find_first_of("%+", pos)) != std::string::npos) {
        switch (str.at(pos)) {
            case '+': str.replace(pos, 1, " ");
            break;
            case '%': {
                std::string hex = str.substr(pos + 1, 2);
                char ascii = std::stoi(hex, nullptr, 16);
                str.replace(pos, 3, 1, ascii);
            }
        }
        pos++;
    }
    return str;
}

/*
 * The main method that performs the basic task of accepting connections
 * from the user.
 */
int main(int argc, char** argv) {
    if (argc <= 2) {
        // Setup the port number for use by the server
        const int port = std::stoi((argc == 2 ? argv[1] : "6003"));
        runServer(port);
    } else if (argc == 4) {
        // Process 1 request from specified file for functional testing
        std::ifstream input(argv[1]);
        std::ofstream output;
        if (argv[2] != std::string("std::cout")) {
            output.open(argv[2]);
        }
        bool genChart = (argv[3] == std::string("true"));
        serveClient(input, (output.is_open() ? output : std::cout), genChart);
    } else {
        std::cerr << "Invalid command-line arguments specified.\n";
    }
    return 0;
}

// End of source code
