package edu.princeton.cs.algs4;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class AirHockeyJUnit {

	@Test
	void wallCheckJUnit() {
		/**
		 * Our JUnit for this project is tiny because all of our
		 * ActionListeners modify the GUI, we used a large chunk
		 * of the Unit-testable code from the book, and 
		 * the majority of our handwritten code is GUI related,
		 * which is not testable in a Unit test.
		 */
		Particle p = new Particle();
		
		p.ry = 1.0;
		assertTrue(AirHockeyGui.wallCheck(p));
		p.ry = 0.98;
		assertFalse(AirHockeyGui.wallCheck(p));
		
		p.ry = 0.51;
		assertTrue(AirHockeyGui.wallCheck(p));
		p.ry = 0.53;
		assertFalse(AirHockeyGui.wallCheck(p));
		
		p.rx = 0.010;
		assertTrue(AirHockeyGui.wallCheck(p));
		p.rx = 0.030;
		assertFalse(AirHockeyGui.wallCheck(p));
		
		p.rx = 0.980;
		assertTrue(AirHockeyGui.wallCheck(p));
		p.rx = 0.960;
		assertFalse(AirHockeyGui.wallCheck(p));
	}
	
	
	
	
	
	
	
	
	

}
