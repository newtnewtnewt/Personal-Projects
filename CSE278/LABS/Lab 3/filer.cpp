// This program uses a loop to read and display all the numbers in a
// file. The ifstream eof member function is used to control the loop.

#include <iostream>
#include <fstream>
using namespace std;

int main()
{
   int number;
   ifstream inputFile;

    //  TODO create accumulator counter here, initialized to 0
  

      //  TODO Open the file here. use the method open( const char* ) with
      // your ifstream object. Ex open("myF")

        if (!inputFile)				         // Test for errors
		cout << "Error opening file.\n";
	else
	{   inputFile >> number;	      // Read the first number
		while (!inputFile.eof())      // While read was good; no eof yet
        	{
         // TODO Increase counter 
	          cout << number << "   ";      // Display the number
	          inputFile >> number;          // Read the next number
                 }
	   cout << endl;

        // TODO  display the  counter.
      
       inputFile.close();               // Close the file
	}
	return 0;
}
