package edu.princeton.cs.algs4;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class enemyPuckRadiusMenuListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		String radius = e.getSource().toString().substring(e.getSource().toString().indexOf("text") + 5);
		radius = radius.replace("]", "");
		if(radius.equals("Small")) {
			AirHockeyGui.enemyPuck.radius = 0.020;
		}else if(radius.equals("Standard")) {
			AirHockeyGui.enemyPuck.radius = 0.035;
		}else {
			AirHockeyGui.enemyPuck.radius = 0.070;
		}
	}

}
