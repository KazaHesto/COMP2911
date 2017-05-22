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
    public Timer gameTimer;
    public int seconds;
    public ArrayList<Box> boxes;
    public Stack<ArrayList<Box>> undoBoxes;
    public ArrayList<Box> resetBoxes;
}