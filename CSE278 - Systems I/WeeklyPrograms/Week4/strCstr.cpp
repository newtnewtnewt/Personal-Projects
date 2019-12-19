
/* 
 * File:   main.cpp
 * Author: fguerino
 *
 * Created on September 4, 2018, 3:11 PM
 */

#include <cstdlib>
#include <iostream>
#include <string>
#include<cstring>
using namespace std;

/*
 * 
 */
int main(int argc, char** argv) {
    
    char st[20];
    string str;
    int number_friends = 5;
    
    printf("Goodbye, dear %n friends", &number_friends);
    cout<< "Enter your message:" << endl;
    
    getline(cin, str);
    
    strcpy(st, str.c_str());
    printf("Your message was %s \n",st );
    
    return 0;
}

