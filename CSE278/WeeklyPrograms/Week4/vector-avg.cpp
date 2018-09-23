// This program demonstrates the vector's empty member function.

#include <iostream>
#include <vector>
using namespace std;

// Function prototype
float avgVector(vector<int>);

int main()
{
	vector<int> values;
	int numValues;
	float average;

	cout << "How many values do you wish to average? ";
	cin  >> numValues;
	for (int count = 0; count < numValues; count++)
	{	int tempValue;

		cout << "Enter a value: ";
		cin  >> tempValue;
		values.push_back(tempValue);
	}
	average = avgVector(values);
	cout << "Average: " << average << endl;
	return 0;
}

//*************************************************************
// Definition of function avgVector                           *
// This function accepts an int vector as its argument. If    *
// the vector contains values, the function returns the       *
// average of those values. Otherwise, an error message is    *
// displayed and the function returns 0.0.                    *
//*************************************************************

float avgVector(vector<int> vect)
{
	float total = 0.0;  // accumulator
	float avg = 0.0;    // average

	if (vect.empty())	  // Determine if the vector is empty.
   	cout << "No values to average.\n";
	else
	{	for (int count = 0; count < vect.size(); count++)
			total += vect[count];
		avg = total / vect.size();
	}
	return avg;
}
