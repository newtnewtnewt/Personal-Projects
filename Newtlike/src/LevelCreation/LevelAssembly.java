package LevelCreation;

import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;



public class LevelAssembly {
	int numberOfSmallRooms;
	int numberOfMediumRooms;
	int numberOfLargeRooms;
	char[] monsterPool;
	int currentYVal = 5;
	int currentXVal = 10;
	int levelNumber;
	int roomCount = 0;
	char[][] levelPlatform = new char[50][100];
	List<List<Integer>> roomLocations = new ArrayList<>();
	int totalRooms = numberOfSmallRooms + numberOfMediumRooms + numberOfLargeRooms;
	int lv;
	
	public LevelAssembly(int levelNumber) throws FileNotFoundException { //This tool takes a created level object and assembles a level based on preset level designs
		lv = levelNumber;
		switch(lv) {  //Switch represents difficulty 
		
			case 1:
				numberOfSmallRooms = 4;
				numberOfMediumRooms = 2;
				numberOfLargeRooms= 1;
				monsterPool = new char[] {'S','G'};
				break;
				
			case 2:
				numberOfSmallRooms = 4;
				numberOfMediumRooms = 2;
				numberOfLargeRooms= 1;
				monsterPool = new char[] {'O','7'};
				break;
			
			
			case 3:
				numberOfSmallRooms = 4;
				numberOfMediumRooms = 2;
				numberOfLargeRooms= 1;
				monsterPool = new char[] {'Q','B'};
				break;
		}
		
		levelFiller();
		filePrinter();
		
		
		
		
	}
	
	public void startingRoomGenerator(){ //Builds a 7x7 starting room with @ character to represent starting character
		char[][] startRoom =RoomGen.roomMaker(7, 7);
		startRoom[3][3] = '@';
		int startX = levelPlatform[0].length/2;
		int startY= levelPlatform.length/2;
				
		for(int i = 0; i < startRoom.length; i++) {
			for(int j = 0; j< startRoom[0].length; j++) {
				levelPlatform[startY][startX] = startRoom[i][j];
				startX++;
			}
			startX = levelPlatform[0].length/2;
			startY++;
		}
		
	}
	
	public void levelFiller(){ //Fills the level with rooms and provides spacing based on pre-existing rooms. 3 rooms to a row.
		roomLocations = new ArrayList<>();
		Random rand = new Random();
		startingRoomGenerator();
		
		while(numberOfSmallRooms > 0 || numberOfMediumRooms > 0 || numberOfLargeRooms > 0) {//Insures only the required # of rooms are placed
			
			
			int choice = rand.nextInt(3) + 1;
			
			
			if(choice == 1 && numberOfSmallRooms > 0) {
				
				char[][] temp = RoomGen.createSmallRoom();
				
				temp = RoomGen.monsterPopulator(temp, monsterPool, 1, 2);
				
				numberOfSmallRooms--;
				roomCount++;
				
				
				if(roomCount % 3 == 0) {
					currentYVal = currentYVal+ 10;
					currentXVal = 10;
					roomCount = 0;
				}
				if(currentXVal + temp[0].length > levelPlatform[0].length && currentXVal + temp[0].length < levelPlatform[0].length + 15){
					currentXVal = currentXVal + 15;
				}
				if(currentYVal + temp.length > levelPlatform.length/2 && currentYVal + temp.length < levelPlatform.length/2 + 15){
					currentYVal = currentYVal + 15;
				}
					
				//The following code helps with spacing and prevents collision with the starting room when generating rooms.
				
				
				
				
				for(int i = 0; i < temp.length; i++) {
					if(i == 0) {
						roomLocations.add(Arrays.asList(currentXVal, currentYVal));
					}
					for(int j = 0; j <temp[0].length; j++) {
						levelPlatform[currentYVal][currentXVal] = temp[i][j];
						currentXVal++;
						
					}
					currentXVal = currentXVal - temp[0].length;
					currentYVal++;
					
					
				}
				roomLocations.add(Arrays.asList(currentXVal, currentYVal));
				
				currentXVal = currentXVal + 20 + temp[0].length;
				currentYVal = currentYVal - temp.length;
				
			}
			
				
			
			else if(choice == 2 && numberOfMediumRooms > 0) {
				char[][] temp = RoomGen.createMediumRoom();
				temp = RoomGen.monsterPopulator(temp, monsterPool, 1, 3);
				numberOfMediumRooms--;
				roomCount++;
				
				
				if(roomCount % 3 == 0) {
					currentYVal = currentYVal+ 10;
					currentXVal = 10;
				}
				if(currentXVal + temp[0].length > levelPlatform[0].length && currentXVal + temp[0].length < levelPlatform[0].length + 15){
					currentXVal = currentXVal + 15;
				}
				if(currentYVal + temp.length > levelPlatform.length/2 && currentYVal + temp.length < levelPlatform.length/2 + 15){
					currentYVal = currentYVal + 15;
				}
				
				
				
				
				
				for(int i = 0;i < temp.length; i++) {
					if(i == 0) {
						roomLocations.add(Arrays.asList(currentXVal, currentYVal));
					}
					for(int j = 0; j <temp[0].length; j++) {
						levelPlatform[currentYVal][currentXVal] = temp[i][j];
						currentXVal++;
						
					}
					currentXVal = currentXVal - temp[0].length;
					currentYVal++;
				}
				
				roomLocations.add(Arrays.asList(currentXVal, currentYVal));
				
				currentXVal = currentXVal + 20 + temp[0].length;
				currentYVal = currentYVal - temp.length;
				
			}
			
			else if(choice == 3 && numberOfLargeRooms > 0) {
				char[][] temp = RoomGen.createLargeRoom();
				temp = RoomGen.monsterPopulator(temp, monsterPool, 1, 4);
				numberOfLargeRooms--;
				roomCount++;
				
				if(roomCount% 3  == 0) {
					currentYVal = currentYVal+ 10;
					currentXVal = 10;
				}
				if(currentXVal + temp[0].length > levelPlatform[0].length && currentXVal + temp[0].length < levelPlatform[0].length + 15){
					currentXVal = currentXVal + 15;
				}
				if(currentYVal + temp.length > levelPlatform.length/2 && currentYVal + temp.length < levelPlatform.length/2 + 15){
					currentYVal = currentYVal + 15;
				}
				
				
				
				
				for(int i = 0;i < temp.length; i++) {
					for(int j = 0; j <temp[0].length; j++) {
						levelPlatform[currentYVal][currentXVal] = temp[i][j];
						currentXVal++;
					
					}
					currentXVal = currentXVal - temp[0].length;
					currentYVal++;
				}
				
				roomLocations.add(Arrays.asList(currentXVal, currentYVal));
				
				currentXVal = currentXVal + 20 + temp[0].length;
				currentYVal = currentYVal - temp.length;
				
			}
			
			
			
			
		}
		
		
		
	}
	public void levelDisplay() {//Displays entire level 
		for(int i = 0; i < levelPlatform.length; i++) {
			for(int j = 0; j < levelPlatform[i].length; j++) {
				System.out.print(levelPlatform[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	public void filePrinter() throws FileNotFoundException {
		File levelFile = new File("C:\\Users\\newtn\\eclipse-workspace\\Newtlike\\levelFile" + lv + ".txt");
		levelFile.getParentFile().mkdirs();
		
		PrintWriter outPut = new PrintWriter(levelFile);
		
		for(int i = 0; i < levelPlatform.length; i++) {
			for(int j = 0; j < levelPlatform[i].length; j++) {
				outPut.print(levelPlatform[i][j] + " ");
			}
			outPut.println();
		}
		outPut.close();
	}
	
	
	
	}
	
	

