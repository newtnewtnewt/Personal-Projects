/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/* 
 * File:   main.cpp
 * Author: newtn
 *
 * Created on November 1, 2018, 7:10 PM
 */


#include "278sqla.h"
#include <fstream>
#include <vector>
using namespace std;

/*
 * 
 */
int main(int argc, char** argv) {
    


    
    std::ofstream output; //Create the output stream
   
    
    output.open("nsa-mi6.txt"); //Open the output stream
    
    if (!output.good()) { //Checks to makesure the output stream is good
        std::cout << "Error opening input or output streams.";
    }

    if (argc != 2) { //Make sure that argument amount is correct
        std::cout << "Specify token and output file Name";
        return 1;
    }

    string token= argv[1];  //Stores the token as a string
    
    if(token != "dddddd"){ //If the token is not the default token, it converts the token to a usable one
        token = get_token(argv[1]); 
    }
    
    string query = "SHOW TABLES;"; //Start Query grabs the table names
    vector<string> tableTitles; //Creates a vector to store the table names
    
    if (connect_query(0, &token[0], &query[0]) == 0) { //Insures Connection is Actually Achieved
        connect_query(1, &token[0], &query[0]); //Activates the first query
        while (*results != "") { //Runs until the end of the stored results.
            tableTitles.push_back(*results); //Stores the given Table Name in the Vector
            results++; //Increments the index to grab the next Table Name
        }
        for(auto x: tableTitles){ //For Each Loop to grab each table name
            query = "DESCRIBE " + x; //Calls the mySQL Describe query on each table 
            connect_query(1, &token[0], &query[0]); //Actually uses the query written
            string schema = x + "("; //Opens the saved schema
            while(*results != ""){ //Runs until the schema is out of items
                schema = schema + *results + ", "; //Append each schema item to the larger schema string 
                results++; //Increment to the next entry in the schema
            }
                schema = schema.substr(0, schema.length()-2); //Cleans the String of the space and end comma
                schema = schema + ")"; //Closes the Schema
                output << schema << endl; //Prints schema to the next line 
        }
   
    }
    else{
        cout << "Token entered is not correct. Try again!" << endl; //Error if token is incorrect 
    }
    return 0;
}

