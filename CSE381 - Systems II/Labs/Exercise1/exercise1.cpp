/**
 * A program to demonstrate:
 *   1. Structuring logic to be algorithmic & type-agnostic
 *      (as much as possible)
 *   2. Demonstrating the advantages of working with generic I/O streams
 *      2.1. Use standard I/O streams (std::cin, std::cout)
 *      2.2. Use file I/O stream (std::ifstream) and string-streams
 *      2.3. Use of socket I/O and HTTP
 *
 * Copyright (C) 2019 raodm@miamiOH.edu
 */

#include <boost/asio.hpp>
#include <string>
#include <iostream>
#include <fstream>

using namespace boost::asio;
using namespace boost::asio::ip;

// A convenience data type used in the printMax method below. Such
// type aliases are used to improve readability of code, streamline
// algorithmic thinking, and ease code maintenance.
using InputType = int;

/**
 * Method to print the maximum value of a given type. Inputs are read
 * from a given input stream.  This method assumes that there is at
 * least 1 valid input in the given input stream.  Moreover, it
 * assumes that the data in the input stream is compatible with
 * InputType.  The result is printed to a given output stream.
 *
 * @param is The input stream from where input values are to be
 * read. Inputs are read until end-of-file is reached.  The default
 * stream is std::cin (type Control+D to input logical end-of-file).
 *
 * @param os The output stream to where maximum value is to be
 * written.  The default stream is std::cout.
 */
void printMax(std::istream& is = std::cin, std::ostream& os = std::cout) {
    // Intermediate variables 
    InputType input, max;
    // Setup the first input as the current maximum
    is >> max;
    
    // Process inputs from the given input stream
    while (is >> input) {
        // Track the maximum input we have observed so far.
        if (input > max) {
            max = input;  // found new maximum value.
        }
    }
    
    // Print the maximum value to the specified output stream.
    os << "Maximum value = " << max << std::endl;
}

/**
 * Helper method to create an TCP I/O stream to send an HTTP request
 * to a web server and obtain file contents as response.  Note that
 * this method does not process the response. However, it reads and
 * discards the HTTP response headers sent by the server.
 *
 * @param path The path to the file on the web server to be
 * obtained. E.g. "~raodm/test.html".
 *
 * @param host An optional host name for the web server. The default
 * host is currently assumed to be ceclnx01.cec.miamioh.edu.
 */
bool setupHttpStream(tcp::iostream& stream, const std::string& path,
                     const std::string& host = "ceclnx01.cec.miamioh.edu") {
    // Establish a TCP connection to the given web server at port
    // number 80.
    stream.connect(host, "80");
    if (!stream.good()) {
        return false;  // invalid connection. Nothing further to do.
    }

    // Send HTTP request to the web server requesting the desired
    // file.  This is the same GET request that a browser generates to
    // obtain the file from the server.
    stream << "GET /"  << path << " HTTP/1.1\r\n"
           << "Host: " << host << "\r\n"
           << "Connection: Close\r\n\r\n";

    // Assuming the file is valid, the web server will send us a
    // response.  First, HTTP response code and ensure it is "200 OK"
    // status code indicating things went well at the server.
    std::string line;
    std::getline(stream, line);  // Read a whole line! yes, important.
    if (line.find("200 OK") == std::string::npos) {
        return false;  // Web server reported an error!
    }

    // Next read and discard HTTP response headers. The response
    // headers contain information that is useful for processing the
    // data. However, here we are doing very simple text
    // processing. Consequently, we ignore the response headers.
    while (std::getline(stream, line) && (line != "\r") && !line.empty()) {}

    // Now that we have skipped over the response headers (indicated
    // by a blank line or just a line with "\r") the next information
    // read from the stream will be the actual response data from the
    // server.  We don't do processing of data in this method. We just
    // return true to indicate successful processing up to this point.
    return true;
}

int main() {
    // Read numbers from std::cin and print to std::cout.  This is the
    // default setting.
    std::cout << "Using standard I/O (type inputs and press Ctrl+D)...\n";
    printMax();  // same as calling printMax(std::cin, std::cout);


    //---------------------------------------------------------------
    // Instead of reading numbers from standard input (i.e.,
    // std::cin), here we read numbers from an input file and write
    // results to an on output file.
    std::cout << "Using file I/O...\n";
    std::ifstream inputs("exercise1_numbers.txt");  // input  file stream
    std::ofstream output("exercise1_output.txt");   // output file stream
    // Have the helper method process the data for us
    printMax(inputs, output);

    //---------------------------------------------------------------
    // Instead of a local file, use data from a file stored on a
    // web-server and print maximum value.
    
    // First, use method to establish connection with a web server and
    // setup an I/O stream to read contents of a file at path
    // "~raodm/exercise1_numbers.txt".
    std::cout << "Socket and HTTP-based I/O...\n";
    tcp::iostream stream;
    if (!setupHttpStream(stream, "~raodm/exercise1_numbers.txt")) {
        // Something went wrong in getting the data from the server.
        std::cout << "Error obtaining data from server.\n";
        return 1;  // Unsuccessful run of program (non-zero exit code)
    }
    // Let the helper method print the maximum
    printMax(stream, std::cout);

    //---------------------------------------------------------------
    // Successful run of a program is indicated by a return value of
    // zero.
    return 0;
}
