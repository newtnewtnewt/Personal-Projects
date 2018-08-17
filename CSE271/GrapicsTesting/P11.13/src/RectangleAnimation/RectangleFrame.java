/* Name: Noah Dunn
 * Class: CSE 271 A
 * Date: 4/24/2018
 * File: RectamgleFrame
 * Objective: Build a bouncing rectangle
 */

package RectangleAnimation;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import javax.swing.JFrame;

public class RectangleFrame extends JFrame { //Provide the frame for the animation.
	private static final int FRAME_WIDTH = 300;
	public static int getFrameWidth() {
		return FRAME_WIDTH;
	}
	public static int getFrameHeight() {
		return FRAME_HEIGHT;
	}
	private static final int FRAME_HEIGHT = 400;
	
	private RectangleComponent scene;
	
	class TimerListener implements ActionListener{ //Create a timer listener that moves the the rectangle by 1,1 each time 
		
		
		public void actionPerformed(ActionEvent arg0) {
			scene.moveRectangle(1,1);
		}
		
	
	}
	public RectangleFrame() { //Actually build the animation
		scene = new RectangleComponent();
		this.add(scene);
		
		this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ActionListener listener = new TimerListener();
		
		final int DELAY = 10;
		Timer t = new Timer(DELAY, listener);
		t.start();
		this.setVisible(true);
	}
	
}
