import java.awt.Color;
import java.awt.Shape;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.util.Random;


public class Worm extends Threat{
	public Worm(Point loc) {
		//this.id = id;
		strength = 10;
		is_alive=true;
		speed = 20;
		location = loc;
		is_attacked = false;
	}
	
	@Override
	public void move() {
		if (!is_attacked) {
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
	
	@Override
	public java.awt.Shape getShape(){			
		Shape oval = new Ellipse2D.Float(location.x, location.y, 40, 10);
		//Shape line = new Line2D.Float(x1, y1, x1+4, y1+4);
		return oval;
	}
	
	@Override
	public Color getColor(){
		return Color.red;
	}
	
	@Override
	public String getString() {
		return "worm\nstrength: "+strength;
	}
	
	@Override
	public java.awt.Point getStringPosition(){
		return location;
	}
	
}
