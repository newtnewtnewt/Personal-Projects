/* 
 * File:   main.cpp
 * Author: dunnnm2
 *
 * Copyright 2019 Noah Dunn
 */
#include <ext/stdio_filebuf.h>
#include <unistd.h>
#include <sys/wait.h>
#include <cstdlib>
#include <iostream>
#include <cstring>
#include <vector>
#include <iomanip>
#include <string>
#include <fstream>

const int READ = 0;
const int WRITE = 1;

void vectorLoad(std::vector<std::string> &command1, 
    std::vector<std::string> &command2, std::string file ) { 
    std::ifstream is(file);
    std::string parameter; bool onOne = true;
    while (is >> std::quoted(parameter)) { 
        if (parameter == "|") {
            onOne = false;
        } else if (onOne) {
            command1.push_back(parameter);
        } else {
            command2.push_back(parameter);
        }
    }
}

void myExecConvert(std::vector<std::string> command1, 
        std::vector<std::string> command2,
        std::vector<char*> &argList1,
        std::vector<char*> &argList2) {
    //  This stores the linux commands in their new format

    //  Does the prints and stores inputs as C-strings 
    for (auto& s : command1) { 
        argList1.push_back(&s[0]); 
    }
    for (auto& x : command2) { 
        argList2.push_back(&x[0]); 
    }
    argList1.push_back(nullptr); 
    argList2.push_back(nullptr);  
}


int main(int argc, char** argv) {
    std::vector<std::string> cmd1, cmd2; 
    vectorLoad(cmd1, cmd2, argv[1]);
    std::vector<char*> command1, command2; 
    myExecConvert(cmd1, cmd2, command1, command2);
    
    int pipefd[2]; 
    pipe(pipefd); 
    
    int pid2, pid1 = fork();
    if (pid1 == 0) {
        close(pipefd[READ]); 
        dup2(pipefd[WRITE], WRITE);
        execvp(command1[0], &command1[0]);
    } else { 
        pid2 = fork();
        if (pid2 == 0) {
            close(pipefd[WRITE]); 
            dup2(pipefd[READ], READ);
            execvp(command2[0], &command2[0]);
        }        
    }
    waitpid(pid1, nullptr, 0); 
    close(pipefd[1]); 
    waitpid(pid2, nullptr, 0);
    return 0;
}

