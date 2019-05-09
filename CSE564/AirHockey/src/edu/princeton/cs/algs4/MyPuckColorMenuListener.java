package edu.princeton.cs.algs4;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

public class MyPuckColorMenuListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		String color = e.getSource().toString().substring(e.getSource().toString().indexOf("text") + 5);
		color = color.replace("]", "");
		if(color.equals("Blue")) {
			AirHockeyGui.myPuck.color = StdDraw.BLUE;
		}else if(color.equals("Red")) {
			AirHockeyGui.myPuck.color = StdDraw.RED;
		}else if(color.equals("Black")) {
			AirHockeyGui.myPuck.color = StdDraw.BLACK;
		}else if(color.equals("Green")) {
			AirHockeyGui.myPuck.color = StdDraw.GREEN;
		}else {
			AirHockeyGui.myPuck.color = StdDraw.ORANGE;
		}
	}

}

