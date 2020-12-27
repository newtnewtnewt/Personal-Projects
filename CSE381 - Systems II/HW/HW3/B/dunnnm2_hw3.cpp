/* 
 * File:   dunnnm2_homework2.cpp
 * Author: Noah Dunn
 * Copyright (C) 2019 dunnnm2@miamiOH.edu
 * Created on September 13th, 2019 
 * 
 * This program will read in file input related to the ps command in linux
 * and output a process tree of process ids, parent ids, and commands 
 * corresponding to those.
 */

#include "dunnnm2_hw3.h"
#include <cstdlib>
#include <fstream>
#include <iostream>
#include <string>
#include <sstream>

/*This empty constructor is simply to be able to call the methods in main*/
ProcessTrees::ProcessTrees(){};

/* This method uses the two already loaded maps and a string id to print 
 * initial column information and begin the recursive step printRecursion()
 * 
 **/
void ProcessTrees::printInfo(std::string id,
    std::unordered_map<std::string, std::string>& 
    processToParent, std::unordered_map <std::string, std::string>& 
    processToCommand) { 
    //  Prints our required header information
    std::cout << "Process tree for PID: " << id << std::endl;
    std::cout << "PID\tPPID\tCMD" << std::endl;
    //  Begin the recursion
    printRecursion(id, processToParent, processToCommand);
}
/* Our recursive step is designed to burrow to the very bottom, 
 * and then prints from bottom up, as is desired for the top-down perspective.
 * Our base case is when the id matches 1, and we burrow if that is not the 
 * case
 *
 */
void ProcessTrees::printRecursion(std::string id, 
    std::unordered_map<std::string, std::string>& 
    processToParent, std::unordered_map <std::string, std::string>& 
    processToCommand) {
    //  1 indicates we have hit the end of the process tree
    if (id == "1") {
        //  Print the final process first, and spring the recursion back up
        std::cout << id << "\t" << processToParent[id] << "\t " << 
                processToCommand[id] << std::endl;
        return;
    } else {
        //  We call the recursion first to burrow deeper with the parent id
        //  using processToParent[id], before printing at the current level
        //  and springing back up
        printRecursion(processToParent[id], processToParent, processToCommand);
        std::cout << id << "\t" << processToParent[id] << "\t " <<
                processToCommand[id] << std::endl; 
    }    
}

/* readFiles loads all information from the given file into 
 * two maps, one which stores processid -> parentprocessid relationship
 * and the other which stores processid -> command relationship,
 * this is all read in from fileName
 *
 */
void ProcessTrees::readFiles(std::unordered_map<std::string, std::string>& 
    processToParent, std::unordered_map <std::string, std::string>& 
    processToCommand, std::string fileName) { 
    //  Load our file with an ifstream 
    std::ifstream input = std::ifstream(fileName);
    //  Get our storage variables set up 
    std::string line = ""; std::string pid = "";
    std::string ppid = ""; std::string cmd = "";
    //  Get rid of the first line of garbage
    std::getline(input, line);
    //  Run while we still have lines to go
    while (std::getline(input, line)) {
        //  Convert our line into a string stream
        std::istringstream iss(line);
        //  Store only the columns we want, filtering all other garbage.
        iss >> pid >> pid >> ppid >> cmd >> cmd >> cmd >> cmd;
        //  Due to multipart commands in linux, this concatenates 
        //  the final column
        std::string pieceMeal = ""; cmd = "";
        iss >> pieceMeal; cmd += pieceMeal;
        while (iss >> pieceMeal) {
            cmd += " " + pieceMeal;
        }
        //  Store in the seperate maps
        processToParent[pid] = ppid;
        processToCommand[pid] = cmd;
    }
}
/*
 * The main method creates our unordered maps for storage,
 * instantiates our chosen object to call methods from
 * and then calls those desired methods with the arguments 
 * defined above
 *
 *
 */
int main(int argc, char** argv) {  
    std::unordered_map<std::string, std::string> proToPar = 
            std::unordered_map<std::string, std::string>();
    std::unordered_map<std::string, std::string> proToCommand = 
            std::unordered_map<std::string, std::string>();
    ProcessTrees pt = ProcessTrees();
    pt.readFiles(proToPar, proToCommand, argv[1]);  
    pt.printInfo(argv[2], proToPar, proToCommand);
}


