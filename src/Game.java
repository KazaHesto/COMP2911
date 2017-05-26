import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Stack;

import javax.swing.Timer;

public class Game extends Observable implements ActionListener {

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
	private Player resetPlayer;
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
	private boolean isTutorial;
	private boolean generateBerry;

	// constructor
	public Game(int row, int column) {
		this.isTutorial = false;
		this.row = row;
		this.column = column;
		this.undoPlayer = new Stack<Integer>();
		this.undoBoxes = new Stack<ArrayList<Box>>();
		this.gameTimer = new Timer(1000, this);
		this.seconds = 0;
		if (row != -1 && column != -1) {
			newLevel();
		}
	}

	public Game(SaveData data) {
		this.isTutorial = false;
		this.matrix = data.matrix;
		this.resetState = data.resetState;
		this.player = data.player;
		this.resetPlayer = data.resetPlayer;
		this.undoPlayer = data.undoPlayer;
		this.gameTimer = new Timer(1000, this);
		this.seconds = data.seconds;
		this.numMoves = data.numMoves;
		this.boxes = data.boxes;
		this.berries = data.berries;
		this.undoBoxes = data.undoBoxes;
		this.resetBoxes = data.resetBoxes;
		this.checkWin = false;
		this.berryState = data.berryState;
		this.berryCount = data.berryCount;
	}

	public void newLevel() {
		LevelGenerator gen = new LevelGenerator();
		this.matrix = gen.generateLevel(this.row - 2, this.column - 2);
		this.resetState = copyMatrix(this.matrix);
		this.player = new Player(gen.getPlayer());
		this.resetPlayer = new Player(this.player);
		this.boxes = new ArrayList<Box>();
		for (Coordinate box : gen.getBox()) {
			this.boxes.add(new Box(box.getRow(), box.getColumn()));
		}
		this.resetBoxes = getBoxes();
		this.berries = new ArrayList<Berry>();
//		this.berries.add(new Berry(5, 6));
//		System.out.println(2 + (int) (Math.random() * ((this.column - 6) + 1)));
//		System.out.println(2 + (int) (Math.random() * ((this.row - 6) + 1)));
		this.generateBerry = false;
		while(!generateBerry){
			int berryRow = 2 + (int) (Math.random() * ((this.row - 6) + 1));
			int berryColumn = 2 + (int) (Math.random() * ((this.column - 6) + 1));
			if(!isBox(berryRow, berryColumn) && !isWall(berryRow, berryColumn)){
				this.berries.add(new Berry(berryRow,berryColumn));
				generateBerry = true;
				break;
			}
		}
		this.resetBerries = getBerries();
		this.berryState = false;
		this.berryCount = 0;
		this.player.setIsBox(false);
	}

	public void startTut() {
		this.isTutorial = true;
		this.matrix = new int[][] {
			{1,1,1,1,1,1,1,1,1,1,1},
			{1,4,4,1,4,1,4,1,4,1,1},
			{1,1,1,1,4,1,1,1,1,1,1},
			{1,1,1,1,4,4,4,1,4,3,1},
			{1,1,1,3,4,4,4,1,4,3,1},
			{1,1,1,1,1,1,1,1,1,1,1}
		};
		this.player = new Player(1, 1);
		this.undoPlayer = new Stack<Integer>();
		this.undoBoxes = new Stack<ArrayList<Box>>();
		this.gameTimer = new Timer(1000, this);
		this.seconds = 0;
		this.boxes = new ArrayList<Box>();
		this.boxes.add(new Box(2, 4));
		this.boxes.add(new Box(3, 7));
		this.boxes.add(new Box(4, 7));
		this.resetBoxes = getBoxes();
		this.berries = new ArrayList<Berry>();
		this.resetBoxes = getBoxes();
		this.berries.add(new Berry(1,6));
		this.resetBerries = getBerries();
		this.berryState = false;
		this.berryCount = 0;
	}

	public int[][] getMatrix() {
		return matrix;
	}

	public void changeMap(int[][] matrix) {
		this.matrix = matrix;
		this.resetState = copyMatrix(matrix);
	}

	public boolean isTutorial() {
		return isTutorial;
	}

	public ArrayList<Box> getBoxes() {
		ArrayList<Box> boxes = new ArrayList<Box>();
		for (Box box : this.boxes) {
			boxes.add(new Box(box));
		}
		return boxes;
	}

	public ArrayList<Berry> getBerries() {
		ArrayList<Berry> berries = new ArrayList<Berry>();
		for (Berry berry : this.berries) {
			berries.add(new Berry(berry));
		}
		return berries;
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

	public void pauseTimer() {
		this.gameTimer.stop();
	}

	// method to reset game level when R is pressed
	public void resetGame() {
		this.numMoves = 0;
		if (!this.isTutorial) {
			this.player = new Player(this.resetPlayer);
		} else {
			this.player.setPosition(1, 1);
		}
		this.matrix = copyMatrix(this.resetState);
		this.boxes = this.resetBoxes;
		this.berries = this.resetBerries;
		this.resetBerries = getBerries();
		this.resetBoxes = getBoxes();
		this.checkWin = false;
		this.seconds = 0;
		this.undoPlayer.clear();
		this.undoBoxes.clear();
		this.player.setIsBox(false);
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
			this.player.setDirection(270);
			this.player.setIsBox(false);
			if (!isObstructed(row, column - 1) || isBox(row, column - 1)) {
				if (moveBox(row, column - 1, DIRECTION.LEFT)) {
					if (removeBerry(row, column - 1)) {
						movePlayer(row, column - 1);
					}
				}
			}
		} else if (keyPress == 'S') {
			this.player.setDirection(180);
			this.player.setIsBox(false);
			if (!isObstructed(row + 1, column) || isBox(row + 1, column)) {
				if (moveBox(row + 1, column, DIRECTION.DOWN)) {
					if (removeBerry(row + 1, column)) {
						movePlayer(row + 1, column);
					}
				}
			}
		} else if (keyPress == 'D') {
			this.player.setDirection(90);
			this.player.setIsBox(false);
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
		return isBox(row, column) || matrix[row][column] == Resources.WALL;
	}

	/**
	 * Checks if there is a box at the specified position
	 * 
	 * @param column
	 * @param row
	 * @return
	 */
	public boolean isBox(int row, int column) {
		for (Box box : this.boxes) {
			if (box.getRow() == row && box.getColumn() == column) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isWall(int row, int column){
		if(this.matrix[row][column] == 1){
			return true;
		}
		return false;
	}

	private boolean isBerry(int row, int column) {
		for (Berry berry : this.berries) {
			if (berry.getRow() == row && berry.getColumn() == column) {
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
		this.player.setIsBox(true);
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

	private int getBerry(int row, int column) {
		int index = 0;
		for (Berry berry : this.berries) {
			if (berry.getRow() == row && berry.getColumn() == column) {
				movePlayer(row, column);
				return index;
			}
			index++;
		}
		return -1;
	}

	private void movePlayer(int row, int column) {
		this.undoPlayer.push(player.getColumn());
		this.undoPlayer.push(player.getRow());
		this.player.setPosition(row, column);
		this.numMoves++;
	}

	private boolean removeBerry(int row, int column) {
		if (!isBerry(row, column)) {
			return true;
		}
		this.berryState = true;
		berries.remove(getBerry(row, column));
		return false;
	}

	// logic to undo move
	public void undoMove() {
		if (!this.undoPlayer.empty() && !this.undoBoxes.isEmpty()) {
			this.numMoves++;
			Integer row = this.undoPlayer.pop();
			Integer column = this.undoPlayer.pop();
			this.player.setPosition(row, column);
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
				if (this.matrix[row][column] == Resources.CROSS && !isBox(row, column)) {
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
		return this.player.getDirection();
	}

	public boolean getPlayerIsBox() {
		return this.player.getIsBox();
	}

	public int getNumMoves() {
		return this.numMoves;
	}

	public SaveData getState() {
		return new SaveData(this.matrix, this.resetState, this.player, this.resetPlayer, this.undoPlayer, this.seconds,
				this.numMoves, this.boxes, this.undoBoxes, this.resetBoxes, this.berries, this.berryState,
				this.berryCount);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.seconds++;
		if (this.berryState) {
			this.berryCount++;
			this.seconds--;
			if (berryCount == 3) {
				berryCount = 0;
				this.berryState = false;
			}
		}
		setChanged();
		notifyObservers();
	}
}
