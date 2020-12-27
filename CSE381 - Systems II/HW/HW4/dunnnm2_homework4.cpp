/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/* 
 * File:  dunnnm2_homework4.cpp
 * Copyright 2019 Noah Dunn 
 *
 * Created on September 20, 2019, 7:24 PM
 */

#include <unistd.h>
#include <sys/wait.h>
#include <cstdlib>
#include <string>
#include <fstream>
#include <iostream>
#include <vector>
#include <iomanip>


/* Almost this entire block was ripped off Dr. Rao's slides */

using StrVec = std::vector<std::string>;

/*Provided a command list, this method will execute linux commands 
 formatted as required by execvp*/
void myExec(StrVec argList) {
    std::cout << "Running:";  
    //  This stores the linux commands in their new format
    std::vector<char*> args; 
    //  Does the prints and stores inputs as C-strings 
    for (auto& s : argList) { 
        std::cout << " " << s; 
        //  Stores a pointer to the beginning of each string
        args.push_back(&s[0]); 
    }
    std::cout << std::endl; 
    //  All commands are terminated with nullptr
    args.push_back(nullptr);  
    //  Executes the command on the shell
    execvp(args[0], &args[0]); 
}
/* This method takes unformatted linux commands and runs them through
 * the method myExec to format them.
 * The body then forks off a parent process and executes it.
 * If set to SERIAL mode, it will wait on the parent process before ending,
 * otherwise it will allow all processes to run if set to PARALLEL,
 * to be waited on later 
 */
int createFork(std::vector<std::string> args, std::string mode) {
    const int pid = fork();  //  Call the parent->child split and store the pid
    if (pid == 0) {  //  If it's the child, run the process
        //  Run the desired command
        myExec(args);  //  Go through the format + run pipeline
        return pid;   //  This is throw away 
    } else {
        //  Wait for the parent to run if running in parallel
        if (mode == "SERIAL") {
            int exitValue = 0; 
            waitpid(pid, &exitValue, 0);  //  Pass in the exit parameter
            std::cout << "Exit code: " << exitValue << std::endl;
        }
        return pid;  
    }
}
/* This takes an input stream in the case of either SERIES or PARALLEL
 * runs, and either allows createFork to handle all the heavy lifting 
 * in the case of SERIES, or calls all the waits in the case of PARALLEL
 */
void lineProcessing(std::ifstream& input, std::string str) {
    std::string line;  //  Each line of the shell file
    std::vector<int> pids;  //  Where we store pids for parallel processes
    while (std::getline(input, line)) {  //  Grab the lines one by one
        //  If the lines are comments, ignore
        if (line[0] == '#' || line == "") {  
            continue;
        // Execute exit if prompted
        } else if (line.substr(0, 4) == "exit") {
            break;
        } else {
            std::vector<std::string> args; std::string argument; 
            std::istringstream iss(line);
            //  Store each command per line
            while (iss >> std::quoted(argument)) {
                args.push_back(argument);
            }
            //  Execute process with formatted args
            int pid = createFork(args, str);  
            //  Store child processes pids
            if (pid != 0) { pids.push_back(pid); }  
        }
    }
    //  Only execute the waits here if we are running in parallel
    if (str == "PARALLEL") {  
        for (auto& pid : pids) {
            int exitValue = 0;   //  How we store the exit codes
            waitpid(pid, &exitValue, 0);
            std::cout << "Exit code: " << exitValue << std::endl;
        }
    }
}
/* This is the bulk of the console run, which process es inputs 
 * and ignores # lines and exits with the exit command.
 * This also determines if a SERIAL or PARALLEL command has been run, and 
 * forwards that over to the parameterized command.
 * 
 * 
 */
void lineProcessing() {
    std::string line; 
    //  Prompt stanard console
    while (std::cout << "> ", std::getline(std::cin, line)) { 
        //  Ignore user inputted lines
        if (line[0] == '#' || line == "") { continue; }  
        else if (line.substr(0, 4) == "exit") {  //  Exit the shell
            break;
        } else {
            std::vector<std::string> args; std::string argument; 
            std::istringstream iss(line);
            //  Store command line from user
            while (iss >> std::quoted(argument)) {  
                args.push_back(argument);
            }
            //  Check if one of our custom baked commands is being run
            if (args[0] == "SERIAL" || args[0] == "PARALLEL") {  
            //  Open our desired file
                std::ifstream in = std::ifstream(args[1]);
                if (in.good()) {  //  Check if it's legit
                    //  Call our custom baked messages
                    if (args[0] == "SERIAL")
                        lineProcessing(in, "SERIAL");
                    else
                        lineProcessing(in, "PARALLEL");
                }
            } else {  //  Run our non-weird commands;
                createFork(args, "SERIAL");
            }
        }
    }
}

//  The main method simply facilitates the beginning of the console execution
int main(int argc, char** argv) {
    lineProcessing();
    return 0;
}

