/* Name: Noah Dunn
 * Class: CSE 271 A
 * Date: 4/24/2018
 * File: RectangleComponent
 * Objective: Build a bouncing rectangle
 */
package RectangleAnimation;

import java.awt.Graphics;

import javax.swing.JComponent;

public class RectangleComponent extends JComponent{
	private static final int RECTANGLE_WIDTH = 20;
	private static final int RECTANGLE_HEIGHT = 30;
	boolean xBounce = false;
	boolean yBounce = false;
	private int xLeft;
	private int yTop;
	
	public RectangleComponent() {//Construct the actual rectangle
		xLeft = 0;
		yTop = 0;
	}
	
	public void paintComponent(Graphics g) { //Paint the rectangle
		g.fillRect(xLeft, yTop, RECTANGLE_WIDTH, RECTANGLE_HEIGHT);
	}
	
	public void moveRectangle(int dx, int dy) { //Animate the rectangle and account for bouncing.
		
		if(xLeft + 35 <= RectangleFrame.getFrameWidth()  && yTop + 70<= RectangleFrame.getFrameHeight() && xLeft + 35 >= 35  && yTop + 70 >= 70){
			
			
			if(!xBounce && !yBounce) {
				xLeft = xLeft + dx;
				yTop = yTop + dy; 
				repaint();
			}
			else if(!xBounce && yBounce) {
				xLeft = xLeft + dx;
				yTop = yTop - dy; 
				repaint();
			}
			else if(xBounce && !yBounce) {
				xLeft = xLeft - dx;
				yTop = yTop + dy;
				repaint();
			}
			else if(xBounce && yBounce) {
				xLeft = xLeft - dx;
				yTop = yTop -dy;
				repaint();
	
			}
		}
		else if(xLeft + 35 > RectangleFrame.getFrameWidth()  && yTop + 70<= RectangleFrame.getFrameHeight()){
			xBounce = !xBounce;
			xLeft = xLeft - 1; 
			if(yBounce) {
				yTop = yTop -1;
			}
			else {
				yTop = yTop + 1;
			}
			repaint();
		}
		else if(xLeft + 35 <= RectangleFrame.getFrameWidth()  && yTop + 70 > RectangleFrame.getFrameHeight()) {
			yBounce = !yBounce;
			yTop = yTop - 1; 
			if(xBounce) {
				xLeft = xLeft -1;
			}
			else {
				xLeft = xLeft + 1;
			}
			repaint();
		}
		
		else if(xLeft + 35 < 35  && yTop + 70 <= RectangleFrame.getFrameHeight()) {
			xBounce = !xBounce;
			xLeft = xLeft + 1;
			if(yBounce) {
				yTop = yTop -1;
			}
			else {
				yTop = yTop + 1;
			}
			repaint();
		}
		
		else if(xLeft + 35 <= RectangleFrame.getFrameWidth()  && yTop + 70 < 70) {
			yTop = yTop + 1;
			yBounce = !yBounce;
			if(yBounce) {
				yTop = yTop -1;
			}
			else {
				yTop = yTop + 1;
			}
			repaint();
		}
		
		else if(xLeft + 35 < 35  && yTop + 70 < 70) {
			xLeft = xLeft + 1;
			yTop = yTop + 1;
			xBounce = !xBounce; 
			yBounce = !yBounce;
			repaint();
		}
		
		
			
	}
}

	

