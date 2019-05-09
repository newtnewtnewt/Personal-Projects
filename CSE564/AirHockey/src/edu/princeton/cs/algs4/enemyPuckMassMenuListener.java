package edu.princeton.cs.algs4;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class enemyPuckMassMenuListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		String mass = e.getSource().toString().substring(e.getSource().toString().indexOf("text") + 5);
		mass = mass.replace("]", "");
		if(mass.equals("Light")) {
			AirHockeyGui.enemyPuck.mass = 0.001;
		}else if(mass.equals("Standard")) {
			AirHockeyGui.enemyPuck.mass= 2;
		}else {
			AirHockeyGui.enemyPuck.mass = 500;
		}
	}

}
