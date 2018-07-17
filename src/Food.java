import java.awt.Color;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

public class Food implements Displayable{
	Point location;
	boolean is_consumed;
	
	public Food(Point p) {
		location = p;
		is_consumed = false;
	}
	
	@Override
	public java.awt.Shape getShape(){			
		Shape oval = new Ellipse2D.Float(location.x, location.y, 10, 10);
		return oval;
	}
	
	@Override
	public Color getColor(){
		return Color.orange;
	}
	
	@Override
	public String getString() {
		return "food";
	}
	
	@Override
	public java.awt.Point getStringPosition(){
		return location;
	}
}
