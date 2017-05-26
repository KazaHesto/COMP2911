import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Stack;

import javax.swing.Timer;

public class Game extends Observable implements ActionListener {

	// defining enums to represent "DIRECTION"
	public enum DIRECTION {
		UP, DOWN, LEFT, RIGHT
	}
	
	// defining enums to represent the possible "GAMESTATE"
	public enum GAMESTATE {
		TUTORIAL, GAME
	};

	// declaring variables
	
	// the matrix representing the map
	private int[][] matrix;
	// the original state of each map
	private int[][] resetState;
	// a stack to implement "undo-ing" moves (for the player)
	private Stack<Integer> undoPlayer;
	// a stack to implement "undo-ing" moves (for boxes)
	private Stack<ArrayList<Box>> undoBoxes;
	// how many moves have been made so far
	private int numMoves;
	// a Player instance
	private Player player;
	// the original Player instance
	private Player resetPlayer;
	// a list of the boxes in the map
	private ArrayList<Box> boxes;
	// the original state of the boxes
	private ArrayList<Box> resetBoxes;
	// a list of the berries in the map
	private ArrayList<Berry> berries;
	// the original state of the berries
	private ArrayList<Berry> resetBerries;
	// a Boolean to represent if a level has been cleared
	private Boolean checkWin;
	// a timer for how long the consumer has taken to complete the level
	private Timer gameTimer;
	// the number of "seconds" (from the timer) to be displayed in the game
	private int seconds;
	// the number of rows in the map
	private int row;
	// the number of columns in the map
	private int column;
	// the state of the berries in the map
	private boolean berryState;
	// the number of berries in the map
	private int berryCount;
	// the current state of the game
	private GAMESTATE state = GAMESTATE.GAME;

	// constructor
	/**
	 * constructor
	 * 
	 * @param 	row			the size of the map's rows
	 * @param 	column		the size of the map's columns
	 * @return	-
	 * @throws 	-
	 */
	public Game(int row, int column) {
		// if we are creating a new game (not a tutorial)
		if(state == GAMESTATE.GAME){
			// initialise the number of rows in the game
			this.row = row;
			// initialise the number of columns in the game
			this.column = column;
			// create an array so that we can undo moves for the player
			this.undoPlayer = new Stack<Integer>();
			// create an array so we can undo box movement
			this.undoBoxes = new Stack<ArrayList<Box>>();
			// creating a new timer for the game
			this.gameTimer = new Timer(1000, this);
			// initialising the time the consumer to complete the level as 0 initially
			this.seconds = 0;
			// create a new level (maze)
			newLevel();
		}
	}
	
	/**
	 * alternative constructor (for tutorials only)
	 * 
	 * @param 	row			the size of the map's rows
	 * @param 	column		the size of the map's columns 
	 * @param 	matrix		the tutorial map
	 * @return	-
	 * @throws	-
	 */
	Game(int row, int column, int[][] matrix){
		// we are creating a new tutorial
		state = GAMESTATE.TUTORIAL;
		// initialise the number of rows in the game
		this.row = row;
		// initialise the number of columns in the game
		this.column = column;
		// create a new player and place him on the map
		this.player = new Player(1, 1);
		// create an array so that we can undo moves for the player
		this.undoPlayer = new Stack<Integer>();
		// create an array so we can undo box movement
		this.undoBoxes = new Stack<ArrayList<Box>>();
		// creating a new timer for the game
		this.gameTimer = new Timer(1000, this);
		// initialising the time the consumer to complete the level as 0 initially
		this.seconds = 0;
		// initialising a list to hold the boxes in the map
		this.boxes = new ArrayList<Box>();
		// adding a new box to the list
		this.boxes.add(new Box(2, 4));
		// adding a new box to the list
		this.boxes.add(new Box(3, 7));
		// adding a new box to the list
		this.boxes.add(new Box(4, 7));
		// initialising the list of original boxes to equal the list of boxes just initialised
		// refer to lines previous
		this.resetBoxes = getBoxes();
		// initialising a list to store the berries in the map
		this.berries = new ArrayList<Berry>();
		// adding a new berry to the list of berries
		this.berries.add(new Berry(1,6));
		// intialising the list of original berries to equal the list of boxes just initialised
		// refer to lines previous
		this.resetBerries = getBerries();
		// setting the berries to be un-triggered
		this.berryState = false;
		// setting the number of berries triggered to be 0
		this.berryCount = 0;
		// create the tutorial level
		newTutLevel(matrix);
	}

	/**
	 * alternative constructor (for normal games)
	 * 
	 * @param 	data	the saved data of a previous game
	 * @return	-
	 * @throws	-
	 */
	public Game(SaveData data) {
		// initialising matrix to saved data's matrix
		this.matrix = data.matrix;
		// initialising resetState to saved data's resetState
		this.resetState = data.resetState;
		// initialising player to saved data's player
		this.player = data.player;
		// initialising resetPlayer to saved data's resetPlayer
		this.resetPlayer = data.resetPlayer;
		// initialising undoPlayer to saved data's undoPlayer
		this.undoPlayer = data.undoPlayer;
		// initialising gameTimer to it's original state
		this.gameTimer = new Timer(1000, this);
		// initialising seconds to saved data's seconds
		this.seconds = data.seconds;
		// initialising number of moves to saved data's number of moves
		this.numMoves = data.numMoves;
		// initialising boxes to saved data's boxes
		this.boxes = data.boxes;
		// initialising berries to saved data's berries
		this.berries = data.berries;
		// initialising undoBoxes to saved data's undoBoxes
		this.undoBoxes = data.undoBoxes;
		// initialising resetBoxes to saved data's resetBoxes
		this.resetBoxes = data.resetBoxes;
		// initialising win check to saved data's win check
		this.checkWin = false;
		// initialising berryState to saved data's berryState
		this.berryState = data.berryState;
		// initialising berryCount to saved data's berryCount
		this.berryCount = data.berryCount;
	}

	/**
	 * method: newLevel() -> generates a new level
	 * 
	 * @param	-
	 * @return	-
	 * @throws	-
	 */
	public void newLevel() {
		// create a new LevelGenerator instance
		LevelGenerator gen = new LevelGenerator();
		// intialise the game's matrix based on the generated level
		// (the "-2" accounts for the fact that the path tracer doesn't
		// recognise outside walls)
		this.matrix = gen.generateLevel(this.row - 2, this.column - 2);
		// copy the new level to "resetState" (holds the original state of the game)
		this.resetState = copyMatrix(this.matrix);
		// initialise the new player
		this.player = new Player(gen.getPlayer());
		// initialise the new player to be the original player
		this.resetPlayer = new Player(this.player);
		// creating a list of boxes for the map
		this.boxes = new ArrayList<Box>();
		// populating the list from the new level's list of boxes
		for (Coordinate box : gen.getBox()) {
			this.boxes.add(new Box(box.getRow(), box.getColumn()));
		}
		// initialising resetBoxes to be equal to the original boxes
		this.resetBoxes = getBoxes();
		// creating a new list for the berries
		this.berries = new ArrayList<Berry>();
		// adding the berries to the list
		this.berries.add(new Berry(5, 6));
		// copying the original berries so we can reset them
		this.resetBerries = getBerries();
		// the berries have not been triggered yet
		this.berryState = false;
		// no berries have been triggered yet
		this.berryCount = 0;
		// no berries have been triggered yet
	}
	
	/**
	 * method: newTutLevel() -> creates the level for the tutorial
	 * 
	 * @param 	matrix
	 * @return	-
	 * @throws	-
	 */
	public void newTutLevel(int[][] matrix){
		// sets the game's matrix to the one parsed in
		this.matrix = matrix;
		// records the original matrix
		this.resetState = copyMatrix(matrix);
	}
	
	/**
	 * getter: getBerryList() -> gets the list of berries
	 * 
	 * @param	-
	 * @return	this.berries	the list of berries
	 * @throws	-
	 */
	public ArrayList<Berry> getBerryList(){
		// returns the list of berries
		return this.berries;
	}

	/**
	 * getter: getMatrix() -> gets the game's matrix
	 * 
	 * @param	-
	 * @return	this.matrix		the game's matrix
	 * @throws	-
	 */
	public int[][] getMatrix() {
		// returns the game's matrix
		return this.matrix;
	}

	/**
	 * getter: getBoxes() -> gets the boxes in the game
	 * 
	 * @param	-
	 * @return	boxes		the list of boxes in the game
	 * @throws	-
	 */
	public ArrayList<Box> getBoxes() {
		// initialising a new list of boxes to populate
		ArrayList<Box> boxes = new ArrayList<Box>();
		// populating the list from the record inside this instance
		for (Box box : this.boxes) {
			boxes.add(new Box(box));
		}
		// returning desired list
		return boxes;
	}

	/**
	 * getter: getBerries() -> gets the berries in the current level
	 * 
	 * @param	-
	 * @return	berries		the list of berries
	 * @throws	-
	 */
	public ArrayList<Berry> getBerries() {
		// initialising a new list of berries to populate
		ArrayList<Berry> berries = new ArrayList<Berry>();
		// populating the list for the record inside this instance
		for (Berry berry : this.berries) {
			berries.add(new Berry(berry));
		}
		// returning desired list
		return berries;
	}

	/**
	 * method: copyMatrix() -> copies a matrix
	 * 
	 * @param 	original	the original matrix
	 * @return	copy		the copied matrix
	 * @throws	-
	 */
	private int[][] copyMatrix(int[][] original) {
		// creating a new array to hold the copied matrix
		int[][] copy = new int[original.length][];
		// copying the original matrix to the new one
		for (int i = 0; i < original.length; i++) {
			copy[i] = original[i].clone();
		}
		// returning desired matrix
		return copy;
	}

	/**
	 * getter: getSeconds() -> returns the number of 
	 * 			seconds elapsed during the level
	 * 
	 * @param	-
	 * @return	this.seconds	the number of seconds elapsed
	 * @throws	-
	 */
	public int getSeconds() {
		// returns the number of seconds elapsed
		return this.seconds;
	}

	/**
	 * method: pauseTimer() -> pauses the game timer
	 * 
	 * @param	-
	 * @return	-
	 * @throws	-
	 */
	public void pauseTimer() {
		// stops (pauses) the timer
		this.gameTimer.stop();
	}

	/**
	 *  method: resetGame() -> resets game level when R is pressed
	 *  
	 *  @param	-
	 *  @return	-
	 *  @throws	-
	 */
	public void resetGame() {
		// resets the number of moves made
		this.numMoves = 0;
		// if there is a tutorial being played, not a normal game
		if(state != GAMESTATE.TUTORIAL){
			// reset the player
			this.player = new Player(this.resetPlayer);
		} else {
			// resets the player's position
			this.player.setPosition(1, 1);
		}
		// reset's the game's matrix to the original version
		this.matrix = copyMatrix(this.resetState);
		// reset's the game's boxes to their original configuration
		this.boxes = this.resetBoxes;
		// reset's the game's berries to their original configuration
		this.berries = this.resetBerries;
		// re-initialises the resetBoxes variable to the original configuration
		this.resetBoxes = getBoxes();
		// reset's the game's win parameter to reflect the reset
		this.checkWin = false;
		// reset's the game's variable that shows how long the level has been played
		this.seconds = 0;
		// clears the player's undo list
		this.undoPlayer.clear();
		// clears the box's undo list
		this.undoBoxes.clear();
		// the game state has changed
		setChanged();
		// notify the observer to the changes made
		notifyObservers();
	}

	/**
	 * method: update() -> updates the game state
	 * 
	 * @param 	keyPress	the keyboard input
	 * @return	-
	 * @throws	-
	 */
	public void update(char keyPress) {
		// find the player's row
		int row = this.player.getRow();
		// find the player's column
		int column = this.player.getColumn();
		// Checks key press
		if (keyPress == 'W') {
			// Set direction player is facing
			this.player.setDirection(0);
			this.player.setIsBox(false);
			// Move player if they are not obstructed, otherwise check for a box
			if (!isObstructed(row - 1, column) || isBox(row - 1, column)) {
				if (moveBox(row - 1, column, DIRECTION.UP)) {
					if (removeBerry(row - 1, column)) {
						movePlayer(row - 1, column);
					}
				}
			}
		} else if (keyPress == 'A') {
			// Set direction player is facing
			this.player.setDirection(270);
			this.player.setIsBox(false);
			// Move player if they are not obstructed, otherwise check for a box
			if (!isObstructed(row, column - 1) || isBox(row, column - 1)) {
				if (moveBox(row, column - 1, DIRECTION.LEFT)) {
					if (removeBerry(row, column - 1)) {
						movePlayer(row, column - 1);
					}
				}
			}
		} else if (keyPress == 'S') {
			// Set direction player is facing
			this.player.setDirection(180);
			this.player.setIsBox(false);
			// Move player if they are not obstructed, otherwise check for a box
			if (!isObstructed(row + 1, column) || isBox(row + 1, column)) {
				if (moveBox(row + 1, column, DIRECTION.DOWN)) {
					if (removeBerry(row + 1, column)) {
						movePlayer(row + 1, column);
					}
				}
			}
		} else if (keyPress == 'D') {
			// Set direction player is facing
			this.player.setDirection(90);
			this.player.setIsBox(false);
			// Move player if they are not obstructed, otherwise check for a box
			if (!isObstructed(row, column + 1) || isBox(row, column + 1)) {
				if (moveBox(row, column + 1, DIRECTION.RIGHT)) {
					if (removeBerry(row, column + 1)) {
						movePlayer(row, column + 1);
					}
				}
			}
		}
		// Start the game timer on the first move
		if (!this.gameTimer.isRunning()) {
			this.gameTimer.start();
		}
		// check if game was won
		checkWin();
		// the game state has changed
		setChanged();
		// notify the Observer pattern
		notifyObservers();
	}

	/**
	 * method: isObstructed() -> checks if specified coordinate is obstructed
	 * 
	 * @param 	column		column coordinate to check
	 * @param 	row			row coordinate to check
	 * @return 	true if there exists a box or wall, false otherwise
	 * @throws	-
	 */
	private boolean isObstructed(int row, int column) {
		// return true if there exists a box or wall, false otherwise
		return isBox(row, column) || matrix[row][column] == Constants.WALL;
	}

	/**
	 * method: isBox() -> checks if there is a box at the specified position
	 * 
	 * @param 	column		column coordinate to check
	 * @param 	row			row coordinate to check
	 * @return	boolean		true if there is a box at the coordinate, false otherwise
	 * @throws	-
	 */
	public boolean isBox(int row, int column) {
		// for each box in the list
		for (Box box : this.boxes) {
			// check if the box's coordinates match the ones parsed in
			if (box.getRow() == row && box.getColumn() == column) {
				// return success
				return true;
			}
		}
		// else return failure
		return false;
	}

	/**
	 * method: isBerry() -> checks if there is a berry at the specified coordinate
	 * 
	 * @param 	row			row coordinate to check
	 * @param 	column		column coordinate to check
	 * @return	boolean		true if there is a berry at the coordinate, false otherwise
	 * @throws	-
	 */
	private boolean isBerry(int row, int column) {
		// for each berry in the list
		for (Berry berry : this.berries) {
			// check if the berry's coordinates match the ones parsed in
			if (berry.getRow() == row && berry.getColumn() == column) {
				// return success
				return true;
			}
		}
		// else return failure
		return false;
	}

	/**
	 * method: moveBox() -> moves box from specified position in specified direction
	 * 
	 * @param 	column		column coordinate of box
	 * @param 	row			row coordinate of box
	 * @param 	direction	direction of box to move in
	 * @return 	true if successful or no box exists at the specified location,
	 *          false if box is colliding with something
	 * @throws	-
	 */
	private boolean moveBox(int row, int column, DIRECTION direction) {
		// add the boxes to the undo list
		this.undoBoxes.add(getBoxes());
		// if there is no box at the coordinates
		if (!isBox(row, column)) {
			return true;
		}
		
		this.player.setIsBox(true);
		// if the player moved up
		if (direction == DIRECTION.UP) {
			// Check if move is legal
			if (!isObstructed(row - 1, column)) {
				getBox(row, column).setPosition(row - 1, column);
				return true;
			}
		// if player moved down
		} else if (direction == DIRECTION.DOWN) {
			// Check if move is legal
			if (!isObstructed(row + 1, column)) {
				getBox(row, column).setPosition(row + 1, column);
				return true;
			}
		// if player moved left
		} else if (direction == DIRECTION.LEFT) {
			// Check if move is legal
			if (!isObstructed(row, column - 1)) {
				getBox(row, column).setPosition(row, column - 1);
				return true;
			}
		// if player moved right
		} else if (direction == DIRECTION.RIGHT) {
			// Check if move is legal
			if (!isObstructed(row, column + 1)) {
				getBox(row, column).setPosition(row, column + 1);
				return true;
			}
		}
		// pop off the first box off the undo stack
		this.undoBoxes.pop();
		// return a failure
		return false;
	}

	/**
	 * method: getBox() -> return the box at the specified coordinates
	 * 
	 * @param 	row		row specified
	 * @param 	column	column specified
	 * @return	box		the box at the coordinates (can be null if no box present)
	 * @throws	-
	 */
	private Box getBox(int row, int column) {
		// for each box in the list
		for (Box box : this.boxes) {
			// check if a box exists at the coordinates
			if (box.getRow() == row && box.getColumn() == column) {
				// return the box
				return box;
			}
		}
		// return nothing if box not found
		return null;
	}

	/**
	 * getter: getBerry() -> gets the index of the berry at the coordinates
	 * 
	 * @param 	row		row specified
	 * @param 	column	column specified
	 * @return	index	index of the berry in the list
	 * @throws	-
	 */
	private int getBerry(int row, int column) {
		//initialise an index variable to return later
		int index = 0;
		// for all the berries in the list
		for (Berry berry : this.berries) {
			// if the berry is at the coordinates
			if (berry.getRow() == row && berry.getColumn() == column) {
				// move the player to the berry
				movePlayer(row, column);
				// return the index of the berry
				return index;
			}
			index++;
		}
		// return failure
		return -1;
	}

	/**
	 * method: movePlayer() -> moves the player to the coordinates
	 * 
	 * @param 	row		
	 * @param 	column	
	 * @return	-
	 * @throws	-
	 */
	private void movePlayer(int row, int column) {
		// add the player's current column to the undo list
		this.undoPlayer.push(player.getColumn());
		// add the player's current row to the undo list
		this.undoPlayer.push(player.getRow());
		// set the player's position to the new coordinates
		this.player.setPosition(row, column);
		// increment the move counter
		this.numMoves++;
	}

	/**
	 * method: removeBerry() -> removes the berry at the coordinates
	 * 
	 * @param 	row			row of the berry
	 * @param 	column		column of the berry
	 * @return	boolean		result of the operation
	 * @throws	-
	 */
	private boolean removeBerry(int row, int column) {
		// if there is no berry at the coordinates
		if (!isBerry(row, column)) {
			// return success
			return true;
		}
		// else set the berry's state to true
		this.berryState = true;
		// remove the berry
		berries.remove(getBerry(row, column));
		// return failure
		return false;
	}

	/**
	 * method: undoMove() -> logic to undo move
	 * 
	 * @param	-
	 * @return	-
	 * @throws	-
	 */
	public void undoMove() {
		// if there are moves to undo
		if (!this.undoPlayer.empty() && !this.undoBoxes.isEmpty()) {
			// increment move counter
			this.numMoves++;
			// pop of the previous row coordinate of the player
			Integer row = this.undoPlayer.pop();
			// pop of the previous column coordinate of the player
			Integer column = this.undoPlayer.pop();
			// set the player to their previous position
			this.player.setPosition(row, column);
			// pop a box off the undo list
			this.boxes = this.undoBoxes.pop();
		}
		// the game state has changed
		setChanged();
		// notify the Observer
		notifyObservers();
	}

	/**
	 * method: checkWin() -> tests to see if the level has been completed
	 * 
	 * @param	-
	 * @return	-
	 * @throws	-
	 */
	private void checkWin() {
		// assume we won until proven otherwise
		this.checkWin = true;
		// for the whole map's rows
		for (int row = 0; row < this.matrix.length; row++) {
			// for the whole map's columns
			for (int column = 0; column < this.matrix[row].length; column++) {
				// if there is a cross at the coordinate
				if (this.matrix[row][column] == Constants.CROSS && !isBox(row, column)) {
					// we did not win
					this.checkWin = false;
				}
			}
		}
		// if no crosses were found
		if (this.checkWin == true) {
			// stop the timer
			this.gameTimer.stop();
		}
	}
	
	/**
	 * getter: getGameState() -> gets the game's state
	 * 
	 * @param	-
	 * @return	this.state	the game's state
	 * @throws	-
	 */
	public GAMESTATE getGameState(){
		// return the game state
		return this.state;
	}
	
	/**
	 * setter: isTutorial() -> checks to see if we are running the tutorial
	 * 
	 * @param	-
	 * @return	GAMESTATE.TUTORIAL	a variable that indicates if the
	 * 								gamestate is in tutorial mode
	 * @throws	-
	 */
	public GAMESTATE isTutorial(){
		// return desired variable
		return GAMESTATE.TUTORIAL;
	}
	
	/**
	 * setter: isGame() -> checks to see if we are running a normal game
	 * 
	 * @param	-
	 * @return	GAMESTATE.GAME	a variable that indicates if the
	 * 							gamestate is in normal game mode
	 * @throws	-
	 */
	public GAMESTATE isGame(){
		// return desired variable
		return GAMESTATE.GAME;
	}
	
	/**
	 * setter: isWin() -> checks to see if we have won
	 * 
	 * @param	-
	 * @return	this.checkWin	a variable that indicates if we have won
	 * @throws	-
	 */
	public boolean isWin() {
		// returns result
		return this.checkWin;
	}

	/**
	 * getter: getPlayerRow() -> gets the player's row
	 * 
	 * @param	-
	 * @return	this.player.getRow()	the player's row
	 * @throws	-
	 */
	public int getPlayerRow() {
		// returns the player's row
		return this.player.getRow();
	}

	/**
	 * getter: getPlayerColumn() -> gets the player's column
	 * 
	 * @param	-
	 * @return	this.player.getColumn()		the player's column
	 * @throws	-
	 */
	public int getPlayerColumn() {
		// returns the player's column
		return this.player.getColumn();
	}

	/**
	 * getter: getPlayerDirection() -> gets the player's direction
	 * 
	 * @param	-
	 * @return	this.player.getDirection()		the player's direction
	 * @throws	-
	 */
	public int getPlayerDirection() {
		// returns the player's direction
		return this.player.getDirection();
	}

	/**
	 * getter: getPlayerIsBox() -> gets whether the player is colliding with a box
	 * 
	 * @param	-
	 * @return	this.player.getIsBox	checks if a collision between player and box occurs
	 * @throws	-
	 */
	public boolean getPlayerIsBox(){
		// returns a boolean value
		return this.player.getIsBox();
	}

	/**
	 * getter: getNumMoves() -> gets number of moves made by player
	 * 
	 * @param	-
	 * @return	this.numMoves	number of moves made
	 * @throws	-
	 */
	public int getNumMoves() {
		// returns number of moves made
		return this.numMoves;
	}

	/**
	 * getter: getState() -> returns the state of the game currently
	 * 
	 * @param	-
	 * @return	SaveData	all the data needed to save the current game state
	 * @throws	-
	 */
	public SaveData getState() {
		// returns a new SaveData instance initialised with the current game state's variables
		return new SaveData(this.matrix, this.resetState, this.player, this.resetPlayer, this.undoPlayer, this.seconds,
				this.numMoves, this.boxes, this.undoBoxes, this.resetBoxes, this.berries, this.berryState,
				this.berryCount);
	}

	/**
	 * Override: actionPerformed() -> actions to be performed every seconds
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// increment second count
		this.seconds++;
		// if a berry has been triggered
		if (this.berryState) {
			// increment number of berries triggered
			this.berryCount++;
			// "pause" the timer for 3 seconds
			this.seconds--;
			if (berryCount == 3) {
				berryCount = 0;
				this.berryState = false;
			}
		}
		// the game state has changed
		setChanged();
		// notify the observer
		notifyObservers();
	}
}
