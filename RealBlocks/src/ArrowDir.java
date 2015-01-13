import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Point;

/**This class holds the information for the background arrows.
 * 
 * @author Craig Blumenfeld
 * @author Jason Ma
 * @version 1.2 April 17, 2013
 */
public class ArrowDir {
	Point position;	
	Image img;
	Color color;
	float opacity;

	/**The constructor sets the initial values of the variables.
	 * 
	 * @param p 		Starting position
	 * @param vector	Starting direction vector
	 * @param img		Image to use
	 * @param speed		Speed per frame
	 */
	public ArrowDir(Point p, float op, Image img){
		position = p;
		opacity=op;
		this.img = img;
		color=new Color(1f,1f,1f,op);
	}   
	
	/**update changes the color and opacity of the arrow.
	 * 
	 * @param delta		The amount by which the opacity changes
	 */
	public void update(int delta){
		opacity+=delta*0.0005;
		color=new Color(1f,1f,1f,opacity);
	}

	/**getColor returns the color of the arrow.
	 * 
	 * @return		The color of the arrow 	
	 */
	public Color getColor() {
		return color;
	}

	/**setImage sets the image used to display the arrow.*/
	public void setImage(Image arrow) {
		img=arrow;
	}

}
