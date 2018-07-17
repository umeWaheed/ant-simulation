import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.Point;
import java.util.ArrayList;

public class Queen extends Ant{
	int pace,work,war;
	int work_num,war_num =1;
	ArrayList<Ant> ants = new ArrayList<Ant>();
	
	public Queen(Point p) {
		life = 10*365;	// 10 years of life
		is_alive=true;
		pace = 40; //after 40 days new ants will be produced
		work = 3;	// n workers will be produced at a time
		war = 2;	// n warriors will be produced
		eating_cap= 5; // food units 
		location = p;
		id = 0;
	}
	
	public ArrayList<Ant> give_birth(int date) {
		for (int i=0 ; i<work ; i++) {
			//produce workers
			ants.add(new Worker(new Point(this.getStringPosition().x,this.getStringPosition().y),work_num, date, n));
			work_num++;
		}
		for (int i=0 ; i<war ; i++) {
			//produce warriors
			ants.add(new Warrior(new Point(this.getStringPosition().x,this.getStringPosition().y),war_num, date, n));
			war_num++;
		}
		return ants;
	}
	
	@Override
	public java.awt.Shape getShape(){			
		Shape oval = new Ellipse2D.Float(location.x, location.y, 20, 20);
		//Shape line = new Line2D.Float(x1, y1, x1+4, y1+4);
		return oval;
	}
	
	@Override
	public Color getColor(){
		return Color.yellow;
	}
	
	@Override
	public String getString() {
		return "Queen!!";
	}
	
	@Override
	public java.awt.Point getStringPosition(){
		return location;
	}
}
