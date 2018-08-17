package OlympicRings;
/* Name: Noah Dunn
 * Class: CSE 271 A
 * Date: 4/24/2018
 * File: OlympicRingSingle
 * Objective: Create and paint the olympic rings
 */
public class OlympicRingSingle { //An object to provide for individual ring data. 
	String color;
	int x;
	int y;
	public OlympicRingSingle(String color, int x, int y) {
		this.color = color;
		this.x=x;
		this.y=y;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
}
