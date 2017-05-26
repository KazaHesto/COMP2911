import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class LevelMap extends JPanel implements ActionListener {
	// declaring variables
	
	// setting box height
	private final int BOX_HEIGHT = 48;
	// setting box's side height
	private final int BOX_SIDE_HEIGHT = 64;
	// setting box width
	private final int BOX_WIDTH = 64;
	// setting score gutter size
	private final int SCORE_GUTTER = 120;

	// number of rows in the map
	private int rows;
	// number of columns in the map
	private int columns;
	// declaring a Resources instance
	private Resources resources;
	// grid representing the map
	private int[][] grid;
	// LevelMapController instance
	private LevelMapController controller;
	// x-coordinate
	private double x;
	// y-coordinate
	private double y;
	// x-coordinate used as a temporary value
	private int tempX;
	// y-coordinate used as a temporary value
	private int tempY;
	// Timer instance
	private Timer timer;
	// number of moves made
	private int numMoves;
	// number of seconds played
	private int seconds;
	// list of boxes
	private ArrayList<Box> boxes;
	// list of berries
	private ArrayList<Berry> berries;
	// represents direction in which the player is facing
	private int playerDirection;
	// determines whether something is a box
	private boolean isBox;

	/**
	 * constructor
	 * 
	 * @param 	rows		rows for the map matrix
	 * @param 	columns		columns for the map matrix
	 * @return	-
	 * @throws	-
	 */
	public LevelMap(int rows, int columns) {
		super();
		// initialise the number of rows in the map
		this.rows = rows;
		// initialise the number of columns in the map
		this.columns = columns;
		// initialise a new Resources instance
		this.resources = new Resources();
		// setting size of map
		setSize(this.columns * BOX_WIDTH, this.rows * BOX_HEIGHT);
		// visability settings
		setFocusable(true);
		setVisible(true);
		// creating a new timer
		this.timer = new Timer(5, this);
		// wiping the map
		clearLevelMap();
	}
	
	/**
	 * method: clearLevelMap() -> clears the level Map
	 * 
	 * @param	-
	 * @return	-
	 * @throws	-
	 */
	public void clearLevelMap() {
		// reset number of moves made
		this.numMoves = 0;
		// reset timer
		this.seconds = 0;

		// reset x-coordinate
		this.x = -1;
		// reset y-coordinate
		this.y = -1;
		// reset box list
		this.boxes = new ArrayList<Box>();
		// reset berry list
		this.berries = new ArrayList<Berry>();
	}

	/**
	 * setter: setGrid() -> set the grid matrix for the game
	 * 
	 * @param 	grid		input matrix for the game
	 * @return	-
	 * @throws	-
	 */
	public void setGrid(int[][] grid) {
		// creating a new matrix
		this.grid = new int[grid.length][];
		// populating the matrix
		for (int i = 0; i < grid.length; i++) {
			this.grid[i] = grid[i].clone();
		}
	}
	
	/**
	 * setter: setDirection() -> sets the direction of the player in levelMapController
	 * 
	 * @param 	playerDirection		gets the direction the player is in
	 * @return	-
	 * @throws	-
	 */
	public void setDirection(int playerDirection) {
		// sets direction
		this.playerDirection = playerDirection;
	}
	
	/**
	 * setter: setIsBox() -> sets if the player is colliding with a box for box 
	 * 							moving animations
	 * 
	 * @param 	isBox	sets if the player is colliding with box or not
	 * @return	-
	 * @throws	-
	 */
	public void setIsBox(boolean isBox) {
		// sets internal variable based on parsed Boolean variable
		this.isBox = isBox;
	}
	
	/**
	 * setter: setBoxPositions() -> set the positions for the box in levelMapController
	 * 
	 * @param 	boxes	sets all the positions of the boxes
	 * @return	-
	 * @throws	-
	 */
	public void setBoxPositions(ArrayList<Box> boxes) {
		// copies the box list
		this.boxes = boxes;
	}
	
	/**
	 * setter: setBerryPositions() -> set the positions for the berries in levelMapController
	 * 
	 * @param 	berries		set all the positions of the berries
	 * @return	-
	 * @throws	-
	 */
	public void setBerryPositions(ArrayList<Berry> berries) {
		// copies berry list
		this.berries = berries;
	}

	/**
	 * setter: setNumMoves() -> the number of moves the player has made in levelMapController
	 * 
	 * @param 	numMoves	number of moves the player has made
	 * @return	-
	 * @throws	-
	 */
	public void setNumMoves(int numMoves) {
		// setting internal variable based on input
		this.numMoves = numMoves;
	}
	
	/**
	 * setter: setPlayerPosition() -> the position of the player in levelMapController
	 * 
	 * @param 	x		the x cooridinate
	 * @param 	y		the y cooridinate
	 * @return	-
	 * @throws	-
	 */
	public void setPlayerPosition(int x, int y) {
		// if a position has not been set
		if (this.x == -1 && this.y == -1) {
			// set position
			this.x = x;
			this.y = y;
		} else {
			// if the timer is not running
			if (!this.timer.isRunning()) {
				// start the timer
				this.timer.start();
			}
			// set the temporary variables within this class
			this.tempX = x;
			this.tempY = y;
		}
	}

	/**
	 * setter: setController() -> set KeyListener for levelMapController
	 * 
	 * @param 	controller		the levelMap controller
	 * @return	-
	 * @throws	-
	 */
	public void setController(LevelMapController controller) {
		// sets the controller variable within this class
		this.controller = controller;
		// adds a key listener to the controller so it can react
		// depending on input
		this.addKeyListener(this.controller);
	}
	
	/**
	 * method: fuzzyMatch() -> general function that compares two numbers
	 * 
	 * @param 	a 						number of type double
	 * @param 	b 						number of type double
	 * @return 	Maths.abs(a-b)<0.01		checking if two numbers are less than 0.01
	 */
	private boolean fuzzyMatch(double a, double b) {
		// return result
		return Math.abs(a - b) < 0.01;
	}
	
	/**
	 * method: showWin() -> shows the winning window
	 * 
	 * @param	-
	 * @return	(an int)		returns what button is pressed
	 * @throws	-
	 */
	public int showWin() {
		// list of choices available
		String[] choices = { "Next Level", "Reset", "Quit Game" };
		// show the options to the user
		int choice = JOptionPane.showOptionDialog(null, "Do you want to continue?", "",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, choices, choices[0]);
		// depending on each choice, act accordingly
		if (choice == JOptionPane.CANCEL_OPTION) {
			System.exit(1);
		} else if (choice == JOptionPane.YES_OPTION) {
			return 1;
		} else if (choice == JOptionPane.NO_OPTION) {
			return 2;
		}
		return 0;
	}
	
	/**
	 * methodL showTutWin() -> shows the winning window for tutorial level
	 * 
	 * @param	-
	 * @return	(an int)	an int that represents the user's choice
	 * @throws	-
	 */
	public int showTutWin() {
		// list of choices available
		String[] choices = {"Practise", "Start Game", "Quit Game"};
		// show the options to the user
		int choice = JOptionPane.showOptionDialog(null, "Congradulations, Tutorial Complete!", "",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, choices, choices[0]);
		// depending on each choice, act accordingly
		if (choice == JOptionPane.CANCEL_OPTION) {
			System.exit(1);
		} else if (choice == JOptionPane.YES_OPTION) {
			return 1;
		} else if (choice == JOptionPane.NO_OPTION) {
			return 2;
		}
		return 0;
		
	}
	
	/**
	 * method: updateTime() -> update the time
	 * 
	 * @param 	update		number of seconds passed in game
	 * @return	-
	 * @throws	-
	 */
	public void updateTime(int update) {
		// update the seconds variable
		this.seconds = update;
	}
	
	/**
	 * method: isBox() -> check if the player is colliding with box
	 * 
	 * @param 	row			row coordinate
	 * @param 	column		column coordinate
	 * @return 	Boolean		true if it collides and false otherwise
	 */
	private boolean isBox(int row, int column) {
		// for each box in the list
		for (Box box : this.boxes) {
			// if a box is at the coordinates
			if (box.getRow() == row && box.getColumn() == column) {
				// return success
				return true;
			}
		}
		// else, return failure
		return false;
	}


	/**
	 * override: getPrefferedSize() -> sets size of window
	 * 
	 * @param	-
	 * @return	(a Dimension instance)		the window size
	 * @throws	-
	 */
	@Override
	public Dimension getPreferredSize() {
		// return the window size
		return new Dimension(this.columns * BOX_WIDTH, this.rows * BOX_HEIGHT + SCORE_GUTTER);
	}

	/**
	 * override: paintComponent() -> repaints the window
	 * 
	 * @param	g		the graphics to repaint with
	 * @return	-
	 * @throws	-
	 */
	@Override
	public void paintComponent(Graphics g) {
		// For some reason this is needed on Windows?
		g.clearRect(0, 0, this.columns * BOX_WIDTH, SCORE_GUTTER);
		// "Title" on top of score pane
		Font fnt0 = new Font("ariel", Font.BOLD, 50);
		g.setFont(fnt0);
		FontMetrics metrics = g.getFontMetrics();
		g.drawString(Resources.GAME_TITLE, this.columns * BOX_WIDTH / 2 - metrics.stringWidth(Resources.GAME_TITLE) / 2,
				50);
		// Shows score at the top of the window
		Font font = new Font("Veranda", Font.BOLD, 20);
		g.setFont(font);
		g.setColor(Color.BLACK);
		g.drawString("Move count: " + this.numMoves, 5, 80);
		g.drawString("Timer: " + this.seconds, 5, 110);

		// Floor and side wall layer
		for (int row = 0; row < this.grid.length; row++) {
			for (int col = 0; col < this.grid[row].length; col++) {
				if (this.grid[row][col] == Resources.CROSS && row != 0 && this.grid[row - 1][col] == Resources.WALL) {
					g.drawImage(this.resources.getImage(Resources.WALL_SIDE_CROSS), col * BOX_WIDTH, (int) (row * BOX_HEIGHT + SCORE_GUTTER), BOX_WIDTH,
							BOX_HEIGHT, null);
				}
				if (this.grid[row][col] == Resources.FLOOR && row != 0 && this.grid[row - 1][col] == Resources.WALL) {
					g.drawImage(this.resources.getImage(Resources.WALL_SIDE), col * BOX_WIDTH, (int) (row * BOX_HEIGHT + SCORE_GUTTER), BOX_WIDTH,
							BOX_SIDE_HEIGHT, null);
				}
				if (this.grid[row][col] == Resources.FLOOR) {
					g.drawImage(this.resources.getImage(Resources.FLOOR_TILE), col * BOX_WIDTH, (int) ((row + 0.9) * BOX_HEIGHT + SCORE_GUTTER), BOX_WIDTH,
							BOX_HEIGHT, null);
				}
				if (this.grid[row][col] == Resources.CROSS) {
					g.drawImage(this.resources.getImage(Resources.CROSS_TILE), col * BOX_WIDTH, (int) ((row + 0.9) * BOX_HEIGHT + SCORE_GUTTER), BOX_WIDTH,
							BOX_HEIGHT, null);
				}
			}
		}

		// Draws box side
		for (Box box : this.boxes) {
			if (this.grid[box.getRow()][box.getColumn()] == Resources.CROSS) {
				g.drawImage(this.resources.getImage(Resources.BOX_CROSS_SIDE), box.getColumn() * BOX_WIDTH,
						(int) ((box.getRow() + 0.5) * BOX_HEIGHT + SCORE_GUTTER), BOX_WIDTH, BOX_SIDE_HEIGHT, null);
			} else {
				g.drawImage(this.resources.getImage(Resources.BOX_SIDE), box.getColumn() * BOX_WIDTH,
						(int) ((box.getRow() + 0.5) * BOX_HEIGHT + SCORE_GUTTER), BOX_WIDTH, BOX_SIDE_HEIGHT, null);
			}
		}

		// draws berries
		for (Berry berry : this.berries) {
			if (!isBox(berry.getRow(), berry.getColumn())) {
				g.drawImage(this.resources.getImage(Resources.BERRY_TILE), berry.getColumn() * BOX_WIDTH,
					(int) ((berry.getRow() + 0.8) * BOX_HEIGHT + SCORE_GUTTER), BOX_WIDTH, BOX_SIDE_HEIGHT, null);
		
			}
		}

		// represents player based on his direction and if he is next to a box
		if (playerDirection == 0) {
			if (isBox == true) {
				g.drawImage(this.resources.getImage(Resources.PLAYER_PUSH_UP), (int) (this.x * BOX_WIDTH),
						(int) ((this.y) * BOX_HEIGHT + SCORE_GUTTER), BOX_WIDTH, BOX_SIDE_HEIGHT, null);
			} else {
				g.drawImage(this.resources.getImage(Resources.PLAYER_UP), (int) (this.x * BOX_WIDTH),
						(int) ((this.y) * BOX_HEIGHT + SCORE_GUTTER), BOX_WIDTH, BOX_SIDE_HEIGHT, null);
			}
		} else if (playerDirection == 90) {
			if (isBox == true) {
				g.drawImage(this.resources.getImage(Resources.PLAYER_PUSH_RIGHT), (int) (this.x * BOX_WIDTH),
						(int) ((this.y) * BOX_HEIGHT + SCORE_GUTTER), BOX_WIDTH, BOX_SIDE_HEIGHT, null);
			} else {
				g.drawImage(this.resources.getImage(Resources.PLAYER_RIGHT), (int) (this.x * BOX_WIDTH),
						(int) ((this.y) * BOX_HEIGHT + SCORE_GUTTER), BOX_WIDTH, BOX_SIDE_HEIGHT, null);
			}
		} else if (playerDirection == 180) {
			if (isBox == true) {
				g.drawImage(this.resources.getImage(Resources.PLAYER_PUSH_DOWN), (int) (this.x * BOX_WIDTH),
						(int) ((this.y) * BOX_HEIGHT + SCORE_GUTTER), BOX_WIDTH, BOX_SIDE_HEIGHT, null);
			} else {
				g.drawImage(this.resources.getImage(Resources.PLAYER_PUSH_DOWN), (int) (this.x * BOX_WIDTH),
						(int) ((this.y) * BOX_HEIGHT + SCORE_GUTTER), BOX_WIDTH, BOX_SIDE_HEIGHT, null);
			}
		} else if (playerDirection == 270) {
			if (isBox == true) {
				g.drawImage(this.resources.getImage(Resources.PLAYER_PUSH_LEFT), (int) (this.x * BOX_WIDTH),
						(int) ((this.y) * BOX_HEIGHT + SCORE_GUTTER), BOX_WIDTH, BOX_SIDE_HEIGHT, null);
			} else {
				g.drawImage(this.resources.getImage(Resources.PLAYER_LEFT), (int) (this.x * BOX_WIDTH),
						(int) ((this.y) * BOX_HEIGHT + SCORE_GUTTER), BOX_WIDTH, BOX_SIDE_HEIGHT, null);
			}
		}

		// Draws box top
		for (Box box : this.boxes) {
			if (this.grid[box.getRow()][box.getColumn()] == Resources.CROSS) {
				g.drawImage(this.resources.getImage(Resources.BOX_CROSS_TOP), box.getColumn() * BOX_WIDTH,
						(int) ((box.getRow() + 0.1) * BOX_HEIGHT + SCORE_GUTTER), BOX_WIDTH, BOX_HEIGHT, null);
			} else {
				g.drawImage(this.resources.getImage(Resources.BOX_TOP), box.getColumn() * BOX_WIDTH,
						(int) ((box.getRow() + 0.1) * BOX_HEIGHT + SCORE_GUTTER), BOX_WIDTH, BOX_HEIGHT, null);
			}
		}

		// top wall layer and cross glow effect
		for (int row = 0; row < this.grid.length; row++) {
			for (int col = 0; col < this.grid[row].length; col++) {
				if (this.grid[row][col] == Resources.WALL) {
					g.drawImage(this.resources.getImage(Resources.WALL_TOP), col * BOX_WIDTH, row * BOX_HEIGHT + SCORE_GUTTER, BOX_WIDTH,
							BOX_HEIGHT, null);
				}
				if (this.grid[row][col] == Resources.CROSS && !isBox(row, col)) {
					g.drawImage(this.resources.getImage(Resources.CROSS_ARROW), col * BOX_WIDTH, (int) ((row + 0.9 - 1) * BOX_HEIGHT + SCORE_GUTTER),
							BOX_WIDTH, BOX_HEIGHT, null);
				}
			}
		}
	}

	/**
	 * override: actionPerformed() -> used for animations
	 * 
	 * @param	arg0	an ActionEvent instance
	 * @return	-
	 * @throws	-
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// code to make the animations smooth
		if (!fuzzyMatch(this.tempX, this.x)) {
			this.x = (5 * this.x + this.tempX) / 6;
		}
		if (!fuzzyMatch(this.tempY, this.y)) {
			this.y = (5 * this.y + this.tempY) / 6;
		}
		if (fuzzyMatch(this.tempX, this.x) && fuzzyMatch(this.tempY, this.y)) {
			this.timer.stop();
			this.x = this.tempX;
			this.y = this.tempY;
		}
		// repaint the window
		repaint();
	}
}
