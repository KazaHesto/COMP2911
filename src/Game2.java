import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
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
	private int[][] originalState;
	private int[][] resetState;
	private int numMoves;
	private Player player;
	private Boolean checkWin;
	private Timer gameTimer;
	private int seconds;

	// constructor
	public Game2() {
		this.matrix = new int[][] {
			{0,0,0,0,0,0,0,0,0,0,0},
			{0,4,4,4,4,4,4,4,4,0,0},
			{0,0,0,0,2,0,4,4,0,0,0},
			{0,0,0,0,4,0,4,2,4,3,0},
			{0,0,0,3,4,4,4,2,4,3,0},
			{0,0,0,0,0,0,0,0,0,0,0}
		};
		this.originalState = new int[][] {
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
			{0,0,0,0,2,0,4,4,0,0,0},
			{0,0,0,0,4,0,4,2,4,3,0},
			{0,0,0,3,4,4,4,2,4,3,0},
			{0,0,0,0,0,0,0,0,0,0,0}
		};
				
		this.player = new Player(1, 1);
		this.gameTimer = new Timer(1000, this);
		this.seconds = 0;
		// needs
		// map - adj matrix
		// player
		// linkedlist of boxes
		// linkedlist of crosses
	}

	public int[][] getMatrix() {
		return matrix;
	}
	//method to reset game level when R is pressed
	public void ResetGame(){
		//restart timer not working?
		this.numMoves = 0;
		this.player.setPosition(1,1);
		    for(int y = 0; y < this.resetState.length; y++){
		    	for (int x = 0; x < this.resetState[y].length; x++){
		    		this.matrix[y][x] = this.resetState[y][x];
		    	}
		    } 
	}

	// update the game state
	public void update(char keyPress) {
		int row = this.player.getRow();
		int column = this.player.getColumn();
		// Checks key press
		if (keyPress == 'W') {
			// Set direction player is facing
			this.player.setDirection(0);
			// Move player if they are not obstructed, otherwise check for a
			// box, and see if box can be moved
			if (!isObstructed(row - 1, column)) {
				this.player.setPosition(row - 1, column);
			} else if (isBox(row - 1, column)) {
				if (moveBox(row - 1, column, DIRECTION.UP)) {
					this.player.setPosition(row - 1, column);
				}
			}
		} else if (keyPress == 'A') {
			this.player.setDirection(270);
			if (!isObstructed(row, column - 1)) {
				this.player.setPosition(row, column - 1);
			} else if (isBox(row, column - 1)) {
				if (moveBox(row, column - 1, DIRECTION.LEFT)) {
					this.player.setPosition(row, column - 1);
				}
			}
		} else if (keyPress == 'S') {
			this.player.setDirection(180);
			if (!isObstructed(row + 1, column)) {
				this.player.setPosition(row + 1, column);
			} else if (isBox(row + 1, column)) {
				if (moveBox(row + 1, column, DIRECTION.DOWN)) {
					this.player.setPosition(row + 1, column);
				}
			}
		} else if (keyPress == 'D') {
			this.player.setDirection(90);
			if (!isObstructed(row, column + 1)) {
				this.player.setPosition(row, column + 1);
			} else if (isBox(row, column + 1)) {
				if (moveBox(row, column + 1, DIRECTION.RIGHT)) {
					this.player.setPosition(row, column + 1);
				}
			}
		}
		checkWin();
		if (!this.gameTimer.isRunning()) {
			this.gameTimer.start();
		}
		this.numMoves++;
		setChanged();
		notifyObservers();
	}

	/**
	 * Checks if specified coordinate is obstructed
	 * 
	 * @param column column coordinate to check
	 * @param row row coordinate to check
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
		return matrix[row][column] == BOX;
	}

	/**
	 * Moves box from specified position in specified direction
	 * 
	 * @param column column coordinate of box
	 * @param row row coordinate of box
	 * @param direction direction of box to move in
	 * @return true if successful, false if box is colliding with something
	 */
	private boolean moveBox(int row, int column, DIRECTION direction) {
		if (direction == DIRECTION.UP) {
			// Check if move is legal
			if (!isObstructed(row - 1, column)) {
				this.matrix[row][column] = this.originalState[row][column];
				this.matrix[row - 1][column] = BOX;
				return true;
			}
		} else if (direction == DIRECTION.DOWN) {
			// Check if move is legal
			if (!isObstructed(row + 1, column)) {
				this.matrix[row][column] = this.originalState[row][column];
				this.matrix[row + 1][column] = BOX;
				return true;
			}
		} else if (direction == DIRECTION.LEFT) {
			// Check if move is legal
			if (!isObstructed(row, column - 1)) {
				this.matrix[row][column] = this.originalState[row][column];
				this.matrix[row][column - 1] = BOX;
				return true;
			}

		} else if (direction == DIRECTION.RIGHT) {
			// Checks if move is legal
			if (!isObstructed(row, column + 1)) {
				this.matrix[row][column] = this.originalState[row][column];
				this.matrix[row][column + 1] = BOX;
				return true;
			}
		}
		return false;
	}

	private void checkWin() {
		// check if crosses are present in level
		this.checkWin = true;
		for (int i = 0; i < this.matrix.length; i++) {
			for (int j = 0; j < this.matrix[i].length; j++) {
				if (this.matrix[i][j] == CROSS) {
					this.checkWin = false;
				}
			}
		}
		if (this.checkWin == true) {
			this.gameTimer.stop();
			System.out.println("win");
		}
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