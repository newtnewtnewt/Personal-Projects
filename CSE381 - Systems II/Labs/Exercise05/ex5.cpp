// Copyright (C) 2019 raodm@miamioh.edu

#include <sys/types.h>
#include <unistd.h>
#include <sys/wait.h>

#include <iostream>
#include <string>
#include <vector>
#include <unordered_map>
#include <sstream>
#include <fstream>
#include <cstdlib>


/**
 *  Reminder regarding input file format --
 *
 *  A list of files are supplied, with 1-line per file. Each line
 *  consists of 4 tab-separated columns in the following format:
 *
 *   permissions    userID    groupID   path
 *
 * NOTE: The path is always only 1 word (no spaces).
 */

/**
 * Implement this method to print path of files that are readable by a
 * given user.
 *
 * @param is The input stream from where the permissions and other
 * information for files are to be read.  See earlier note on the file
 * format.
 *
 * @param user The userID for whom readable files are to be printed.
 */
void printReadableFilesForUser(std::istream& is, const std::string& user) {
    std::string path;
    while (is >> path) {
        if (path[0] == 'r') {
            is >> path;
            if (path == user) {
                is >> path >> path;
                std::cout << path << std::endl;
            } else {
                is >> path >> path;
            }
        } else {
            is >> path >> path >> path;
        }
    }
}

/**
 * Implement this method to print print the group that have the
 * highest number of files with execute permissions enabled for the
 * group.
 *
 * @param is The input stream from where the permissions and other
 * information for files are to be read.  See earlier note on the file
 * format.
 */
void printGroupWithMostExecutableFiles(std::istream& is) {
    std::string groupName = "";
    std::string perms = "";
    std::string highestGroupName = "";
    int highestNum = -1;
    std::unordered_map<std::string, int> storage;
    while (is >> perms) {        
        is >> groupName >> groupName;
        if (perms[5] == 'x') {
            int currentVal = storage[groupName] + 1; 
            storage[groupName] = currentVal;
            if (currentVal > highestNum) {
                highestGroupName = groupName;
                highestNum = currentVal;
            }
        }
        is >> groupName;
    }
    std::cout << highestGroupName << ": " << highestNum << std::endl;    
}

/**
 * Implement this method to execute files for which a user has execute
 * permissions. The files must be executed serially in the same order
 * they occur in the list of files.
 *
 * NOTE: There are no command-line arguments to be supplied to the
 * executable files. So execl would be simpler to use.
 *
 * @param is The input stream from where the permissions and other
 * information for files are to be read.  See earlier note on the file
 * format.
 *
 * @param user The userID for whom executable files are to be
 * executed.
 */
void execProgramForGivenUser(std::istream& is, const std::string& user) {
    std::string word;
    std::string path;
    std::string userGrab;
    std::string perms;
    while (is >> perms) {
        is >> userGrab;
        is >> path >> path;
        if (user == userGrab && path[0] == '.' && perms[0] == 'r') {
            const int pid = fork(); 
            if (pid == 0) {
                std::cout << "Running: " << path << std::endl; 
                std::vector<char*> args; args.push_back(&path[0]);
                args.push_back(nullptr);
                execvp(args[0], &args[0]);
            } else {
                waitpid(pid, nullptr, 0);
            }
        }         
    }
}

// End of source code
