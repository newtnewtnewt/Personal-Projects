package DotDrawer;
/* Name: Noah Dunn
 * Class: CSE 271 A
 * Date: 4/24/2018
 * File: DotFrame
 * Objective: Create a frame that can draw points and a triangle.
 */
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class DotFrame extends JFrame { //Construct a subclass of JFrame for building
	private int mouseClicks = 0;
	private int xVal1 = 0;
	private int yVal1 = 0;
	private int xVal2 = 0;
	private int yVal2 = 0;
	private int xVal3 = 0;
	private int yVal3 = 0;
	private JComponent paintArea;
	private boolean mouseClicked = false;
	
	public DotFrame() { //Constructor for the actual frame
		this.setSize(500,500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel panel = new JPanel();
		paintArea = new PainterComponent();             //Create the component to do the painting
		paintArea.addMouseListener(new ClickListener()); //Activate the Mouse Listener in the paint area
		panel.add(paintArea);
		this.add(panel,BorderLayout.CENTER);
		this.setVisible(true);
	}
		
	
	
	class ClickListener implements MouseListener{
		
		@Override
		public void mouseClicked(MouseEvent arg0) { //Grabs x and y values based on which how many times clicked
			if(mouseClicks == 0) {
				xVal1 = arg0.getX();
				yVal1 = arg0.getY();
				mouseClicked = true;
				paintArea.repaint();
				
			}
			else if(mouseClicks == 1) {
				mouseClicked = true;
				xVal2 = arg0.getX();
				yVal2 = arg0.getY();
				paintArea.repaint();
			}
			else if(mouseClicks == 2) {
				mouseClicked = true;
				xVal3 = arg0.getX();
				yVal3 = arg0.getY();
				paintArea.repaint();
			}
		}
		

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
	}
		class PainterComponent extends JComponent{   //Create a painting board as a subclass of JComponent 
			public PainterComponent() {
				this.setPreferredSize(new Dimension(500,500));
				
			}
				public void paintComponent(Graphics g) { //Paints based on number of clicks 
					g.clearRect(0, 0, this.getWidth(), this.getWidth());   //Make the canvas white
					if(mouseClicks == 0 && mouseClicked) { //Place the first point
						g.drawOval(xVal1, yVal1, 5, 5);                
						mouseClicks++;
						mouseClicked = false;
					}
					
					else if(mouseClicks == 1 && mouseClicked) { //Place the second point and draw a line between the two
					g.drawOval(xVal1, yVal1, 5, 5);
					g.drawOval(xVal2, yVal2, 5, 5);
					g.drawLine(xVal1, yVal1, xVal2, yVal2);
					mouseClicks++;
					mouseClicked = false;
					}
					else if(mouseClicks == 2 && mouseClicked) { //Place the third point and fill the triangle
					g.drawOval(xVal1, yVal1, 5, 5);
					g.drawOval(xVal2, yVal2, 5, 5);
					g.drawOval(xVal3, yVal3, 5, 5);
					g.drawLine(xVal1, yVal1, xVal2, yVal2);
					g.drawLine(xVal1, yVal1, xVal3, yVal3);
					g.drawLine(xVal2, yVal2, xVal3, yVal3);
					mouseClicks = 0;
					mouseClicked = false;
					}
			}
		}
}
	
		
		
	


