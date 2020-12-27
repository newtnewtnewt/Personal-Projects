package edu.princeton.cs.algs4;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyPuckMassMenuListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		String mass = e.getSource().toString().substring(e.getSource().toString().indexOf("text") + 5);
		mass = mass.replace("]", "");
		if(mass.equals("Light")) {
			AirHockeyGui.myPuck.mass = 0.001;
		}else if(mass.equals("Standard")) {
			AirHockeyGui.myPuck.mass= 2;
		}else {
			AirHockeyGui.myPuck.mass = 500;
		}
	}

}
