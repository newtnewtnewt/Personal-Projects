import java.util.Scanner;

public class PasswordH1 {

	public static void main(String[] args) {
		
		String password = "chicken"; //Establishes password as Chicken
		boolean didNotFindPassword = true; //Break condition
		Scanner in = new Scanner(System.in); //Reads user input 
		
		while(didNotFindPassword) { //Loops until correct password is found
			
			System.out.print("Enter password: "); //Prompts user for input 
			String attempt = in.next();           //Recieves input 
			
			if(attempt.equals(password)) {       //Checks if password is correct 
				didNotFindPassword = false;      //Breaks if password is correct 
			}
			
			else {
				System.out.println("Incorrect Password! Try again!");//Loops again if incorrect 
			}
		}
		
		System.out.println("You found the password!"); //Rewards user with message
		in.close();
	}

}
