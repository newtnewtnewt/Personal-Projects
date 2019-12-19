
public class Game {
	ArrayDictionary<Board, Integer> possibleBoards;
	
	public Game() {
		/**
		 * Creates a Game object, which instantiates the Dictionary of Moves and fills it
		 */
		possibleBoards = new ArrayDictionary<Board, Integer>();
		Board startingBoard = new Board();
		boardBuilder(startingBoard, 0);
		
	}
	

	public void boardBuilder(Board currentBoard, int chainLink) {
		/**
		 * A recursive method that allows for the generation of all possible boards, which are validated 
		 * within the method.
		 */
		
		if(chainLink == 9) {
			if(currentBoard.isValidBoard()) {
				int getBestPlay = currentBoard.bestMoveGen();
				possibleBoards.add(currentBoard, getBestPlay);
			}
			
			return;
			
		}
		
		if(chainLink == 0) {
			Board newBoard1 = currentBoard.copyOf();
			newBoard1.board[0][0] = 'X';
			boardBuilder(newBoard1, chainLink + 1);
			
			Board newBoard2 = currentBoard.copyOf();
			newBoard2.board[0][0] = 'O';
			boardBuilder(newBoard2, chainLink + 1);
			
			Board newBoard3 = currentBoard.copyOf();
			newBoard3.board[0][0] = '-';
			boardBuilder(newBoard3, chainLink + 1);
		}
		else {
			int rowVal = chainLink / 3;
			int columnVal = chainLink % 3;
			
			Board newBoard4 = currentBoard.copyOf();
			newBoard4.board[rowVal][columnVal] = 'X';
			boardBuilder(newBoard4, chainLink + 1);
			
			Board newBoard5 = currentBoard.copyOf();
			newBoard5.board[rowVal][columnVal] = 'O';
			boardBuilder(newBoard5, chainLink + 1);
			
			Board newBoard6 = currentBoard.copyOf();
			newBoard6.board[rowVal][columnVal] = '-';
			boardBuilder(newBoard6, chainLink + 1);
		}

		
	}
	
	public int getBestMove(String board){
		/**
		 * @returns the best move given a board as a string @param
		 */
		Board testBoard = new Board();
		
		for(int i = 0; i < 9; i++) {
			int rowVal = i / 3;
			int columnVal = i % 3;
			testBoard.board[rowVal][columnVal] = board.charAt(i);
			
			
		}
		return testBoard.bestMoveGen();
	}
}
