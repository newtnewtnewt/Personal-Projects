package edu.princeton.cs.algs4;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class PuckListener extends MouseMotionAdapter {
	Stopwatch sw = new Stopwatch();
	static int SPEED = 10;
	@Override
	public void mouseMoved(MouseEvent e) {
		double time = sw.elapsedTime();
		sw = new Stopwatch();
		AirHockeyGui.myPuck.vx = ((double)e.getX()/AirHockeyGui.WIDTH - AirHockeyGui.myPuck.rx)/((time));
		AirHockeyGui.myPuck.vy = -(((1 - (double)e.getY()/(AirHockeyGui.HEIGHT)) - AirHockeyGui.myPuck.ry)/((time)));
		AirHockeyGui.myPuck.vx = AirHockeyGui.myPuck.vx * SPEED;
		AirHockeyGui.myPuck.vy = AirHockeyGui.myPuck.vy * SPEED;
		AirHockeyGui.myPuck.setPuckPosition(((double)e.getX()/AirHockeyGui.WIDTH), 1 -((double)e.getY()/AirHockeyGui.HEIGHT));
		StdDraw.enableDoubleBuffering();
	}
}


