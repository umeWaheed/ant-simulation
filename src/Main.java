import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.Timer;

public class Main {
	
	static Queen q = new Queen(new Point(30,30));
	static Nest n1 = new Nest(new Point(q.getStringPosition().x,q.getStringPosition().y+15));
	static int  current_date = 0;	//days that have passed since execution
	static int date_timer = 0; 
	static int died = 0;	//the total ants died
	static ArrayList<Queen> queens = new ArrayList<Queen>();
	static ArrayList<Nest> nest = new ArrayList<Nest>();
	static ArrayList<Ant> ants = new ArrayList<Ant>();	//holds the ants currently in display
	static ArrayList<Material> mat = new ArrayList<Material>();	//the materials in display
	static ArrayList<Food> fg = new ArrayList<Food>();	//the food in display
	static ArrayList<Threat> threats = new ArrayList<Threat>();	//the threats in display
	static int wid =500;	//width of the window
	static int height = 500;	//height of the window
	static GUI_for_Displayable g = new GUI_for_Displayable("Ant colony",wid,height,Color.lightGray);
	static Ant ant1;
	static Point location;
	static boolean war_started = false;	 //indicates if war has been started or not
	
	public static void increment_day() {
		date_timer+=1;
		String textMessage = "";
		String text = "Days passed= "+current_date;
		ArrayList<Ant> consumed_ants = new ArrayList<Ant>();	//the ants that have died
		
		if (current_date==180 && date_timer==1) {
			g.displayMessage("Flood!!!!");
			flood();
		}
		
		if (current_date==100 && date_timer==1) {	//when n months have passed give birth to fertilized female
			if (queens.size()<2) {
			Queen q2 = new Queen(new Point(300,300));
			g.addDisplayable(q2);
			queens.add(q2);
			Nest n2 = new Nest(new Point(q2.location.x,q2.location.y+10));
			nest.add(n2);
			q2.n = n2;
			g.setBackground(Color.lightGray);
			g.addDisplayable(n2);
			g.displayMessage("New nest has been created");
			}
			
		}
		
		if (current_date==150 && date_timer==1) {
			war_started = true;
			g.displayMessage("War!!!");
		}
		
		if (date_timer==5) {
			date_timer = 0;
			current_date++;
		
			for (Ant a : ants) {
				if (a.life == (int)current_date-a.born_date) { // check if life of ant is over
					a.is_alive = false;
					died++;
					textMessage+=a.getString()+" ";
					consumed_ants.add(a);
				}
			}
			for (Ant a : consumed_ants) {
				if (g.removeDisplayable(a))
				 System.out.print(a.id+" is dying no life "+g.elements.size());
				ants.remove(a);
				System.out.println(" "+ants.size());
			}
			if (consumed_ants.size()>0)	//if any ant is dead display message
				g.displayMessage(textMessage+" are dead. Life period is over");
		}
		g.setBottomFieldText(text+"  Died ants = "+died);	//display total days passed and ants that are dead
	}
	
	static Timer move_ants = new Timer(100,new ActionListener() {
		@Override 
		public void actionPerformed(ActionEvent e) {
			if (!war_started) {
				for (Ant a : ants) {
					if (a instanceof Worker)
						if (((Worker)a).food_found || ((Worker)a).mat_found)	//if worker has found any food or material then return to nest
							((Worker)a).go_back();
						else
							a.move();
					else		// a warrior will move simply
						a.move();
				}
				
			for (Threat t : threats) {
				t.move();
			}
			check_collision();	//check collisions between objects
			increment_day();	//add to days
			g.updateView();		//update the current view
		}
			else {
				for (Ant a : ants) {
					if (a instanceof Warrior) {
						//only warrior ants move in war
						a.move();
					}
				}
				war();
				increment_day();
				g.updateView();
			}
			
		}
	});

	
	static Timer new_resources = new Timer(30000, new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (!war_started) {	//if war has started do not add resources
				if (fg.size()<10) {	// if food on display<10 then add new food
					addMaterial();
					addFood();
				}
				addThreat();
				addAnt();
			}
		}
	});
	
	static Timer eat_food = new Timer(50000, new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			String text = "No food for ";
			ArrayList<Ant> consumed_ants = new ArrayList<Ant>();
			
			for (Nest n : nest) {
			ArrayList<Ant> war = new ArrayList<Ant>();
			ArrayList<Ant> work = new ArrayList<Ant>();
			if (n.isEmpty()) {	//if nest is empty then there is no food
				g.displayMessage("No food");
				g.displayMessage("No food, queen is dying");
				q.is_alive = false;
				g.removeDisplayable(q);
				queens.remove(q);
			//end simulation
			}
			else {
				System.out.println("eating");
				boolean success = n.removeFood(q.eating_cap);	//feed queen according to capacity
				if (!success) {									//if there is not enough food for queen, it dies
					g.displayMessage("No food, queen is dying");
					q.is_alive = false;
					g.removeDisplayable(q);
					queens.remove(q);
				//end simulation
				}
				else {		//feed other ants
					for (Ant a : ants) {
						if (a instanceof Worker && a.n == n)
							work.add(a);
						if (a instanceof Warrior && a.n == n)
							war.add(a);
					}
					for (Ant a : work) {	//feed workers first
						success = n.removeFood(a.eating_cap);
						if (!success) {	//no food for ant
							if (a.starve_days>0) {
								a.starve_days--;	//starve for that day
								a.speed-=10;		//reduce speed of ant
							}
							else {					//if the starvation capacity is over ant dies
								a.is_alive = false;	
								consumed_ants.add(a);
								System.out.println("No food for "+a.id);
								text+=a.id+" ";
							}
						}
					}
					for (Ant a : war) {
						success = n.removeFood(a.eating_cap);
						if (!success) {	//no food for ant
							if (a.starve_days>0) {
								a.starve_days--;	//starve for that day
								a.speed-=10;
							}
							else {
								a.is_alive = false;
								consumed_ants.add(a);
								System.out.println("No food for "+a.getString());
								text+=a.getString()+" ";
							}
						}
					}
				
				for (Ant a : consumed_ants) {
					ants.remove(a);	//remove the ant from list
					System.out.println("No food for "+a.getString());
					text+=a.getString()+" ";
					g.removeDisplayable(a);	//remove from display
				}
				if (consumed_ants.size()>0)
					g.displayMessage(text);
					}
			}
			}
		}
	});
	
	public static void check_collision() {
		ArrayList<Food> consumed_food = new ArrayList<Food>();
		ArrayList<Threat> consumed_threat = new ArrayList<Threat>();
		ArrayList<Ant> consumed_ants = new ArrayList<Ant>();
		ArrayList<Material> consumed_mat = new ArrayList<Material>();
		for (Ant a : ants) {
			//a worker can collide with food, material and threats
			if (a instanceof Worker) {	
				//only worker can pick up food
				for (Food f : fg) {
					//check location of food and if ant does not has food or material already
					if ((a.location.x-10<f.location.x && f.location.x<a.location.x+10) && 
							(a.location.y-10<f.location.y && f.location.y<a.location.y+10) &&
							!((Worker)a).food_found && !((Worker)a).mat_found){
						
						if (f instanceof BigFood) {	
							//pick one food from pile and share location with others 
							
							if (((BigFood) f).pile.size()>0) {	//more food in pile
								((Worker) a).food_found = true; // the food has been found now go back
								((BigFood) f).pile.remove(0);	//remove food from pile
								ant1 = a;
								location = f.location;
								if (!comm.isRunning())
									comm.start();
							}
							if (((BigFood) f).pile.size()==0){	//only one unit left
								//((Worker) a).food_found = true; // the food has been found now go back
								f.is_consumed = true;
								g.removeDisplayable(f);
								//((BigFood) f).pile.remove(0);
								consumed_food.add(f);
								comm.stop();
							}
							
						}else {	//simple food can be carried by single ant
							f.is_consumed = true;
							consumed_food.add(f);				
							((Worker) a).food_found = true; // the food has been found now go back
							g.removeDisplayable(f);	//remove the eaten food from display
						}
					}
				}
				//check collision with threats. a threat can attack worker if it is not attacked itself by a warrior
				for (Threat t : threats) {
					if ((a.location.x-10<t.location.x && t.location.x<a.location.x+10) && 
							(a.location.y-10<t.location.y && t.location.y<a.location.y+10) && !t.is_attacked){
						// the ant has been eaten by threat
						consumed_ants.add(a);
						a.is_alive= false;
					}
				}
				//check collision with material and pick it up
				for (Material m : mat) {
					if ((a.location.x-10<m.location.x && m.location.x<a.location.x+10) && 
							(a.location.y-10<m.location.y && m.location.y<a.location.y+10) &&
							!((Worker)a).food_found && !((Worker)a).mat_found){
						m.is_consumed = true;	//the material has been picked
						((Worker) a).mat_found = true;	//the worker has found material
						consumed_mat.add(m);			
						g.removeDisplayable(m);			//remove the material from display
					}
					}
				}
			else {	
				//a warrior ant can only collide with threats
				for (Threat t : threats) {
					if ((a.location.x-20<t.location.x && t.location.x<a.location.x+20) && 
							(a.location.y-20<t.location.y && t.location.y<a.location.y+20)){
						if (!((Warrior)a).is_attacking) {
							((Warrior)a).is_attacking = true;	//attack the threat 't'
							t.is_attacked = true;	//set threat as being attacked
						}
						t.reduce_strength();
						//continue attacking unless the threat dies
						if (!t.is_alive) {		
							g.removeDisplayable(t);	//remove threat from display when dead
							Food f = new Food(new Point(t.location));
							g.addDisplayable(f);	//change the corpse into food
							fg.add(f);
							consumed_threat.add(t);
							((Warrior)a).is_attacking = false;	//reset the attacking ability of ant
						}
					}
				}
			}
		}
		//update lists
		for (Material m : consumed_mat)
			mat.remove(m);
		for (Threat t : consumed_threat)
			threats.remove(t);
		for (Food f : consumed_food)
			fg.remove(f);
		for (Ant a : consumed_ants) {
			ants.remove(a);
			if(g.removeDisplayable(a))
				System.out.println("removed ant "+a.id);
			}
	}
	
	static Timer comm = new Timer(10000, new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			//communicate location with another ant
			Ant ant2;	//get a worker ant from list other than the current ant	
			for (Ant a : ants) {
				if (a instanceof Worker && a.id != ant1.id) {
					ant2 = a;
					ant2.location = location;	//set location to the food's location
					((Worker)ant2).path = ((Worker)ant1).path;	//set path of the second ant
					break;
					}
				}
		}
	});
	
	//add threat at random location 2 worms and 1 wasp at a time
	public static void addThreat() {
		Random r;
		for (int i=0 ; i<2 ; i++) {
			r = new Random();
			Threat t = new Worm(new Point(r.nextInt(wid-50),r.nextInt(height-50)));
			g.addDisplayable(t);
			threats.add(t);
		}
		r = new Random();
		Threat t = new Wasp(new Point(r.nextInt(wid-50),r.nextInt(height-50)));
		g.addDisplayable(t);
		threats.add(t);
	}
		
	// adds ants according to the queen's ability 
	public static void addAnt() {
		for (Queen q : queens) {
			ants = q.give_birth(current_date);
			for (Ant a : ants)
				g.addDisplayable(a);
		}
	}
	//adds food from the food generator class
	public static void addFood() {
		Random r = new Random();
		BigFood bf = new BigFood(new Point(r.nextInt(wid-50),r.nextInt(height-50)));
		g.addDisplayable(bf);
		fg.add(bf);
		
		for (int i=0 ; i<5 ; i++) {
			r = new Random();
			Food f = new Food(new Point(r.nextInt(wid-50),r.nextInt(height-50))); 
			fg.add(f);
			g.addDisplayable(f);
		}
	}
	
	//adds material 
	public static void addMaterial() {
		Random r = new Random();
		for (int i=0 ; i<10 ; i++) {
			Material m = new Material(new Point(r.nextInt(wid-50),r.nextInt(height-50))); 
			mat.add(m);
			g.addDisplayable(m);
		}
	}
	
	public static void flood() {
		for (Nest n : nest) {
			n.foodstore = 0;
			n.material = 0;
		}
		addFood();
		addMaterial();
		g.setBackground(Color.cyan);
		g.updateView();
	}
	
	public static void war() {
		ArrayList<Ant> hill1 = new ArrayList<Ant>();
		ArrayList<Ant> hill2 = new ArrayList<Ant>();
		ArrayList<Ant> consumed = new ArrayList<Ant>();
		
		for (Ant a : ants) {
			if (a instanceof Warrior) {
				if(a.n == nest.get(0))
					hill1.add(a);	//the residents of nest1
				else	
					hill2.add(a);	//the residents of nest2
			}
			else {
				a.location = a.n.location;	//simple ants return to nest and protect queen
			}
		}
		
		for (Ant a : hill1) {
			for (Ant a1 : hill2) {
				if ((a.location.x-20<a1.location.x && a1.location.x<a.location.x+20) && 
						(a.location.y-20<a1.location.y && a1.location.y<a.location.y+20)){
					a1.is_alive = false;
					a.is_alive = false;
					consumed.add(a1);
					consumed.add(a);	//both ants die
				}
			}
		}
		for (Ant a : consumed) {
			g.removeDisplayable(a);
			ants.remove(a);
		}
	}
	
	public static void main(String[]args) {
		//add food
		addFood();
		
		//add queen
		g.addDisplayable(q);
		q.n = n1; //add nest for queen
		System.out.println(q.n);
		
		//add ants
		queens.add(q);
		nest.add(n1);
		addAnt();
		
		//add threats
		addThreat();
		
		//add materials
		addMaterial();
		
		//add nest
		g.addDisplayable(n1);
		
		//move ants
		move_ants.start();
		
		//eat food
		eat_food.start();
		
		new_resources.start();
	}
}
