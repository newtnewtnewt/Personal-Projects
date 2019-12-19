/* Name: Noah Dunn
 * Class: CSE 271
 * Date: 5/15/2018
 * Filename: MazeSolveDisplayer
 * Description: Create a tool that displays the maze
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class MazeSolveDisplayer extends JFrame {
	private int[][]maze;
	JComponent paintyComponent;
	static int currentX = 0;
	static int currentY = 0;
	static int startX = 0;
	static int startY = 0;
	static int currentEntry = 0;
	ArrayList<Integer> solutions;
	Timer t1;
	
	public MazeSolveDisplayer(int[][] maze, ArrayList<Integer> solution) { //Create the actual drawing frame
		this.setSize(1000,700);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.maze = maze;
		paintyComponent = new painterComponent(); 
		t1 = new Timer(500, new TimerListener()); 
		t1.start();
		this.solutions = solution;
		this.add(paintyComponent, BorderLayout.CENTER);
	
		
	}
	
	public void drawMaze(int[][] maze) { //Draw according to the maze
		paintyComponent.repaint();
		
	}
	
	private class painterComponent extends JComponent{
		public void paintComponent(Graphics g) {  //Paint the solution using a timer in increments of 0.5 seconds 
			currentY = 0;
			currentX = 0;
			for (int posY = 0; posY < 7; posY++) {
				if(posY > 0) {
				currentY = currentY + 100;
				}
				currentX = 0;
				for (int posX = 0; posX < 10; posX++) {
						if(maze[posY][posX] == 1) {
							g.setColor(Color.BLACK);
						}
						else if(maze[posY][posX] == 0 ) {
							g.setColor(Color.WHITE);
						}
						else if(maze[posY][posX] == 3) {
							g.setColor(Color.white);
							startX = currentX;
							startY = currentY;
						}
	
						else if(maze[posY][posX] == 2) {
							g.setColor(Color.BLUE);
						}
						
						g.fillRect(currentX, currentY, 100, 100);
						currentX = currentX + 100;
					}
			}
					
				
			
				int reX = startX;
				int reY = startY;
				int i = 0;
				for(i = 0; i < currentEntry; i++) {
				
					
					if(solutions.get(i) == 10) {
						g.setColor(Color.red);
						g.fillRect(reX, reY, 100, 100);
						reY = reY - 100;
					}
					else if(solutions.get(i) == 11) {
						g.setColor(Color.red);
						g.fillRect(reX, reY, 100, 100);
						reX = reX + 100;
					}
					else if(solutions.get(i) == 12) {
						g.setColor(Color.red);
						g.fillRect(reX, reY, 100, 100);
						reY = reY + 100;
					}
					else if(solutions.get(i) == 13) {
						
						g.setColor(Color.red);
						g.fillRect(reX, reY, 100, 100);
						reY = reX - 100;
					}
				}
				if(currentEntry == 0) {
					g.setColor(Color.green);
					g.fillRect(startX, startY, 100, 100);
				}
				if(i == currentEntry) {
					g.setColor(Color.GREEN);
					g.fillRect(reX, reY, 100, 100);
				}
						
			}
		}
		
	
	class TimerListener implements ActionListener {//Changes a state every 0.5 seconds 

		
		public void actionPerformed(ActionEvent arg0) {
			currentEntry++;
			drawMaze(maze);
			if(currentEntry == solutions.size())
				t1.stop();
		}
		
	}
	
}
