import java.io.IOException;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.ResourceLoader;

/**This class initiates the Blocks(the game). It adds the different states (Main menu, gameplay, instructions, etc.). It also makes the main menu the initial state.
 *
 * @author Craig Blumenfeld
 * @author Jason Ma
 * @version 1.2 April 17, 2013
 */
public class BlocksGame extends StateBasedGame {
    public static final int MAINMENUSTATE = 0;
    public static final int GAMEPLAYSTATE = 1;
    public static final int INSTRUCTIONSTATE = 2;
    public static final int	BUILDLEVELSTATE= 3;
    public static final int	LEVELCSTATE= 4;
    public Audio music;
    
    /**The constructor sets the title of the window to "Blocks".     
     * @throws IOException */
    public BlocksGame() throws IOException
    {
        super("Blocks");
        music= AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("Data/Music.wav"));
    }//Ends constructor
 
    public static void main(String[] args) throws SlickException, IOException
    {
         AppGameContainer app = new AppGameContainer(new BlocksGame());
         app.setDisplayMode(800, 600, false);
         app.setShowFPS(false);
         app.setVSync(true);
         app.start();
         
    }//Ends main

    /**initStatesList adds all of the states Blocks will use.
     * 
     * @param gameContainer			gameContainer holds the game.
     * @exception SlickException	SlickException is a generic exception thrown by everything in the library.
     */
    public void initStatesList(GameContainer gameContainer) throws SlickException {
    	music.playAsMusic(1f, 1f, true);
        this.addState(new MainMenuState(MAINMENUSTATE));
        this.addState(new GameplayState(GAMEPLAYSTATE));
        this.addState(new InstrutionState(INSTRUCTIONSTATE));
        this.addState(new BuildLevelState(BUILDLEVELSTATE));
        this.addState(new LevelCState(LEVELCSTATE));
  
    }//Ends initStatesList
    
}//Ends BlocksGame