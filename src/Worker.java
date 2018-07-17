import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

public class Worker extends Ant{
	boolean food_found = false;
	boolean mat_found = false;
	ArrayList<Point> path;
	
	public Worker(Point p, int i, int born, Nest n){
		life = 8*7; // weeks*7days
		eating_cap = 1; // food units
		is_alive = true;
		location = p;
		id = i;
		born_date = born;	//store the date when ant was born
		speed = 20;
		starve_days = 2;	//starvation capacity
		path = new ArrayList<Point>();	// store the path followed to find food and material
		this.n = n;
	}
	
	@Override
	public void move() {
		//search food and material
		path.add(new Point(this.location));
		Random r = new Random();
		int ran = r.nextInt(4);
		int x= 0 ; int y=0;
		switch (ran) {
			case 0:	//move up
				y = -speed;
				break;
			case 1:	//move down
				y = speed;
				break;
			case 2:	//move left 
				x = -speed;
				break;
			case 3:	//move right
				x = speed;
				break;	
			}
		int newX = location.x+x;
		int newY = location.y+y;
		if ((!(newX<=10 || newX>=480))&&(!(newY<=10 || newY>=480)))
			location.setLocation(newX, newY);
	}
	
	public void go_back() {
		//return back to nest using trails
		if (path.size()==0) {	// if the list has been traversed then go find food again
			if (food_found)
				n.addFood();	//add food in the store
			else
				n.addMaterial();
			food_found = false;
			mat_found = false;
			//System.out.println(Nest.foodstore);
		}
		else {
			location.setLocation(path.get(path.size()-1));	//get the last position of the ant
			path.remove(path.size()-1);	//remove the last index
		}
	}
	
	@Override
	public java.awt.Shape getShape(){			
		Shape oval = new Ellipse2D.Float(location.x, location.y, 20, 20);
		//Shape line = new Line2D.Float(x1, y1, x1+4, y1+4);
		return oval;
	}
	
	@Override
	public Color getColor(){
		return Color.magenta;
	}
	
	@Override
	public String getString() {
		return "worker"+id;
	}
	
	@Override
	public java.awt.Point getStringPosition(){
		return location;
	}
}
