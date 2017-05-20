import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
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
	private int[][] originalState;
	private int[][] resetState;
	private int[][] prevState;
	private Stack<Integer> undoPlayer;
	private LinkedList<int[][]> undoMatrix;
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
		
		this.resetState = new int[this.matrix.length][this.matrix[1].length];
		this.resetState = copyMatrix(this.matrix, this.resetState);
		this.prevState = new int[this.matrix.length][this.matrix[1].length];
		this.prevState = copyMatrix(this.matrix, this.prevState);

		this.player = new Player(1, 1);
		this.undoPlayer = new Stack<Integer>();
		this.undoMatrix = new LinkedList<int[][]>();
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

	private int[][] copyMatrix(int[][] resetState, int[][] matrix) {
		for (int y = 0; y < resetState.length; y++) {
			for (int x = 0; x < resetState[y].length; x++) {
				matrix[y][x] = resetState[y][x];
			}
		}
		return matrix;
	}

	// method to reset game level when R is pressed
	public void resetGame() {
		this.numMoves = 0;
		this.player.setPosition(1, 1);
		this.matrix = copyMatrix(this.resetState, this.matrix);
		this.checkWin = false;
		this.seconds = 0;
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
			// Move player if they are not obstructed, otherwise check for a
			// box, and see if box can be moved
			if (!isObstructed(row - 1, column)) {
				//create a temp matrix to add to linked list of matrix for undo
				int[][] temp = new int[this.matrix.length][this.matrix[1].length];
				copyMatrix(this.matrix, temp);
				this.undoMatrix.add(temp);
				this.player.setPrevPosition(row,column);
				this.player.setPosition(row - 1, column);
				this.undoPlayer.push(this.player.getPrevColumn());
				this.undoPlayer.push(this.player.getPrevRow());
			} else if (isBox(row - 1, column)) {
				if (moveBox(row - 1, column, DIRECTION.UP)) {
					this.player.setPrevPosition(row,column);
					this.player.setPosition(row - 1, column);
					this.undoPlayer.push(this.player.getPrevColumn());
					this.undoPlayer.push(this.player.getPrevRow());
				}
			}
		} else if (keyPress == 'A') {
			this.player.setDirection(270);
			if (!isObstructed(row, column - 1)) {
				//create a temp matrix to add to linked list of matrix for undo
				int[][] temp = new int[this.matrix.length][this.matrix[1].length];
				copyMatrix(this.matrix, temp);
				this.undoMatrix.add(temp);
				this.player.setPrevPosition(row,column);
				this.player.setPosition(row, column - 1);
				this.undoPlayer.push(this.player.getPrevColumn());
				this.undoPlayer.push(this.player.getPrevRow());
			} else if (isBox(row, column - 1)) {
				if (moveBox(row, column - 1, DIRECTION.LEFT)) {
					this.player.setPrevPosition(row,column);
					this.player.setPosition(row, column - 1);
					this.undoPlayer.push(this.player.getPrevColumn());
					this.undoPlayer.push(this.player.getPrevRow());
				}
			}
		} else if (keyPress == 'S') {
			this.player.setDirection(180);
			if (!isObstructed(row + 1, column)) {
				//create a temp matrix to add to linked list of matrix for undo
				int[][] temp = new int[this.matrix.length][this.matrix[1].length];
				copyMatrix(this.matrix, temp);
				this.undoMatrix.add(temp);
				this.player.setPrevPosition(row,column);
				this.player.setPosition(row + 1, column);
				this.undoPlayer.push(this.player.getPrevColumn());
				this.undoPlayer.push(this.player.getPrevRow());
			} else if (isBox(row + 1, column)) {
				if (moveBox(row + 1, column, DIRECTION.DOWN)) {
					this.player.setPrevPosition(row,column);
					this.player.setPosition(row + 1, column);
					this.undoPlayer.push(this.player.getPrevColumn());
					this.undoPlayer.push(this.player.getPrevRow());
				}
			}
		} else if (keyPress == 'D') {
			this.player.setDirection(90);
			if (!isObstructed(row, column + 1)) {
				//create a temp matrix to add to linked list of matrix for undo
				int[][] temp = new int[this.matrix.length][this.matrix[1].length];
				copyMatrix(this.matrix, temp);
				this.undoMatrix.add(temp);
				this.player.setPrevPosition(row,column);
				this.player.setPosition(row, column + 1);
				//add the players previous move to stack
				this.undoPlayer.push(this.player.getPrevColumn());
				this.undoPlayer.push(this.player.getPrevRow());
			} else if (isBox(row, column + 1)) {
				if (moveBox(row, column + 1, DIRECTION.RIGHT)) {
					this.player.setPrevPosition(row,column);
					this.player.setPosition(row, column + 1);
					this.undoPlayer.push(this.player.getPrevColumn());
					this.undoPlayer.push(this.player.getPrevRow());
				}
			}
		}
		if (!this.gameTimer.isRunning()) {
			this.gameTimer.start();
		}
		this.numMoves++;
		checkWin();
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
		//create a temp matrix to add to linked list of matrix for undo
		int[][] temp = new int[this.matrix.length][this.matrix[1].length];
		copyMatrix(this.matrix, temp);
		this.undoMatrix.add(temp);
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
	
    public static void printRow(int[] row) {
        for (int i : row) {
            System.out.print(i);
            System.out.print("\t");
        }
        System.out.println();
    }
	
    //logic to undo move
	public void undoMove(){
		if(!this.undoPlayer.empty()){
			Integer row = this.undoPlayer.pop();
			Integer column = this.undoPlayer.pop();
			this.player.setPosition(row, column);
		}
		if(!this.undoMatrix.isEmpty()){
			this.matrix = this.undoMatrix.getLast();
			this.undoMatrix.removeLast();
		}
		if(this.numMoves != 0){
			this.numMoves++;
		}
	}
	
	//when reset is used when game is running empty all data
	public void resetUndo(){
		while(!this.undoPlayer.empty()){
			this.undoPlayer.pop();
		}
		this.undoMatrix.clear();
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