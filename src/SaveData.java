import java.util.ArrayList;
import java.util.Stack;

import javax.swing.Timer;

public class SaveData implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	public int[][] matrix;
	public int[][] originalState;
	public int[][] resetState;
	public int row;
	public int column;
	public int direction;
	public Stack<Integer> undoPlayer;
	public Stack<int[][]> undoMatrix;
	public int seconds;
	public int numMoves;
	public ArrayList<Box> boxes;
	public Stack<ArrayList<Box>> undoBoxes;
	public ArrayList<Box> resetBoxes;

	public SaveData(int[][] matrix, int[][] resetState, int row, int column, int direction, Stack<Integer> undoPlayer,
			int seconds, int numMoves, ArrayList<Box> boxes, Stack<ArrayList<Box>> undoBoxes,
			ArrayList<Box> resetBoxes) {
		super();
		this.matrix = matrix;
		this.resetState = resetState;
		this.row = row;
		this.column = column;
		this.direction = direction;
		this.undoPlayer = undoPlayer;
		this.seconds = seconds;
		this.numMoves = numMoves;
		this.boxes = boxes;
		this.undoBoxes = undoBoxes;
		this.resetBoxes = resetBoxes;
	}
}