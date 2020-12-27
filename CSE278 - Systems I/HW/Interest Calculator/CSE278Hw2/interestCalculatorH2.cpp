/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/* 
 * File:   main.cpp
 * Author: newtn
 *
 * Created on September 18, 2018, 12:12 PM
 */

#include <cstdlib>
#include<fstream>
#include<iostream>
#include "Date2.h"
#include <cmath>
#include <iomanip>

using namespace std;




unsigned day_count(const Date2 & psta,const Date2 & pend){
    
    int count = 0;
    unsigned int m1, d1, y1;
    unsigned int m2, d2, y2;
    unsigned currentDay = 0;
  
    Date2 crashTest = Date2();
    crashTest = psta;
    crashTest.get(m1, d1, y1);
    
    Date2 crashTestOther = Date2();
    crashTestOther = pend;
    crashTestOther.get(m2,d2,y2);
   
        
    Date2 temp = Date2(m1, d1, y1);
    
    if(y1 == y2){
        count = crashTestOther.dayofyear() - crashTest.dayofyear(); 
        return count;
    }
       
    while(y1 != y2){
        if(m1 != 1 || d1 != 1){
            currentDay = temp.dayofyear();
            Date2 ex = Date2(12, 31, y1);
            int additiveValue = (ex.dayofyear() - currentDay);
            count = count + additiveValue;
            y1 += 1;
            m1 =1;
            d1 = 1;
        }
        else{
        currentDay = temp.dayofyear();
        Date2 ex = Date2(12, 31, y1);
        count += (ex.dayofyear());
        y1 += 1;
        m1 =1;
        d1 = 1;     
        }
    }
    count += (crashTestOther.dayofyear());
    
    return count;
    
            
}




 /******** Function Members ********/
 /***** Class constructors *****/
  Date2::Date2(){
      month = 1;
      day = 1;
      year = 2018;
  }
  /*----------------------------------------------------------------------
      Precondition:  None.
      Postcondition: Date is initialized to 01/01/2018
  -----------------------------------------------------------------------*/
 
  Date2::Date2(unsigned m , unsigned d , unsigned y ){
      int test = validate(m, d, y);
      if(test == 1){
          cout << "Invalid date chosen" << endl;
          return;
      }
      
      month = m;
      day = d;
      year = y;
  }
  /*----------------------------------------------------------------------
      Construct a class object (explicit values).

      Precondition: parameters are set
      Postcondition:  corresponding data members are initialized
   -----------------------------------------------------------------------*/


/***** Set operation *****/
  void Date2::set(unsigned m, unsigned d,  unsigned y, bool & success ){
      int test = validate(m, d, y);
      if(test == 1){
          success = 0;
          return;
      }
      month = m;
      day = d;
      year = y;
      
  }
/*------------------------------------------------------------------------
      Precondition: parameters are set
      Postcondition:  corresponding data members are initialized,
      after validation.
      success is set to 1 (true) , if successful.
*/

/***** get operation *****/
  void Date2::get(unsigned & m, unsigned &  d,  unsigned & y ){
      m = month;
      d = day;
      y = year;
  }
/*------------------------------------------------------------------------
      Precondition: None
      Postcondition:  private values are returned
*/


/***** Day of year *****/
unsigned Date2::dayofyear( ){
    int currentMonth = 1;
    int totalDays = 0;
    bool leapYear = 0;
    
    if((year % 4 == 0 && year % 100 != 0) || year % 400 == 0){
        leapYear = 1;
    }
    
    
    while(currentMonth != month){
        if(!leapYear && currentMonth == 2){
            totalDays += 28;
        } else {
            totalDays += ndays[currentMonth - 1];
        }
        currentMonth++;
    }
    
    totalDays += day;
    return totalDays;
}
/*------------------------------------------------------------------------
      Precondition: None
      Postcondition:  day of the year is returned. Must account for leap years.
*/
 
int Date2::validate(unsigned m, unsigned d,  unsigned y ){
       int ret = 0;
       bool leapYear = 0;
    
    if((year % 4 == 0 && year % 100 != 0) || year % 400 == 0){
        leapYear = 1;
    }
       
       
       if(m > 12){
           ret = 1;
       }
       
       if(ndays[m - 1] < d){
           ret = 1;
       }
       
       if(m == 2 && !leapYear && d > 28){
           ret = 1;
       }
       
       return ret;
   }
/*------------------------------------------------------------------------
      Precondition: The parameters are set.
      Postcondition:  checks for a valid date and returns code.
      ( 0 valid , 1 invalid.. you may make up other values). 
*/

/*
 * 
 */

int main(int argc, char* argv[]) {
 
    if(argc != 3){
        cout << "Program requires input and output. Try again!" << endl;
        return 0;
    }
    ifstream input;
    ofstream out; 
    
    input.open(argv[1]); 
    out.open(argv[2]);
    
    if(!input.good() || !out.good()){
        cerr << "One ouput stream is not correct";
        return 1;
    }
    while(!input.eof()){
        string inputString;
        getline(input, inputString);
        
        int m1, d1, y1;
        int m2, d2, y2;
        string Name;
        
        int emptySpace = 0;
        for(int i = 0; i < 7; i++){
            int endSpot = 0;
           
           
            endSpot = inputString.find(" ", emptySpace);
            string tempEntry = inputString.substr(emptySpace, endSpot-emptySpace);
            emptySpace  = endSpot + 1;
      
            if(i == 0){
                Name = tempEntry;
            }
            
            if(i == 1){
                
                for(int i = 0; i < tempEntry.length(); i++){
                    tempEntry[i] = tolower(tempEntry[i]);
                }
               
                if(tempEntry == "jan"){
                        m1 = 1;
                }
                 if(tempEntry == "feb"){
                        m1 = 2;
                }
                if(tempEntry == "mar"){
                        m1 = 3;
                }
                if(tempEntry == "apr"){
                        m1 = 4;
                }
                if(tempEntry == "may"){
                        m1 = 5;
                }
                if(tempEntry == "jun"){
                        m1 = 6;
                }
                if(tempEntry == "jul"){
                        m1 = 7;
                }
                if(tempEntry == "aug"){
                        m1 = 8;
                }
                if(tempEntry == "sep"){
                        m1 = 9;
                }
                if(tempEntry == "oct"){
                        m1 = 10;
                }
                if(tempEntry == "nov"){
                        m1 = 11;
                }
               if(tempEntry == "dec"){
                        m1 = 12;
                }
            }
            
            if(i == 2){
                d1 = atoi(tempEntry.c_str());
               
            }
            if(i == 3){
                y1 = atoi(tempEntry.c_str());
                 
            }
            if(i == 4){
                for(int i = 0; i < tempEntry.length(); i++){
                    tempEntry[i] = tolower(tempEntry[i]);
                }
                if(tempEntry == "jan"){
                        m2 = 1;
                }
                 if(tempEntry == "feb"){
                        m2 = 2;
                }
                if(tempEntry == "mar"){
                        m2 = 3;
                }
                if(tempEntry == "apr"){
                        m2 = 4;
                }
                if(tempEntry == "may"){
                        m2 = 5;
                }
                if(tempEntry == "jun"){
                        m2 = 6;
                }
                if(tempEntry == "jul"){
                        m2 = 7;
                }
                if(tempEntry == "aug"){
                        m2 = 8;
                }
                if(tempEntry == "sep"){
                        m2 = 9;
                }
                if(tempEntry == "oct"){
                        m2 = 10;
                }
                if(tempEntry == "nov"){
                        m2 = 11;
                }
               if(tempEntry == "dec"){
                        m2 = 12;
                }
            }
         
            
            if(i == 5){
                d2 = atoi(tempEntry.c_str());
                
            }
            if(i == 6){
                y2 = atoi(tempEntry.c_str());  
               
            }        
        }
        Date2 firstDate = Date2(m1,d1,y1);
        Date2 secondDate = Date2(m2,d2,y2);
        int daysInBetween = day_count(firstDate, secondDate);
        double multiple = pow(1.000164, daysInBetween);
        
        double finalAmount = 600 * multiple;
        cout << Name << ": " << setprecision(2) << fixed << finalAmount << endl;
        out << Name << ": " << setprecision(2) << fixed << finalAmount << endl;
        }
        
        
    
    

    input.close();
    out.close();
    return 0;
};  


