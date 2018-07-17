import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.event.*;
import java.io.IOException;
import java.lang.IllegalArgumentException;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;

public class GUI_for_Displayable extends JFrame implements MouseListener{
	
	private int offset = 12;
	private float fontSize = 10.5f;
	private Graphic p;
	private Color bg = null;
	private BufferedImage img = null;
	public ArrayList<Displayable> elements;
	private ArrayList<MouseEvent> clics;
	private JTextField jtf;
	

	public GUI_for_Displayable(String title, int width, int height, Color bg){
		super(title);
		this.elements = new ArrayList<Displayable>();
		this.clics = new ArrayList<MouseEvent>();
		this.bg = bg;
		this.setLayout(new BorderLayout());
		this.p = new Graphic();
		this.p.addMouseListener(this);
		this.getContentPane().add(this.p,BorderLayout.CENTER);
		jtf = new JTextField(20);
		this.getContentPane().add(jtf,BorderLayout.SOUTH);
		this.setSize(width,height);
		this.setResizable(false);
		this.setLocation((java.awt.Toolkit.getDefaultToolkit().getScreenSize().width-this.getWidth())/2,(java.awt.Toolkit.getDefaultToolkit().getScreenSize().height-this.getHeight())/2);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	

	public GUI_for_Displayable(String title, String imageFile) throws IOException{
		super(title);
		this.elements = new ArrayList<Displayable>();
		this.clics = new ArrayList<MouseEvent>();
		this.bg = Color.BLACK;
		this.img = ImageIO.read(new File(imageFile));
		this.setLayout(new BorderLayout());
		this.p = new Graphic();
		this.p.addMouseListener(this);
		this.getContentPane().add(this.p,BorderLayout.CENTER);
		jtf = new JTextField(20);
		this.getContentPane().add(jtf,BorderLayout.SOUTH);
		this.setSize(img.getWidth(),img.getHeight()+48);
		this.setResizable(false);
		this.setLocation((java.awt.Toolkit.getDefaultToolkit().getScreenSize().width-this.getWidth())/2,(java.awt.Toolkit.getDefaultToolkit().getScreenSize().height-this.getHeight())/2);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	public void setBackground(Color background) {
		this.bg = background;
	}
	
	public void updateView() {
		this.p.repaint();
	}
	
	public void addDisplayable(Displayable d){
		this.elements.add(d);
		this.p.repaint();
	}
	

	public boolean removeDisplayable(Displayable d){
		if(this.elements.remove(d)){
			this.p.repaint();
			return true;
		}
		else 
			return false;
	}
	
	public void mouseClicked(MouseEvent e){
		this.clics.add(e);
	}
	
	public void mouseEntered(MouseEvent e){}
	
	public void mouseExited(MouseEvent e){}
	
	public void mousePressed(MouseEvent e){}
	
	public void mouseReleased(MouseEvent e){}
	
	
	public MouseEvent getClic(){
		if(this.clics.isEmpty()) return null;
		else return this.clics.remove(0);
	}
	
	private class Graphic extends JPanel{
		public void paint(Graphics gr){
			if(GUI_for_Displayable.this.bg != null){
				gr.setColor(GUI_for_Displayable.this.bg);
				gr.fillRect(0,0,this.getWidth(),this.getHeight());
				gr.setColor(Color.DARK_GRAY);
				for (int i=100 ; i<500 ; i+=100) {
					gr.drawLine(i, 0, i, this.getHeight());
					gr.drawLine(0, i, this.getWidth(), i);
				}
			}
			if(GUI_for_Displayable.this.img != null){
				gr.drawImage(GUI_for_Displayable.this.img,0,0,this);
			}
			for(Displayable d:GUI_for_Displayable.this.elements){
				gr.setColor(d.getColor());
				Shape sh = d.getShape();
				if(sh instanceof java.awt.geom.Line2D || sh instanceof java.awt.geom.Path2D){
					((Graphics2D) gr).setStroke(new BasicStroke(5.0f));
					((Graphics2D) gr).draw(sh);
				}
				else { 
					((Graphics2D) gr).fill(sh);
					}
				gr.setColor(Color.black);
				Font f = gr.getFont().deriveFont(Font.BOLD);
				f = f.deriveFont(fontSize+2);
				gr.setFont(f);
				Point p = d.getStringPosition();
				String[] s = d.getString().split("\n");
				/*int max = 0;
				for(int i=1;i<s.length;i++){if(s[i].length()>s[max].length()) max = i;}
				Rectangle2D r2d = f.getStringBounds(s[max],((Graphics2D) gr).getFontRenderContext());
				gr.setColor(Color.WHITE);
				gr.fillRect(p.x,p.y,(int) r2d.getWidth(),(int) (r2d.getHeight()*s.length+((s.length-1)*GUI_for_Displayable.this.offset)));
				gr.setColor(Color.BLACK);*/
				for(int i=0;i<s.length;i++) gr.drawString(s[i],p.x,p.y+(i+1)*GUI_for_Displayable.this.offset);
			}
		}
	}
	

	public void displayMessage(String m){
		JOptionPane.showMessageDialog(this,m);
	}
	

	public void setBottomFieldText(String s){
		this.jtf.setText(s);
	}	
	
}
