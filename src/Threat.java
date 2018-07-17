import java.awt.Point;

public abstract class Threat implements Displayable{
	public int id;
	public int strength;
	public int speed;
	boolean is_alive;
	boolean is_attacked;
	Point location;
	
	public abstract void move(); 
	
	public void reduce_strength() {
		strength--;
		if (strength==0) {
			is_alive = false; 
			}
	}
}
