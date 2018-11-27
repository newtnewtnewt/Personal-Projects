/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/* 
 * File:   main.cpp
 * Author: newtn
 *
 * Created on November 17, 2018, 1:42 PM
 */

#include <cstdlib>
#include <fstream>
#include <iostream>
#include <string>
#include <vector>
#include <iomanip>
using namespace std;

/*
 * 
 */
string typeGrabber(string token){
    /*
     
     This method is an asssistant method that grabs a string and assesses its function 
     * based on Dr. Fabio's specifications.
     
     */
    if(token == "+" || token == "-" || token == "/" || token == "*"){ //"Op" Operators
      return "op";  
    }
    else if(token == "!=" || token == "==" || token == "<=" || token == ">=" || token == "<=" || token == "<" || token == ">"){ //Relops or Relational Operators
        return "relop";
    }
    else if(token == ";" || token == "," || token == "."){//Punctuation Documentation
        return "punct";
    }
    else if(token == "{" || token == "("){ //Left Delimeters
        return "ldelim";
    }
    else if(token == "}" || token == ")" ){//Right Delimeters
        return "rdelim";
    }
    else if(token == "if" || token == "while" || token == "do" || token == "switch"){ //Conditional Operators
        return "keywd";
    }
    else if(token == "short" || token == "int" || token == "long"){ //Data types 
        return "dtype";
    }
    else if(token == "="){ //Assignment operator
        return "assig"; 
    }
    else{ //This block checks if the potential token is either valid as a variable name, or invalid as a data type
        for(int i = 0; i < token.length(); i++){
            if(!isalnum(token[i]) || !islower(token[i])){
                return "error";
            }
        }
        return "ident";
    }
   
    
};
bool isValid(vector<string> sampleVec){ //This methods checks if a piece of inputted code is valid based on Brackets
    int leftP = 0;
    int rightP = 0;
    int leftB = 0;
    int rightB = 0;
    for(int i = 0; i < sampleVec.size(); i++){ 
        //Iterates through every stored variable in the vector and checks if the brackets/parenthesis are balanced
        if(sampleVec[i] == "("){
            leftP++;
        }
        else if(sampleVec[i] == ")"){
            rightP++;
        }
        else if(sampleVec[i] == "{"){
            rightB++;
        }
        else if(sampleVec[i] == "}"){
            leftB++;
        }
    }
    return (rightP == leftP && rightB == leftB); //Returns true if both sets of brackets are equally matched
    
};

int dataSize(string type){ //Returns a specified data type value depending on the byte size of the data
    if(type == "long"){
        return 8;
    }
    else if(type == "int"){
        return 4;
    }
    else{
        return 2;
    } 
};


int main(int argc, char** argv) {
    //Generate the two output streams for the first part of this assignment 
    ifstream input;
    ofstream output;
    input.open(argv[1]);
    //Stream is opened, now we grab the file names for use with output.
    string name = string(argv[1]);
    string modName = "";
    int index = 0;
    while(name[index] != '.' && index != name.size()){
        modName = modName + name[index];
        index++;
    }
    name = modName;
    
    //Generate the first output file
    output.open("" + name + "-lex.txt");
    
    //Check to make sure the command line input is correct
    if(argc != 2){
      cout << "You should simply input one file name as input";
      return 0;
    }
    //Create two vectors, one for tokens and another in parallel for the type of those tokens
    vector<string> typeVector;
    vector<string> tokenVector;
    
    //Run until we hit the end of the file
    while(input.peek() != EOF){
        //Grab each piece as a token and save it as a string 
        string token;
        input >> token;
        //If the token is ever an invalid space, we assume the program cannot run 
        if(token == " "){
            cout << "Error: Program was not capable of ending, use a \".\" to indicate End of Program" << endl;
            output << "Error: Program was not capable of ending, use a \".\" to indicate End of Program" <<endl;
            return 0;
        }
        //Place each token into the token Vector
        tokenVector.push_back(token);
        if(typeGrabber(token) != "error"){
            typeVector.push_back(typeGrabber(token));
        }
        //If the token is not a valid type, Reject it with a lexing error
        else{
            cout << "Lexing error at token: " << token <<  endl;
            output << "Lexing error at token: " << token <<  endl;  
            return 0;
        }
        if(token == "."){
            break;
        }
        
    }
    //Check if the brackets/Parenthesis are balanced, if they are not print an error 
    bool valid = isValid(tokenVector);
    if(!valid){
        cout << "Error: Number of Brackets is not valid" << endl;
        output << "Error: Number of Brackets is not valid" << endl;
        return 0;
    }
    //Print the lexer table to the -lex.txt file 
    for(int i = 0; i < typeVector.size(); i++){
        output << left << setw(15) << tokenVector[i];
        output << typeVector[i] << endl;
    }
    //Close the old Stream
    output.close();
    //Open the new stream for the symbol table
    
    output.open("" + name + "-sym.txt");
    
    int address = 0;
    
    
    for(int i = 0; i < typeVector.size(); i++){ //This loop generates A symbol table
        if(typeVector[i] == "dtype" && i + 1 != typeVector.size() && typeVector[i + 1] == "ident"){ //If we hit a datatype followed by an identifier we process 
            //Header of the hex digit is 0x
            string formatDress = "0x";
            //Convert the current address to hex
            stringstream hexStream;
            hexStream << hex << address;
            //Convert the stringStream to a usable string 
            string hexStreamString = hexStream.str();
            
            //Use a variable to determine how many 0s are necessary in the address
            int formatL = hexStreamString.size();
            if(formatL == 1){
                formatDress = formatDress + "000";
            }
            else if(formatL == 2){
                formatDress = formatDress + "00";
            }
            else if(formatL == 3){
                formatDress = formatDress + "0";
            }
            //Append the address to the 0x with 0s
            formatDress = formatDress + hexStreamString;
            
            //Calculate the necessary space to add to memory
            int addValue = dataSize(tokenVector[i]);
            //Print if this is the first entry in the token vector 
            if(i == 0){
              output << left << setw(10) << tokenVector[i + 1];
              output << left << setw(10) << formatDress;
              output << left << setw(10) << addValue << endl;
              address = address + addValue;
            }
            //Check for duplicates in the token vector
            for(int j = 0; j < i; j++){
                //Only print originals
                if(j == i - 1 && tokenVector[j] != tokenVector[i]){
                  output << left << setw(10) << tokenVector[i + 1];
                  output << left << setw(10) << formatDress;
                  output << left << setw(10) << addValue << endl;
                  address = address + addValue;
                  
                }
                else if(tokenVector[j] == tokenVector[i]){
                    break;
                }
            }
           
        }
        //Another example of a potential data type Syntax error
        else if(typeVector[i] == "dtype" && (i + 1 == typeVector.size() || typeVector[i + 1] != "ident")){
            cout << "Error: Data Type declared with no variable name";
            output << "Error: Data Type declared with no variable name";
            return 0;
        }
                  
    }
    
    
    
   
    return 0;
};

