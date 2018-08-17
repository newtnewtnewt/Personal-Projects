/* This bulk of static classes are used to manipulate and create the 
 * arrays of Character objects that will be used in building the map 
 * for each level. All the methods presents either create rooms, display them, or 
 * edit their contents.
 */
package LevelCreation;


import java.util.Random;

public class RoomGen {
	

	
	public static char[][] roomMaker(int height, int length){ //This tool generates rooms given specified conditions
		char[][] room = new char[height][length];
		for(int i = 0; i < room[0].length;i++) { //Fill in the top row with bricks
			room[0][i] = '#';
		}
		for(int i = 0; i < room.length; i++) { //Fill in the first column with bricks
			room[i][0] = '#';
		}
		for(int i = 0; i < length; i++) {//Fill in the bottom row with bricks
			room[height-1][i] ='#';
		}
		for(int i = 0; i < height; i++) {//Fill in the final column with bricks
			room[i][length-1] = '#';
		}
		for(int i = 1; i<height-1; i++) {//Fill in the empty space with dots
			for(int j = 1; j < length-1; j++) {
				room[i][j] = '.';
			}
		}
		
		
		return room;
		
	}
	public static char[][] addExit(String location, char[][] temp){//Places exits for each room given the side of the room
		int length = temp[0].length;
		int height = temp.length;
		Random rand = new Random();
		
		switch(location) {
		
		case "top":   //Top exit generation
			int slot = rand.nextInt(length-2) + 1;
			temp[0][slot] = '^';
			return temp;
				
		case "bottom"://Bottom exit generation
			slot = rand.nextInt(length-2) + 1;
			temp[height-1][slot] = 'v';
			return temp;
		
		case "left": //Left exit generation
			slot = rand.nextInt(height-2) + 1;
			temp[slot][0] = '<';
			return temp;
		
		case "right"://Right exit generation
			slot = rand.nextInt(height-2) + 1;
			temp[slot][length-1] = '>';
			return temp;
		}
		return null;
	}
	
	public static int[] chooseRandomEmpty(char[][] temp) {//Randomly generates an empty position
		Random rand = new Random(); 
		int length = temp[0].length;
		int height = temp.length;
		
		int randomLengthNum = rand.nextInt(length - 2) + 1;
		int randomHeightNum = rand.nextInt(height - 2) + 1;
		while(temp[randomHeightNum][randomLengthNum] != '.') {
			randomLengthNum = rand.nextInt(length - 2) + 1;
			randomHeightNum = rand.nextInt(height - 2) + 1;
		}
		
		int[] importantVals = {randomHeightNum, randomLengthNum};
		return importantVals;
	}
	
	public static char[][] createSmallRoom(){//Small room generation using roomMaker tool
		Random rand = new Random();
		char[][] temp = roomMaker(7, rand.nextInt(6) + 3);
		return temp;
	}
	public static char[][] createMediumRoom(){//Medium room generation using roomMaker
		Random rand = new Random();
		char[][] temp = roomMaker(7, rand.nextInt(10) + 6);
		return temp;
	}
	public static char[][] createLargeRoom(){//Large room generation using roomMaker
		Random rand = new Random();
		char[][] temp = roomMaker(7, rand.nextInt(15) + 11);
		return temp;
	}
	public static char[][] placePieces(char characterIcon, int numberOfCharacter, char[][] currentRoom){//Places elements in room randomly in empty spots.
		for(int i = 0; i < numberOfCharacter; i++) {
			int[] position = chooseRandomEmpty(currentRoom);
			int yVal = position[0];
			int xVal = position[1];
			currentRoom[yVal][xVal] = characterIcon;
		}
		return currentRoom;
	}
	
	public static void roomDisplayer(char[][] temp) { //Display a room
		
		for(int i = 0; i < temp.length; i++) {
			for(int j = 0; j < temp[i].length; j++) {
				System.out.print(temp[i][j] + " ");
			}
			System.out.println();
		}
		
	}
	
	//This tool populates a room with monsters given a monster pool, and the number of monsters, pseudorandomly
	
	public static char[][] monsterPopulator(char[][] arrayWithoutMonsters, char[]monsterPool, int lowerRange, int upperRange) { 
		Random rand = new Random();
		int rollNumber = rand.nextInt(upperRange) + lowerRange;
		char[][] temp = arrayWithoutMonsters;
		
		while(rollNumber > 0) {
			int chosenMonster = rand.nextInt(monsterPool.length);
			RoomGen.placePieces(monsterPool[chosenMonster], 1, temp);
			rollNumber--;
		}
		return temp;
	}
	
	
	
	
}
