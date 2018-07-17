import java.awt.Color;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

public class Nest implements Displayable{
	int foodstore=30;
	int material = 0;
	Point location;
	
	public Nest(Point p) {
		location = p;
	}
	
	public void addFood() {
		foodstore++;
	}
	
	public void addMaterial() {
		material++;
	}
	
	public boolean removeFood(int amount) {
		//remove food from store
		if (foodstore>=amount) {
			foodstore-=amount;
			System.out.println(foodstore);
			return true;
			}
		else { 
			return false;
			}
	}
	
	public boolean isEmpty() {
		if (foodstore==0)	//return true if no more food is available
			return true;
		else
			return false;
	}
	
	@Override
	public java.awt.Shape getShape(){			
		Shape oval = new Ellipse2D.Float(location.x, location.y, 20, 20);
		//Shape line = new Line2D.Float(x1, y1, x1+4, y1+4);
		return oval;
	}
	
	@Override
	public Color getColor(){
		return Color.cyan;
	}
	
	@Override
	public String getString() {
		return "food: "+foodstore+"\n materials: "+material;
	}
	
	@Override
	public java.awt.Point getStringPosition(){
		return location;
	}
}
