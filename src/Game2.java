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

	private final int WALL = 0;
	private final int BOX = 2;
	private final int CROSS = 3;

	// local stuff for the game
	private int[][] matrix;
	private int[][] resetState;
	private Stack<Integer> undoPlayer;
	private Stack<int[][]> undoMatrix;
	private int numMoves;
	private Player player;
	private ArrayList<Box> boxes;
	private ArrayList<Box> resetBoxes;
	private Boolean checkWin;
	private Timer gameTimer;
	private int seconds;

	// constructor
	public Game2() {
		newLevel();
		this.player = new Player(1, 1);
		this.undoPlayer = new Stack<Integer>();
		this.undoMatrix = new Stack<int[][]>();
		this.gameTimer = new Timer(1000, this);
		this.seconds = 0;
		// needs
		// map - adj matrix
		// player
		// linkedlist of boxes
		// linkedlist of crosses
	}

	private void newLevel() {
		// Will eventually replace this with calls to LevelGenerator
		this.matrix = new int[][] {
			{0,0,0,0,0,0,0,0,0,0,0},
			{0,4,4,4,4,4,4,4,4,0,0},
			{0,0,0,0,4,0,4,4,0,0,0},
			{0,0,0,0,4,0,4,4,4,3,0},
			{0,0,0,3,4,4,4,4,4,3,0},
			{0,0,0,0,0,0,0,0,0,0,0}
		};
		this.resetState = new int[][] {
			{0,0,0,0,0,0,0,0,0,0,0},
			{0,4,4,4,4,4,4,4,4,0,0},
			{0,0,0,0,4,0,4,4,0,0,0},
			{0,0,0,0,4,0,4,4,4,3,0},
			{0,0,0,3,4,4,4,4,4,3,0},
			{0,0,0,0,0,0,0,0,0,0,0}
		};
		this.boxes = new ArrayList<Box>();
		this.boxes.add(new Box(2, 4));
		this.boxes.add(new Box(3, 7));
		this.boxes.add(new Box(4, 7));
		this.resetBoxes = getBoxes();
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
	
	public int[][] setMatrix(int[][] dataMatrix){
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

	// method to reset game level when R is pressed
	public void resetGame() {
		this.numMoves = 0;
		this.player.setPosition(1, 1);
		this.matrix = copyMatrix(this.resetState);
		this.boxes = this.resetBoxes;
		this.resetBoxes = getBoxes();
		this.checkWin = false;
		this.seconds = 0;
		this.undoPlayer.clear();
		this.undoMatrix.clear();
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
					movePlayer(row - 1, column);
				}
			}
		} else if (keyPress == 'A') {
			this.player.setDirection(270);
			if (!isObstructed(row, column - 1) || isBox(row, column - 1)) {
				if (moveBox(row, column - 1, DIRECTION.LEFT)) {
					movePlayer(row, column - 1);
				}
			}
		} else if (keyPress == 'S') {
			this.player.setDirection(180);
			if (!isObstructed(row + 1, column) || isBox(row + 1, column)) {
				if (moveBox(row + 1, column, DIRECTION.DOWN)) {
					movePlayer(row + 1, column);
				}
			}
		} else if (keyPress == 'D') {
			this.player.setDirection(90);
			if (!isObstructed(row, column + 1) || isBox(row, column + 1)) {
				if (moveBox(row, column + 1, DIRECTION.RIGHT)) {
					movePlayer(row, column + 1);
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
		return this.matrix[row][column] == BOX || matrix[row][column] == WALL;
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
		this.undoMatrix.add(copyMatrix(this.matrix));
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
		this.undoMatrix.pop();
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

	private void movePlayer(int row, int column) {
		this.undoPlayer.push(player.getColumn());
		this.undoPlayer.push(player.getRow());
		this.player.setPosition(row, column);
		this.numMoves++;
	}

	// logic to undo move
	public void undoMove() {
		if (!this.undoPlayer.empty()) {
			Integer row = this.undoPlayer.pop();
			Integer column = this.undoPlayer.pop();
			this.player.setPosition(row, column);
		}
		if (!this.undoMatrix.isEmpty()) {
			this.matrix = this.undoMatrix.pop();
		}
		this.numMoves++;
		setChanged();
		notifyObservers();
	}

	private void checkWin() {
		// check if crosses are present in level
		this.checkWin = true;
		for (int row = 0; row < this.matrix.length; row++) {
			for (int column = 0; column < this.matrix[row].length; column++) {
				if (this.matrix[row][column] == CROSS && !isBox(row, column)) {
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

	public void updateMoves(int update) {
		this.numMoves = update;
	}

	public int getTime() {
		return this.seconds;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		seconds++;
		setChanged();
		notifyObservers();
	}
}