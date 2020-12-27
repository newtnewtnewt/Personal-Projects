/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/* 
 * File:   main.cpp
 * Author: newtn
 *
 * Created on October 16, 2018, 6:12 PM
 */

#include <cstdlib>
#include <iostream>
#include <cstdlib>
#include <ctime>
#include "dice.h"



using namespace std;
    //Creates private face variables for the dice rolls
    int face1;
    int face2;

    PairofDice::PairofDice(){ //Ties the Random Number Generator to the CPU clock at a given time 
        
       srand (time(NULL));
    }
    
    int * PairofDice::first(){ //Returns a pointer to the value of the first face rolled.
        return &face1;
    }   
    
    int PairofDice::getval1(){ //Returns the value of the first face rolled.
        return face1;
    }  
    
    int PairofDice::getval2(){ //Returns the value of the second face rolled.
        return face2;
    } 

    void PairofDice::roll(){ //Utilizes a random number generator to generate the values for both die faces.
        face1  = rand()%6 + 1;
        face2 = rand()%6 + 1;
    }
    void PairofDice::shows(){ //Displays the values of both dice 
        cout << face1 << " " << face2 << endl; 
    }

    int PairofDice::sumvalue(){ //Return the sum of both die faces
        return face1 + face2;
    }
    bool PairofDice::big(){ //Returns if both die faces are equal to 6
        return (face1 == face2 && face1 == 6);
    }
    bool PairofDice::small(){//Returns if both die faces are equal to 1
        return (face1 == face2 && face1 == 1);
    }

    void PairofDice::operator = (PairofDice rhs){ 
        //Sets the PairofDice on the left side of the operand equal to that 
        //of the right side of the operand.
        face1 = rhs.getval1();
        face2 = rhs.getval2();
    }
    bool operator == (PairofDice &set1, PairofDice &set2){
        
        //Checks if two PairofDice possess the same dice, regardless of order.
      if((set1.getval1() == set2.getval1() && set1.getval2() == set2.getval2()) ||(set1.getval1() == set2.getval2() && set1.getval2() == set2.getval1())){
          return true;
      }
      return false;
    }
    


    


