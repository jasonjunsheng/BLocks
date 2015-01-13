import java.awt.Rectangle;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**This class contains the information for the EndZones that the blocks have to reach.
*
* @author Craig Blumenfeld
* @author Jason Ma
* @version 1.2 April 17, 2013
*/
public class EndZone {
	
    private int xpos;
	private int ypos;
	public int endID; ///1 to 4 for each block 5 if it works for all 
	private int xsize;
	private int ysize; 
	private Color color; 
	public Image b;
    public EndZone(int xpos, int ypos, int id) throws SlickException {
        this.xpos  = xpos;
        this.ypos = ypos;
        xsize = 20;
        ysize = 20;
        endID=id;
        if(id==1){
			color=(new Color(249,152,152)); //red
			b=new Image("Data/b11.png");
		}else if(id==2){
			color=(new Color(152,249,152)); //green
			b=new Image("Data/b22.png");
		}else if (id==3){
			color=(new Color(255,228,100)); //yellow
			b=new Image("Data/b33.png");
		}else if(id==4){
			color=(new Color(152,152,249));  //blue
			b=new Image("Data/b44.png");
		}
    }//Ends constructor
 
	
	/**returns a rectangle with dimensions of the rectangle
	 * @return Rectangle 
	 * */
    public Rectangle getBounds() {
        return new Rectangle(xpos, ypos, xsize, ysize);
    }//Ends getBounds
    
    

	/**returns the color of the end zone
	 * @return color
	 * */
	public Color getColor() {
		return color;
	}

	
	/**set color for the end zone class
	 * @param  color  sets the color of the end zone to match the presets of the level.
	 * 
	 * */
	public void setColor(Color color) {
		this.color = color;
	}

	/**animation for when the block reaches it's proper end zone.
	 *
	 * */
	public void shrink() {
		// TODO Auto-generated method stub
		if(xsize>0 && ysize>0){
			xsize-=.75;
	    	ysize-=.75;
		}
		
	}


	public Image getImage() {
		return b;
	}

}
