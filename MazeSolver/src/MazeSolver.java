/* Name: Noah Dunn
 * Class: CSE 271
 * Date: 5/15/2018
 * Filename: MazeSolver
 * Description: Create a tool that solves the maze 
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class MazeSolver {
	final static int WALL = 1;
	final static int SPACE = 0;
	final static int CHARACTER = 3;
	final static int EXIT = 2;
	final static int VISITED = 4;
	
	final static int MOVE_UP = 10;
	final static int MOVE_RIGHT = 11;
	final static int MOVE_DOWN = 12;
	final static int MOVE_LEFT = 13;

	final static int MAZE_WIDTH = 10;
	final static int MAZE_HEIGHT = 7;
	static int startX;
	static int startY;

	public static void main(String[] args) throws FileNotFoundException { //Load the maze, solve the maze, display the maze
		int[][] maze = new int[MAZE_HEIGHT][MAZE_WIDTH];
		loadMaze("maze3", maze);
		ArrayList<Integer> solutionRay = findSolution(maze, startX, startY);
		MazeSolveDisplayer x = new MazeSolveDisplayer(maze, solutionRay);
	}

	private static void loadMaze(String filename, int[][] maze) throws FileNotFoundException { //Load the maze
		File newFile = new File(filename);
		Scanner in = new Scanner(newFile);

		while (in.hasNextLine()) {
			for (int i = 0; i < 7; i++) {
				for (int j = 0; j < 10; j++) {
					maze[i][j] = in.nextInt();
					if(maze[i][j] == CHARACTER){
						startY = i;
						startX = j;
						
					}
					
				}
			}

		}
	}

	private static  ArrayList<Integer> findSolution(int[][] board, int posx, int posy) { //Discover the solution using recursion
		ArrayList<Integer> moveList=null;
		if (posx+1<MAZE_WIDTH) {
			if (board[posy][posx+1]==EXIT) {
				moveList = new ArrayList<Integer>();
				moveList.add(MOVE_RIGHT);
				return moveList;
			} 
			else if (board[posy][posx+1]==SPACE) {
				moveList = new ArrayList<Integer>();
				moveList.add(MOVE_RIGHT);
				int[][] newBoard = arrayCopy(board);
				newBoard[posy][posx+1]=VISITED;
				ArrayList<Integer> result = findSolution(newBoard,posx+1,posy);
				
				if (result == null) {
					moveList = null;
				} 
				
				else {
					moveList.addAll(result);
					return moveList;
				}
			}
		}
		
		if (posx-1 >= 0) {
			if (board[posy][posx-1]==EXIT) {
				moveList = new ArrayList<Integer>();
				moveList.add(MOVE_LEFT);
				return moveList;
			} 
			else if (board[posy][posx-1]==SPACE) {
				moveList = new ArrayList<Integer>();
				moveList.add(MOVE_LEFT);
				int[][] newBoard = arrayCopy(board);
				newBoard[posy][posx-1]=VISITED;
				
				ArrayList<Integer> result = findSolution(newBoard,posx-1,posy);
				
				if (result == null) {
					moveList = null;
				} 
				
				else {
					moveList.addAll(result);
					return moveList;
				}
			}
		}
		
		if (posy+1 < MAZE_HEIGHT) {
			if (board[posy+1][posx]==EXIT) {
				moveList = new ArrayList<Integer>();
				moveList.add(MOVE_DOWN);
				return moveList;
			} 
			else if (board[posy+1][posx]==SPACE) {
				moveList = new ArrayList<Integer>();
				moveList.add(MOVE_DOWN);
				int[][] newBoard = arrayCopy(board);
				newBoard[posy+1][posx]=VISITED;
				ArrayList<Integer> result = findSolution(newBoard,posx,posy+1);
				
				if (result == null) {
					moveList = null;
				} 
				
				else {
					moveList.addAll(result);
					return moveList;
				}
		}
		}
		
		if (posy-1 >= 0) {
			if (board[posy-1][posx]==EXIT) {
				moveList = new ArrayList<Integer>();
				moveList.add(MOVE_UP);
				return moveList;
			} 
			else if (board[posy-1][posx]==SPACE) {
				moveList = new ArrayList<Integer>();
				moveList.add(MOVE_UP);
				int[][] newBoard = arrayCopy(board);
				newBoard[posy-1][posx]=VISITED;
				ArrayList<Integer> result = findSolution(newBoard,posx,posy-1);
				
				if (result == null) {
					moveList = null;
				} 
				
				else {
					moveList.addAll(result);
					return moveList;
				}
		}
		}
		return null;
	
	
	}
	private static int[][] arrayCopy(int[][] array) { //Copy the array!
		
		if (array== null)
			return null;
		
		int[][] newArray = new int[array.length][];
		
		for (int r = 0; r < array.length; r++) {
			newArray[r] = array[r].clone();
		}
		
		return newArray;
		}
	
}
