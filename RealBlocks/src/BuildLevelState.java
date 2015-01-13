import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.ResourceLoader;


/**This class displays the instructions to teach the user how to play the game.
 * 
 * @author Craig Blumenfeld
 * @author Jason Ma
 * @version 1.2 April 17, 2013
 */
public class BuildLevelState extends BasicGameState{
	private int stateID=3;
	private Font font;
	private TrueTypeFont trueTypeFont;
	String message1, message2, message3;
	private Audio buttonEffect;
	public ArrayList<Block> Blocks; 
	public ArrayList<Obstacle> Obstacles;
	public ArrayList<Teleporter> Teleporters ;
	public ArrayList<EndZone> EndZones ;
	public ArrayList<Block> Enemy ;
	public ArrayList<String> filePath;
	public String[][] map;
	private int teleCount;
	private int blockCount;
	private int endzCount;
	private boolean dInfo;
	private boolean eneBol;
	private JOptionPane popMessages;
	private String recentSave;
	private boolean paused;
	private Input input;


	public void enter(GameContainer container, StateBasedGame stateBasedGame) throws SlickException {
		container.getInput().clearKeyPressedRecord();
	}
	/**The constructor set the instruction state ID.
	 * 
	 * @param stateID	The main menu's ID.
	 */
	public	BuildLevelState( int stateID ) 
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
		font = new Font("Microsoft Tai Le", Font.BOLD, 12);
		trueTypeFont = new TrueTypeFont(font, true);
		trueTypeFont = new TrueTypeFont(font, true);
		paused=false;
		blockCount=0;
		input = gc.getInput();
		endzCount=0;
		teleCount=0;
		popMessages=new JOptionPane();
		message1 = "Menu";
		message2 = "Resume";
		Blocks = new ArrayList<Block>(); 
		Obstacles = new ArrayList<Obstacle>();
		Teleporters = new ArrayList<Teleporter>();
		EndZones = new ArrayList<EndZone>();
		Enemy = new ArrayList<Block>();
		map=new String[30][40];
		eneBol=true;
		for(int i=0;i<map.length;i++){
			for(int j=0;j<map[i].length;j++){
				map[i][j]="@"; 			
			}
		}
		try{
			buttonEffect = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("Data/button-9.wav"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		dInfo=false;
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
		gc1.setColor(new Color(22,22,22));
		if(!paused){
			for(int i = 0; i < 40; i++){
				for(int j = 0;j < 30; j++){
					gc1.drawLine(i*20, 0, i*20, 600);
				}
			}//Ends for loop
			for(int i = 0; i < 40; i++){
				for(int j = 0;j < 30; j++){
					gc1.drawLine(0, j*20, 800, j*20);
				}
			}//Ends for loop
			int tCount=0;
			//j=x i=y
			for(int i=0;i<map.length;i++){
				for(int j=0;j<map[i].length;j++){
					if (map[i][j] == "E"){
						Enemy aEnemy=new Enemy(j*20,i*20,1,1);
						gc1.setColor(aEnemy.getColor());
						gc1.fillOval(aEnemy.getBounds().x, aEnemy.getBounds().y, 20, 20);
					}else if (map[i][j] == "e"){
						Enemy aEnemy=new Enemy(j*20,i*20,1,2);
						gc1.setColor(aEnemy.getColor());
						gc1.fillOval(aEnemy.getBounds().x, aEnemy.getBounds().y, 20, 20);
					}
					else if (map[i][j] == "#"){
						Obstacle aObs=new Obstacle(j*20,i*20);
						gc1.drawImage(aObs.getImage(),j*20,i*20);
					}else if(isInteger(map[i][j])) {//1-4 is block 1-5 2-6 3-7 4-8
						int num=Integer.parseInt(map[i][j]);
						if(num<=4){
							Block aBlock=new Block(j*20, i*20, num);
							gc1.drawImage(aBlock.getImage(),j*20,i*20);
						}else{
							EndZone aZone=new EndZone(j*20, i*20,num-4);
							gc1.setColor(aZone.getColor());
							gc1.fillRect(aZone.getBounds().x,aZone.getBounds().y,20,20);
						}
					}else if(map[i][j]=="T"){
						String s="Data/t"+(tCount+1)+".png";
						Image t=new Image(s);
						gc1.drawImage(t,j*20,i*20);
						tCount++;
					}
				}
			}
			if(dInfo==true){
				displayInstructions(gc1);
			}
			trueTypeFont.drawString(20,20, "Tab for help");
			trueTypeFont.drawString(20,30, "Esc for Menu");
		}else{
			gc1.setColor(new Color(1f,1f,1f,.2f));
			gc1.fillRect(340, 200, 100, 200);
			trueTypeFont.drawString(366f, 210.0f, message1, Color.red);
			trueTypeFont.drawString(366f, 240.0f, message2, Color.red);

		}


	}//Ends render


	/**update updates the values the dictate the layout of the main menu and call other states if a button is pressed.
	 *  
	 * @param gc					gc holds the game.
	 * @param sbg					sbg holds the statebased game.
	 * @param delta					delta holds the value by which the button sizes increase when the mouse hovers over them.
	 * @exception SlickException	SlickException is a generic exception thrown by everything in the library.
	 */
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		input=gc.getInput();
		int mouseX = input.getMouseX();
		int mouseY = input.getMouseY();
		int absX=Math.round((mouseX/20));
		int absY=Math.round((mouseY/20));
		if(input.isMouseButtonDown((input.MOUSE_RIGHT_BUTTON))){
			if(map[absY][absX]=="T"){
				teleCount--;
			}
			map[absY][absX]="@";
		}else if(input.isKeyPressed((input.KEY_1))){
			if(map[absY][absX]=="@" && !search("1")){
				map[absY][absX]=(1)+"";
			}
		}else if(input.isKeyPressed((input.KEY_2))){
			if(map[absY][absX]=="@"  && !search("2")){
				map[absY][absX]=(2)+"";

			}
		}else if(input.isKeyPressed((input.KEY_3))){
			if(map[absY][absX]=="@" && !search("3")){
				map[absY][absX]=(3)+"";
			}
		}else if(input.isKeyPressed((input.KEY_4))){
			if(map[absY][absX]=="@"  && !search("4")){
				map[absY][absX]=(4)+"";
			}
		}else if(input.isKeyPressed((input.KEY_5))){
			if(map[absY][absX]=="@" && !search("5")){
				map[absY][absX]=(5)+"";
			}
		}else if(input.isKeyPressed((input.KEY_6))){
			if(map[absY][absX]=="@" && !search("6")){
				map[absY][absX]=(6)+"";
			}
		}else if(input.isKeyPressed((input.KEY_7))){
			if(map[absY][absX]=="@" && !search("7")){
				map[absY][absX]=(7)+"";
			}
		}else if(input.isKeyPressed((input.KEY_8))){
			if(map[absY][absX]=="@" && !search("8")){
				map[absY][absX]=(8)+"";
			}
		}else if(input.isKeyDown((input.KEY_O))){
			if(map[absY][absX]=="@"){
				map[absY][absX]="#";
			}
		}else if(input.isKeyPressed((input.KEY_T))){
			if(map[absY][absX]=="@" && teleCount<2 ){
				map[absY][absX]="T";
				teleCount++;
			}
		}else if(input.isKeyPressed((input.KEY_E))){
			if(eneBol==true)
			map[absY][absX]="E";
			else
			map[absY][absX]="e";	
			eneBol=!eneBol;
		

		}
		else if(input.isKeyDown((input.KEY_C))){
			clearAll();
		}else if(input.isKeyPressed((input.KEY_TAB))){
			if(dInfo==false){
				dInfo=true;
			}
			else{
				dInfo=false;
			}
		}else if(input.isKeyPressed((input.KEY_S))){
			saveMap();
			recentSave=null;
		}else if(input.isKeyPressed((input.KEY_P))){
			saveMap();
			GameplayState a= (GameplayState) sbg.getState(1);
			if(recentSave!=null){
				a.readFiles(new File("level/"+recentSave));
				sbg.enterState(1);
			}


		}else if(input.isKeyPressed((input.KEY_ESCAPE))){
			paused=!(paused);
		}else if(paused){
			if(mouseX > 366 && mouseX< 400 && mouseY <230 && mouseY > 210)
				if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON)){
					buttonEffect.playAsSoundEffect(1.0f, 1.0f, false);
					init(gc, sbg);
					sbg.enterState(BlocksGame.MAINMENUSTATE);

				}
			if(mouseX > 366 && mouseX< 410 && mouseY <270 && mouseY > 240)
				if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON)){
					buttonEffect.playAsSoundEffect(1.0f, 1.0f, false);
					paused = false;
				}
		}

	}//Ends update

	private void saveMap() {
		
		int i=JOptionPane.showConfirmDialog(popMessages, "Are sure you want to save?");
		if(i==0){
			PrintWriter writer = null;
			String s=null;
			s=JOptionPane.showInputDialog("Name of File?");
			if(s==null){
				JOptionPane.showMessageDialog(popMessages,"Wrong File Name, Try Again");
			}else{
				s=s+".txt";
				recentSave=s;
				try {
					writer = new PrintWriter("level/"+s,"UTF-8");
					for(int j=0;j<map.length;j++){
						for(int k=0;k<map[j].length;k++){
							writer.print(map[j][k]);
						}
						writer.println();
					}
					writer.close();

				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				JOptionPane.showMessageDialog(popMessages,"File saved");
			}
		}

	}

	public void clearAll() {
		for(int i=0;i<map.length;i++){
			for(int j=0;j<map[i].length;j++){
				map[i][j]="@";

			}
		}

	}

	public boolean isInteger( String s )  
	{  
		try { 
			Integer.parseInt(s); 
			return true;
		} catch(NumberFormatException e) { 
			return false; 
		}
	} 

	public boolean search( String s )  
	{  
		for(int i=0;i<map.length;i++){
			for(int j=0;j<map[i].length;j++){
				if(map[i][j]==s)
					return true;
			}
		}
		return false;
	} 

	public void displayInstructions(Graphics gc1){
		if(dInfo=true){
			gc1.setColor(new Color(1f,1f,1f,.2f));
		}
		else{
			gc1.setColor(new Color(1f,1f,1f,0f));
		}
		gc1.fillRect(200, 100, 400, 400);
		int x=220;
		trueTypeFont.drawString(220,200, "The Level Editors works by placing objects through key press");
		trueTypeFont.drawString(220,x+=10, "Key Chart:");
		trueTypeFont.drawString(220,x+=10, "1-4 for Blocks");
		trueTypeFont.drawString(220,x+=10, "1 for red, 2 for green, 3 for yellow, 4 for blue");
		trueTypeFont.drawString(220,x+=10, "5-8 for Endzones, Endzones correspond with Blocks Respectively");
		trueTypeFont.drawString(220,x+=10, "5 for red, 6 for green, 7 for yellow, 8 for blue");
		trueTypeFont.drawString(220,x+=10, "E for enemy, press again to cycle between two");
		trueTypeFont.drawString(220,x+=10, "pink enemy move right and left, red enemy move up and down");
		trueTypeFont.drawString(220,x+=10, "O for Obstacles");
		trueTypeFont.drawString(220,x+=10, "T for Teleporters");
		trueTypeFont.drawString(220,x+=10, "Right Click to Remove a Object");
		trueTypeFont.drawString(220,x+=10, "C for Clear All");
		trueTypeFont.drawString(220,x+=10, "S for Save Level");
		trueTypeFont.drawString(220,x+=10, "P Play Level, On play you will be prompted to save the map");

	}
}//Ends InstructionState