import java.awt.Robot;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JOptionPane;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.ResourceLoader;

/**This class displays the main menu and allows the user to choose between reading the instructions, playing the game, and exiting.
 * 
 * @author Craig Blumenfeld
 * @author Jason Ma
 * @version 1.2 April 17, 2013
 */
public class MainMenuState extends BasicGameState {
	int stateID = 1;
	Image background = null;
	Image startGameOption = null;
	int startX = 664, startY = 470;
	float startGameScale = 1;
	Image exitOption = null;
	int exitX=44, exitY = 537;
	float exitScale = 1;
	Image l = null;
	int lX=181, lY = 172;
	float lScale = 1;
	Image b = null;
	int bX=81, bY = 77;
	float bScale = 1;
	Image credits = null;
	int creditsX=431, creditsY = 132;
	float creditsScale = 1;
	float scaleStep = 0.0001f;
	float optionscale= 1;
	Image options= null;
	int optionsX = 320, optionsY = 100;
	Image k= null;
	int kX = 600, kY = 75;
	double colorG=0;
	boolean gradient;
	private Audio buttonEffect;

	/**The constructor set the main menu's state ID.
	 * 
	 * @param stateID	The main menu's ID.
	 */
	public MainMenuState( int stateID ) 
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

	public void enter(GameContainer container, StateBasedGame stateBasedGame) throws SlickException {
		container.getInput().clearKeyPressedRecord();
	}
	
	/**init sets the initial values for the variables.
	 * 
	 * @param gc					gc holds the game.
	 * @param sbg					sbg holds the statebased game.
	 * @exception SlickException	SlickException is a generic exception thrown by everything in the library.
	 */
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		gc.setTargetFrameRate(60);
		background = new Image("Data/mainmenu.png");
		startGameOption= new Image("Data/start.png");
		exitOption =new Image("Data/exit.png");
		b= new Image("Data/b.png");
		l = new Image("Data/l.png");
		credits = new Image("Data/cred.png");
		options= new Image("Data/option.png");
		k= new Image("Data/k.png");
		gradient=true;
		try{
			buttonEffect = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("Data/button-9.wav"));
		}/*Ends try*/ catch (IOException e) {
			e.printStackTrace();
		}//ends catch
	}//Ends init

	/**render draws the buttons and background of the main menu.
	 * 
	 * @param gc					gc holds the game.
	 * @param sbg					sbg holds the state-based game.
	 * @param gc1					gc1 allows the method to draw to the screen.
	 * @exception SlickException	SlickException is a generic exception thrown by everything in the library.
	 */
	public void render(GameContainer gc, StateBasedGame sbg, Graphics gc1) throws SlickException {
		background.draw(0, 0);
		if(colorG>=200)	{
			gradient=false;
		}//Ends if
		else if(colorG<=0.0){
			gradient=true;
		}//Ends else if statement	
		if(gradient==true)
			colorG+=0.2;
		else if (gradient==false)
			colorG-=0.2;
		gc1.setColor(new org.newdawn.slick.Color((int)(25+colorG),(int)(122+colorG),(int)(150+colorG)));
		gc1.fillRect(startX-12, startY-6, 50, 50);
		gc1.fillRect(optionsX-8, optionsY-7, 50, 50);
		gc1.fillRect(lX-15, lY-6, 50, 50);
		gc1.fillRect(creditsX-10, creditsY-7, 50, 50);
		startGameOption.draw(startX, startY, startGameScale);
		exitOption.draw(exitX, exitY, exitScale);
		b.draw(bX, bY, bScale);
		l.draw(lX, lY, lScale);
		options.draw(optionsX, optionsY, optionscale);
		k.draw(kX, kY, lScale);
		credits.draw(creditsX, creditsY, creditsScale);
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
		boolean insideStartGame = false;
		boolean insideExit = false;
		boolean insideCredit=false;
		boolean insideB=false;
		boolean insideoptions=false;
		boolean insideL=false;
		if( ( mouseX >= startX && mouseX <= startX + startGameOption.getWidth()) &&
				( mouseY >= startY && mouseY <= startY + startGameOption.getHeight()) ){
			insideStartGame = true;
		}//Ends if statement
		else if(( mouseX >= exitX && mouseX <= exitX+ exitOption.getWidth()) &&
				( mouseY >= exitY && mouseY <= exitY + exitOption.getHeight()) ){
			insideExit = true;
		}//Ends else if
		else if(( mouseX >= creditsX && mouseX <= creditsX+ credits.getWidth()) &&
				(mouseY >= creditsY && mouseY <= creditsY + credits.getHeight())){
			insideCredit=true;	
		}//Ends else if
		else if(( mouseX >= bX && mouseX <= bX+ b.getWidth()) &&
				(mouseY >= bY && mouseY <= bY + b.getHeight())){
			insideB=true;
		}//Ends else if
		else if(( mouseX >= optionsX && mouseX <= optionsX+ options.getWidth()) &&
				(mouseY >= optionsY && mouseY <= optionsY + options.getHeight())){
			insideoptions=true;
		}//Ends else if
		else if(( mouseX >= lX && mouseX <= lX+ l.getWidth()) &&
				(mouseY >= lY && mouseY <= lY + l.getHeight())){
			insideL=true;
		}//Ends else if
		
		
		if(insideL){
			if(lScale < 1.15f)
				lScale += scaleStep * delta;
			if ( input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) ){
				buttonEffect.playAsSoundEffect(1.0f, 1.0f, false);
			
				
				sbg.enterState(BlocksGame.LEVELCSTATE);
			}
		}else{
			if(lScale > 1.0f)
				lScale -= scaleStep * delta;
		}//Ends else statement
	
	
		
		if(insideStartGame){
			if(startGameScale < 1.15f)
				startGameScale += scaleStep * delta;
			if ( input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) ){
				buttonEffect.playAsSoundEffect(1.0f, 1.0f, false);
				sbg.enterState(BlocksGame.GAMEPLAYSTATE);
			}
		}//Ends if statement
		else{
			if(startGameScale > 1.0f)
				startGameScale -= scaleStep * delta;
		}//Ends else statement
		if(insideExit)
		{
		
			if(exitScale < 1.15f)
				exitScale +=  scaleStep * delta;
			if ( input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) )
				gc.exit();
		}//Ends if statement
		else{
			if(exitScale > 1.0f)
				exitScale -= scaleStep * delta;
		}//ends else statement
		if(insideCredit)
		{
		
			if(creditsScale < 1.15f)
				creditsScale +=  scaleStep * delta;
			if ( input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) ){
				buttonEffect.playAsSoundEffect(1.0f, 1.0f, false);
				sbg.enterState(BlocksGame.INSTRUCTIONSTATE);
			}//Ends if
		}/*Ends if statement*/
		else{
			if(creditsScale > 1.0f)
				creditsScale -= scaleStep * delta;
		}//ends else statement
		
		if(insideB)
		{
			if(bScale < 1.15f)
				bScale += scaleStep * delta;
			if ( input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) ){
				buttonEffect.playAsSoundEffect(1.0f, 1.0f, false);
				sbg.enterState(BlocksGame.BUILDLEVELSTATE);
			}
		}/*Ends if statement*/
		else{
			if(bScale > 1.0f)
				bScale -= scaleStep * delta;
		}//ends else statement
	
	}//Ends update


}//Ends MainMenuState