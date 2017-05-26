import java.io.Serializable;
import java.util.ArrayList;
import java.util.Stack;

public class SaveData implements Serializable {
	// declaring variables
	
	private static final long serialVersionUID = 1L;

	// the matrix (map) to be saved
	public int[][] matrix;
	// the original map for this level
	public int[][] originalState;
	// the state to be set to after a reset
	public int[][] resetState;
	// a Player instance
	public Player player;
	// the original player
	public Player resetPlayer;
	// a stack to undo moves
	public Stack<Integer> undoPlayer;
	// a stack that stores the matrices throughout the game
	public Stack<int[][]> undoMatrix;
	// the timer
	public int seconds;
	// the number of moves made
	public int numMoves;
	// a list of boxes
	public ArrayList<Box> boxes;
	// a list to undo box moves
	public Stack<ArrayList<Box>> undoBoxes;
	// a list to reset the boxes
	public ArrayList<Box> resetBoxes;
	// a list of berries
	public ArrayList<Berry> berries;
	// the state of the berries
	public boolean berryState;
	// the number of berries in the level
	public int berryCount;

	/**
	 * constructor
	 * 
	 * @param 	matrix			the game matrix
	 * @param 	resetState		the original state of game matrix
	 * @param 	player			the player object 
	 * @param 	resetPlayer		the original state of the player object
	 * @param 	undoPlayer		all the moves made
	 * @param 	seconds			the in game time
	 * @param 	numMoves		number of moves made by player
	 * @param 	boxes			the boxes in the game
	 * @param 	undoBoxes		moves the boxes have made
	 * @param 	resetBoxes		the original state of the boxes
	 * @param 	berries			the berries in the game
	 * @param 	berryState		the original position of the berries
	 * @param 	berryCount		the number of berries left
	 * @return	-
	 * @throws	-
	 */
	public SaveData(int[][] matrix, int[][] resetState, Player player, Player resetPlayer, Stack<Integer> undoPlayer,
			int seconds, int numMoves, ArrayList<Box> boxes, Stack<ArrayList<Box>> undoBoxes, ArrayList<Box> resetBoxes,
			ArrayList<Berry> berries, boolean berryState, int berryCount) {
		super();
		// initialise the saved data based on the game's current state
		this.matrix = matrix;
		this.resetState = resetState;
		this.player = player;
		this.undoPlayer = undoPlayer;
		this.seconds = seconds;
		this.numMoves = numMoves;
		this.boxes = boxes;
		this.undoBoxes = undoBoxes;
		this.resetBoxes = resetBoxes;
		this.berries = berries;
		this.berryState = berryState;
		this.berryCount = berryCount;
	}
}