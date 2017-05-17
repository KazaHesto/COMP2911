import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

public class LevelMap extends JPanel implements ActionListener {

	public enum STATE {
		MENU, GAME
	};

	private final int BOX_HEIGHT = 64;
	private final int BOX_WIDTH = 64;
	private final int SCORE_GUTTER = 60;

	private STATE state = STATE.MENU;
	private Menu menu;

	// private final int UP = 0;
	// private final int DOWN = 1;
	// private final int LEFT = 2;
	// private final int RIGHT = 3;

	private int rows;
	private int columns;
	private int[][] grid;
	private LevelMapController controller;
	private MenuController Mcontroller;
	private Image wall;
	private Image player;
	private Image box;
	private Image cross;
	private double x;
	private double y;
	private int tempX;
	private int tempY;
	private Timer timer;
	private int numMoves;

	public LevelMap(int rows, int columns) {
		super();
		this.menu = new Menu();
		this.rows = rows;
		this.columns = columns;
		setSize(this.columns * BOX_WIDTH, this.rows * BOX_HEIGHT);
		setFocusable(true);
		setVisible(true);

		this.wall = loadImage("/textures/Wall.png.png");
		this.player = loadImage("/textures/ManU1.png.png");
		this.box = loadImage("/textures/Box2.png.png");
		this.cross = loadImage("/textures/Cross.png.png");

		this.timer = new Timer(16, this);
		this.timer.stop();
		this.numMoves = 0;

		this.x = -1;
		this.y = -1;
	}

	public STATE getState() {
		return this.state;
	}

	// Updates the grid shown in the ui
	public void setGrid(int[][] grid) {
		this.grid = new int[grid.length][];
		for (int i = 0; i < grid.length; i++) {
			this.grid[i] = grid[i].clone();
		}
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

	public STATE setState() {
		this.state = STATE.GAME;
		return STATE.GAME;
	}

	public void setMenuController(MenuController Mcontroller) {
		this.Mcontroller = Mcontroller;
		this.addMouseListener(this.Mcontroller);
	}

	// Decides what image to show at each tile
	private Image changeImage(int z) {
		switch (z) {
		case 0:
			return this.wall;
		case 1:
			return this.player;
		case 2:
			return this.box;
		case 3:
			return this.cross;
		}
		return null;
	}

	// Reads images from file system
	private Image loadImage(String path) {
		try {
			return ImageIO.read(Game2.class.getResource(path));
		} catch (IOException e) {
			System.out.println("File not found");
		}
		return null;
	}

	private boolean fuzzyMatch(double a, double b) {
		return Math.abs(a - b) < 0.01;
	}

	// Sets the size of this JPanel
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(this.columns * BOX_WIDTH, this.rows * BOX_HEIGHT + SCORE_GUTTER);
	}

	// Sets what happens when level map is repainted
	@Override
	public void paintComponent(Graphics g) {
		if (this.state == STATE.GAME) {
			// For some reason this is needed on Windows?
			g.clearRect(0, 0, this.columns * BOX_WIDTH, this.rows * BOX_HEIGHT + SCORE_GUTTER);
			// Shows score at the top of the window
			Font font = new Font("Veranda", Font.BOLD, 40);
			g.setFont(font);
			g.setColor(Color.BLACK);
			g.drawString("MOVES MADE: " + this.numMoves, 5, 45);

			// Shows level
			for (int row = 0; row < this.grid.length; row++) {
				for (int col = 0; col < this.grid[row].length; col++) {
					if (this.grid[row][col] < 4 && this.grid[row][col] != 1) {
						g.drawImage(changeImage(this.grid[row][col]),
								col * BOX_WIDTH, row * BOX_HEIGHT + SCORE_GUTTER, null);
					}
				}
				g.drawImage(changeImage(1), (int) (this.x * BOX_WIDTH),
						(int) (this.y * BOX_HEIGHT + SCORE_GUTTER), null);

				g.drawImage(changeImage(1), (int) (this.x * BOX_WIDTH),
						(int) (this.y * BOX_HEIGHT + SCORE_GUTTER), null);
			}
		} else if (this.state == STATE.MENU) {
			menu.render(g);
		}
	}

	// Sets what happens when the timer changes
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
