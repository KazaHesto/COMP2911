import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import javax.swing.Timer;

public class Game2 extends Observable implements ActionListener {

	// local stuff for the game
	private int[][] matrix;
	private int[][] originalState;
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
		this.player = new Player(1, 1);
		this.gameTimer = new Timer(1000, this);
		this.gameTimer.start();
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

	// update the game state
	// probs gets passed user key input
	// then changes the game state
	public void update(int xCoord, int yCoord, char keyPress) {
		// get user input
		int x = getXCoordinate();
		int tempX = x;
		int y = getYCoordinate();
		int tempY = y;
		System.out.println(x + " " + y);
		x += xCoord;
		y += yCoord;
		char key = keyPress;
		if (matrix[y][x] == 0) {
			return;
		} else if (matrix[y][x] == 2) {
			if (key == 'W') {
				if (matrix[y - 1][x] == 0 || matrix[y - 1][x] == 2) {
					return;
				}
				matrix[y - 1][x] = matrix[y][x];
				matrix[y][x] = 4;
				this.player.setPosition(x, y);
				matrix[tempY][tempX] = originalState[tempY][tempX];

			} else if (key == 'A') {
				if (matrix[y][x - 1] == 0 || matrix[y][x - 1] == 2) {
					return;
				}
				matrix[y][x - 1] = matrix[y][x];
				matrix[y][x] = 4;
				this.player.setPosition(x, y);
				matrix[tempY][tempX] = originalState[tempY][tempX];
			} else if (key == 'S') {
				if (matrix[y + 1][x] == 0 || matrix[y + 1][x] == 2) {
					return;
				}
				matrix[y + 1][x] = matrix[y][x];
				matrix[y][x] = 4;
				this.player.setPosition(x, y);
				matrix[tempY][tempX] = originalState[tempY][tempX];
			} else if (key == 'D') {
				if (matrix[y][x + 1] == 0 || matrix[y][x + 1] == 2) {
					return;
				}
				matrix[y][x + 1] = matrix[y][x];
				matrix[y][x] = 4;
				this.player.setPosition(x, y);
				matrix[tempY][tempX] = originalState[tempY][tempX];
			}
		} else {
			this.player.setPosition(x, y);
			matrix[tempY][tempX] = originalState[tempY][tempX];
		}
		checkWin();
		this.numMoves++;
		setChanged();
		notifyObservers();
		// parse input - menu,info,quit,reset,move

		// pass move to player -> player checks for collision, moves accordingly
		// and updates position
		// if collsion -> if box -> box checks for collision and player&box move
		// and update positions
		// if collision with box and box cant move on changes are made

		// get coords for player and boxes, update the adj matrix with new
		// coords
	}

	private void checkWin() {
		// check if crosses are present in level
		checkWin = true;
		for (int i = 0; i < this.matrix.length; i++) {
			for (int j = 0; j < this.matrix[i].length; j++) {
				if (this.matrix[i][j] == 3) {
					this.checkWin = false;
				}
			}
		}
		if (checkWin == true) {
			this.gameTimer.stop();
			System.out.println("win");
		}
	}

	public int getXCoordinate() {
		return this.player.getX();
	}

	public int getYCoordinate() {
		return this.player.getY();
	}

	public int getNumMoves() {
		return this.numMoves;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		seconds++;
		System.out.println("Seconds: " + seconds);
		setChanged();
		notifyObservers();
		this.gameTimer.stop();
		this.gameTimer.start();
	}
	
	public void updateMoves(int update){
		this.numMoves = update;
	}
	
	public int getTime(){
		return this.seconds;
	}
}