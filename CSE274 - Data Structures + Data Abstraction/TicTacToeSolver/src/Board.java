
public class Board {
	/**The Board class is an Object class 
	 * intended to mimic and store the entries of a Tic-Tac-Toe Board
	 */
	public char[][] board;
	
	public Board() {
		/**The constructor fills the whole board with blank spaces
		 * 
		 */
		board = new char[3][3];
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				board[i][j] = '-';
				
			}
		}
	}
	public String toString() {
		/**ToString() takes no arguments and 
		 * @returns a string matching the board from top left to bottom right
		 * 
		 * 
		 */
		String boardString = "";
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				boardString = boardString + board[i][j];
			}
			
		}
		return boardString;
	}
	public Board copyOf() {
		/**Deep copies a board and @returns a new copy of it
		 * 
		 */
		Board newBoard = new Board();
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				newBoard.board[i][j] = board[i][j];
				
			}
		}
		return newBoard;
	}
	public void boardPrint() {
		/**Prints a 2D representation of the board
		 * for use in gameplay
		 */
	
		
		System.out.println(" " + board[0][0] + " | " + board[0][1] + " | " + board[0][2]);
		System.out.println("===|===|===");
		System.out.println(" " + board[1][0] + " | " + board[1][1] + " | " + board[1][2]);
		System.out.println("===|===|===");
		System.out.println(" " + board[2][0] + " | " + board[2][1] + " | " + board[2][2]);
		
		
	
	}
	
	public boolean isValidBoard() {
		/** @return a boolean based on whether or not a board 
		 * is in a valid state, given that X always starts
		 * 
		 */
		int xCount = 0;
		int oCount = 0;
		
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				if(board[i][j] == 'X') {
					xCount++;
				}
				else if(board[i][j] == 'O') {
					oCount++;
				}
			
			}
		}
		
		if(Math.abs(xCount - oCount) > 1) {
			
			return false;
		}
		if(oCount > xCount) {
			
			return false;
		}
		if((board[0][0] == board[0][1] && board[0][1] == board[0][2] && board[0][0] != '-') || 
		   (board[0][0] == board[1][0] && board[1][0] == board[2][0] && board[0][0] != '-') ||
		   (board[1][0] == board[1][1] && board[1][1] == board[1][2] && board[1][0] != '-') ||
		   (board[2][0] == board[2][1] && board[2][1] == board[2][2] && board[2][0] != '-') ||
		   (board[0][1] == board[1][1] && board[1][1] == board[2][1] && board[0][1] != '-') ||
		   (board[0][2] == board[1][2] && board[1][2] == board[2][2] && board[0][2] != '-') ||
		   (board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[0][0] != '-') ||
		   (board[2][0] == board[1][1] && board[1][1] == board[0][2] && board[2][0] != '-')) {
			
			return false;
		}
		if(catBoard()) {
			return false;
		}
		
		
		return true;
			
	}
	public boolean equals(Object otherBoard) {
		/**
		 * Compares two boards and @returns if they are equal or not 
		 */
		Board otherBoardx = (Board) otherBoard;
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				if(board[i][j] != otherBoardx.board[i][j]) {
					return false;
				}
				
			}
		}
		return true;
	}
	
	
	public int bestMoveGen() {
		/**
		 * Generates the best move given a large set of circumstances.
		 * Wins/Prevention of Losses are always prioritized
		 * If the middle is available, it is claimed to ensure that at least a tie can
		 * exist.
		 * Corners are taken in next precedence order.
		 * If no optimal play is available, the last resort is any availability
		 * @returns the optimal play
		 */
		if(board[0][0] == board[0][1] && board[0][0] != '-' && board[0][2] == '-') {
			return 2;
		}
		else if(board[1][0] == board[1][1] && board[1][0] != '-' && board[1][2] == '-') {
			return 5;
		}
		else if(board[2][0] == board[2][1] && board[2][0] != '-' && board[2][2] == '-') {
			return 8;
		}
		else if(board[0][1] == board[0][2] && board[0][1] != '-' && board[0][0] == '-') {
			return 0;
		}
		else if(board[1][1] == board[1][2] && board[1][1] != '-' && board[1][0] == '-') {
			return 3;
		}
		else if(board[2][1] == board[2][2] && board[2][1] != '-' && board[2][0] == '-') {
			return 6;
		}
		else if(board[0][0] == board[0][2] && board[0][0] != '-' && board[0][1] == '-') {
			return 1;
		}
		else if(board[1][0] == board[1][2] && board[1][0] != '-' && board[1][1] == '-') {
			return 4;
		}
		else if(board[2][0] == board[2][2] && board[2][0] != '-' && board[2][1] == '-') {
			return 7;
		}
		else if(board[0][0] == board[2][0] && board[0][0] != '-' && board[1][0] == '-') {
			return 3;
		}
		else if(board[0][1] == board[2][1] && board[0][1] != '-' && board[1][1] == '-') {
			return 4;
		}
		else if(board[0][2] == board[2][2] && board[0][2] != '-' && board[1][2] == '-') {
			return 5;
		}
		else if(board[0][0] == board[2][2] && board[0][0] != '-' && board[1][1] == '-') {
			return 4;
		}
		else if(board[2][0] == board[0][2] && board[2][0] != '-' && board[1][1] == '-') {
			return 4;
		}
		else if(board[0][0] == board[1][1] && board[0][0] != '-' && board[2][2] == '-') {
			return 8;
		}
		else if(board[0][2] == board[1][1] && board[0][2] != '-' && board[2][0] == '-') {
			return 6;
		}
		
		else if(board[0][0] == board[1][0] && board[0][0] != '-' && board[2][0] == '-') {
			return 6;
		}
		else if(board[0][1] == board[1][1] && board[0][1] != '-' && board[2][1] == '-') {
			return 7;
		}
		else if(board[0][2] == board[1][2] && board[0][2] != '-' && board[2][2] == '-') {
			return 8;
		}
		else if(board[1][0] == board[2][0] && board[1][0] != '-' && board[0][0] == '-') {
			return 0;
		}
		else if(board[1][1] == board[2][1] && board[1][1] != '-' && board[0][1] == '-') {
			return 1;
		}
		else if(board[1][2] == board[2][2] && board[1][2] != '-' && board[0][2] == '-') {
			return 2;
		}
		
		else if(board[0][0] == board[1][1] && board[0][0] != '-' && board[2][2] == '-') {
			return 8;
		}
		else if(board[1][1] == board[2][2] && board[1][1] != '-' && board[0][0] == '-') {
			return 0;
		}
		else if(board.equals(new Board().board)) {
			return 8;
		}
		else if(board[1][1] ==  '-') {
			return 4;
		}
		else if(board[0][0] == '-' && board[2][0] == '-' && board[2][2] == '-' && board[0][2] == '-') {
			return 0;
		}
		else if(board[0][0] != '-' && board[2][2] == '-') {
			return 8;
		}
		else if(board[0][2] != '-' && board[2][0] == '-') {
			return 6;
		}
		else if(board[2][0] != '-' && board[0][2] == '-') {
			return 2;
		}
		else if(board[2][2] != '-' && board[0][0] == '-') {
			return 0;
		}
		else if((board[0][0] != board[0][2] || board[0][0] != board[2][0])  && board[2][2] == '-') {
			return 8;
		}
		else if((board[2][2] != board[0][2] || board[2][2] != board[2][0])  && board[0][0] == '-') {
			return 0;
		}
		else if(board[0][0] == '-') {
			return 0;
		}
		else if(board[0][2] == '-') {
			return 2;
		}
		else if(board[2][0] == '-') {
			return 6;
		}
		else if(board[2][2] == '-') {
			return 8;
		}
		else {
			for(int i = 0; i < 9; i++) {
				int rowVal = i / 3;
				int columnVal = i % 3;
				if(board[rowVal][columnVal] == '-') {
					return i;
				}
				
			}
		}
		System.out.println("How did you get here?");
		return -1;
	
	}
	
	public boolean catBoard() {
		/** Determines if a board is catted or not, as in it has ended a tie
		 * @returns the answer to this
		 * 
		 */
		int spaceCounter = 0;
		
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				if(board[i][j] == '-') {
					spaceCounter++;
				}
			}
		}
		if(spaceCounter == 0) {
			return true;
		}
		return false;
		
		
	}
	
	
	
	
	public boolean gameWon() {
		/**
		 * @returns if a win condition has been hit
		 */
		if((board[0][0] == board[0][1] && board[0][1] == board[0][2] && board[0][0] != '-') || 
				   (board[0][0] == board[1][0] && board[1][0] == board[2][0] && board[0][0] != '-') ||
				   (board[1][0] == board[1][1] && board[1][1] == board[1][2] && board[1][0] != '-') ||
				   (board[2][0] == board[2][1] && board[2][1] == board[2][2] && board[2][0] != '-') ||
				   (board[0][1] == board[1][1] && board[1][1] == board[2][1] && board[0][1] != '-') ||
				   (board[0][2] == board[1][2] && board[1][2] == board[2][2] && board[0][2] != '-') ||
				   (board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[0][0] != '-') ||
				   (board[2][0] == board[1][1] && board[1][1] == board[0][2] && board[2][0] != '-')) {
					return true;
		}
					
		return false;
		
	}
	public void makeMove(int choice, char piece) {
		/**
		 * Places the selected move on the board @param choice with the respective @param token 
		 */
		int rowVal = choice / 3;
		int columnVal = choice % 3;
		board[rowVal][columnVal] = piece;
		
	}
	
	public int hashCode() {
		/**
		 * Generates a hashCode very similar to Java's hashcode generation utilizing the prime constant of 5.
		 * @returns the hashCode
		 */
		int hash = 0;
		int n = toString().length();
		for(int i = 0; i < n; i++) {
			hash = 5 * hash + toString().charAt(i);
		}
		return hash;
	}
	
}
