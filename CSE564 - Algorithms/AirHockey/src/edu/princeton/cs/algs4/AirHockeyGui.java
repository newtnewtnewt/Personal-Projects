package edu.princeton.cs.algs4;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import edu.princeton.cs.algs4.StdDraw;


public class AirHockeyGui {
	/*
	 * Some persistent pieces modified
	 */
	static final int WIDTH = 812;
	static final int HEIGHT = 812;
	
	static final double XWALLLEFT = 0.020;
	static final double XWALLRIGHT = 0.970;
	static final double YWALLTOP = 0.5;
	static final double YWALLBOTTOM = 0.075;
	
	static int homePoints = 0;
	static int opposingPoints = 0;
	
	static Particle myPuck;
	static Particle enemyPuck;
	static Particle hockeyPuck;
	
	static Color myPuckColor = StdDraw.RED;
	static Color enemyPuckColor = StdDraw.BLUE;
	static Color hockeyPuckColor = StdDraw.BLACK;
	
	
	static double myPuckRadius = 0.035;
	static double enemyPuckRadius = 0.035;
	static double hockeyPuckRadius = 0.035;

	static double myPuckMass = 2;
	static double enemyPuckMass = 2;
	static double hockeyPuckMass = 2;
	
	
	public static void main(String[] args) {
		gameSetup();
	}
	/*
	 * This sets our pucks up and generates our board.
	 */
	public static void gameSetup() {
		
		StdDraw.enableDoubleBuffering();
		myPuck = new Particle(XWALLLEFT + 0.225 * (XWALLRIGHT - XWALLLEFT), 0.075 + 0.5 * (0.925-0.075) + 0.25, 
		0, 0, myPuckRadius, myPuckMass, myPuckColor);
		enemyPuck = new Particle(XWALLLEFT + 0.765 * (XWALLRIGHT - XWALLLEFT), 0.075 + 0.5 * (0.925-0.075) + 0.25, 
	    0, 1, enemyPuckRadius, enemyPuckRadius, enemyPuckColor);
		hockeyPuck = new Particle(XWALLLEFT + 0.495 * (XWALLRIGHT - XWALLLEFT), 0.075 + 0.5 * (0.925-0.075) + 0.25,
		0, 0, hockeyPuckRadius, hockeyPuckRadius, hockeyPuckColor);
		
		StdDraw.setCanvasSize(WIDTH, HEIGHT);
		JPanel boardPanel = new JPanel();

        JMenu menu= new JMenu("Menu");
        StdDraw.frame.getJMenuBar().add(menu);
        
        JMenu myPuckSettings = new JMenu("My Puck Settings");
        JMenu enemyPuckSettings = new JMenu("Enemy Puck Settings");
        JMenu hockeyPuckSettings = new JMenu("Hockey Puck Settings");
        
        menu.add(myPuckSettings);
        menu.add(enemyPuckSettings);
        menu.add(hockeyPuckSettings);
        
        JMenu myPuckColor = new JMenu("Color");
        JMenu myPuckMass = new JMenu("Mass");
        JMenu myPuckRadius = new JMenu("Radius");
        JMenu enemyPuckColor = new JMenu("Color");
        JMenu enemyPuckMass = new JMenu("Mass");
        JMenu enemyPuckRadius = new JMenu("Radius");
        JMenu hockeyPuckColor = new JMenu("Color");
        JMenu hockeyPuckMass = new JMenu("Mass");
        JMenu hockeyPuckRadius = new JMenu("Radius");
        
        myPuckSettings.add(myPuckColor);
        myPuckSettings.add(myPuckMass);
        myPuckSettings.add(myPuckRadius);
        enemyPuckSettings.add(enemyPuckColor);
        enemyPuckSettings.add(enemyPuckMass);
        enemyPuckSettings.add(enemyPuckRadius);
        hockeyPuckSettings.add(hockeyPuckColor);
        hockeyPuckSettings.add(hockeyPuckMass);
        hockeyPuckSettings.add(hockeyPuckRadius);
        
        JMenuItem myPuckBlue = new JMenuItem("Blue");
        JMenuItem myPuckRed = new JMenuItem("Red");
        JMenuItem myPuckBlack = new JMenuItem("Black");
        JMenuItem myPuckGreen = new JMenuItem("Green");
        JMenuItem myPuckOrange = new JMenuItem("Orange");
        JMenuItem enemyPuckBlue = new JMenuItem("Blue");
        JMenuItem enemyPuckRed = new JMenuItem("Red");
        JMenuItem enemyPuckBlack = new JMenuItem("Black");
        JMenuItem enemyPuckGreen = new JMenuItem("Green");
        JMenuItem enemyPuckOrange = new JMenuItem("Orange");
        JMenuItem hockeyPuckBlue = new JMenuItem("Blue");
        JMenuItem hockeyPuckRed = new JMenuItem("Red");
        JMenuItem hockeyPuckBlack = new JMenuItem("Black");
        JMenuItem hockeyPuckGreen = new JMenuItem("Green");
        JMenuItem hockeyPuckOrange = new JMenuItem("Orange");
        
        JMenuItem myPuckSmall = new JMenuItem("Small");
        JMenuItem myPuckStandard = new JMenuItem("Standard");
        JMenuItem myPuckLarge = new JMenuItem("Large");
        JMenuItem enemyPuckSmall = new JMenuItem("Small");
        JMenuItem enemyPuckStandard = new JMenuItem("Standard");
        JMenuItem enemyPuckLarge = new JMenuItem("Large");
        JMenuItem hockeyPuckSmall = new JMenuItem("Small");
        JMenuItem hockeyPuckStandard = new JMenuItem("Standard");
        JMenuItem hockeyPuckLarge = new JMenuItem("Large");
        
        JMenuItem myPuckLight = new JMenuItem("Light");
        JMenuItem myPuckStandardWeight = new JMenuItem("Standard");
        JMenuItem myPuckHeavy = new JMenuItem("Heavy");
        JMenuItem enemyPuckLight = new JMenuItem("Light");
        JMenuItem enemyPuckStandardWeight = new JMenuItem("Standard");
        JMenuItem enemyPuckHeavy = new JMenuItem("Heavy");
        JMenuItem hockeyPuckLight = new JMenuItem("Light");
        JMenuItem hockeyPuckStandardWeight = new JMenuItem("Standard");
        JMenuItem hockeyPuckHeavy = new JMenuItem("Heavy");
        
       
        myPuckBlue.addActionListener(new MyPuckColorMenuListener());
        myPuckRed.addActionListener(new MyPuckColorMenuListener());
        myPuckBlack.addActionListener(new MyPuckColorMenuListener());
        myPuckGreen.addActionListener(new MyPuckColorMenuListener());
        myPuckOrange.addActionListener(new MyPuckColorMenuListener());
        enemyPuckBlue.addActionListener(new enemyPuckColorMenuListener());
        enemyPuckRed.addActionListener(new enemyPuckColorMenuListener());
        enemyPuckBlack.addActionListener(new enemyPuckColorMenuListener());
        enemyPuckGreen.addActionListener(new enemyPuckColorMenuListener());
        enemyPuckOrange.addActionListener(new enemyPuckColorMenuListener());
        hockeyPuckBlue.addActionListener(new hockeyPuckColorMenuListener());
        hockeyPuckRed.addActionListener(new hockeyPuckColorMenuListener());
        hockeyPuckBlack.addActionListener(new hockeyPuckColorMenuListener());
        hockeyPuckGreen.addActionListener(new hockeyPuckColorMenuListener());
        hockeyPuckOrange.addActionListener(new hockeyPuckColorMenuListener());
        
        myPuckSmall.addActionListener(new MyPuckRadiusMenuListener());
        myPuckStandard.addActionListener(new MyPuckRadiusMenuListener());
        myPuckLarge.addActionListener(new MyPuckRadiusMenuListener());
        enemyPuckSmall.addActionListener(new enemyPuckRadiusMenuListener());
        enemyPuckStandard.addActionListener(new enemyPuckRadiusMenuListener());
        enemyPuckLarge.addActionListener(new enemyPuckRadiusMenuListener());
        hockeyPuckSmall.addActionListener(new hockeyPuckRadiusMenuListener());
        hockeyPuckStandard.addActionListener(new hockeyPuckRadiusMenuListener());
        hockeyPuckLarge.addActionListener(new hockeyPuckRadiusMenuListener());
        
        myPuckLight.addActionListener(new MyPuckMassMenuListener());
        myPuckStandardWeight.addActionListener(new MyPuckMassMenuListener());
        myPuckHeavy.addActionListener(new MyPuckMassMenuListener());
        enemyPuckLight.addActionListener(new enemyPuckMassMenuListener());
        enemyPuckStandardWeight.addActionListener(new enemyPuckMassMenuListener());
        enemyPuckHeavy.addActionListener(new enemyPuckMassMenuListener());
        hockeyPuckLight.addActionListener(new  hockeyPuckMassMenuListener());
        hockeyPuckStandardWeight.addActionListener(new hockeyPuckMassMenuListener());
        hockeyPuckHeavy.addActionListener(new  hockeyPuckMassMenuListener());
        
        
        myPuckColor.add(myPuckBlue);
        myPuckColor.add(myPuckRed);
        myPuckColor.add(myPuckBlack);
        myPuckColor.add(myPuckGreen);
        myPuckColor.add(myPuckOrange);
        enemyPuckColor.add(enemyPuckBlue);
        enemyPuckColor.add(enemyPuckRed);
        enemyPuckColor.add(enemyPuckBlack);
        enemyPuckColor.add(enemyPuckGreen);
        enemyPuckColor.add(enemyPuckOrange);
        hockeyPuckColor.add(hockeyPuckBlue);
        hockeyPuckColor.add(hockeyPuckRed);
        hockeyPuckColor.add(hockeyPuckBlack);
        hockeyPuckColor.add(hockeyPuckGreen);
        hockeyPuckColor.add(hockeyPuckOrange);
        
        myPuckRadius.add(myPuckSmall);
        myPuckRadius.add(myPuckStandard);
        myPuckRadius.add(myPuckLarge);
        enemyPuckRadius.add(enemyPuckSmall);
        enemyPuckRadius.add(enemyPuckStandard);
        enemyPuckRadius.add(enemyPuckLarge);
        hockeyPuckRadius.add(hockeyPuckSmall);
        hockeyPuckRadius.add(hockeyPuckStandard);
        hockeyPuckRadius.add(hockeyPuckLarge);
        
        myPuckMass.add(myPuckLight);
        myPuckMass.add(myPuckStandardWeight);
        myPuckMass.add(myPuckHeavy);
        enemyPuckMass.add(enemyPuckLight);
        enemyPuckMass.add(enemyPuckStandardWeight);
        enemyPuckMass.add(enemyPuckHeavy);
        hockeyPuckMass.add(hockeyPuckLight);
        hockeyPuckMass.add(hockeyPuckStandardWeight);
        hockeyPuckMass.add(hockeyPuckHeavy);
       

		boardPanel.setSize(WIDTH/2, 418);
		boardPanel.addMouseMotionListener(new PuckListener());
		boardPanel.setVisible(true);
		boardPanel.setOpaque(false);
		StdDraw.frame.add(boardPanel);   
		
	
		
		StdDraw.picture(0.5, 0.75, "editTable.PNG");
		StdDraw.picture(0.5, 0.75, "editTable.png");
		myPuck.draw();
		enemyPuck.draw();
		hockeyPuck.draw();
		
		StdDraw.enableDoubleBuffering();
		
		//This is our "Game Loop" which is rather informal and is just an infinitely looping while.
		
		while(true) {
			StdDraw.pause(1);
			StdDraw.show();
			StdDraw.pause(1);
			enemyPuck.move(0.001);
			
			if(hockeyPuck.timeToHit(myPuck) < 0.00001) {
				hockeyPuck.bounceOff(myPuck);
			}
			if(hockeyPuck.timeToHit(enemyPuck) <= 0.001) {
				enemyPuck.bounceOff(hockeyPuck);
			}
			enemyPuck.vx = 0;
		
			
			hockeyPuck.move(0.001);
			if(enemyPuck.ry > 0.95) {
				enemyPuck.vy = -enemyPuck.vy;
			}
			else if(enemyPuck.ry < 0.55) {
				enemyPuck.vy = -enemyPuck.vy;
			}
			if(enemyPuck.kineticEnergy() > 100) {
				enemyPuck.vy = enemyPuck.vy/2;
			}
			reDraw();
			goalCheck(hockeyPuck);
			wallCheck(hockeyPuck);
		}
		
		
		
		
	}
	//This redraws the board every time a change is called
	public static void reDraw() {
		
		StdDraw.clear();
		StdDraw.picture(0.5, 0.75, "editTable.png");
		myPuck.draw();
		enemyPuck.draw();
		hockeyPuck.draw();
		StdDraw.text(0.075, 0.75, "" + homePoints);
		StdDraw.text(0.91, 0.75, "" + opposingPoints);	
	}
	//This checks if the hockeypuck has gone in the goal.
	public static boolean goalCheck(Particle p) {
		
		if((p.rx + p.radius >= (0.91 + 0.0525)) && ((p.ry + p.radius) <= (0.5 + 0.25 + 0.08) && (p.ry - p.radius) >= 0.5 + 0.25 - 0.08)) {
			homePoints++;
			hockeyPuck.setPuckPosition(XWALLLEFT + 0.495 * (XWALLRIGHT - XWALLLEFT), 0.075 + 0.5 * (0.925-0.075) + 0.25);
			hockeyPuck.vx = 0;
			hockeyPuck.vy = 0;
			return true;
		}
		else if((p.rx - p.radius <= (0.075 - 0.0525)) && ((p.ry + p.radius) <= (0.495 + 0.25 + 0.08) && (p.ry - p.radius) >= 0.25 + 0.495 - 0.08)) {
			opposingPoints++;
			hockeyPuck.setPuckPosition(XWALLLEFT + 0.495 * (XWALLRIGHT - XWALLLEFT), 0.075 + 0.5 * (0.925-0.075) + 0.25);
			hockeyPuck.vx = 0;
			hockeyPuck.vy = 0;
			return true;
		}
		
		return false;
	}
	//This is a check to see whether or not a wall has been hit.
	public static boolean wallCheck(Particle p) {
		boolean bounce = false;
		if(p.ry >= YWALLTOP + 0.25 + 0.24) {
			p.bounceOffHorizontalWall();
			bounce = true;
		}
		if(p.ry <= YWALLTOP + 0.02) {
			p.bounceOffHorizontalWall();
			bounce = true;
		}
		if(p.rx <= XWALLLEFT) {
			p.bounceOffVerticalWall();
			bounce = true;
		}
		if(p.rx >= XWALLRIGHT) {
			p.bounceOffVerticalWall();
			bounce = true;
		}
		
		return bounce;
	}

}
