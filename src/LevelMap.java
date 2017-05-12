import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

public class LevelMap extends JPanel implements ActionListener {

	private static final int BOX_HEIGHT = 64;
	private static final int BOX_WIDTH = 64;

//	private static final int UP = 0;
//	private static final int DOWN = 1;
//	private static final int LEFT = 2;
//	private static final int RIGHT = 3;

	private int rows;
	private int columns;
	private int[][] grid;
	private Game game;
	private LevelMapController controller;
	private Image wall;
	private Image player;
	private Image box;
	private Image cross;
	private double x;
	private double y;
	private double tempX;
	private double tempY;
	private Timer timer;

	public LevelMap(int rows, int columns, Game game) {
		super();
		this.rows = rows;
		this.columns = columns;
		setSize(this.columns * BOX_WIDTH, this.rows * BOX_HEIGHT);
		setFocusable(true);
		setVisible(true);

		this.game = game;

		this.wall = loadImage("/textures/Wall.png.png");
		this.player = loadImage("/textures/ManU1.png.png");
		this.box = loadImage("/textures/Box2.png.png");
		this.cross = loadImage("/textures/Cross.png.png");

		this.timer = new Timer(32, this);
		this.timer.stop();

		this.x = -1;
		this.y = -1;
	}

	public void setGrid(int[][] grid) {
		this.grid = new int[grid.length][];
		for (int i = 0; i < grid.length; i++) {
			this.grid[i] = grid[i].clone();
		}
		checkPlayer();
	}

	private void checkPlayer() {
		double x = 0;
		double y = 0;
		for (int row = 0; row < this.grid.length; row++) {
			for (int col = 0; col < this.grid[row].length; col++) {
				if (this.grid[row][col] == 1) {
					x = col;
					y = row;
				}
			}
		}
		if (this.x == -1 && this.y == -1) {
			this.x = x;
			this.y = y;
		} else {
		if (!this.timer.isRunning()) {
				this.timer.start();
			}
			this.tempX = x;
			this.tempY = y;

//			animate(x, y);
//			this.x = x;
//			this.y = y;
		}
	}

	public void setController(LevelMapController controller) {
		this.controller = controller;
		this.addKeyListener(this.controller);
	}

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

	private BufferedImage loadImage(String path) {
		try {
			return ImageIO.read(Game.class.getResource(path));
		} catch (IOException e) {
			System.out.println("File not found");
		}
		return null;
	}

	private boolean fuzzyMatch(double a, double b) {
		return Math.abs(a - b) < 0.1;
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(this.columns * BOX_WIDTH, this.rows * BOX_HEIGHT);
	}

	@Override
	public void paintComponent(Graphics g) {
		for (int row = 0; row < this.grid.length; row++) {
			for (int col = 0; col < this.grid[row].length; col++) {
				if (this.grid[row][col] < 4 && this.grid[row][col] != 1) {
					g.drawImage(changeImage(this.grid[row][col]), col * BOX_WIDTH, row * BOX_HEIGHT, null);
				} else {
					g.setColor(Color.WHITE);
					g.fillRect(col * BOX_WIDTH, row * BOX_HEIGHT, BOX_WIDTH, BOX_HEIGHT);
				}
			}
		}
		g.drawImage(changeImage(1), (int) (this.x * BOX_WIDTH), (int) (this.y * BOX_HEIGHT), null);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (!fuzzyMatch(this.tempX, this.x)) {
			this.x = (20 * this.x + this.tempX) / 21;
		}
		if (!fuzzyMatch(this.tempY, this.y)) {
			this.y = (20 * this.y + this.tempY) / 21;
		}
		if (fuzzyMatch(this.tempX, this.x) && fuzzyMatch(this.tempY, this.y)) {
			this.timer.stop();
			this.x = this.tempX;
			this.y = this.tempY;
		}
		repaint();
	}
}
