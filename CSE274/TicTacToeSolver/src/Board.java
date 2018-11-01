
public class Board {
	public char[][] board;
	
	public Board() {
		board = new char[3][3];
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				board[i][j] = '-';
				
			}
		}
	}
	public String toString() {
		String boardString = "";
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				boardString = boardString + board[i][j];
			}
			
		}
		return boardString;
	}
	public Board copyOf() {
		Board newBoard = new Board();
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				newBoard.board[i][j] = board[i][j];
				
			}
		}
		return newBoard;
	}
	public void boardPrint() {
		
	
		
		System.out.println(" " + board[0][0] + " | " + board[0][1] + " | " + board[0][2]);
		System.out.println("===|===|===");
		System.out.println(" " + board[1][0] + " | " + board[1][1] + " | " + board[1][2]);
		System.out.println("===|===|===");
		System.out.println(" " + board[2][0] + " | " + board[2][1] + " | " + board[2][2]);
		
		
	
	}
	
	public boolean isValidBoard() {
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
		int rowVal = choice / 3;
		int columnVal = choice % 3;
		board[rowVal][columnVal] = piece;
		
		
	}
	
}
