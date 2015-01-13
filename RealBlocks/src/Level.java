import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.io.FileReader;
import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;





public class Level {
	private FileReader fr;
	private BufferedReader br;
	private int row, col;
	public final ArrayList<Point> startPos, goals;
	public ArrayList<Block> blockList;
	public ArrayList<Obstacle> obstacleList;
	public ArrayList<Teleporter> telePort;
	public ArrayList<EndZone> endzoneList;
	public ArrayList<String> messages;
	private Image b1;
	private Image b2;
	private Image b3;
	private Image b4;
	private Image obs;
	public int endgameCounter;
	public ArrayList<Enemy> enemyList;
	
	public Level() throws Exception {
		startPos = new ArrayList<Point>();
		goals = new ArrayList<Point>();
		blockList = new ArrayList<Block>();
		obstacleList = new ArrayList<Obstacle>();
		endzoneList = new ArrayList<EndZone>();
		telePort = new ArrayList<Teleporter>();
		messages=new ArrayList<String>();
		enemyList=new ArrayList<Enemy>();
		
	}
	
	public void importLevel(File txt) throws Exception  {
		fr = new FileReader(txt); 
		br = new BufferedReader(fr); 
		String line;
		int y = 0;
		int blockCount=0;
		int endzCount=0;
		int teleCount=0;
		int[] telex=new int[2];
		int[] teley=new int[2]; 
		while((line = br.readLine()) != null) {
			for(int x=0; x<line.length(); x++) {
				if (line.charAt(x) == '#'){
					Obstacle tempObstacle=new Obstacle(x*20,y*20);
					obstacleList.add(tempObstacle);
				}else if(isInteger(line.substring(x,x+1))) {//1-4 is block 1-5 2-6 3-7 4-8
					int num=Integer.parseInt(line.substring(x,x+1));
					if(num<=4){
						Block aBlock=new Block(x*20, y*20, num);
						blockList.add(aBlock);
						blockCount++;
					}else{
						EndZone aZone=new EndZone(x*20, y*20,num-4);
						endzoneList.add(aZone);//5-8
						endzCount++;
					}
				}else if(line.charAt(x) == 'T'){
					telex[teleCount]=x;
					teley[teleCount]=y;
					teleCount++;
				}else if(line.charAt(x) == 'E'){
					Enemy aEnemy=new Enemy(x*20,y*20,1,1);
					enemyList.add(aEnemy);
				}else if(line.charAt(x) == 'e'){
					Enemy aEnemy=new Enemy(x*20,y*20,1,2);
					enemyList.add(aEnemy);
				}
				
			}
			y++;
			if(teleCount==2){
				telePort.add(new Teleporter(telex[0]*20,teley[0]*20,telex[1]*20,teley[1]*20,1));
			}
		} 
		endgameCounter=blockCount;
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
    
    public int[] findTele(char t, File txt) throws IOException{
    	int[] temp=new int[2];
    	FileReader fra = new FileReader(txt); 
		BufferedReader bra = new BufferedReader(fra); 
		String line;
		int y = 0;
		while((line = bra.readLine()) != null) {
			for(int x=0; x<line.length(); x++) {
				if (line.charAt(x) == 's'){
					temp[0]=x;
				}
			}
			y++;
		} 
		temp[1]=y;
    	return temp;
    }
}