/**
 * This source file has the main method that facilitates UNIT testing
 * the various methods based on command-line arguments.
 *
 * NOTE: -------  DO  NOT  MODIFY   THIS  FILE  --------
 *
 * Copyright (C) 2019 raodm@miamioh.edu
 */

#include <string>
#include <fstream>
#include <functional>

// Prototype declarations for the methods we will be testing.
void printReadableFilesForUser(std::istream& is, const std::string& user);
void printGroupWithMostExecutableFiles(std::istream& is);
void execProgramForGivenUser(std::istream& is, const std::string& user);


/**
 * The main method uses command-line arguments and calls the method
 * under test.
 */
int main(int argc, char *argv[]) {
    std::string cmd = argv[1];  // The test to be run.
    std::ifstream is(argv[2]);  // Input with list of file information
    // Perform the appropriate test
    if (cmd == "question1") {
        printReadableFilesForUser(is, argv[3]);
    } else if (cmd == "question2") {
        printGroupWithMostExecutableFiles(is);
    } else {
        execProgramForGivenUser(is, argv[3]);
    }
    // All done
    return 0;
}

// End of source code
