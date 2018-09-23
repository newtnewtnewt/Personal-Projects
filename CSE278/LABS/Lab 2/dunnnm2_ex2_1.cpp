/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


#include <iostream>
#include <string>

using namespace std;

int main(){
    string input; 
    int length;
    string firstCharacter;
    string lastCharacter;
    string firstWord;
    string lastWord;
    
    cout << "Enter a line: " ;
    getline(cin, input);
    
    length = input.length();
    int firstBlank = input.find(" ");
    int lastBlank = input.rfind(" ");
    
    firstCharacter = input.substr(0, 1);
    lastCharacter = input.substr(length-1);
    firstWord = input.substr(0, firstBlank);
    lastWord = input.substr(lastBlank + 1);
    
    cout<< "Length of Line: " << length << "\n";
    cout<< "First Character: " << firstCharacter << "\n";
    cout<< "Last Character: " << lastCharacter << "\n"; 
    cout<< "First word: " << firstWord << "\n";
    cout<< "Last word: " << lastWord << "\n"; 
    
    return 0;
   
}
