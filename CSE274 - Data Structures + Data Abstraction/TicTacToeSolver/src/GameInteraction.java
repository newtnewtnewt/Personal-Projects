import java.util.ArrayList;
import java.util.Scanner;

public class GameInteraction {
	
	public static void main(String args[]) {
	Scanner in = new Scanner(System.in);
	Game buildStart = new Game();
	
	
	System.out.println("The Game Begins");
	Board startingBoard = new Board();
	ArrayList<Integer> moveList = new ArrayList<Integer>();
	
	while(true) {
		startingBoard.boardPrint();
		System.out.print("Choose a position 0-8 player! ");
		int choice; 
		choice = in.nextInt();
		
		while(choice < 0 || choice > 8 || moveList.contains(choice) ) {
			System.out.println("This is not a valid position, try again!");
			choice = in.nextInt();
		}
		startingBoard.makeMove(choice, 'X');
		moveList.add(choice);
		
		if(startingBoard.gameWon()) {
			System.out.println("The player wins!");
			startingBoard.boardPrint();
			break;
		}
		
		if(startingBoard.catBoard()) {
			System.out.println("There is a tie!");
			startingBoard.boardPrint();
			break;
		}
		
		System.out.println("Computer's turn");
		int computerChoice = buildStart.possibleBoards.getValue(startingBoard);
		System.out.println("Computer chooses " + computerChoice);
		startingBoard.makeMove(computerChoice, 'O');
		moveList.add(computerChoice);
		if(startingBoard.gameWon()) {
			System.out.println("The computer wins!");
			startingBoard.boardPrint();
			break;
		}
		if(startingBoard.catBoard()) {
			System.out.println("There is a tie!");
			startingBoard.boardPrint();
			break;
		}
		
	}
	in.close();
	
}
}
