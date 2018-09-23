// This program displays the number of days in each month. It uses an
// array of string objects.
// and  hold the number of days in each month. Both are initialized with 
// initialization lists at the time they are created.

#include <iostream>
#include <iomanip>
#include <string>
using namespace std;

int main()
{
	string month[12] = {"January", "February","March",   "April", 
	                     "May", "June",  "July",  "August",
			  "September","October", "November","December"};

	int days[12] = {31, 28, 31, 30, 
        	       31, 30, 31, 31, 
                      30, 31, 30, 31};

//  the number of days in each month is displayed.
//
	for (int count = 0; count < 12; count++)
	{
		cout << setw(9) << left << month[count] << " has ";
		cout << days[count] << " days.\n";
	}
	return 0;
}
