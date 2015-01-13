import java.awt.Rectangle;
import java.util.ArrayList;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;


/**This class is the Block or the puzzle piece that the user controls
 * 
 * @author Craig Blumenfeld
 * @author Jason Ma
 * @version 1.2 April 17, 2013
 */

public class Block {

	private int xpos;
	private int ypos;
	private int speed;
	private int xsize;
	private int ysize; 
	public  int blockid;
	private Color color;
	public Image b;
	public boolean movable;
	/**The constructor sets the blocks initial configuration like id, color, xpos, ypos.
	 * 
	 * @param  xpos  x position on screen
	 * @param  ypos  y position on screen
	 * @param  id contains the id for reference purpose
	 * @param  a  contains the color 	 
	 * @throws SlickException 
	 * */
	public Block(int xpos, int ypos, int id) throws SlickException {
		this.xpos  = xpos;
		this.ypos = ypos;
		speed = 1;
		xsize = 20;
		ysize = 20;
		blockid=id;
		movable=true;
		if(id==1){
			color=(new Color(249,152,152)); //red
			b=new Image("Data/b1.png");
		}else if(id==2){
			color=(new Color(152,249,152)); //green
			b=new Image("Data/b2.png");
		}else if (id==3){
			color=(new Color(255,228,100)); //yellow
			b=new Image("Data/b3.png");
		}else if(id==4){
			color=(new Color(152,152,249));  //blue
			b=new Image("Data/b4.png");
		}

	}//Ends constructor

	/**moves block position to the left	 */
	public void moveLeft(){
		if(movable==true)
		xpos=xpos-speed;
	}//ends moveLeft

	/**moves block position to the right */
	public void moveRight(){
		if(movable==true)
		xpos=xpos+speed;
	}//Ends moveRight


	/**moves block position to the up	*/
	public void moveUp(){
		if(movable==true)
		ypos=ypos-speed;
	}//ends moveLeft

	/**moves block position to the down */
	public void moveDown(){
		if(movable==true)
		ypos=ypos+speed;
	}//Ends moveRight

	/**returns a rectangle with dimensions of the block 
	 * @return Rectangle 
	 * */
	public Rectangle getBounds() {
		return new Rectangle(xpos, ypos, xsize, ysize);
	}//Ends getBounds

	/**moves block position to the down */
	public void finish() {
		xsize-=1;
		ysize-=1;
	}//Ends getBounds


	/**returns the color of the block.
	 * @return color
	 * */
	public Color getColor() {
		// TODO Auto-generated method stub
		return color;
	}

	/**checks if the block has collided in the down direction
	 * @return true       if it has
	 * @return false      if it hasn't
	 * @param  Obstacles  Contains all the obstacles on screen.
	 * @param  Blocks     Contains all the blocks on screen
	 * */
	public boolean collisionDown(ArrayList<Obstacle> Obstacles, ArrayList<Block> Blocks) {
		if(this.getBounds().y>=620){
			movable=false;
			return true;
		}
		for(int i = 0; i < Obstacles.size(); i++){
			int oY=Obstacles.get(i).getBounds().height;
			int oX=Obstacles.get(i).getBounds().width;
			if(this.getBounds().y+20==Obstacles.get(i).getBounds().y && Obstacles.get(i).getBounds().x-20<this.getBounds().x&&this.getBounds().x<Obstacles.get(i).getBounds().x+oX){
				return true;
			}//Ends if statement
		}//Ends for loop
		for(int i = 0; i < Blocks.size(); i++){
			if(this != Blocks.get(i)){
				if((this.getBounds().y+20==Blocks.get(i).getBounds().y&& Blocks.get(i).getBounds().x-20< this.getBounds().x&&this.getBounds().x<Blocks.get(i).getBounds().x+20) || Blocks.get(i).getBounds().intersects(this.getBounds())){
					return true;
				}//Ends if statement
			}//Ends if statement
		}//Ends for loop
		return false;
	}//Ends collisionDown

	/**checks if the block has collided in the up direction
	 * @return true       if it has
	 * @return false      if it hasn't
	 * @param  Obstacles  Contains all the obstacles on screen.
	 * @param  Blocks     Contains all the blocks on screen
	 * */
	public boolean collisionUp( ArrayList<Obstacle> Obstacles, ArrayList<Block> Blocks) {
		if(this.getBounds().y<=-20){
			movable=false;
			return true;
		}
		for(int i = 0; i < Obstacles.size(); i++){
			int oY=Obstacles.get(i).getBounds().height;
			int oX=Obstacles.get(i).getBounds().width;
			if(this.getBounds().y==Obstacles.get(i).getBounds().y+oY && Obstacles.get(i).getBounds().x-20< this.getBounds().x&&this.getBounds().x<Obstacles.get(i).getBounds().x+oX){
				return true; 
			}//Ends if statement
		}//Ends for loop
		for(int i = 0; i < Blocks.size(); i++){
			if(this != Blocks.get(i)){
				if((this.getBounds().y-20==Blocks.get(i).getBounds().y&& Blocks.get(i).getBounds().x-20< this.getBounds().x&&this.getBounds().x<Blocks.get(i).getBounds().x+20 )|| Blocks.get(i).getBounds().intersects(this.getBounds())){
					return true;
				}//Ends if statement
			}//Ends if statement
		}//Ends for loop
		return false;
	}//Ends collisionDown

	/**checks if the block has collided in the left direction
	 * @return true       if it has
	 * @return false      if it hasn't
	 * @param  Obstacles  Contains all the obstacles on screen.
	 * @param  Blocks     Contains all the blocks on screen
	 * */
	public boolean collisionLeft(ArrayList<Obstacle> Obstacles, ArrayList<Block> Blocks) {
		if(this.getBounds().x<=-20){
			movable=false;
			return true;
		}
			
		for(int i = 0; i < Obstacles.size(); i++){
			int oY=Obstacles.get(i).getBounds().height;
			int oX=Obstacles.get(i).getBounds().width;
			if(this.getBounds().x==Obstacles.get(i).getBounds().x+oX && Obstacles.get(i).getBounds().y-20< this.getBounds().y&&this.getBounds().y<Obstacles.get(i).getBounds().y+oY){
				return true;
			}//Ends if statement
		}//Ends for loop
		for(int i = 0; i < Blocks.size(); i++){
			if(this != Blocks.get(i)){
				if((this.getBounds().x-20==Blocks.get(i).getBounds().x&&Blocks.get(i).getBounds().y-20< this.getBounds().y&&this.getBounds().y<Blocks.get(i).getBounds().y+20) || Blocks.get(i).getBounds().intersects(this.getBounds())){
					return true;
				}//Ends if statement
			}//Ends if statement
		}//Ends for loop
		return false;
	}//Ends collisionLeft

	/**checks if the block has collided in the right direction
	 * @return true       if it has
	 * @return false      if it hasn't
	 * @param  Obstacles  Contains all the obstacles on screen.
	 * @param  Blocks     Contains all the blocks on screen
	 * */
	public boolean collisionRight (ArrayList<Obstacle> Obstacles, ArrayList<Block> Blocks) {		
		if(this.getBounds().x>=820){
			movable=false;
			return true;
		}
		for(int i = 0; i < Obstacles.size(); i++){
			int oY=Obstacles.get(i).getBounds().height;
			int oX=Obstacles.get(i).getBounds().width;
			if(this.getBounds().x+20==Obstacles.get(i).getBounds().x&&Obstacles.get(i).getBounds().y-20< this.getBounds().y&&this.getBounds().y<Obstacles.get(i).getBounds().y+oY){
				return true;
			}
		}//Ends for loop
		for(int i = 0; i < Blocks.size(); i++){
			if(this != Blocks.get(i)){
				if((this.getBounds().x+20==Blocks.get(i).getBounds().x&&Blocks.get(i).getBounds().y-20< this.getBounds().y&&this.getBounds().y<Blocks.get(i).getBounds().y+20)){
					return true;
				}//Ends if statement
			}//Ends if statement
		}//Ends for loop
		return false;
	}//Ends collisionRight

	/**checks if the block has collided with an obstacle.
	 * @return true       if it has
	 * @return false      if it hasn't
	 * @param  Obstacles  Contains all the obstacles on screen.
	 * 
	 * */
	public boolean checkIntersect(ArrayList<Obstacle> Obstacles){
		for(int i = 0; i < Obstacles.size(); i++){
			if(Obstacles.get(i).getBounds().intersects(this.getBounds())){
				return true;
			}//Ends if statement
		}
		return false;
	}

	public void teleport(int x, int y){
		xpos = x;
		ypos = y;
	}

	public Image getImage() {
		// TODO Auto-generated method stub
		return b;
	}

}//Ends Block