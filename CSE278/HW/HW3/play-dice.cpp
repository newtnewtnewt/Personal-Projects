/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/* 
 * File:   play-dice.cpp
 * Author: newtn
 *
 * Created on October 16, 2018, 7:14 PM
 */

#include <cstdlib>
#include <iostream>
#include "dice.h"
#include <thread>
#include <chrono>
#include <vector>
using namespace std;

/*
 * 
 */

bool previousChecker(vector<PairofDice> &x, PairofDice &currentDice){
   
    for(int i = 0; i < x.size(); i++){
        if(x.at(i) == currentDice){
            return true;
        }
    }
    return false;
}
 
int main(int argc, char** argv) {
    //Introduction to the game
    cout << "Welcome to the Game" << endl;
    //Create our dice 
    PairofDice diceRollers = PairofDice();
    //Set Roll count at 0
    int numOfRolls = 0;
    
    cout << "Hit Enter to Roll the Dice! " << endl; //Introduce the roll
    cin.ignore(); //Wait for user prompt each time 
    diceRollers.roll(); //Roll the dice
    diceRollers.shows(); //Show the result of the dice
    vector<PairofDice> previousRoll; //Create a previous roll storage variable
    
    numOfRolls++; //Increment Roll 
    
    while(!diceRollers.big() && !diceRollers.small() && diceRollers.sumvalue() != 5 && !(previousChecker(previousRoll, diceRollers) )){ //While the win conditions are not met
        previousRoll.push_back(diceRollers); //Set the previous dice to the current roll
        cout << "You haven't won yet, Hit Enter to Roll Again" << endl; //Prompt the roll again
        cin.ignore(); //Prompt for enter
        diceRollers.roll(); //Roll
        diceRollers.shows(); //Show the roll results
        numOfRolls++; //Increment the roll
    }
    
    cout << "Your score is " << numOfRolls << ". Your opponent's turn!" << endl; //Print player score
    
    
    vector<PairofDice> previousRollRobot; //Create the previous robot's dice
    
    int numOfOtherRolls = 0; //Create the robot's number of rolls
    numOfOtherRolls++; //Increment the roll count
    diceRollers.roll(); //Roll the dice
    diceRollers.shows(); //Show the dice
    while(!diceRollers.big() && !diceRollers.small() && diceRollers.sumvalue() != 5 && !(previousChecker(previousRollRobot, diceRollers) )){ //Robot keeps rolling while the win conditions are not met
        previousRollRobot.push_back(diceRollers); //Set the dice to the previous roll
        std::this_thread::sleep_for (std::chrono::milliseconds(200)); //Sleep to allow for a minor pause in rolls.
        diceRollers.roll(); //Roll the dice
        diceRollers.shows(); //Show the dice
        numOfOtherRolls++; //Increment the roll count
    }
    
    //Compare the two counts and print who won 
    if(numOfRolls < numOfOtherRolls){
        cout << "You won! Your score was: " << numOfRolls << endl;
    }
    else if(numOfRolls > numOfOtherRolls){
        cout << "The Computer Won. His score was: " << numOfOtherRolls << endl;
    }
    else{
        cout << "You tied! Both of your scores were: " << numOfRolls << endl;
    }
    
    return 0;
}

