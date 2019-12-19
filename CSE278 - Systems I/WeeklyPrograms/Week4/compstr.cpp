// This program uses the return value of strcmp to alphabetically
// sort two strings entered by the user.
#include <iostream>
#include <cstring>   // <string>  may  work too
using namespace std;

int main()
{
	char name1[30], name2[30];

	cout << "Enter a name (last name first): ";
	cin.getline(name1, 30);
	cout << "Enter another name: ";
	cin.getline(name2, 30);
	cout << "Here are the names sorted alphabetically:\n";
	if (strcmp(name1, name2) < 0)
		cout << name1 << endl << name2 << endl;
	else if (strcmp(name1, name2) > 0)
		cout << name2 << endl << name1 << endl;	
	else
		cout << "You entered the same name twice!\n";
	return 0;
}

