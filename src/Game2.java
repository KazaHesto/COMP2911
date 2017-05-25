import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Stack;

import javax.swing.Timer;

public class Game2 extends Observable implements ActionListener {

	public enum DIRECTION {
		UP, DOWN, LEFT, RIGHT
	}

	// local stuff for the game
	private int[][] matrix;
	private int[][] resetState;
	private Stack<Integer> undoPlayer;
	private Stack<ArrayList<Box>> undoBoxes;
	private int numMoves;
	private Player player;
	private ArrayList<Box> boxes;
	private ArrayList<Box> resetBoxes;
	private ArrayList<Berry> berries;
	private ArrayList<Berry> resetBerries;
	private Boolean checkWin;
	private Timer gameTimer;
	private int seconds;
	private int row;
	private int column;
	private boolean berryState;
	private int berryCount;

	// constructor
	public Game2(int row, int column) {
		this.row = row;
		this.column = column;
		this.player = new Player(1, 1);
		this.undoPlayer = new Stack<Integer>();
		this.undoBoxes = new Stack<ArrayList<Box>>();
		this.gameTimer = new Timer(1000, this);
		this.seconds = 0;
		newLevel();
		// needs
		// map - adj matrix
		// player
		// linkedlist of boxes
		// linkedlist of crosses
	}

	public Game2(int[][] matrix, int[][] resetState, Player player, Stack<Integer> undoPlayer,
			int seconds, int numMoves, ArrayList<Box> boxes, Stack<ArrayList<Box>> undoBoxes,
			ArrayList<Box> resetBoxes, ArrayList<Berry> berries, boolean berryState, int berryCount) {
		this.matrix = matrix;
		this.resetState = resetState;
		this.player = player;
		this.undoPlayer = undoPlayer;
		this.gameTimer = new Timer(1000, this);
		this.seconds = seconds;
		this.numMoves = numMoves;
		this.boxes = boxes;
		this.berries = berries;
		this.undoBoxes = undoBoxes;
		this.resetBoxes = resetBoxes;
		this.checkWin = false;
		this.berryState = berryState;
		this.berryCount = berryCount;
	}

	public void newLevel() {
		LevelGenerator gen = new LevelGenerator();
		// Will eventually replace this with calls to LevelGenerator
	/*	this.matrix = new int[][] {
			{1,1,1,1,1,1,1,1,1,1,1},
			{1,4,4,4,4,4,4,4,4,1,1},
			{1,1,1,1,4,1,4,4,1,1,1},
			{1,1,1,1,4,1,4,4,4,3,1},
			{1,1,1,3,4,4,4,4,4,3,1},
			{1,1,1,1,1,1,1,1,1,1,1}
		};
		this.resetState = new int[][] {
			{1,1,1,1,1,1,1,1,1,1,1},
			{1,4,4,4,4,4,4,4,4,1,1},
			{1,1,1,1,4,1,4,4,1,1,1},
			{1,1,1,1,4,1,4,4,4,3,1},
			{1,1,1,3,4,4,4,4,4,3,1},
			{1,1,1,1,1,1,1,1,1,1,1}
		};*/
		this.matrix = gen.generateLevel(this.row - 2, this.column - 2);
		this.resetState = copyMatrix(this.matrix);
		this.boxes = new ArrayList<Box>();
		this.boxes.add(new Box(2, 4));
		this.boxes.add(new Box(3, 7));
		this.boxes.add(new Box(4, 7));
		this.resetBoxes = getBoxes();
		this.berries = new ArrayList<Berry>();
		this.berries.add(new Berry(5,6));
		this.resetBerries = getBerries();
		this.berryState = false;
		this.berryCount = 0;
	}

	public int[][] getMatrix() {
		return matrix;
	}

	public ArrayList<Box> getBoxes() {
		ArrayList<Box> boxes = new ArrayList<Box>();
		for (Box box : this.boxes) {
			boxes.add(new Box(box));
		}
		return boxes;
	}
	
	public ArrayList<Berry> getBerries(){
		ArrayList<Berry> berries = new ArrayList<Berry>();
		for(Berry berry : this.berries){
			berries.add(new Berry(berry));
		}
		return berries;
	}

	public int[][] setMatrix(int[][] dataMatrix) {
		this.matrix = dataMatrix;
		return this.matrix;
	}

	private int[][] copyMatrix(int[][] original) {
		int[][] copy = new int[original.length][];
		for (int i = 0; i < original.length; i++) {
			copy[i] = original[i].clone();
		}
		return copy;
	}

	public int getSeconds() {
		return this.seconds;
	}

	// method to reset game level when R is pressed
	public void resetGame() {
		this.numMoves = 0;
		this.player.setPosition(1, 1);
		this.matrix = copyMatrix(this.resetState);
		this.boxes = this.resetBoxes;
		this.berries = this.resetBerries;
		this.resetBoxes = getBoxes();
		this.checkWin = false;
		this.seconds = 0;
		this.undoPlayer.clear();
		this.undoBoxes.clear();
		setChanged();
		notifyObservers();
	}

	// update the game state
	public void update(char keyPress) {
		int row = this.player.getRow();
		int column = this.player.getColumn();
		// Checks key press
		if (keyPress == 'W') {
			// Set direction player is facing
			this.player.setDirection(0);
			// Move player if they are not obstructed, otherwise check for a box
			if (!isObstructed(row - 1, column) || isBox(row - 1, column)) {
				if (moveBox(row - 1, column, DIRECTION.UP)) {
					if(removeBerry(row - 1,column)){
						movePlayer(row - 1, column);
					}
				}
			}
		} else if (keyPress == 'A') {
			this.player.setDirection(270);
			if (!isObstructed(row, column - 1) || isBox(row, column - 1)) {
				if (moveBox(row, column - 1, DIRECTION.LEFT)) {
					if(removeBerry(row,column - 1)){
						movePlayer(row, column - 1);
					}
				}
			}
		} else if (keyPress == 'S') {
			this.player.setDirection(180);
			if (!isObstructed(row + 1, column) || isBox(row + 1, column)) {
				if (moveBox(row + 1, column, DIRECTION.DOWN)) {
					if(removeBerry(row + 1,column)){
						movePlayer(row + 1, column);
					}
				}
			}
		} else if (keyPress == 'D') {
			this.player.setDirection(90);
			if (!isObstructed(row, column + 1) || isBox(row, column + 1)) {
				if (moveBox(row, column + 1, DIRECTION.RIGHT)) {
					if(removeBerry(row,column + 1)){
						movePlayer(row, column + 1);
					}
				}
			}
		}
		// Start the game timer on the first move
		if (!this.gameTimer.isRunning()) {
			this.gameTimer.start();
		}
		checkWin();
		setChanged();
		notifyObservers();
	}

	/**
	 * Checks if specified coordinate is obstructed
	 * 
	 * @param column
	 *            column coordinate to check
	 * @param row
	 *            row coordinate to check
	 * @return Returns true if there exists a box or wall, false otherwise.
	 */
	private boolean isObstructed(int row, int column) {
		return isBox(row, column) || matrix[row][column] == Constants.WALL;
	}

	/**
	 * Checks if there is a box at the specified position
	 * 
	 * @param column
	 * @param row
	 * @return
	 */
	private boolean isBox(int row, int column) {
		for (Box box : this.boxes) {
			if (box.getRow() == row && box.getColumn() == column) {
				return true;
			}
		}
		return false;
	}
	
	private boolean isBerry(int row, int column){
		for(Berry berry : this.berries) {
			if(berry.getRow() == row && berry.getColumn() == column){
				return true;
			}
		}
		return false;
	}

	/**
	 * Moves box from specified position in specified direction
	 * 
	 * @param column
	 *            column coordinate of box
	 * @param row
	 *            row coordinate of box
	 * @param direction
	 *            direction of box to move in
	 * @return true if successful or no box exists at the specified location,
	 *         false if box is colliding with something
	 */
	private boolean moveBox(int row, int column, DIRECTION direction) {
		this.undoBoxes.add(getBoxes());
		if (!isBox(row, column)) {
			return true;
		}
		if (direction == DIRECTION.UP) {
			// Check if move is legal
			if (!isObstructed(row - 1, column)) {
				getBox(row, column).setPosition(row - 1, column);
				return true;
			}
		} else if (direction == DIRECTION.DOWN) {
			if (!isObstructed(row + 1, column)) {
				getBox(row, column).setPosition(row + 1, column);
				return true;
			}
		} else if (direction == DIRECTION.LEFT) {
			if (!isObstructed(row, column - 1)) {
				getBox(row, column).setPosition(row, column - 1);
				return true;
			}

		} else if (direction == DIRECTION.RIGHT) {
			if (!isObstructed(row, column + 1)) {
				getBox(row, column).setPosition(row, column + 1);
				return true;
			}
		}
		this.undoBoxes.pop();
		return false;
	}

	private Box getBox(int row, int column) {
		for (Box box : this.boxes) {
			if (box.getRow() == row && box.getColumn() == column) {
				return box;
			}
		}
		return null;
	}
	
	private int getBerry(int row, int column){
		int index = 0;
		for(Berry berry : this.berries){
			if(berry.getRow() == row && berry.getColumn() == column){
				movePlayer(row,column);
				return index;
			}
			index++;
		}
		System.out.println("Berries!");
		return -1;
	}

	private void movePlayer(int row, int column) {
		this.undoPlayer.push(player.getColumn());
		this.undoPlayer.push(player.getRow());
		this.player.setPosition(row, column);
		this.numMoves++;
	}
	
	private boolean removeBerry(int row, int column){
		if(!isBerry(row, column)){
			return true;
		}
		this.berryState = true;
		berries.remove(getBerry(row,column));
		return false;
	}

	// logic to undo move
	public void undoMove() {
		if(this.player.getRow() != 1 || this.player.getColumn() != 1){
			this.numMoves++;
		}
		if (!this.undoPlayer.empty()) {
			Integer row = this.undoPlayer.pop();
			Integer column = this.undoPlayer.pop();
			this.player.setPosition(row, column);
		}
		if (!this.undoBoxes.isEmpty()) {
			this.boxes = this.undoBoxes.pop();
		}
		setChanged();
		notifyObservers();
	}

	private void checkWin() {
		// check if crosses are present in level
		this.checkWin = true;
		for (int row = 0; row < this.matrix.length; row++) {
			for (int column = 0; column < this.matrix[row].length; column++) {
				if (this.matrix[row][column] == Constants.CROSS && !isBox(row, column)) {
					this.checkWin = false;
				}
			}
		}
		if (this.checkWin == true) {
			this.gameTimer.stop();
			System.out.println("win");
		}
	}

	public boolean isWin() {
		return this.checkWin;
	}

	public int getPlayerRow() {
		return this.player.getRow();
	}

	public int getPlayerColumn() {
		return this.player.getColumn();
	}

	public int getPlayerDirection() {
		return this.player.getdirection();
	}

	public int getNumMoves() {
		return this.numMoves;
	}

	public SaveData getState() {
		return new SaveData(this.matrix, this.resetState, this.player,
				this.undoPlayer, this.seconds, this.numMoves, this.boxes, this.undoBoxes, this.resetBoxes, this.berries, this.berryState, this.berryCount);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.seconds++;
		if(this.berryState){
			this.berryCount++;
			this.seconds--;
			if(berryCount == 3){
				berryCount = 0;
				this.berryState = false;
			}
		}
		setChanged();
		notifyObservers();
	}
}
