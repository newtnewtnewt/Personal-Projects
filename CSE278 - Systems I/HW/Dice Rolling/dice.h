//  *****    cse-278     Homework  3     **********
// 
//    PairofDice header file (dice.h)
//
//  You may add more methods to this class for testing purposes. 
//  

class PairofDice {

	private:
		int face1;        // outcomes of roll
		int face2;
		
	public:
		PairofDice();     // starts randomization

		int * first();    // returns addres of face1
		int getval1();    //returns value of one die
		int getval2();    //returns value of the other die
		void roll();      // the two dice are rolled 
		void shows();     // display
		int sumvalue();   //  sum of values
		bool big();       //true if dice are 6-6
		bool small();     //true if dice are 1-1								// The object takes the values of the rhs operator.
		void operator = (PairofDice rhs);
		
};

bool operator == (PairofDice &set1, PairofDice &set2);