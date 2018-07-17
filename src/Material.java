import java.awt.Color;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

public class Material implements Displayable{
	Point location;
	boolean is_consumed;
	
	public Material(Point p) {
		location = p;
		is_consumed = false;
	}
	
	@Override
	public java.awt.Shape getShape(){			
		Shape oval = new Ellipse2D.Float(location.x, location.y, 10, 10);
		//Shape line = new Line2D.Float(x1, y1, x1+4, y1+4);
		return oval;
	}
	
	@Override
	public Color getColor(){
		return Color.GREEN;
	}
	
	@Override
	public String getString() {
		return "material";
	}
	
	@Override
	public java.awt.Point getStringPosition(){
		return location;
	}
}
