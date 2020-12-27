package edu.princeton.cs.algs4;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyPuckRadiusMenuListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		String radius = e.getSource().toString().substring(e.getSource().toString().indexOf("text") + 5);
		radius = radius.replace("]", "");
		if(radius.equals("Small")) {
			AirHockeyGui.myPuck.radius = 0.020;
		}else if(radius.equals("Standard")) {
			AirHockeyGui.myPuck.radius = 0.035;
		}else {
			AirHockeyGui.myPuck.radius = 0.070;
		}
	}

}
