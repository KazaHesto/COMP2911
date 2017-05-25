import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Observable;
import java.util.Observer;

public class LevelMapController implements KeyListener, Observer {

	private Game2 game;
	private LevelMap mapUI;

	public LevelMapController(Game2 game, LevelMap mapUI) {
		super();
		this.game = game;
		this.mapUI = mapUI;
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
		if (this.game.isWin()) {
			int option = this.mapUI.showWin();
			if (option == 1) {
				this.game.newLevel();
				this.game.resetGame();
				initLevelMap();
			} else if (option == 2) {
				this.game.resetGame();
				initLevelMap();
			}
		}
	}
}