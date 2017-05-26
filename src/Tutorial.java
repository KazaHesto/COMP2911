import java.util.ArrayList;
import java.util.Observer;
import java.util.Stack;

import javax.swing.JOptionPane;
import javax.swing.Timer;

public class Tutorial implements Observer {

	private Game game;

	public Tutorial(Game game) {
		super();
		this.game = game;
	}

	public void initTutGame() {
		int[][] matrix = new int[][] {
			{1,1,1,1,1,1,1,1,1,1,1},
			{1,4,4,1,4,1,4,1,4,1,1},
			{1,1,1,1,4,1,1,1,1,1,1},
			{1,1,1,1,4,4,4,1,4,3,1},
			{1,1,1,3,4,4,4,1,4,3,1},
			{1,1,1,1,1,1,1,1,1,1,1}
		};
		SaveData start = new SaveData(null, null, null, null, null, 0, 0, null, null, null, null, false, 0);

		this.game = new Game(row, column, matrix);
		showGame(row,column);
		
		this.row = row;
		this.column = column;
		this.player = new Player(1, 1);
		this.undoPlayer = new Stack<Integer>();
		this.undoBoxes = new Stack<ArrayList<Box>>();
		this.gameTimer = new Timer(1000, this);
		this.seconds = 0;
		this.boxes = new ArrayList<Box>();
		this.boxes.add(new Box(2, 4));
		this.boxes.add(new Box(3, 7));
		this.boxes.add(new Box(4, 7));
		this.resetBoxes = getBoxes();
		this.berries = new ArrayList<Berry>();
		this.resetBoxes = getBoxes();
		this.berries.add(new Berry(1,6));
		this.resetBerries = getBerries();
		this.berryState = false;
		this.berryCount = 0;
		newTutLevel(matrix);
	}
	
	if(!tutorialMoveCheck){
		int[][] matrix = new int[][] {
			{1,1,1,1,1,1,1,1,1,1,1},
			{1,4,4,4,4,1,4,1,4,1,1},
			{1,1,1,1,4,1,1,1,1,1,1},
			{1,1,1,1,4,4,4,1,4,3,1},
			{1,1,1,3,4,4,4,1,4,3,1},
			{1,1,1,1,1,1,1,1,1,1,1}
		};
	this.game.newTutLevel(matrix);
	JOptionPane.showMessageDialog(null, "Walk into box to move it in said direction", "Tutorial",
			JOptionPane.INFORMATION_MESSAGE);
	tutorialMoveCheck = true;
}
	
	
	if(this.game.getGameState() == this.game.isTutorial()){
		if(this.game.isBox(3, 4) && !tutorialBoxCheck){
			JOptionPane.showMessageDialog(null, "Move Box into Cross", "Tutorial",
					JOptionPane.INFORMATION_MESSAGE);
			tutorialBoxCheck = true;
		}
		if(this.game.isBox(4,3) && !tutorialBerryCheck){
			int[][] matrix = new int[][] {
				{1,1,1,1,1,1,1,1,1,1,1},
				{1,4,4,4,4,4,4,1,4,1,1},
				{1,1,1,1,4,4,4,1,1,1,1},
				{1,1,1,1,4,4,4,1,4,3,1},
				{1,1,1,3,4,4,4,1,4,3,1},
				{1,1,1,1,1,1,1,1,1,1,1}
			};
			this.game.newTutLevel(matrix);
			JOptionPane.showMessageDialog(null, "Move Player over Berry Power up! It pauses time for 3 seconds", "Tutorial",
					JOptionPane.INFORMATION_MESSAGE);
			tutorialBerryCheck = true;
		}
		if(this.game.getBerryList().isEmpty() && !tutorialEndCheck){
			int[][] matrix = new int[][] {
				{1,1,1,1,1,1,1,1,1,1,1},
				{1,4,4,4,4,4,4,4,4,1,1},
				{1,1,1,1,4,1,4,4,1,1,1},
				{1,1,1,1,4,1,4,4,4,3,1},
				{1,1,1,3,4,4,4,4,4,3,1},
				{1,1,1,1,1,1,1,1,1,1,1}
			};
			this.game.newTutLevel(matrix);
			JOptionPane.showMessageDialog(null, "Move the rest of the boxes into crosses", "Tutorial",
					JOptionPane.INFORMATION_MESSAGE);
			tutorialEndCheck = true;
		}
	}

	if (this.game.isWin()) {
		if (this.game.getGameState() == this.game.isTutorial()){
			int option = this.mapUI.showTutWin();
			if (option == 1) {
				this.game.resetGame();
				initLevelMap();
			} else if (option == 2) {
				this.window.initGame();
			}
		}

	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}
}
