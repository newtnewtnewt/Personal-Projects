/*
 * Copyright 2019 Noah Dunn
 * 
 * 
 * 
 * */
#include <unistd.h>
#include <sys/wait.h>
#include <iostream>
#include <vector>
#include <string>
#include <iomanip>
#include <algorithm>


using StrVec = std::vector<std::string>;

//  pulled straight off Dr. Rao's slides
void myExec(StrVec argList) {
    std::vector<char*> args;  // list of pointers to each word
    for (auto& s : argList) {
        args.push_back(&s[0]);  // address of 1st character in string
    }
    // nullptr is very important
    args.push_back(nullptr);
    // Make execvp system call to run desired process
    execvp(args[0], &args[0]);
}

/**
 * The main method runs a fixed program as a separate child process
 * and waits for it to finish.
 */
int main() {
    // Create a child process.
    const int pid = fork();
    
    if (pid == 0) {
        //  Run the desired command
        myExec({"ls", "-l", "/usr"});
    } else {
        //  Wait for the parent to run 
        waitpid(pid, nullptr, 0);
    }
    return 0;
}

