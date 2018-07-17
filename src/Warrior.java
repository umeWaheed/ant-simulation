import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.util.Random;
import java.awt.Point;

public class Warrior extends Ant{
	boolean is_attacking;
	
	public Warrior(Point p, int i, int born, Nest n){
		life = 7*30; //months*30days
		eating_cap = 1; // food units
		starve_days = 3;
		is_alive = true;
		is_attacking = false;
		location = p;
		id = i;
		born_date = born;
		speed = 30;
		this.n = n;
	}
	
	@Override
	public void move() {
		//search for enemies
		if (!is_attacking) {
			Random r = new Random();
			int ran = r.nextInt(4);
			int x= 0 ; int y=0;
			switch (ran) {
				case 0:	//move up
					y = -speed;
					//System.out.println("moving up");
					break;
				case 1:	//move down
					y = speed;
					//System.out.println("moving down");
					break;
				case 2:	//move left 
					x = -speed;
					//System.out.println("moving left");
					break;
				case 3:	//move right
					x = speed;
					//System.out.println("moving right");
					break;	
			}
			int newX = location.x+x;
			int newY = location.y+y;
			if ((!(newX<=10 || newX>=480))&&(!(newY<=10 || newY>=480)))
				location.setLocation(newX, newY);
		}
	}
	
	/*public void attack(Threat t) {
		is_attacking=true;
		//if a threat is found attack it
		t.reduce_strength();
	}*/
	
	@Override
	public java.awt.Shape getShape(){			
		Shape oval = new Ellipse2D.Float(location.x, location.y, 20, 20);
		return oval;
	}
	
	@Override
	public Color getColor(){
		return Color.green;
	}
	
	@Override
	public String getString() {
		return "warrior"+id;
	}
	
	@Override
	public java.awt.Point getStringPosition(){
		return location;
	}
}
