import java.util.ArrayList;
import java.util.Stack;

import javax.swing.Timer;

public class SaveData implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	public int[][] matrix;
	public int[][] originalState;
	public int[][] resetState;
	public Player player;
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

	public SaveData(int[][] matrix, int[][] resetState, Player player, Stack<Integer> undoPlayer,
			int seconds, int numMoves, ArrayList<Box> boxes, Stack<ArrayList<Box>> undoBoxes,
			ArrayList<Box> resetBoxes, ArrayList<Berry> berries, boolean berryState, int berryCount) {
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