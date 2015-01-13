import java.awt.Rectangle;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;


/**This class contains the Obstacles the player have to navigate against.
 * 
 * @author Craig Blumenfeld
 * @author Jason Ma
 * @version 1.2 April 17, 2013
 */


public class Obstacle {
	
    private int xpos;
	private int ypos;
	private int xsize;
	private int ysize; 
	private Image obs;
    
	/**The constructor sets the obstacles initial configuration like position and location.
	 * 
	 * @param  xpos  x position on screen
	 * @param  ypos  y position on screen
	 * @param  xsize  width
	 * @param  ysize  height
	 * @throws SlickException 
	 * */
    public Obstacle(int xpos, int ypos) throws SlickException {
        this.xpos  = xpos;
        this.ypos = ypos;
        this.xsize  =20;
        this.ysize =20;
        this.obs=new Image("Data/obs.png");
    }//Ends constructor
	
    
    
	/**returns a rectangle with dimensions of the block 
	 * @return Rectangle 
	 * */
    public Rectangle getBounds() {
        return new Rectangle(xpos, ypos, xsize, ysize);
    }//Ends getBounds



	public Image getImage() {
		// TODO Auto-generated method stub
		return obs;
	}

}//Ends Block