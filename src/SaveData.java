import java.io.Serializable;
import java.util.ArrayList;
import java.util.Stack;

public class SaveData implements Serializable {

	private static final long serialVersionUID = 1L;

	public int[][] matrix;
	public int[][] originalState;
	public int[][] resetState;
	public Player player;
	public Player resetPlayer;
	public Stack<Integer> undoPlayer;
	public Stack<int[][]> undoMatrix;
	public int seconds;
	public int numMoves;
	public ArrayList<Box> boxes;
	public Stack<ArrayList<Box>> undoBoxes;
	public ArrayList<Box> resetBoxes;
	public ArrayList<Berry> berries;
	public boolean berryState;
	public int berryCount;

	/**
	 * constructor to set all the save data
	 * @param matrix -> the game matrix
	 * @param resetState -> the original state of game matrix
	 * @param player -> the player object 
	 * @param resetPlayer -> the original state of the player object
	 * @param undoPlayer -> all the moves made
	 * @param seconds -> the in game time
	 * @param numMoves -> number of moves made by player
	 * @param boxes -> the boxes in the game
	 * @param undoBoxes -> moves the boxes have made
	 * @param resetBoxes -> the original state of the boexes
	 * @param berries -> the berries in the game
	 * @param berryState -> the original position of the berries
	 * @param berryCount -> the number of berries left
	 */
	public SaveData(int[][] matrix, int[][] resetState, Player player, Player resetPlayer, Stack<Integer> undoPlayer,
			int seconds, int numMoves, ArrayList<Box> boxes, Stack<ArrayList<Box>> undoBoxes, ArrayList<Box> resetBoxes,
			ArrayList<Berry> berries, boolean berryState, int berryCount) {
		super();
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