import java.awt.Color;
import java.awt.Shape;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.util.Random;


public class Wasp extends Threat{
	public Wasp(Point loc) {
		strength = 20;
		is_alive=true;
		speed = 40;
		location = loc;
		is_attacked = false;
	}
	
	@Override
	public void move() {
		if (!is_attacked) {
			Random r = new Random();
			int ran = r.nextInt(4);
			int y=0;
			switch (ran) {
				case 0:	//move up
					y = -speed;
					break;
				case 1:	//move down
					y = speed;
					break;
			}
			//int newX = location.x+x;
			int newY = location.y+y;
			if (!(newY<=10 || newY>=480))
				location.y = newY;
		}
	}
	
	@Override
	public java.awt.Shape getShape(){			
		Shape oval = new Ellipse2D.Float(location.x, location.y, 20, 20);
		return oval;
	}
	
	@Override
	public Color getColor(){
		return Color.yellow;
	}
	
	@Override
	public String getString() {
		return "wasp\nstrength: "+strength;
	}
	
	@Override
	public java.awt.Point getStringPosition(){
		return location;
	}
	
}
