import java.awt.Rectangle;
import java.util.ArrayList;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Teleporter {

	private int xpos1;
	private int ypos1;
	private int xpos2;
	private int ypos2;
	private int xsize;
	private int ysize; 
	public  int teleid;
	private Color color;
	public Image t1;
	public Image t2;

	public Teleporter(int x1, int y1, int x2,int y2, int id) throws SlickException {
		this.xpos1  = x1;
		this.ypos1 = y1;
		this.xpos2 = x2;
		this.ypos2 = y2;
		t1=new Image("Data/t1.png");
		t2=new Image("Data/t2.png");
		xsize = 20;
		ysize = 20;
		teleid=id;
	}//Ends constructor

	public ArrayList<Rectangle> getBounds() {
		ArrayList<Rectangle> positions = new ArrayList<Rectangle>();
		positions.add(new Rectangle(xpos1, ypos1, xsize, ysize));
		positions.add(new Rectangle(xpos2, ypos2, xsize, ysize));
		return positions;
	}//Ends getBounds
	
	public Color getColor() {
		return color;
	}//Ends get color

}//Ends Teleporter