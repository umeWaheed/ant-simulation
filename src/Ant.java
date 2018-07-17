import java.awt.Point;

public abstract class Ant implements Displayable{
	public int life;
	public int id;
	public boolean is_alive;
	public int born_date;
	public int eating_cap;
	public Point location;
	public int speed;
	public int starve_days;
	public Nest n;
	
	public void die() {
		if (!is_alive){
			//die and remove from the screen
		}
	}
	
	public void move() {
		//queen doesnot move
		//warriors and workers do
	}
	
	public void eat() {
		//eat food according to capacity if it is available else die
		//subtract amount from the food store 
	}
}
