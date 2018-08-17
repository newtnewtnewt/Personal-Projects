package OlympicRings;
/* Name: Noah Dunn
 * Class: CSE 271 A
 * Date: 4/24/2018
 * File: OlympicRings 
 * Objective: Create and paint the olympic rings
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.color.ColorSpace;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class OlympicRings extends JFrame {
	Color colorChoice;
	CircleComponent circlePlatter;
	ArrayList<OlympicRingSingle> circleHolder = new ArrayList<OlympicRingSingle>();

	
	public OlympicRings() { //Construct the Olympic Rings frame
		this.setSize(500,500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Olympic Rings");
		JPanel ringPanel = new JPanel();
		this.setVisible(true);
		circlePlatter = new CircleComponent(); //Grab the component that actually paints the rings
		drawRing("blue", 75, 100);
		drawRing("BLACK", 150, 100);
		drawRing("red",225,100);
		drawRing("yellow",110,140);
		drawRing("green", 185, 140);
		ringPanel.add(circlePlatter);
		this.add(ringPanel, BorderLayout.CENTER);
		this.setVisible(true);
		
	}
	
	
	
	
	
	
	public void drawRing(String color, int x, int y){ //Constructs an Olympic Ring and adds it to the Array of Olympic Rings
		OlympicRingSingle temp = new OlympicRingSingle(color,x,y); 
		circleHolder.add(temp);
		circlePlatter.repaint();
		
	} 
	
	
	
	class CircleComponent extends JComponent{ //Create the component that draws all of the circles.
		
		CircleComponent(){
			this.setPreferredSize(new Dimension(400,400));
		}
		
		public void paintComponent(Graphics g) { //Paint all rings in the array
			g.clearRect(0, 0, this.getWidth(), this.getWidth()); 
			for(OlympicRingSingle x: circleHolder) {
			String color = x.color;
			int xVal = x.x;
			int yVal = x.y;
			
			
			if(color.equalsIgnoreCase("blue")){
				colorChoice = Color.blue;
			}
			else if(color.equalsIgnoreCase("black")){
				colorChoice = Color.black;
			}
			else if(color.equalsIgnoreCase("red")){
				colorChoice = Color.red;
			}
			else if(color.equalsIgnoreCase("yellow")){
				colorChoice = Color.yellow;
			}
			else if(color.equalsIgnoreCase("green")){
				colorChoice = Color.green;
			}
			
			g.setColor(colorChoice);
			g.drawOval(xVal, yVal, 75, 75);
			}
		
		}
	}
	
	
	

}

