/* Noah Dunn 
 * CSE 381
 * Dr. Rao
 * Copyright 2019 Noah Dunn
 * 
 * Multi-threaded word counter
 * This program counts the number of words and valid english words on a web page
 * given some file names
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
#include <cctype>
#include <algorithm>
#include <unordered_map>
#include <thread>

// Using namespaces to streamline code below
using namespace std;
using namespace boost::asio;
using namespace boost::asio::ip;

//  Creating some aliases for commonly used structures
using StrVec = std::vector<std::string>;
using ThreadList= std::vector<std::thread>;
using ValDict = std::unordered_map<std::string, bool>;

/* This method takes a stream, our dictionary, a desired url,
 * and a vector to store any result we get, processes 
 * which words are in the dictionary, and adds them to @param allRuns
 */
void readRequest(tcp::iostream& stream, 
     ValDict& dictionary, std::string url, StrVec& allRuns, size_t runNum) {    
     std::string line;
     //  From Dr. Rao(raodm)
     //  Next read and discard HTTP response headers. The response
     //  headers contain information that is useful for processing the
     //  data. However, here we are doing very simple text
     //  processing. Consequently, we ignore the response headers.
     while (std::getline(stream, line) && (line != "\r") && !line.empty()) {}
     
     //  We need some space to save the number of normal words and english words
     int wordCount = 0; int englishCount = 0;
     //  Grabs every line from the document
     while ( std::getline(stream, line) ) { 
         //  Gets rid of any and all punctuation
         std::replace_if(line.begin(), line.end(), ::ispunct, ' ');
         //  A string stream to grab each individual word
         std::istringstream is(line);
         //  A value to store each word
         std::string word;
         //  While we still have words to grab
         while (is >> word) {
            //  Change every word to lowercase for fair comparison
            std::transform(word.begin(), word.end(), word.begin(), ::tolower);
            //  Count every single word, regardless of validity 
            wordCount++;
            //  If it's in the predetermined dictionary, add it
            if (!(dictionary.find(word) == dictionary.end())) {
                englishCount++;
            }
         }
     }
     //  Add our full set of values to a allRuns to be processed later
     allRuns[runNum] = (url + " " + to_string(wordCount) + " " + 
     to_string(englishCount));
     //  Close the stream
     stream.close();
}

/* Forms a socket connection to a given URL from a fileName
 * using boost sockets on TCP. Other params: @param dictionary, @param allRuns
 * are to be used later in file processing
 */
void setupHttpStream(std::string path, 
        ValDict dictionary, StrVec& allRuns, size_t runNum ) {
    //  Create a TCP protocol socket object
    tcp::iostream stream;
    //  Our full Url provided by @raodm
    std::string fullUrl = 
            "http://os1.csi.miamioh.edu/~raodm/cse381/hw6/SlowGet.cgi?file=";
    //  Append our file Name to the full URL
    fullUrl = fullUrl + path; path = fullUrl; std::string host; 
    //  Clear out our HTTP or HTTPS from hostName
    if (path.substr(0, 4) == "http") { 
        if (path[4] == 's') {
            host = path.substr(8, path.find("/", 8) - 8); 
        } else {
            host = path.substr(7, path.find("/", 7) - 7);
        }
    } else {
        host = path.substr(0, path.find("/"));
    }
    //  host is our hostName, this line grabs file path
    std::string fileName = path.substr(path.find("/", 8)); 
    stream.connect(host, "80");  //  Use the port for HTTP protocol
     //  Call a GET request to the stream
     stream << "GET "  << fileName   << " HTTP/1.1\r\n" 
            << "Host: " << host << "\r\n"
            << "Connection: Close\r\n\r\n";
     //  Assuming the file is valid, the web server will send us a
     //  response.  First, HTTP response code and ensure it is "200 OK"
     //  status code indicating things went well at the server.
      //  Read a whole line! yes, important.
     std::string line; std::getline(stream, line); 
     //  Begin file processing
     readRequest(stream, dictionary, path, allRuns, runNum); 
}
     
/* This loads a file into a ValDict unordered_map for use 
 * later from a given @param fileName
 *
 * 
 */
ValDict loadDictionary(std::string fileName) {
    //  Get our input stream and a variable to store individual words
    std::ifstream fileRead(fileName); std::string word;
    //  The unordered map we are going to use to our dictionary in
    ValDict dictionary;
    while (fileRead >> word) {
        //  Add them to the dictionary with true values
        dictionary[word] = true;
    }
    return dictionary;
}

/*  This method prints all of our @param allRuns runs in
 *  order, given a list of all the files in @param allFiles
 *
 */
void fullReadOut(StrVec allRuns, StrVec allFiles) {
    //  Allows us to store all three parts of each Run results
    std::string url, words, engWords;
    //  We want the results for every file in order
    for (size_t i = 0; i < allFiles.size(); i++) { 
        //  Check amongs all results for the correct one
        for (size_t j = 0; j < allRuns.size(); j++) {
            //  If we find the file
            if ( allRuns[j].find(allFiles[i]) != std::string::npos ) {
                //  Grab all three fields, and print them exactly as desired
                std::istringstream iss(allRuns[j]);
                iss >> url >> words >> engWords;
                std::cout << "URL: " << url << ", words: " << words << ", " 
                        << "English words: " << engWords << std::endl;
                //  Break when we find what we want
                break;
            }
        }
    }
}
  
/* A helper method for our threads to execute to carry out a fixed 
 * number of runs per thread. We input a start and end value to determine
 * which files in @param fileNames we want to process with our other
 * methods 
 */
void clientRuns(ValDict& dictionary, StrVec& allRuns,
        size_t start, size_t end, StrVec& fileNames) {
    //  If we are at an odd number of threads relative to our number of 
    //  needed method calls, this fixes it so the last thread takes care 
    //  of the remainder
    if (end > fileNames.size()) { end = fileNames.size(); } 
    //  This creates an HTTP client and connects it to a given desired file
    for (size_t i = start; i < end; i++) {
         //  Grab one of our files
         std::string path = fileNames[i];
         //  Begin setup
         setupHttpStream(path, dictionary, allRuns, i);
    }
}

/*  This method handles all threading, creates our dictionary, 
 *  and preps all structures to be handled by the remainder of the methods 
 *  present
 *
 */
int main(int argc, char** argv) {
    //  Create our dictionary
    ValDict dictionary = loadDictionary("english.txt");
    //  Vectors to store our data per run, and all our file Names
    StrVec allRuns; StrVec allFiles;
    //  Grab the number of threads we want to operate on
    size_t numOfThreads = std::stoi(argv[1]); 
    //  Grab all our files
    for (size_t i = 2; i < (size_t)argc; i++) {
        allFiles.push_back(argv[i]);
    }
    //  Create a thread list to store our threads, and store the size of 
    //  our files to avoid constant calculation
    ThreadList thrList; size_t fileNum = allFiles.size();
    //  The number of methods we want to conduct per thread
    size_t fileDiv = fileNum/numOfThreads;
    allRuns.resize(allFiles.size());
    
    //  Iterate in chunks based on number of methods per threads
    for (size_t i = 0; i < numOfThreads + 1; i += fileDiv) {
        //  Create a thread that specifies which files 
        //  we will process this run from i to i + fileDiv
        thrList.push_back(std::thread(clientRuns, 
            std::ref(dictionary), std::ref(allRuns), i, 
                i + fileDiv, std::ref(allFiles)));
    }
    //  Join all our threads at the end to insure closure 
    for (auto &t : thrList) {
        t.join();
    }
    //  Read out all our findings
    fullReadOut(allRuns, allFiles);
    return 0;
}

// End of source code
