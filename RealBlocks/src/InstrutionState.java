import java.awt.*;
import java.io.IOException;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.ResourceLoader;


/**This class displays the instructions to teach the user how to play the game.
 * 
 * @author Craig Blumenfeld
 * @author Jason Ma
 * @version 1.2 April 17, 2013
 */
public class InstrutionState extends BasicGameState{
    private int stateID;
    private Font font;
    public TrueTypeFont trueTypeFont;
    String message1, message2, message3;
    private Audio buttonEffect;
   
    

	/**The constructor set the instruction state ID.
	 * 
	 * @param stateID	The main menu's ID.
	 */
	public	InstrutionState( int stateID ) 
    {
       this.stateID = stateID;
    }//Ends constructor
 
	/**getID returns the main menu's state's ID (i.e. 0).
	 * 
	 * @return stateID		The main menu's ID.
	 */
    public int getID() {
        return stateID;
    }//Ends getID
 

	/**init sets the initial values for the variables.
	 * 
	 * @param gc					gc holds the game.
	 * @param sbg					sbg holds the statebased game.
	 * @exception SlickException	SlickException is a generic exception thrown by everything in the library.
	 */
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
    	font = new Font("Microsoft Tai Le", Font.BOLD, 17);
    	trueTypeFont = new TrueTypeFont(font, true);
    	message1 = "The goal of this game is to move the blocks to their respective endzones.";
    	message2 = "The player can control the orientation of gravity with the WASD keys.";
    	message3 = "The player can move the blocks perpendicular to the orientation of gravity using the arrow keys.";
    	try{
			buttonEffect = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("Data/button-9.wav"));
		} catch (IOException e) {
			e.printStackTrace();
		}
    }//Ends init
 
    
	/**render draws the buttons and background of the main menu.
	 * 
	 * @param gc					gc holds the game.
	 * @param sbg					sbg holds the state-based game.
	 * @param gc1					gc1 allows the method to draw to the screen.
	 * @exception SlickException	SlickException is a generic exception thrown by everything in the library.
	 */
    public void render(GameContainer gc, StateBasedGame sbg, Graphics gc1) throws SlickException {
    	gc1.setBackground(new Color(0,0,0));
    	trueTypeFont.drawString(9.0f, 15.0f, "<<", Color.white);
    	trueTypeFont.drawString(20.0f, 45.0f, message1, Color.yellow);
    	trueTypeFont.drawString(20.0f, 63.0f, message2, Color.red);
    	trueTypeFont.drawString(20.0f, 81.0f, message3, Color.green);
    	trueTypeFont.drawString(20.0f, 99.0f, "The background arrows indicate the orientation of gravity.", Color.blue);
    }//Ends render
 
    /**update updates the values the dictate the layout of the main menu and call other states if a button is pressed.
	 *  
	 * @param gc					gc holds the game.
	 * @param sbg					sbg holds the statebased game.
	 * @param delta					delta holds the value by which the button sizes increase when the mouse hovers over them.
	 * @exception SlickException	SlickException is a generic exception thrown by everything in the library.
	 */
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
    	Input input = gc.getInput();
    	int mouseX = input.getMouseX();
    	int mouseY = input.getMouseY();
    	if(mouseX > 9 && mouseX< 32 && mouseY <30 && mouseY > 15)
    		if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON)){
				buttonEffect.playAsSoundEffect(1.0f, 1.0f, false);
    			sbg.enterState(BlocksGame.MAINMENUSTATE);
    		}
    }//Ends update
 
}//Ends InstructionState