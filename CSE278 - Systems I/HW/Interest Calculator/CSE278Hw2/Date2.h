/*-- Date2.h ---------------------------------------------------------------
 
  Homework  2    --  CSE-278  Systems I
 
  This header file defines the data type Date2
  Basic operations are:
     Default constructor -- 
     Explicit-value constructor (does not validate)
     set      To sets a date with validation
     get      retrieve a date
     dayofyear  returns the day of the year.

-------------------------------------------------------------------------*/

#include <iostream>
using namespace std;

#ifndef TIME
#define TIME

//  number of days in months
static int ndays[]={31,29,31,30,31,30, 31,31,30,31,30,31};

class Date2
{
 public:
 /******** Function Members ********/
 /***** Class constructors *****/
  Date2();
  /*----------------------------------------------------------------------
      Precondition:  None.
      Postcondition: Date is initialized to 01/01/2018
  -----------------------------------------------------------------------*/
 
  Date2(unsigned m , unsigned d , unsigned y );
  /*----------------------------------------------------------------------
      Construct a class object (explicit values).

      Precondition: parameters are set
      Postcondition:  corresponding data members are initialized
   -----------------------------------------------------------------------*/


/***** Set operation *****/
  void set(unsigned m, unsigned d,  unsigned y, bool & succes );
/*------------------------------------------------------------------------
      Precondition: parameters are set
      Postcondition:  corresponding data members are initialized,
      after validation.
      succes is set to 1 (true) , if succesful.
*/

/***** get operation *****/
  void get(unsigned & m, unsigned &  d,  unsigned & y );
/*------------------------------------------------------------------------
      Precondition: None
      Postcondition:  private values are returned
*/


/***** Day of year *****/
unsigned dayofyear( );
/*------------------------------------------------------------------------
      Precondition: None
      Postcondition:  day of the year is returne. Must account for leap years.
*/


 private:
   int validate(unsigned m, unsigned d,  unsigned y );
/*------------------------------------------------------------------------
      Precondition: The parameters are set.
      Postcondition:  checks for a valid date and returns code.
      ( 0 valid , 1 invalid.. you may make up other values). 
*/


 /********** Data Members **********/
   unsigned  month;
   unsigned  day;
   unsigned  year;

}; // end of class declaration



#endif
