import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Observable;
import java.util.Observer;

public class LevelMapController implements KeyListener, Observer {
	// declaring variables
	
	// a Game instance
	private Game game;
	// a LevelMap instance
	private LevelMap mapUI;
	
	/**
	 * constructor
	 * 
	 * @param 	game		the gamestate
	 * @param 	mapUI		the game rendering
	 * @param 	window		the jpanel
	 * @return	-
	 * @throws	-
	 */
	public LevelMapController(Game game, LevelMap mapUI, WarehouseBoss window) {
		super();
		// initialise Game instance
		this.game = game;
		// initialise LevelMap instance
		this.mapUI = mapUI;
		// add an observer to the game
		this.game.addObserver(this);
		// intialise the map
		initLevelMap();
	}
	
	/**
	 * method: initLevelMap() -> initialise the rendered image for the start of the game
	 * 
	 * @param	-
	 * @return	-
	 * @throws	-
	 */
	public void initLevelMap() {
		// clear the map
		this.mapUI.clearLevelMap();
		// initialise the matrix
		this.mapUI.setGrid(this.game.getMatrix());
		// set the player's position
		this.mapUI.setPlayerPosition(this.game.getPlayerColumn(), this.game.getPlayerRow());
		// set the box positions
		this.mapUI.setBoxPositions(this.game.getBoxes());
		// set the berry positions
		this.mapUI.setBerryPositions(this.game.getBerries());
		// set the number of moves made
		this.mapUI.setNumMoves(this.game.getNumMoves());
		// update the timer
		this.mapUI.updateTime(this.game.getSeconds());
		// set the direction the player is facing
		this.mapUI.setDirection(this.game.getPlayerDirection());
		// set the player's reference to a box (if it is there)
		this.mapUI.setIsBox(this.game.getPlayerIsBox());
	}

	/**
	 * override: keyPressed() -> takes in keyboard input and acts accordingly
	 * 
	 * @param	arg0	a KeyEvent instance
	 * @return	-
	 * @throws	-
	 */
	@Override
	public void keyPressed(KeyEvent arg0) {
		// get the key input
		int key = arg0.getKeyCode();
		// act accordingly
		if (key == KeyEvent.VK_W || key == KeyEvent.VK_UP) {
			this.game.update('W');
		} else if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) {
			this.game.update('A');
		} else if (key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) {
			this.game.update('S');
		} else if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) {
			this.game.update('D');
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}

	/**
	 * override: update() -> update the game baesd on input
	 * 
	 * @param	arg0	an Observable instance
	 * @param	arg1	an Object instance
	 * @return	-
	 * @throws	-
	 */
	@Override
	public void update(Observable arg0, Object arg1) {
		// set the grid of the game
		this.mapUI.setGrid(this.game.getMatrix());
		// set the player's position
		this.mapUI.setPlayerPosition(this.game.getPlayerColumn(), this.game.getPlayerRow());
		// set the box positions
		this.mapUI.setBoxPositions(this.game.getBoxes());
		// set the berry positions
		this.mapUI.setBerryPositions(this.game.getBerries());
		// set the number of moves made
		this.mapUI.setNumMoves(this.game.getNumMoves());
		// update the timer
		this.mapUI.updateTime(this.game.getSeconds());
		// set the direction the player is facing
		this.mapUI.setDirection(this.game.getPlayerDirection());
		// set the player's reference to a box (if it is there)
		this.mapUI.setIsBox(this.game.getPlayerIsBox());
		// repaint the window
		this.mapUI.repaint();

		// if we have won the game and it isn't a tutorial
		if (this.game.isWin() && !this.game.isTutorial()) {
			// show the option window
			int option = this.mapUI.showWin();
			//  based on the input, act accordingly
			if (option == 1) {
				this.game.newLevel();
				this.game.resetGame();
				initLevelMap();
			} else if (option == 2) {
				this.game.resetGame();
				initLevelMap();
			}
		}
	}
}