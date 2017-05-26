import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

// a class that holds the constants used in the game
public class Resources {
	// declaring variables
	
	// the title of the game
	public static final String GAME_TITLE = "WAREHOUSE BOSS";
	// the text in the help button
	public static final String HELP_TITLE = "Help";
	// the text in the help message
	public static final String HELP_TEXT = "W - Forward\nA - Left\nS - Backwards\nD - Right\nAim : To successfully move all the Warehouse Boxes onto the Green Cross";
	// the text in the "about" button
	public static final String ABOUT_TITLE = "About WAREHOUSE BOSS";
	// the text presented when the "about" button is pressed
	public static final String ABOUT_TEXT = "IT'S A THING";

	// int that represents a wall
	public static final int WALL = 1;
	// int that represents a box
	public static final int BOX = 2;
	// int that represents a cross
	public static final int CROSS = 3;
	// int that represents a floor
	public static final int FLOOR = 4;
	// int that represents berry
	public static final int BERRY = 6;

	public static final int PLAYER_UP = 1;
	public static final int PLAYER_DOWN = 2;
	public static final int PLAYER_LEFT = 3;
	public static final int PLAYER_RIGHT = 4;
	public static final int PLAYER_PUSH_UP = 5;
	public static final int PLAYER_PUSH_DOWN = 6;
	public static final int PLAYER_PUSH_LEFT = 7;
	public static final int PLAYER_PUSH_RIGHT = 8;
	public static final int BOX_TOP = 9;
	public static final int BOX_SIDE = 10;
	public static final int BOX_CROSS_TOP = 11;
	public static final int BOX_CROSS_SIDE = 12;
	public static final int CROSS_TILE = 13;
	public static final int CROSS_ARROW = 14;
	public static final int FLOOR_TILE = 15;
	public static final int WALL_TOP = 16;
	public static final int WALL_SIDE = 17;
	public static final int WALL_SIDE_CROSS = 18;
	public static final int BERRY_TILE = 19;
	public static final int PLAY_BUTTON = 20;
	public static final int TUTORIAL_BUTTON = 21;
	public static final int HELP_BUTTON = 22;
	public static final int QUIT_BUTTON = 23;

	private final Image playerUp;
	private final Image playerDown;
	private final Image playerLeft;
	private final Image playerRight;
	private final Image playerPushUp;
	private final Image playerPushDown;
	private final Image playerPushLeft;
	private final Image playerPushRight;
	private final Image boxTop;
	private final Image boxSide;
	private final Image boxCrossTop;
	private final Image boxCrossSide;
	private final Image crossTile;
	private final Image crossArrow;
	private final Image floorTile;
	private final Image wallTop;
	private final Image wallSide;
	private final Image wallSideCross;
	private final Image berryTile;

	private final Image playButton;
	private final Image tutorialButton;
	private final Image helpButton;
	private final Image quitButton;

	public Resources() {
		super();
		this.playerUp = loadImage("/textures/ManUp.png");
		this.playerDown = loadImage("/textures/ManDown.png");
		this.playerLeft = loadImage("/textures/ManLeft.png");
		this.playerRight = loadImage("/textures/ManRight.png");
		this.playerPushUp = loadImage("/textures/ManPushUp.png");
		this.playerPushDown = loadImage("/textures/ManPushDown.png");
		this.playerPushLeft = loadImage("/textures/ManPushLeft.png");
		this.playerPushRight = loadImage("/textures/ManPushRight.png");
		this.boxTop = loadImage("/textures/BoxTop.png");
		this.boxSide = loadImage("/textures/BoxSide.png");
		this.boxCrossTop = loadImage("/textures/BoxTopCross.png");
		this.boxCrossSide = loadImage("/textures/BoxSideCross.png");
		this.crossTile = loadImage("/textures/Cross.png");
		this.crossArrow = loadImage("/textures/CrossGlow.png");
		this.floorTile = loadImage("/textures/Floor.png");
		this.wallTop = loadImage("/textures/WallTop.png");
		this.wallSide = loadImage("/textures/WallSide.png");
		this.wallSideCross = loadImage("/textures/WallSideCross.png");
		this.berryTile = loadImage("/textures/Berry.png");
		this.playButton = loadImage("/textures/PlayButton.png");
		this.tutorialButton = loadImage("/textures/TutorialButton.png");
		this.helpButton = loadImage("/textures/HelpButton.png");
		this.quitButton = loadImage("/textures/QuitButton.png");
	}

	// Reads images from file system
	private Image loadImage(String path) {
		try {
			return ImageIO.read(getClass().getResource(path));
		} catch (IOException e) {
			System.out.println("File not found");
		}
		return null;
	}

	public Image getImage(int i) {
		switch (i) {
		case PLAYER_UP:
			return this.playerUp;
		case PLAYER_DOWN:
			return this.playerDown;
		case PLAYER_LEFT:
			return this.playerLeft;
		case PLAYER_RIGHT:
			return this.playerRight;
		case PLAYER_PUSH_UP:
			return this.playerPushUp;
		case PLAYER_PUSH_DOWN:
			return this.playerPushDown;
		case PLAYER_PUSH_LEFT:
			return this.playerPushLeft;
		case PLAYER_PUSH_RIGHT:
			return this.playerPushRight;
		case BOX_TOP:
			return this.boxTop;
		case BOX_SIDE:
			return this.boxSide;
		case BOX_CROSS_TOP:
			return this.boxCrossTop;
		case BOX_CROSS_SIDE:
			return this.boxCrossSide;
		case CROSS_TILE:
			return this.crossTile;
		case CROSS_ARROW:
			return this.crossArrow;
		case FLOOR_TILE:
			return this.floorTile;
		case WALL_TOP:
			return this.wallTop;
		case WALL_SIDE:
			return this.wallSide;
		case WALL_SIDE_CROSS:
			return this.wallSideCross;
		case BERRY_TILE:
			return this.berryTile;
		case PLAY_BUTTON:
			return this.playButton;
		case TUTORIAL_BUTTON:
			return this.tutorialButton;
		case HELP_BUTTON:
			return this.helpButton;
		case QUIT_BUTTON:
			return this.quitButton;
		}
		return null;
	}
}
