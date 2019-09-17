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


#ifndef DUNNNM2_HW3_H
#define DUNNNM2_HW3_H

#include <unordered_map>
#include <string>

/* Class ProcessTrees satisfies the class usage
 * requirement for this assignment and includes a constructor,
 * ProcessTrees() which takes no requirements. readFiles(), takes two
 * unordered double string maps as parameter as well as a string fileName 
 * to read from. printInfo takes the desired id to lookup in addition to
 * two maps of the same design. A private recursion step taking the same
 * parameters as print info is also included.
 * */
class ProcessTrees {
    public:
        ProcessTrees();
        void readFiles(std::unordered_map<std::string, std::string> 
            &processToParent, std::unordered_map<std::string, std::string> 
            &processToCommand, std::string fileName);
        void printInfo(std::string id, 
            std::unordered_map<std::string, std::string>& 
            processToParent, std::unordered_map <std::string, std::string>& 
            processToCommand);
    private:
        void printRecursion(std::string id, 
            std::unordered_map<std::string, std::string>& 
            processToParent, std::unordered_map <std::string, std::string>& 
            processToCommand);
};
#endif /* DUNNNM2_HW3_H */

