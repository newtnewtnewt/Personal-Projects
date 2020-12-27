// This program demonstrates the   function template mechanism
// and oveloading
#include <iostream>
using namespace std;

template <typename T>      //notice that the type parameter does not apply only to the parameters
T sum(T val1, T val2)
{
	return val1 + val2;
}

template <typename T>
T sum(T val1, T val2, T val3)
{
	return val1 + val2 + val3;
}

int main()
{
	float num1, num2, num3;

	cout << "Enter two values: ";
	cin >> num1 >> num2;
	cout << "Their sum is " << sum(num1, num2) << endl;
	cout << "Enter three values: ";
	cin >> num1 >> num2 >> num3;
	cout << "Their sum is " << sum(num1, num2, num3) << endl;
	return 0;
}
