/* Noah Dunn 
 * CSE 381
 * Dr. Rao
 * Copyright 2019 Noah Dunn
 * 
 * A simple web-server.  
 * 
 * The web-server performs the following tasks:
 * 
 *     1. Accepts connection from a client.
 *     2. Processes cgi-bin GET request.
 *     3. If not cgi-bin, it responds with the specific file or a 404.
 * 
 */

#include <ext/stdio_filebuf.h>
#include <unistd.h>
#include <sys/wait.h>
#include <boost/asio.hpp>
#include <cstdlib>
#include <iostream>
#include <string>
#include <fstream>
#include <sstream>
#include <vector>
#include <iomanip>

// Using namespaces to streamline code below
using namespace std;
using namespace boost::asio;
using namespace boost::asio::ip;

using StrVec = std::vector<std::string>;

/** Forward declaration for method defined further below.  You will
    need to use this url_decode method in your serveClient method.
 */
std::string url_decode(std::string data);



// Named-constants to keep pipe code readable below
const int READ = 0, WRITE = 1;

/**
 * Process HTTP request (from first line & headers) and
 * provide suitable HTTP response back to the client.
 * 
 * @param is The input stream to read data from client.
 * @param os The output stream to send data to client.
 */

// GET /cgi-bin/exec?cmd=head&args=-2%20%2Fproc%2Fcpuinfo HTTP/1.1
/**
* RequestGood takes the first line of our request as string @param request and 
 * sees if it starts with GET /cgi-bin/exec
**/
bool requestGood(std::string request) {
    return (request.substr(0, 17) == "GET /cgi-bin/exec");
}
/**
 * MyExec takes our string list of linux args and command as @param argList
 * converts it into a char* vector, and executes it using execvp
 */
void  myExec(StrVec argList) { 
    //  This stores the linux commands in their new format
    std::vector<char*> args; 
    //  Does the prints and stores inputs as C-strings 
    for (auto& s : argList) { 
        //  Stores a pointer to the beginning of each string
        args.push_back(&s[0]); 
    }
    //  All commands are terminated with nullptr
    args.push_back(nullptr);  
    //  Executes the command on the shell
    execvp(args[0], &args[0]); 
}

/**  Use the @param args and @param cmd we have been provided to build a String 
vector Capable of being converted later
*/ 
StrVec createArgList(std::string args, std::string cmd) {
    std::vector<std::string> argList; std::string arg;
    std::istringstream ss(args);  //  String stream to handle multiple args
    argList.push_back(cmd);  //  Throws the command in right off the bat
    while (ss >> std::quoted(arg)) {  //  Preserve quotes for commands
        argList.push_back(arg);  //  Add each command
    }
    return argList;
}
/**
 * 
 * This does all the heavy lifting for the pipe, using an OS and the two param
 * @param args @param cmd 
 */ 
void outputPipeline(std::ostream &os, std::string args, std::string cmd) {
    //  Create an String Vector from our args and cmd
    std::vector<std::string> argList = createArgList(args, cmd);
    //  Generate the array to represent read/write streams
    int pipefd[2];
    //  Begin the pipe
    pipe(pipefd);
    //  Fork so we can get a new process going
    const pid_t pid = fork();
    if (pid == 0) { 
        //  Close the unneeded read end
        close(pipefd[READ]);
        //  Map the write end of the array to the actual pipe end
        dup2(pipefd[WRITE], WRITE);
        //  Begin system call
        myExec(argList);
    } else {
        //  Close the unneeded write end of the parent
        close(pipefd[WRITE]);
        //  Establish a buffer to be able to read input through
        __gnu_cxx::stdio_filebuf<char> fb(pipefd[READ], std::ios::in);
        //  Get a stream to read from that buffer
        std::istream is(&fb);
        //  Read all the sizes in hex, and account for the eaten \n character
        for (std::string line; std::getline(is, line);) {
            //  All lines in HTTP calls are terminated with \r\n
            os << std::hex << (line.size() + 1) << "\r\n";
            os << line<< "\n" << "\r\n";
        }
        //  Pass in an exit code to get out
        int exitCode = -1;
        //  Wait for child to conclude
        waitpid(pid, &exitCode, 0);
        //  Print our exit code
        std::string extString = ("Exit code: " + to_string(exitCode));
        //  Finish off with the size and print of our final line
        os << std::hex << (extString.size()) << "\r\n";
        os << extString << "\r\n";
        //  Termination 
        os << "0\r\n\r\n";
    }
}

/*  This method gets all the important information about 
 *  the command we wish to run from the first line of output as @param cmd
 */
std::string grabDecodedCmd(std::string cmd) {
    //  Index of the beginning of the command
    int indexCommandStart = cmd.find("?") + 5;
    //  Length of the command 
    int lengthCommand = cmd.find("&") - indexCommandStart;
    //  Generate the substring representing the whole command call
    std::string encodedCmd = cmd.substr(indexCommandStart, lengthCommand);
    //  Decode the URL
    std::string decodedCmd = url_decode(encodedCmd);
    return decodedCmd;
}
/* Use quoted and url_decode to get command line arguments in the correct 
 * format from our @param args
 */
std::string grabDecodedArgs(std::string args) {
    //  Find the starting index of the command
    int indexCommandStart = args.find("&") + 6;
    //  Find the length of the command
    int lengthCommand = args.find(" ", indexCommandStart) - indexCommandStart;
    //  Grab the string of all the encoded arguments
    std::string encodedArgs = args.substr(indexCommandStart, lengthCommand);
    //  Decode them
    std::string decodedArgs = url_decode(encodedArgs);
    return decodedArgs;
}

/*  The HTTP response we write if the request is what we desire
 *  using our stream @param os and our @param path and the @param cmd
 */
void writeResponse(std::ostream& os, std::string args, std::string cmd, 
        std::string path, bool isGood) {
    //  Standard headers
    if (isGood) {
        os << "HTTP/1.1 200 OK\r\n";
    } else {  
        os << "HTTP/1.1 404 Not Found\r\n"; 
    }
    os << "Content-Type: text/plain\r\n";
    os << "Transfer-Encoding: chunked\r\n";
    os << "Connection: Close\r\n";
    os << "\r\n";
    if (isGood) {
    //  Calls the pipe which will handle all the linux needs
        outputPipeline(os, args, cmd);    
    } else {
        //  We print the invalid request and the path it was valled at
        std::string realPath = "Invalid request: " + path.substr(1);
        //  +1 is to account for the eaten new line character
        os << std::hex << (realPath.size() + 1) << "\r\n";
        os <<  realPath << "\n"  << "\r\n";
        os << "0\r\n\r\n";    
    }
}
// Method 6: Write out encoded bad response
void serveClient(std::istream& is, std::ostream& os) {
    //  The various pieces of the first line
    std::string line, header, path, proto;
    //  Grab the important piece of the request
    std::getline(is, line); 
    //  Process each piece
    std::istringstream(line) >> header >> std::quoted(path) >> proto; 
    //  Decode the args
    std::string args = grabDecodedArgs(path);
    //  Decode the command
    std::string cmd = grabDecodedCmd(path);
    //  If the line is good, we write a good response, 
    //  otherwise we write a bad one
    if (requestGood(line)) {
        writeResponse(os, args, cmd, path, true);
    } else {
        writeResponse(os, args, cmd, path, false);
    }
}





// -----------------------------------------------------------
//       DO  NOT  ADD  OR MODIFY CODE BELOW THIS LINE
// -----------------------------------------------------------

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

/**
 * Runs the program as a server that listens to incoming connections.
 * 
 * @param port The port number on which the server should listen.
 */
void runServer(int port) {
    io_service service;
    // Create end point
    tcp::endpoint myEndpoint(tcp::v4(), port);
    // Create a socket that accepts connections
    tcp::acceptor server(service, myEndpoint);
    std::cout << "Server is listening on port "
              << server.local_endpoint().port() << std::endl;
    // Process client connections one-by-one...forever
    while (true) {
        tcp::iostream client;
        // Wait for a client to connect
        server.accept(*client.rdbuf());
        // Process information from client.
        serveClient(client, client);
    }
}

/*
 * The main method that performs the basic task of accepting connections
 * from the user.
 */
int main(int argc, char** argv) {
    if (argc == 2) {
        // Process 1 request from specified file for functional testing
        std::ifstream input(argv[1]);
        serveClient(input, std::cout);
    } else {
        // Run the server on some available port number.
        runServer(0);
    }
    return 0;
}

// End of source code
