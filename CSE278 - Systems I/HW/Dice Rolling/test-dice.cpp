///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//
///* 
// * File:   test-dice.cpp
// * Author: Noah Dunn
// * Class: CSE 278 
// * 
// *
// * Created on October 16, 2018, 6:21 PM
// */
//
#include <cstdlib>
#include "dice.h"
#include <iostream>

using namespace std;

  

    int main(int argc, char** argv) {
        //Create two sets of dice
        PairofDice firstPair = PairofDice();
        PairofDice secondPair = PairofDice();
        //Roll the first pair and set the second pair equal to the first
        firstPair.roll();
        secondPair = firstPair; 

        //Print both sets of dice to show they are the same
        cout << "First set: " << endl;
        firstPair.shows();
        cout << "Second set: " << endl;
        secondPair.shows(); 
        
        
        //Establish the console output to true/false
        cout.setf(std::ios::boolalpha);
        cout << "Comparing firstPair == secondPair" << endl;
        bool checker = (firstPair == secondPair);
        //Check if the two sets of dice are the same
        cout << checker << endl;
        
        firstPair.roll();
        secondPair.roll();
        cout << "First set: " << endl;
        firstPair.shows();
        cout << "Second set: " << endl;
        secondPair.shows(); 
        
        
        //Establish the console output to true/false
        cout.setf(std::ios::boolalpha);
        cout << "Comparing firstPair == secondPair" << endl;
        checker = (firstPair == secondPair);
        //Check if the two sets of dice are the same
        cout << checker << endl;
        
        
        
        
        //Roll some dice to show that it rolls different numbers
        cout << "A few more sample rolls!" << endl;
            PairofDice newRoll = PairofDice();
            newRoll.roll();
            newRoll.shows();
            newRoll.roll();
            newRoll.shows();
            newRoll.roll();
            newRoll.shows();
            newRoll.roll();
            newRoll.shows();


        return 0;



    }
      
            
