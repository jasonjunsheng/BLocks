import java.awt.Rectangle;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Enemy {
	private int xpos;
	private int ypos;
	private int speed;
	private int xsize;
	private int ysize; 
	public  int eid;
	private int pattern;
	private Color color;
	public boolean up, down, left, right;
	public Image b;
	public int osci;
	/**The constructor sets the blocks initial configuration like id, color, xpos, ypos.
	 * 
	 * @param  xpos  x position on screen
	 * @param  ypos  y position on screen
	 * @param  id contains the id for reference purpose
	 * @param  a  contains the color 	 
	 * @throws SlickException 
	 * */
	public Enemy(int xpos, int ypos, int id, int p) throws SlickException {
		this.xpos  = xpos;
		this.ypos = ypos;
		speed = 1;
		xsize = 20;
		ysize = 20;
		eid=id;
		color=Color.red;
		pattern = p;
		osci=20;
		if(p==1){
			setUp();
			color=color.red;
		}
		else{
			color=color.pink;
			setRight();
		}
	}//Ends constructor

	public void count(){
		osci++;
	}
	public void moveLeft(){
		xpos=xpos-speed;
	}//ends moveLeft
	
	public void moveRight(){
		xpos=xpos+speed;
	}//Ends moveRight
	
	public void moveUp(){
		ypos=ypos-speed;
	}//ends moveLeft
	
	public void moveDown(){
		ypos=ypos+speed;
	}//Ends moveRight
	
	public Rectangle getBounds() {
		return new Rectangle(xpos, ypos, xsize, ysize);
	}//Ends getBounds
	
	public Color getColor(){
		return color;
	}//Ends getColor
	
	public int getPattern(){
		return pattern;
	}
	
	public void setUp(){
		up = true;
		down= false;
		left = false;
		right = false;
	}
	
	public void setDown(){
		up = false;
		down= true;
		left = false;
		right = false;
	}
	
	public void setLeft(){
		up = false;
		down= false;
		left = true;
		right = false;
	}
	
	public void setRight(){
		up = false;
		down= false;
		left = false;
		right = true;
	}
}//Ends enemy
