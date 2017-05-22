import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Observable;
import java.util.Observer;

public class LevelMapController implements KeyListener, Observer {

	private Game2 game;
	private LevelMap mapUI;
	private Player player;

	public LevelMapController(Game2 game, LevelMap mapUI, Player player) {
		super();
		this.game = game;
		this.mapUI = mapUI;
		this.player = player;
		this.game.addObserver(this);
		initLevelMap();
	}

	private void initLevelMap() {
		this.mapUI.clearLevelMap();
		this.mapUI.setGrid(this.game.getMatrix());
		this.mapUI.setPlayerPosition(this.game.getPlayerColumn(), this.game.getPlayerRow());
		this.mapUI.setBoxPositions(this.game.getBoxes());
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
		} else if (key == KeyEvent.VK_K){
			SaveData data = new SaveData();
			data.matrix = game.getMatrix();
			data.row = player.getRow();
			data.column = player.getColumn();
			System.out.println("Save : row = " + data.row + " column = " + data.column);
			try {
				ResourceManager.save(data, "1.save");
			} catch (Exception e) {
				System.out.println("Couldn't save: " + e.getMessage());
			}
		} else if (key == KeyEvent.VK_L){
			try {
				SaveData data = (SaveData) ResourceManager.load("1.save");
				game.setMatrix(data.matrix);
				System.out.println("Load : row = " + data.row + " column = " + data.column);
				player.setPosition(data.row, data.column);
			} catch (Exception e) {
				System.out.println("Couldn't load save data: " + e.getMessage());
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
		this.mapUI.setNumMoves(this.game.getNumMoves());
		this.mapUI.updateTime(this.game.getTime());
		this.mapUI.repaint();
		if (this.game.isWin()) {
			int option = this.mapUI.showWin();
			if (option == 1) {
				// next level stuff
			} else if (option == 2) {
				this.game.resetGame();
				initLevelMap();
			}
		}
	}
}