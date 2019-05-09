package edu.princeton.cs.algs4;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class enemyPuckColorMenuListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		String color = e.getSource().toString().substring(e.getSource().toString().indexOf("text") + 5);
		color = color.replace("]", "");
		if(color.equals("Blue")) {
			AirHockeyGui.enemyPuck.color = StdDraw.BLUE;
		}else if(color.equals("Red")) {
			AirHockeyGui.enemyPuck.color = StdDraw.RED;
		}else if(color.equals("Black")) {
			AirHockeyGui.enemyPuck.color = StdDraw.BLACK;
		}else if(color.equals("Green")) {
			AirHockeyGui.enemyPuck.color = StdDraw.GREEN;
		}else {
			AirHockeyGui.enemyPuck.color = StdDraw.ORANGE;
		}
	}

}
