package edu.princeton.cs.algs4;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class hockeyPuckColorMenuListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		String color = e.getSource().toString().substring(e.getSource().toString().indexOf("text") + 5);
		color = color.replace("]", "");
		if(color.equals("Blue")) {
			AirHockeyGui.hockeyPuck.color = StdDraw.BLUE;
		}else if(color.equals("Red")) {
			AirHockeyGui.hockeyPuck.color = StdDraw.RED;
		}else if(color.equals("Black")) {
			AirHockeyGui.hockeyPuck.color = StdDraw.BLACK;
		}else if(color.equals("Green")) {
			AirHockeyGui.hockeyPuck.color = StdDraw.GREEN;
		}else {
			AirHockeyGui.hockeyPuck.color = StdDraw.ORANGE;
		}
	}

}
