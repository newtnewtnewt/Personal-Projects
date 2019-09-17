/* 
 * File:   dunnnm2_homework2.cpp
 * Author: Noah Dunn
 * Copyright (C) 2019 dunnnm2@miamiOH.edu
 * Created on September 7th, 2019 
 * 
 * This program will read in file data from two files
 * and print login-IDs of users within a group.
 */
#include <cstdlib>
#include <string>
#include <unordered_map>
#include <iostream>
#include <fstream>
#include <sstream>
#include <algorithm>

/* This method takes in 2 params, a fileName string, and a bool 
 * to determine if the user wants groups or users.
 * It filters the data according to the way MU groups and users are set up 
 * in the directories. This returns an unordered map consisting of
 * users + userids, or groups and groupids depending on what is asked
 * for by the bool.
 * 
 * 
 * */
std::unordered_map<std::string, std::string> 
    // Our constructor as described above 
    readUsers(std::string fileName, bool findingUsers) {  
    // instantiate an empty one
    std::unordered_map<std::string, std::string> uidMap = {}; 
    std::ifstream input = std::ifstream(fileName);  //  Open our desired file 
    //  Get all our default values out of the way
    std::string line = "";  //  Our whole raw data line
    // The individual token chunk we are looking at
    std::string pieceBeforeColon = "";  
    std::string uid ="";   //  The user's id that we are examining.
    std::string gids ="";  //  All the group ids per one group
    //  Begin the loop to grab each line of our password/groups files
    while (std::getline(input, line)) {
        std::replace(line.begin(), line.end(), ':', ' ');  //  In case of users
        std::replace(line.begin(), line.end(), ',', ' ');  //  In case of groups
        std::istringstream iss(line);  //  Grab a line for parsing
        std::string gidID = "";  //  One groupID to stash as a key
        
        iss >> uid;  //  Rip the userID as the first parameter
        //  When searching groups, grabs group ids
        if (!findingUsers) {  
            gids = uid + " ";
            iss >> gidID >> gidID;  //  Cycle the useless data
        } else {
            iss >> pieceBeforeColon;
        }
        
        while (iss >> pieceBeforeColon) {  // Parse through the grabbed line
            if (findingUsers) {
                 // Only grab if its the needed id
                if (isdigit(pieceBeforeColon[0])) { 
                    uidMap[pieceBeforeColon] = uid;
                    break;  // Break as soon as we have the id
                }
            } else {
                if (isdigit(pieceBeforeColon[0])) {
                    // Stack all the group ids in succession
                    gids = gids + pieceBeforeColon + " ";
                }
            }
        }
        if (!findingUsers) {
            uidMap[gidID] = gids;  // Throw our desired results in the group map
        }            
    }
    input.close();
    
    return uidMap;    
}
/* Output generation does the remainder of the heavy lifting for
 * our program, grabbing data from both unordered_maps and spitting it out
 * in the desired format
 * 
 */


void outputGeneration(int argc, char** argv,  
    std::unordered_map<std::string, std::string> userList,
    std::unordered_map<std::string, std::string> groupList) {
    std::istringstream iss; std::string fullOut = ""; 
    for (int i = 1; i < argc; i++) {  //  We need to run through all args
        //  Check if the group exists
        if ((groupList.find(argv[i]) == groupList.end())) { 
            std::cout << argv[i] << " = " << "Group not found." << std::endl;
        } else {
            //  Stash the group we are looking at 
            std::string gid = argv[i]; 
            //  And a temp variable for a user in that group
            std::string uid = ""; 
            //  Build a string stream with our long group string
            std::istringstream iss(groupList[gid]); 
            //  Grab the UID 
            iss >> uid; 
            //  Begin formatting the print
            std::cout << gid << " = " << uid << ":";    
            //  Grab all the associated users
            while (iss >> uid) { 
                  fullOut = fullOut + " " + userList[uid] + "(" + uid + ")";
            } 
            //  Print the full string, and reset it
            std::cout << fullOut << std::endl;  fullOut = "";
        }
    }      
}
/*
 * The main method exists solely to build our user dictionary and 
 * group dictionary, as well as load the printing output function 
 * for doing those two things.
 *
 *
 */
int main(int argc, char** argv) {
    std::unordered_map<std::string, std::string> userList = 
            readUsers("passwd", true);
    std::unordered_map<std::string, std::string> groupList =
            readUsers("groups", false);
    outputGeneration(argc, argv, userList, groupList);
    return 0;
}

