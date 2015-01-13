import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

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
public class LevelCState extends BasicGameState{
	private FileReader fr;
	private BufferedReader br;
	private int stateID;
	public static int levelOn;
	private Font font;
	public TrueTypeFont trueTypeFont;
	String message1, message2, message3;
	private Audio buttonEffect;
	private Input input;
	PrintWriter writer;
	public int[] dataArr;
	private boolean bolSelect;
	private int selectx;
	private int selecty;
	private int lCount;

	/**The constructor set the instruction state ID.
	 * 
	 * @param stateID	The main menu's ID.
	 */
	public void enter(GameContainer container, StateBasedGame stateBasedGame) throws SlickException {
		container.getInput().clearKeyPressedRecord();
		container.getInput().clearMousePressedRecord();
		
		try {
			getData();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public	LevelCState( int stateID ) 
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
		dataArr =new int[16];
		try {
			getData();
		} catch (NumberFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		input = gc.getInput();
		bolSelect=false;
		selectx=0;
		selecty=0;
		font = new Font("Microsoft Tai Le", Font.BOLD, 17);
		trueTypeFont = new TrueTypeFont(font, true);
		
		levelOn=dataArr[0];
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
		gc1.setBackground(new Color(9,19,38));
		trueTypeFont.drawString(9.0f, 15.0f, "<<Menu", Color.white);
		gc1.setColor(Color.white);
		lCount=1;
		if(bolSelect==true){
			gc1.setColor(new Color(255,255,35));
			gc1.fillRect(selectx-2,selecty-2,54,54);
		}
		trueTypeFont.drawString(300,100,"start new game",Color.white);
		for(int i=0;i<3;i++){
			for(int j=0;j<5;j++){
				if(dataArr[0]>=lCount){
					gc1.setColor(new Color(25,110,233));
				}else
					gc1.setColor(Color.white);
				gc1.fillRect(j*100+180,i*100+160,50,50);
				trueTypeFont.drawString(j*100+180,i*100+160, ""+lCount, Color.black);
				if(dataArr[lCount]==999)
					trueTypeFont.drawString(j*100+180,i*100+220, "best:"+0, Color.white);
				else
					trueTypeFont.drawString(j*100+180,i*100+220, "best:"+dataArr[lCount], Color.white);
				lCount++;
			}
		}
		
	
	}//Ends render

	/**update updates the values the dictate the layout of the main menu and call other states if a button is pressed.
	 *  
	 * @param gc					gc holds the game.
	 * @param sbg					sbg holds the statebased game.
	 * @param delta					delta holds the value by which the button sizes increase when the mouse hovers over them.
	 * @exception SlickException	SlickException is a generic exception thrown by everything in the library.
	 */
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException  {
		input = gc.getInput();
		int mouseX = input.getMouseX();
		int mouseY = input.getMouseY();
		
		if(mouseX > 300 && mouseX< 400 && mouseY <130 && mouseY > 100){
			if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON)){
				buttonEffect.playAsSoundEffect(1.0f, 1.0f, false);
				refreshGameData();
				GameplayState.cLevel=0;
				sbg.enterState(BlocksGame.GAMEPLAYSTATE);
			}
		}
			
		boolean in=false;

		if(mouseX > 9 && mouseX< 70 && mouseY <30 && mouseY > 15){
			if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON)){
				buttonEffect.playAsSoundEffect(1.0f, 1.0f, false);
				sbg.enterState(BlocksGame.MAINMENUSTATE);
			}
		}
		lCount=1;	
		for(int i=0;i<3;i++){
			for(int j=0;j<5;j++){
				
				if(mouseX >(j*100+180) && mouseX< (j*100+180+50) && mouseY <(i*100+160+50) && mouseY > (i*100+160)){
					
					in=true;
					selecty=(i*100+160);
					selectx=(j*100+180);
				
					if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON)){
						buttonEffect.playAsSoundEffect(1.0f, 1.0f, false);
						System.out.println(lCount<=levelOn);
						if(lCount<=levelOn){
							GameplayState.cLevel=lCount-1;
							GameplayState a= (GameplayState) sbg.getState(1);
							a.nextLevel();
							sbg.enterState(BlocksGame.GAMEPLAYSTATE);
						}
					}
				}
				lCount++;
			}
		}
		if(in==true){
			bolSelect=true;
		}else
			bolSelect=false;
	}//Ends update

	private void getData() throws NumberFormatException, IOException {
		fr = new FileReader(new File("gameData.txt"));
		br = new BufferedReader(fr);
		String s="";
		int i=0;
		while ((s = br.readLine()) != null) {
			dataArr[i]=Integer.parseInt(s);
			i++;
		}
		levelOn=dataArr[0];
	}
	private void refreshGameData() {
		try {
			PrintWriter writer = new PrintWriter("gameData.txt","UTF-8");
			writer.println("1");
			writer.println("999");
			writer.println("999");
			writer.println("999");
			writer.println("999");
			writer.println("999");
			writer.println("999");
			writer.println("999");
			writer.println("999");
			writer.println("999");
			writer.println("999");
			writer.println("999");
			writer.println("999");
			writer.println("999");
			writer.println("999");
			writer.close();
		} catch (NumberFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
		
	}

}//Ends InstructionState