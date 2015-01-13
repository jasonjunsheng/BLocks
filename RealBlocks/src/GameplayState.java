import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.ResourceLoader;

/**This class displays and allows the user to play the game.
 * 
 * @author Craig Blumenfeld
 * @author Jason Ma
 * @version 1.2 April 17, 2013
 */
public class GameplayState extends BasicGameState {
	private FileReader fr;
	private PrintWriter writer;
	private BufferedReader br;
	int stateID = -1;
	int aspeed;
	int endGameCounter;
	ArrayList removeZone; //(figures out which one gets removed)
	KeyAdapter key;
	Image levelClear;
	Image endGameImg;
	boolean left =false, right=false;
	boolean gUp=false, gDown=false, gLeft= false, gRight=false;
	public ArrayList<Block> Blocks = new ArrayList<Block>(); 
	public ArrayList<Obstacle> Obstacles = new ArrayList<Obstacle>();
	public ArrayList<Teleporter> Teleporters = new ArrayList<Teleporter>();
	public ArrayList<EndZone> EndZones = new ArrayList<EndZone>();
	public ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	public ArrayList<ArrowDir> bgShape= new ArrayList<ArrowDir>();
	public String[] levelList;
	public Level currentL;
	public static int cLevel=0;
	private float opacity;
	private float clearY=270;
	private float clearX=220;
	private Image arrow;
	private Image bg;
	private int numberArr;
	private int currentAngle;
	private Audio blockEffect;
	private boolean zoneBol;
	private int endCount;
	private boolean paused;
	private Font font;
	private TrueTypeFont trueTypeFont;
	String message1, message2, message3;
	private Audio buttonEffect;
	int c;
	boolean eUp;
	private int movesCounter;
	private Input input;
	private int[] dataArr;
	private boolean gameFinished;
	private int endGameC;
	private float eopacity;

	public void enter(GameContainer container, StateBasedGame stateBasedGame) throws SlickException {
		container.getInput().clearKeyPressedRecord();
	}
	/**The constructor sets the gameplay state's ID.
	 * 
	 * @param stateID	The gameplay state's ID.
	 */
	public GameplayState(int stateID) 
	{
		this.stateID = stateID;
	}//Ends constructor

	/**getID returns the gameplay state's ID (i.e. 1).
	 * 
	 * @return stateID		The gameplay's ID.
	 */
	public int getID() {
		return stateID;
	}//Ends getID

	/**createbgShape sets the number of arrows used in the background to indicate the direction of gravity.	 */
	public void createbgShape(){
		while(numberArr<5)
			numberArr=(int)(Math.random()*10);
		for(int i=0; i<numberArr;i++)
			bgShape.add(createArr());
	}//Ends createbgShape

	/**createArr creates the arrows used in the background to indicate the direction of gravity.
	 * 
	 * @return aArrow	An arrow in the background.
	 */
	public ArrowDir createArr(){
		float x=(float) (Math.random()*800);
		float op=new Random().nextFloat()*0.2f;
		float y=(float) (Math.random()*600);
		ArrowDir aArrow=new ArrowDir(new Point(x,y),op,arrow);
		return aArrow;
	}//Ends createArr

	/**init sets the initial values for the variables.
	 * 
	 * @param gc					gc holds the game.
	 * @param sbg					sbg holds the statebased game.
	 * @exception SlickException	SlickException is a generic exception thrown by everything in the library.
	 */
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException { 
		dataArr =new int[16];
		gameFinished=false;
		endGameC=0;
		try {
			getData();
		} catch (NumberFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		levelList=new String[15];
		input = gc.getInput();
		paused = false;
		eUp= true;
		gUp=false;
		gDown=true;
		gLeft= false;
		gRight=false;
		opacity=0.0f;
		eopacity=0.0f;
		aspeed=5;
		c=0;
		bg=new Image("Data/bg.png");
		arrow= new Image("Data/arrow.png");
		endGameCounter=0;
		gc.setTargetFrameRate(120);
		zoneBol=false;
		removeZone=new ArrayList<Integer>();
		createbgShape();
		levelClear= new Image("Data/clear.png");
		endGameImg= new Image("Data/endGame.png");
		currentAngle=0;
		movesCounter= 0;
		createFiles();
		if(cLevel<15){
			File aF=new File(levelList[cLevel]);
			readFiles(aF);
			cLevel=0;
		}
		
		try{
			blockEffect = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("Data/Fireworks.wav"));
		}/*ends try*/ catch (IOException e) {
			e.printStackTrace();
		}
		font = new Font("Microsoft Tai Le", Font.BOLD, 18);
		trueTypeFont = new TrueTypeFont(font, true);
		message1 = "Menu";
		message2 = "Resume";
		try{
			buttonEffect = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("Data/button-9.wav"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}//Ends init

	private void createFiles() {
		for(int i=1;i<=15;i++){
			String filePath="level/L"+i+".txt";
			levelList[i-1]=filePath;
		}

	}

	/**createObstacles sets the position and dimensions of the obstacles.	 */
	private void setObstacles() {
		Obstacles=currentL.obstacleList;
	}//Ends createObstacles

	private void setBlocks() {
		Blocks=currentL.blockList;
		endCount=currentL.endgameCounter;
	}//Ends createObstacles

	private void setEndzone() {
		EndZones=currentL.endzoneList;
	}//Ends createObstacles

	private void setEnemies() throws SlickException{
		enemies=currentL.enemyList;
	}

	private void setTelepoters() throws SlickException {
		Teleporters=currentL.telePort;
	}//Ends createObstacles

	public void readFiles(File file) throws SlickException {
		try {	
			currentL=new Level();
			currentL.importLevel(file);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		setObstacles();
		setBlocks();
		setEndzone();
		setTelepoters();
		setEnemies();

	}

	private void endGame(GameContainer gc, StateBasedGame sbg, Graphics gc1) throws SlickException {
		gc1.fillRect((int)(Math.random()*800), (int)(Math.random()*600), (int)(Math.random()*800), (int)(Math.random()*600));

	}
	/**render draws the blocks the player controls, the obstacles, the endzones and the background arrows.
	 * 
	 * @param gc					gc holds the game.
	 * @param sbg					sbg holds the state-based game.
	 * @param gc1					gc1 allows the method to draw to the screen.
	 * @exception SlickException	SlickException is a generic exception thrown by everything in the library.
	 */
	public void render(GameContainer gc, StateBasedGame sbg, Graphics gc1) throws SlickException {
		gc1.setAntiAlias(true);
		gc1.drawImage(bg,0,0,800,600,bg.getWidth(),bg.getHeight(),250, 0);
		if(cLevel<15){
			message3 = "Moves: " + movesCounter;
			trueTypeFont.drawString(666f, 10f, "R to Reset", Color.gray);
			trueTypeFont.drawString(666f, 30f, message3, Color.gray);
			trueTypeFont.drawString(666f, 50f, "Level: "+(cLevel+1), Color.gray);
			if(dataArr[cLevel+1]==999)
				trueTypeFont.drawString(666f, 70f, "Best: 0", Color.gray);
			else
				trueTypeFont.drawString(666f, 70f, "Best: "+dataArr[cLevel+1], Color.gray);
		}

		if(!paused){
			gc1.setColor(Color.white);
			if(bgShape.size()<numberArr)
				bgShape.add(createArr());
			for(int i = 0; i < bgShape.size(); i++){
				gc1.drawImage(bgShape.get(i).img,bgShape.get(i).position.getX(),bgShape.get(i).position.getY(),bgShape.get(i).getColor());
			}//Ends for loop
			for(int i = 0; i < EndZones.size(); i++){
				EndZone temp=EndZones.get(i);
				gc1.setColor(temp.getColor());
				gc1.fillRect(temp.getBounds().x,temp.getBounds().y,temp.getBounds().height, temp.getBounds().width);
				gc1.drawRect(temp.getBounds().x,temp.getBounds().y,20, 20);
			}//Ends for loop
			for(int i = 0; i < enemies.size(); i++){
				Enemy temp=enemies.get(i);
				gc1.setColor(temp.getColor());
				gc1.fillOval(temp.getBounds().x, temp.getBounds().y, 20, 20);

			}//Ends for loop
			for(int i = 0; i < Blocks.size(); i++){
				Block temp=Blocks.get(i);
				gc1.setColor(temp.getColor());
				gc1.drawImage(temp.getImage(), temp.getBounds().x,temp.getBounds().y);

			}//Ends for loop
			for(int i = 0; i < Obstacles.size(); i++){
				Obstacle temp=Obstacles.get(i);
				gc1.setColor(Color.white);
				gc1.drawImage(temp.getImage(), temp.getBounds().x,temp.getBounds().y);
			}//Ends for loop

			for(int i = 0; i < Teleporters.size(); i++){

				Teleporter temp=Teleporters.get(i);
				gc1.drawImage(temp.t1,temp.getBounds().get(0).x, temp.getBounds().get(0).y);
				gc1.drawImage(temp.t2,temp.getBounds().get(1).x, temp.getBounds().get(1).y);
			}//Ends for loop
		
			if(gameFinished==true){
				Color myAlphaColor=new Color(1f,1f,1f,eopacity);
				gc1.drawImage(endGameImg, clearX, clearY,myAlphaColor);
			}else{
				Color myAlphaColor=new Color(1f,1f,1f,opacity);
				gc1.drawImage(levelClear, clearX, clearY,myAlphaColor);
			}
			
		}//Ends if
		else{
			gc1.setColor(new Color(1f,1f,1f,.2f));
			gc1.fillRect(340, 200, 100, 200);
			message3 = "Moves: " + movesCounter;
			trueTypeFont.drawString(356f, 210.0f, message1, Color.white);
			trueTypeFont.drawString(356f, 240.0f, message2, Color.white);
			trueTypeFont.drawString(356f, 270.0f, "Reset", Color.white);
			trueTypeFont.drawString(356f, 300.0f, "Levels", Color.white);
		}


	}//Ends render

	/**update updates the values the dictate the position and state of the arrows, blocks and endzones.
	 *  
	 * @param gc					gc holds the game.
	 * @param sbg					sbg holds the statebased game.
	 * @param delta					delta holds the value by which the "level cleared" sign's size increases when displayed.
	 * @exception SlickException	SlickException is a generic exception thrown by everything in the library.
	 */
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		input=gc.getInput();
		if(cLevel>=15){
			gameFinished=true;
			eopacity+=0.1f;
		}
		if(input.isKeyPressed(Input.KEY_ESCAPE)){
			paused = !paused;
		}
		if(!paused && gameFinished==false){
			if(zoneBol==true)
				removeBlock(removeZone);
			if(endGameCounter!=endCount){
				for(int i = 0; i < bgShape.size(); i++){
					bgShape.get(i).setImage(arrow);
					if(bgShape.get(i).opacity<=1f)
						bgShape.get(i).update(delta);
					else
						bgShape.remove(i);
				}//Ends for loop
				moveEnemies();
				if(movable()==true){
					if(input.isKeyDown(Input.KEY_A) || gLeft==true)
					{
						arrow.setCenterOfRotation(arrow.getWidth() / 2, arrow.getHeight()/ 2);
						arrow.rotate(270-currentAngle);
						currentAngle=270;
						gUp=false; gDown=false; gLeft= true; gRight=false;
						if(input.isKeyPressed(Input.KEY_A))
							movesCounter++;
					}//Ends if statement
					if(input.isKeyDown(Input.KEY_S) || gDown==true)
					{
						arrow.setCenterOfRotation(arrow.getWidth() / 2, arrow.getHeight()/ 2);
						arrow.rotate(180-currentAngle);
						currentAngle=180;
						gUp=false; gDown=true; gLeft= false; gRight=false;
						if(input.isKeyPressed(Input.KEY_S))
							movesCounter++;
					}//Ends if statement
					if(input.isKeyDown(Input.KEY_W)|| gUp==true)
					{
						arrow.setCenterOfRotation(arrow.getWidth() / 2, arrow.getHeight()/ 2);
						arrow.rotate(360-currentAngle);
						currentAngle=0;
						gUp=true; gDown=false; gLeft= false; gRight=false;
						if(input.isKeyPressed(Input.KEY_W))
							movesCounter++;
					}//Ends if statement
					if(input.isKeyDown(Input.KEY_D) || gRight==true)
					{
						arrow.setCenterOfRotation(arrow.getWidth() / 2, arrow.getHeight()/ 2);
						arrow.rotate((90-currentAngle));
						currentAngle=90;
						gUp=false; gDown=false; gLeft= false; gRight=true;
						if(input.isKeyPressed(Input.KEY_D))
							movesCounter++;
					}//Ends if statement
					if(input.isKeyPressed(Input.KEY_R)){
						nextLevel();
					}
					if(input.isKeyDown(Input.KEY_LEFT)){

						if(gDown||gUp){
							for(int j = 0; j < aspeed; j++){
								for(int i = 0; i < Blocks.size(); i++){
									if(!Blocks.get(i).collisionLeft(  Obstacles, Blocks))
										Blocks.get(i).moveLeft();
								}//Ends if statement
							}//Ends for loop
							if(input.isKeyPressed(Input.KEY_LEFT))
								movesCounter++;
						}//Ends if statement
					}//Ends if statement
					if(input.isKeyDown(Input.KEY_RIGHT))
					{
						if(gDown||gUp){
							for(int j=0; j<aspeed; j++){
								for(int i = 0; i < Blocks.size(); i++){
									if(!Blocks.get(i).collisionRight(  Obstacles, Blocks)){
										Blocks.get(i).moveRight();
									}//Ends if statement 
								}//Ends for loop
							}//Ends for loop
							if(input.isKeyPressed(Input.KEY_RIGHT))
								movesCounter++;
						}//Ends if statement
					}//Ends if statement
					if(input.isKeyDown(Input.KEY_UP))
					{
						if(gLeft||gRight){
							for(int j=0; j<aspeed; j++){
								for(int i = 0; i < Blocks.size(); i++){
									if(!Blocks.get(i).collisionUp( Obstacles, Blocks))
										Blocks.get(i).moveUp();
								}//Ends if statement
							}//Ends for loop
							if(input.isKeyPressed(Input.KEY_UP))
								movesCounter++;
						}//Ends if statement
					}//Ends if statement
					if(input.isKeyDown(Input.KEY_DOWN))
					{
						if(gLeft||gRight){
							for(int j = 0; j < aspeed; j++){
								for(int i = 0; i < Blocks.size(); i++){
									if(!Blocks.get(i).collisionDown(Obstacles, Blocks))
										Blocks.get(i).moveDown();
								}//Ends for loop
							}//Ends for loop

							if(input.isKeyPressed(Input.KEY_DOWN))
								movesCounter++;
						}//Ends if statement
					}//Ends if statement
				}//Ends if statement
				if(gDown){
					checkEndZone();
					checkEnemies();
					checkTeleporters();
					checkOffScreen();
					for(int j = 0; j < aspeed+5; j++){
						for(int i = 0; i < Blocks.size(); i++){
							if(!Blocks.get(i).collisionDown(Obstacles, Blocks) )
								Blocks.get(i).moveDown();
						}//Ends for loop
					}//Ends for loop
				}//Ends if statement
				if(gLeft){
					checkEndZone();
					checkEnemies();
					checkTeleporters();
					checkOffScreen();
					for(int j = 0; j < aspeed+5; j++){
						for(int i = 0; i < Blocks.size(); i++){
							if(!Blocks.get(i).collisionLeft(Obstacles, Blocks)  )
								Blocks.get(i).moveLeft();
						}//Ends for loop
					}//Ends for loop
				}//Ends if statement
				if(gRight){
					checkEndZone();
					checkEnemies();
					checkTeleporters();
					checkOffScreen();
					for(int j = 0; j < aspeed+5; j++){
						for(int i = 0; i < Blocks.size(); i++){
							if(!Blocks.get(i).collisionRight(Obstacles, Blocks) ){
								Blocks.get(i).moveRight();
							}//Ends if statement
						}//Ends for loop
					}//Ends for loop
				}//Ends if statement
				if(gUp){
					checkEndZone();
					checkEnemies();
					checkTeleporters();
					checkOffScreen();
					for(int j = 0; j < aspeed+5; j++){
						for(int i = 0; i < Blocks.size(); i++){
							if(!Blocks.get(i).collisionUp( Obstacles, Blocks) )
								Blocks.get(i).moveUp();
						}//Ends for loop
					}//Ends for loop
				}//Ends if statement
			}//Ends if statement
			else{
				//next level
				opacity+= (delta*0.001); //shows level clear sign.
				if(clearX>200 && clearY>250){
					clearY-=delta*0.05;
					clearX-=delta*0.01;
				}//Ends if statement
				if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON)){
					cLevel++;
					if(cLevel>=LevelCState.levelOn){
						LevelCState.levelOn=cLevel+1;
					}
					updateGameData();
					nextLevel();
				}

			}//Ends else statement
		}//ends if
		if(paused){
			int mouseX = input.getMouseX();
			int mouseY = input.getMouseY();
			if(mouseX > 356 && mouseX< 420 && mouseY <240 && mouseY > 210)
				if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON)){
					buttonEffect.playAsSoundEffect(1.0f, 1.0f, false);
					sbg.enterState(BlocksGame.MAINMENUSTATE);
					movesCounter=0;
					init(gc, sbg);
				}
			if(mouseX > 356 && mouseX< 440 && mouseY <259 && mouseY > 240)
				if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON)){
					buttonEffect.playAsSoundEffect(1.0f, 1.0f, false);
					paused = false;
				}
			if(mouseX > 356 && mouseX< 440 && mouseY <290 && mouseY > 270)
				if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON)){
					buttonEffect.playAsSoundEffect(1.0f, 1.0f, false);
					nextLevel();
				}
			if(mouseX > 356 && mouseX< 440 && mouseY <320 && mouseY > 300)
				if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON)){
					buttonEffect.playAsSoundEffect(1.0f, 1.0f, false);
					sbg.enterState(BlocksGame.LEVELCSTATE);
				}
		}


	}//Ends update

	private void updateGameData() {
		try {
			getData();
			int max=dataArr[cLevel];
			dataArr[0]=LevelCState.levelOn;
			if(movesCounter<=max){
				dataArr[cLevel]=movesCounter;
			}
			writer = new PrintWriter("gameData.txt","UTF-8");
			for(int j=0;j<dataArr.length;j++){
				writer.print(dataArr[j]);
				if(j<15)
					writer.println();

			}
			writer.close();
		} catch (NumberFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
	private void checkOffScreen() throws SlickException{
		for(int i = 0; i < Blocks.size(); i++)
			if(Blocks.get(i).getBounds().x<0||Blocks.get(i).getBounds().y<0||Blocks.get(i).getBounds().x>800||Blocks.get(i).getBounds().y>600){
				nextLevel();
				return;
			}
	}

	public void nextLevel() throws SlickException { //resets everything for the next level
		if(cLevel<15){
			File aF=new File(levelList[cLevel]);
			readFiles(aF);
			paused = false;
			eUp= true;
			gUp=false;
			gDown=true;
			gLeft= false;
			gRight=false;
			opacity=0.0f;
			aspeed=5;
			c=0;
			endGameCounter=0;
			zoneBol=false;
			removeZone=new ArrayList<Integer>();
			createbgShape();
			//			arrow.rotate(180-currentAngle);
			//			currentAngle=0;
			movesCounter= 0;
		}

	}

	private void checkEnemies() throws SlickException{
		if(enemies.size()!=0){
			for(int i = 0; i < enemies.size(); i++){
				for(int j = 0; j< Blocks.size(); j++){
					if(enemies.get(i).getBounds().intersects(Blocks.get(j).getBounds())){
						currentAngle=0;
						nextLevel();
					}

				}//Ends for loop
			}//Ends for loop
		}
	}//Ends checkEnemies

	private void moveEnemies(){
		if(enemies.size()!=0){
			for(int i=0; i<enemies.size(); i++){
				if(enemies.get(i).up){
					enemies.get(i).moveUp();
					enemies.get(i).count();
				}//Ends if statement
				else if(enemies.get(i).down){
					enemies.get(i).moveDown();
					enemies.get(i).count();
				}//Ends else statement
				else if(enemies.get(i).left){
					enemies.get(i).moveLeft();
					enemies.get(i).count();
				}
				else{
					enemies.get(i).moveRight();
					enemies.get(i).count();
				}
				if(enemies.get(i).osci%70==0){
					if(enemies.get(i).up)
						enemies.get(i).setDown();
					else if(enemies.get(i).down)
						enemies.get(i).setUp();
					else if(enemies.get(i).left)
						enemies.get(i).setRight();
					else
						enemies.get(i).setLeft();
				}//Ends if statement
			}//Ends for loop
		}

	}//Ends moveEnemies
	private void checkTeleporters() {
		if(Teleporters.size()!=0){
			Teleporter temp;
			int delta = 20;
			int diffX=Teleporters.get(0).getBounds().get(0).x - Teleporters.get(0).getBounds().get(1).x;
			for(int i=0; i<Teleporters.size();i++){
				temp = Teleporters.get(i);
				for(int j = 0; j < Blocks.size(); j++){
					for(int k = 0; k < temp.getBounds().size(); k++){
						if(temp.getBounds().get(k).intersects(Blocks.get(j).getBounds())){
							if(k == 0){
								if(gUp){
									if(!occupied(Blocks.get(j).getBounds().x-diffX, temp.getBounds().get(1).y-delta,'u'))
										Blocks.get(j).teleport(Blocks.get(j).getBounds().x-diffX, temp.getBounds().get(1).y-delta);
								}//Ends if statement
								else if(gDown){
									if(!occupied(Blocks.get(j).getBounds().x-diffX, temp.getBounds().get(1).y+delta,'d'))
										Blocks.get(j).teleport(Blocks.get(j).getBounds().x-diffX, temp.getBounds().get(1).y+delta);
								}
								else if(gRight){
									if(!occupied(Blocks.get(j).getBounds().x-diffX+delta+delta, temp.getBounds().get(1).y,'r'))
										Blocks.get(j).teleport(Teleporters.get(i).getBounds().get(1).x+delta, temp.getBounds().get(1).y);
								}
								else{ 
									if(!occupied(Blocks.get(j).getBounds().x-diffX-delta-delta, temp.getBounds().get(1).y,'l'))
										Blocks.get(j).teleport(Blocks.get(j).getBounds().x-diffX-delta-delta, temp.getBounds().get(1).y);
								}
							}//Ends if statement
							else{
								if(gUp){
									if(!occupied(Blocks.get(j).getBounds().x+diffX, temp.getBounds().get(0).y-delta,'u'))
										Blocks.get(j).teleport(Blocks.get(j).getBounds().x+diffX, temp.getBounds().get(0).y-delta);
								}//Ends if
								else if(gDown){
									if(!occupied(Blocks.get(j).getBounds().x+diffX, temp.getBounds().get(0).y+delta,'d'))
										Blocks.get(j).teleport(Blocks.get(j).getBounds().x+diffX, temp.getBounds().get(0).y+delta);
								}
								else if(gRight){
									if(!occupied(Blocks.get(j).getBounds().x+diffX+delta+delta, temp.getBounds().get(0).y,'r'))
										Blocks.get(j).teleport(Blocks.get(j).getBounds().x+diffX, temp.getBounds().get(0).y);
								}
								else{
									if(!occupied(temp.getBounds().get(0).x-delta, temp.getBounds().get(0).y,'l'))
										Blocks.get(j).teleport(temp.getBounds().get(0).x-delta, temp.getBounds().get(0).y);
								}
							}//Ends else statement
						}//Ends if statement
					}//Ends for loop
				}//Ends for loop

			}//Ends for loop

		}

	}//Ends 

	/**checkEndZone checks to see if any block has reached its appropriate endzone*/
	private void checkEndZone() {
		for(int i=0; i<EndZones.size();i++){
			for(int j = 0; j < Blocks.size(); j++){
				if(EndZones.get(i).getBounds().intersects(Blocks.get(j).getBounds())){
					if(EndZones.get(i).endID==Blocks.get(j).blockid){
						blockEffect.playAsSoundEffect(1.0f, 1.0f, false);
						Blocks.get(j).finish();
						Blocks.remove(j);
						zoneBol=true;
						removeZone.add(i);
						endGameCounter++;
					}//Ends if statement
				}//Ends if statement
			}//Ends for loop
		}//Ends for loop
	}//Ends checkEndZone

	/**Movable checks to see if the gravity can move the blocks.
	 * 
	 * @return		Returns true if the blocks can move (in the direction of gravity) and false otherwise.
	 */
	private boolean movable() {
		if(gDown==true){
			for(int i = 0; i < Blocks.size(); i++){
				if(!Blocks.get(i).collisionDown(Obstacles, Blocks))
					return false;
			}//Ends for loop
		}//Ends if statement
		else if(gUp==true){
			for(int i = 0; i < Blocks.size(); i++){ 
				if(!Blocks.get(i).collisionUp( Obstacles, Blocks))
					return false;
			}//Ends for loop
		}//Ends else if
		else if(gRight==true){
			for(int i = 0; i < Blocks.size(); i++){
				if(!Blocks.get(i).collisionRight(  Obstacles, Blocks))
					return false;
			}//Ends for loop
		}//Ends else if
		else if(gLeft==true){
			for(int i = 0; i < Blocks.size(); i++){
				if(!Blocks.get(i).collisionLeft(  Obstacles, Blocks))
					return false;
			}//Ends for loop
		}//Ends else if	
		return true;
	}//Ends movable

	/**removeBlock calls the endzone's shrink method.
	 * 
	 * @param removeZone2.get	Holds the ID of the endzone.
	 */
	private void removeBlock(ArrayList removeZone){
		for(int i=0; i<removeZone.size();i++){
			EndZones.get((Integer) removeZone.get(i)).shrink();
		}

	}//Ends removeBlock

	private boolean occupied(int x, int y, char dir){
		for(int i =0; i < Blocks.size(); i++){
			Block temp = Blocks.get(i);
			if(dir== 'u'|| dir == 'd'){
				if((temp.getBounds().x-20 < x && temp.getBounds().x+20 > x) && y ==temp.getBounds().y)
					return true;
			}//Ends if statement
			if(dir== 'l'|| dir == 'r'){
				if(temp.getBounds().x==x && (temp.getBounds().y-20 < y && temp.getBounds().y+20 > y))
					return true;
			}//Ends if statement
		}//Ends for loop
		return false;
	}//Ends occupied

	private void getData() throws NumberFormatException, IOException {
		fr = new FileReader(new File("gameData.txt"));
		br = new BufferedReader(fr);
		String s="";
		int i=0;
		while ((s = br.readLine()) != null) {
			dataArr[i]=Integer.parseInt(s);
			i++;
		}
	}

}//Ends GameplayState