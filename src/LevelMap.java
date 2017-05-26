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

	private final int BOX_HEIGHT = 48;
	private final int BOX_SIDE_HEIGHT = 64;
	private final int BOX_WIDTH = 64;
	private final int SCORE_GUTTER = 120;

	private int rows;
	private int columns;
	private Resources resources;
	private int[][] grid;
	private LevelMapController controller;
	private double x;
	private double y;
	private int tempX;
	private int tempY;
	private Timer timer;
	private int numMoves;
	private int seconds;
	private ArrayList<Box> boxes;
	private ArrayList<Berry> berries;
	private int playerDirection;
	private boolean isBox;

	public LevelMap(int rows, int columns) {
		super();
		this.rows = rows;
		this.columns = columns;
		this.resources = new Resources();
		setSize(this.columns * BOX_WIDTH, this.rows * BOX_HEIGHT);
		setFocusable(true);
		setVisible(true);
		this.timer = new Timer(5, this);
		clearLevelMap();
	}

	public void clearLevelMap() {
		this.numMoves = 0;
		this.seconds = 0;

		this.x = -1;
		this.y = -1;
		this.boxes = new ArrayList<Box>();
		this.berries = new ArrayList<Berry>();
	}

	// Updates the grid shown in the ui
	public void setGrid(int[][] grid) {
		this.grid = new int[grid.length][];
		for (int i = 0; i < grid.length; i++) {
			this.grid[i] = grid[i].clone();
		}
	}

	public void setDirection(int playerDirection) {
		this.playerDirection = playerDirection;
	}

	public void setIsBox(boolean isBox) {
		this.isBox = isBox;
	}

	public void setBoxPositions(ArrayList<Box> boxes) {
		this.boxes = boxes;
	}

	public void setBerryPositions(ArrayList<Berry> berries) {
		this.berries = berries;
	}

	public void setNumMoves(int numMoves) {
		this.numMoves = numMoves;
	}

	public void setPlayerPosition(int x, int y) {
		if (this.x == -1 && this.y == -1) {
			this.x = x;
			this.y = y;
		} else {
			if (!this.timer.isRunning()) {
				this.timer.start();
			}
			this.tempX = x;
			this.tempY = y;
		}
	}

	// Adds keylistener
	public void setController(LevelMapController controller) {
		this.controller = controller;
		this.addKeyListener(this.controller);
	}

	private boolean fuzzyMatch(double a, double b) {
		return Math.abs(a - b) < 0.01;
	}

	public int showWin() {
		String[] choices = { "Next Level", "Reset", "Quit Game" };
		int choice = JOptionPane.showOptionDialog(null, "Do you want to continue?", "",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, choices, choices[0]);
		if (choice == JOptionPane.CANCEL_OPTION) {
			System.exit(1);
		} else if (choice == JOptionPane.YES_OPTION) {
			return 1;
		} else if (choice == JOptionPane.NO_OPTION) {
			return 2;
		}
		return 0;
	}
	
	public int showTutWin() {
		String[] choices = {"Practise", "Start Game", "Quit Game"};
		int choice = JOptionPane.showOptionDialog(null, "Congradulations, Tutorial Complete!", "",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, choices, choices[0]);
		if (choice == JOptionPane.CANCEL_OPTION) {
			System.exit(1);
		} else if (choice == JOptionPane.YES_OPTION) {
			return 1;
		} else if (choice == JOptionPane.NO_OPTION) {
			return 2;
		}
		return 0;
		
	}

	public void updateTime(int update) {
		this.seconds = update;
	}

	private boolean isBox(int row, int column) {
		for (Box box : this.boxes) {
			if (box.getRow() == row && box.getColumn() == column) {
				return true;
			}
		}
		return false;
	}

	// Sets the size of this JPanel
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(this.columns * BOX_WIDTH, this.rows * BOX_HEIGHT + SCORE_GUTTER);
	}

	// Sets what happens when level map is repainted
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

		for (Berry berry : this.berries) {
			if (!isBox(berry.getRow(), berry.getColumn())) {
				g.drawImage(this.resources.getImage(Resources.BERRY_TILE), berry.getColumn() * BOX_WIDTH,
					(int) ((berry.getRow() + 0.8) * BOX_HEIGHT + SCORE_GUTTER), BOX_WIDTH, BOX_SIDE_HEIGHT, null);
		
			}
		}

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

	@Override
	public void actionPerformed(ActionEvent arg0) {
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
		repaint();
	}
}
