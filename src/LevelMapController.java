import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JOptionPane;

public class LevelMapController implements KeyListener, Observer {

	private Game game;
	private LevelMap mapUI;
	private WarehouseBoss window;
	private boolean tutorialMoveCheck;
	private boolean tutorialBoxCheck;
	private boolean tutorialBerryCheck;
	private boolean tutorialEndCheck;

	public LevelMapController(Game game, LevelMap mapUI, WarehouseBoss window) {
		super();
		this.game = game;
		this.mapUI = mapUI;
		this.window = window;
		this.tutorialMoveCheck = false;
		this.tutorialBoxCheck = false;
		this.tutorialBerryCheck = false;
		this.tutorialEndCheck = false;
		this.game.addObserver(this);
		initLevelMap();
	}

	public void initLevelMap() {
		this.mapUI.clearLevelMap();
		this.mapUI.setGrid(this.game.getMatrix());
		this.mapUI.setPlayerPosition(this.game.getPlayerColumn(), this.game.getPlayerRow());
		this.mapUI.setBoxPositions(this.game.getBoxes());
		this.mapUI.setBerryPositions(this.game.getBerries());
		this.mapUI.setNumMoves(this.game.getNumMoves());
		this.mapUI.updateTime(this.game.getSeconds());
		this.mapUI.setDirection(this.game.getPlayerDirection());
		this.mapUI.setIsBox(this.game.getPlayerIsBox());
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		int key = arg0.getKeyCode();
		if (key == KeyEvent.VK_W || key == KeyEvent.VK_UP) {
			this.game.update('W');
		} else if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) {
			this.game.update('A');
		} else if (key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) {
			this.game.update('S');
		} else if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) {
			this.game.update('D');
			if(this.game.getGameState() == this.game.isTutorial()){
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
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		this.mapUI.setGrid(this.game.getMatrix());
		this.mapUI.setPlayerPosition(this.game.getPlayerColumn(), this.game.getPlayerRow());
		this.mapUI.setBoxPositions(this.game.getBoxes());
		this.mapUI.setBerryPositions(this.game.getBerries());
		this.mapUI.setNumMoves(this.game.getNumMoves());
		this.mapUI.updateTime(this.game.getSeconds());
		this.mapUI.setDirection(this.game.getPlayerDirection());
		this.mapUI.setIsBox(this.game.getPlayerIsBox());
		this.mapUI.repaint();
		
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
			if(this.game.getGameState() == this.game.isGame()){
				int option = this.mapUI.showWin();
				if (option == 1) {
					this.game.newLevel();
					this.game.resetGame();
					initLevelMap();
				} else if (option == 2) {
					this.game.resetGame();
					initLevelMap();
				}
			} else  if (this.game.getGameState() == this.game.isTutorial()){
				int option = this.mapUI.showTutWin();
				if (option == 1) {
					this.game.resetGame();
					initLevelMap();
				} else if (option == 2) {
					this.window.initGame();
				}
			}

		}
	}
}