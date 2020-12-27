/* 
 * A simple Banking-type web-server.  
 * 
 * This multithreaded web-server performs simple bank transactions on
 * accounts.  Accounts are maintained in an unordered_map.  
 * 
 * Copyright 2019 Noah Dunn
 * 
 */

// All the necessary includes are present
#include <boost/asio.hpp>
#include <iostream>
#include <string>
#include <sstream>
#include <thread>
#include <memory>
#include <unordered_map>
#include <mutex>
#include <iomanip>

// Setup a server socket to accept connections on the socket
using namespace boost::asio;
using namespace boost::asio::ip;

// An easier way of writing our shared pointer for our iostream
using TcpStreamPtr = std::shared_ptr<tcp::iostream>;

//  This is going to be used a lot, might as well make an alias for it
using AccountList = std::unordered_map<std::string, double>;
//  Our global mutex necessary for proper locking
std::mutex gate;


// Forward declaration for method defined further below
std::string url_decode(std::string);

/* Given the body for an HTTP repsonse @param reponseString 
 * and an output stream @param &os, this prints an appropriately
 * formatted HTTP response
 */
void generateHTTPResponse(std::string responseString, std::ostream &os) {
    //  The length of our response body
    int stringLength = responseString.length();
    //  All the necessary headers for an OK response
    os << "HTTP/1.1 200 OK" << "\r\n";
    os << "Server: BankServer" << "\r\n";
    os << "Content-Length: " << stringLength << "\r\n";
    os << "Connection: Close" << "\r\n";
    os << "Content-Type: text/plain" << "\r\n";
    //  The blank line in between headers and body
    os << "\r\n";
    //  The full body string
    os <<  responseString;
}

/* This takes in our full list of accounts @param accounts, the individual
 * account we desire @param account, a boolean value to see if the account
 * exists or not @param check, the amount we want to modify the acccount by
 * @param amount, and the action we are seeking to perform @param option
 * and carries out a mutex-locked operation based on desired action.
 * Our @return double only applies for status operations  
 */
double checkAccountExistance(AccountList& accounts, std::string account, 
        bool& check, double amount, std::string option) {  
    //  Begin critical section
    std::lock_guard<std::mutex> guard(gate);
    //  If it's a reset, we clear the accounts AccountList
    if (option == "reset") {
        accounts.clear();
    //  If we can find the account in the AccountList, we carry out some actions
    } else if (!(accounts.find(account) == accounts.end())) {
        //  Set our existence checker to true
        check = true;
        //  If the option is credit, add the desired amount to the account.
        if (option == "credit") {
            accounts[account] += amount;
        //  If the option is status, return the amount in the account.
        } else if (option == "status") {
            double status = accounts[account];
            return status;
        }
    //  If the account does not exist, and we tried a create, debit, credit
    //  or status option on it 
    } else {
        //  If the account doesn't exist and the user wants to make one, 
        //  allow them 
        if (option == "create") {
            //  Existance checker is false, the account did not exist
            check = false;
            //  Create an account with a default balance of 0
            accounts[account] = 0.0;
        }
        //  The account didn't exist and we tried performing a check
        //  or edit related operation
        check = false;
    }
    //  A default value that won't be used
    return -1;
}

/*  If the user wants to perform a status operation
 *  on their @param account in @param accounts, it will evaluate,
 *  and either print the balance as desired, or report the account not found,
 *  and send the results through the ostream @param os
 */
void statusTransaction(AccountList& accounts, 
        std::string account, std::ostream& os) {
    //  Default value for our existance checker
    bool check = false;
    std::string response;  //  The HTTP response body
    //  We want to save the output to use in our response body
    double balance = 
    checkAccountExistance(accounts, account, check, 0, "status");
    //  The account exists
    if (check) {
        //  Build a correct response string 
        response = "Account " + account + ": $";
        //  We want our balance to two decimals of precision
        std::ostringstream oss; oss.precision(2);
        //  We want our output to appear streamlined
        oss << std::fixed << balance;
        //  Append the balance to the other piece of the response
        response += oss.str(); 
    //  The account does not exist
    } else {
         response = "Account not found";
    }
    //  Generate the response
    generateHTTPResponse(response, os);
}

/*  If the user wants to perform a creation operation
 *  on their @param account in @param accounts, it will evaluate,
 *  and either generate a new account or print an error message
 *  and send the results through the ostream @param os
 */
void createTransaction(AccountList& accounts, 
        std::string account, std::ostream& os) {
    //  Our default existence check value
    bool check = false;
    //  The reponse body we want to send
    std::string response; 
    //  We don't need anything from this method call, it just needs to occur
    checkAccountExistance(accounts, account, check, 0, "create");
    //  If the account did not exist and was created 
    if (!check) {
        response = "Account "  + account + " created"; 
    //  If the account already exists
    } else {
        response = "Account " + account + " already exists";
    }
    //  Generate the HTTP response
    generateHTTPResponse(response, os);
}

/*  If the user wants to perform a credit operation
 *  on their @param account in @param accounts, it will evaluate,
 *  and either add/subtract @param amount to the account balance, 
 *  depending on @param cred, or will report that the account was not found
 */
void credDebTransaction(AccountList& accounts,
        std::string account, std::ostream& os, double amount) {
    //  Our existence check and our HTTP response body
    bool check = false; std::string response; 
    checkAccountExistance(accounts, account, check, amount, "credit");
    //  If the account exists, report balance update
    if (check) {
        response = "Account balance updated";
        //  If the account does not exist, report that it wasn't found
    } else {
            response = "Account not found";
    }
    
    //  Generate the response with the correct response body
    generateHTTPResponse(response, os);
}
/*  If the user wants to perform a reset operation
 *  We purge everything in @param accounts and report the results
 */
void resetTransaction(AccountList& accounts, std::ostream& os) {
    //  This is done to avoid error
    bool voidCheck; 
    //  We are only concerned with sending the request
    checkAccountExistance(accounts, "N/A", voidCheck, 0, "reset");
    //  We successfully purged the map
    std::string response = "All accounts reset";
    //  Generate our response
    generateHTTPResponse(response, os);
}
/* This method calls a helper method based on @param command, and sends a full
 * @param request to be processed by the helper methods. Output is eventually
 * generated through @param os and utilizes @param accounts as our information
 * data.
 */
void execTransaction(const std::string& command, const std::string& request,
        AccountList& accounts, std::ostream& os) {
    //  Grab the whole request line
    std::istringstream reqStream = std::istringstream(request);
    //  Generate storage variables
    std::string account, trash; double amount;
    //  Get rid of the trash we don't care about and store the account #
    //  And the amount value if the transaction is debit or credit
    reqStream >> trash >> trash >> trash >> account >> trash >> amount;
    // Call the helper method based on command 
    if (command == "status") {      
        statusTransaction(accounts, account, os);
    } else if (command == "credit") {
        credDebTransaction(accounts, account, os, amount);
    } else if (command == "reset") {
        resetTransaction(accounts, os);
    } else if (command == "debit") {
        credDebTransaction(accounts, account, os, -amount);
    } else if (command == "create") { 
        createTransaction(accounts, account, os);
    }
}

/* This method helps serve the @param stream inputstream 
 * and @param os outputstream of our socket connection to each client.
 * Important information is stripped from the header response and used as
 * desired. 
 */
void serveClient(std::istream& stream, std::ostream& os, 
        AccountList& accounts) {
    //  We want to get the first Line of the HTTP request @var firstLine
    //  From there, we can grab the desired whole string of inputs
    //  as @var request, and also just the desired @var command
    std::string firstLine; std::string request; std::string command;
    //  Grabs the first line
    std::getline(stream, firstLine);
    //  Parses the junk and just grabs the path request
    std::istringstream(firstLine) >> request >> request;
    //  Make the string more palatable for inputstream parsing
    std::replace(request.begin(), request.end(), '=', ' '); 
    std::replace(request.begin(), request.end(), '&', ' ');
    //  Use @var reqStr to grab the command
    std::istringstream reqStr = std::istringstream(request);
    reqStr >> command >> command; 
    // Read and discard rest of the HTTP request headers.
    std::string dummy;
    while (std::getline(stream, dummy), dummy != "\r") {}
    //  Send over the full request, as well as the command seperate
    execTransaction(command, request, accounts, os);    
}


//  A small helper method for each of our thread to extract the
//  input and output stream from the socket
void threadMain(TcpStreamPtr client, AccountList& accounts) {
    serveClient(*client, *client, accounts);
}
/**
 * From Dr. Rao 
 * 
 * Top-level method to run a custom HTTP server to process bank
 * transaction requests using multiple threads. Each request should
 * be processed using a separate detached thread. This method just loops 
 * for-ever.
 *
 * @param server The boost::tcp::acceptor object to be used to accept
 * connections from various clients.
 */

void runServer(tcp::acceptor& server) {
    //  Generate our overall several AccountList
    AccountList accounts;
    //  To quote Dr. Campbell "Servers should not die"
    while (true) {
        //  Generate our client shared pointer for easy cleanup
        TcpStreamPtr client = std::make_shared<tcp::iostream>();
        // Wait for a client to connect
        server.accept(*client->rdbuf());
        std::thread thr(threadMain, client, std::ref(accounts));
        // Process information from client.
        thr.detach();
    }    
}

//-------------------------------------------------------------------
//  DO  NOT   MODIFY  CODE  BELOW  THIS  LINE
//-------------------------------------------------------------------

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

// Helper method for testing.
void checkRunClient(const std::string& port);

/*
 * The main method that performs the basic task of accepting
 * connections from the user and processing each request using
 * multiple threads.
 */
int main(int argc, char** argv) {
    // Setup the port number for use by the server
    const int port = (argc > 1 ? std::stoi(argv[1]) : 0);
    io_service service;
    // Create end point.  If port is zero a random port will be set
    tcp::endpoint myEndpoint(tcp::v4(), port);
    tcp::acceptor server(service, myEndpoint);  // create a server socket
    // Print information where the server is operating.    
    std::cout << "Listening for commands on port "
              << server.local_endpoint().port() << std::endl;
    // Check run tester client.
#ifdef TEST_CLIENT
    checkRunClient(argv[1]);
#endif

    // Run the server on the specified acceptor
    runServer(server);
    
    // All done.
    return 0;
}
// End of source code

