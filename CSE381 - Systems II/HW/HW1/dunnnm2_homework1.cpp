/* 
 * File:   dunnnm2_homework1.cpp
 * Author: Noah Dunn
 * Copyright (C) 2019 dunnnm2@miamiOH.edu
 * Created on August 29, 2019, 4:34 PM
 * 
 * This program reads input from a webpage utilizing an
 * HTTP GET request, parses through rows of sales data,
 * and prints out sales data (or the absence of) for a given person.
 * If selected, it will also print out the top seller for a given piece of 
 * data.
 */



#include <boost/asio.hpp>
#include <string>
#include <iostream>
#include <fstream>
#include <unordered_map>
#include <iomanip>

using namespace boost::asio;
using namespace boost::asio::ip;

 /** A note from Dr. Rao 
  * Helper method to create an TCP I/O stream to send an HTTP request
  * to a web server and obtain file contents as response. 
  * 
  * From me, @dunnm2
  * The initial method has been modified to process data and print the results
  * of those processes
  * @param stream : A given TCP iostream that will form the connection to 
  * the webpage we wish to retrieve information from 
  * @param path : The location of a file on said webpage
  * @param inputName : The user we are looking for 
  * @param maxFlag   : This boolean flag determines whether or not 
  * we wish to see the max number of sales for a particular data set.
  **/

bool setupHttpStream(tcp::iostream& stream, const std::string& path
, std::string inputName, bool maxFlag) {
     //  Establish a TCP connection to the given web server at port
     //  number 80.
    std::string streamVal;  //  We need to chop the current path into two parts.
    if (path.substr(0, 4) == "http") {  //  Get rid of http or https
        if (path[4] == 's') {
            streamVal = path.substr(8, path.find("/", 8) - 8); 
        } else {
            streamVal = path.substr(7, path.find("/", 7) - 7);
        }
    } else {
        streamVal = path.substr(0, path.find("/"));
    }
    //  streamVal is our actual address, this line grabs file path
    std::string fileName = path.substr(path.find("/", 8)); 
    stream.connect(streamVal, "80");  //  Use the port for HTTP protocol
if (!stream.good()) {   
    return false;  //  invalid connection. Nothing further to do.
} 
     //  From Dr. Rao (@raodm)
     //  Send HTTP request to the web server requesting the desired
     //  file.  This is the same GET request that a browser generates to
     //  obtain the file from the server.
     stream << "GET /"  << fileName   << " HTTP/1.1\r\n" 
            << "Host: " << streamVal << "\r\n"
            << "Connection: Close\r\n\r\n";
     //  Assuming the file is valid, the web server will send us a
     //  response.  First, HTTP response code and ensure it is "200 OK"
     //  status code indicating things went well at the server.
     std::string line;
     std::getline(stream, line);  //  Read a whole line! yes, important.
     if (line.find("200 OK") == std::string::npos) {
         return false;  //  Web server reported an error!
     } 
     //  We establish an unordered map to save our Name-Amount pairs
     std::unordered_map<std::string, int> dictionary = {};
     //  Data is in three parts, but we only care for Name and Amount
     std::string name = ""; std::string company = "";
     int amount; std::string topAmountPerson = "";
     
     //  From Dr. Rao(raodm)
     //  Next read and discard HTTP response headers. The response
     //  headers contain information that is useful for processing the
     //  data. However, here we are doing very simple text
     //  processing. Consequently, we ignore the response headers.
     while (std::getline(stream, line) && (line != "\r") && !line.empty()) {}
     
     //  We want to toss the column headers from the data
     std::getline(stream, line);
     //  Run until the stream is empty
     while (stream >> std::quoted(name) >> std::quoted(company) >> amount) { 
         //  The last input is doubled if this line is not included.
         //  Quoted keeps our escape characters in the data in their
         //  correct + desired format
          
         //  We add the amount to the UM under each person's name
         dictionary[name] += amount; 
         //  If we have a new big winner, swap the name
        if (dictionary[name] > dictionary[topAmountPerson]) { 
            topAmountPerson = name;}
    }   
     //  We determine if the salesperson in question actually exists 
     if (dictionary[inputName]) {
        std::cout << "Sales by " << inputName << " = " <<
                dictionary[inputName] << std::endl;
        if (maxFlag) {  //  If the flag is set, print the top amount person
            std::cout << "Top employee: " << topAmountPerson << 
            " with sales: " << dictionary[topAmountPerson] << std::endl;
        }
     } else {  //  If they don't we just print this
         std::cout << "Employee " << inputName << " not found." << std::endl;
     }
     return true;
}

int main(int argc, char *argv[]) {
     //  We are grabbing all input from CL
     std::string url = argv[1];    
     std::string employeeName = argv[2];
     std::string readFlag = argv[3];
     //  Converting string to bool
     bool MaxFlag;
     if (readFlag == "true") {
         MaxFlag = true;
     } else {
         MaxFlag = false;
     }
     
     //  Create our TCP-based IOstream
     tcp::iostream stream;
     //  Run our setup method to connect, process, and print our data
     if (!setupHttpStream(stream, url, employeeName, MaxFlag)) {
         //  Something went wrong in getting the data from the server.
         std::cout << "Error obtaining data from server.\n";
         return 1;  //  Unsuccessful run of program (non-zero exit code)
     } 
     
     return 0;
}



