import java.awt.Color;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

public class BigFood extends Food implements Displayable{
	ArrayList<Food> pile;
	
	public BigFood(Point p) {
		super(p);
		pile = new ArrayList<Food>();
		for (int i=0 ; i<3 ; i++) {	//add 4 food units in pile
			pile.add(new Food(new Point(p.x,p.y)));
		}
	}
	
	@Override
	public java.awt.Shape getShape(){			
		Shape oval = new Ellipse2D.Float(location.x, location.y, 20, 20);
		return oval;
	}
	
	@Override
	public Color getColor(){
		return Color.orange;
	}
	
	@Override
	public String getString() {
		return "Big food\nfood="+pile.size();
	}
	
	@Override
	public java.awt.Point getStringPosition(){
		return location;
	}
}
