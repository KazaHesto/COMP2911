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
		game.addObserver(this);
		this.mapUI.setGrid(this.game.getMatrix());
		this.game.addObserver(this);
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		int key = arg0.getKeyCode();
		if (key == KeyEvent.VK_W || key == KeyEvent.VK_UP) {
			this.game.update(0, -1, 'W');
			this.game.addMove();
			System.out.println("numMoves = " + this.game.getNumMoves());
		} else if (key == KeyEvent.VK_A|| key == KeyEvent.VK_LEFT) {
			this.game.update(-1, 0, 'A');
			this.game.addMove();
			System.out.println("numMoves = " + this.game.getNumMoves());
		} else if (key == KeyEvent.VK_S|| key == KeyEvent.VK_DOWN) {
			this.game.update(0, 1, 'S');
			this.game.addMove();
			System.out.println("numMoves = " + this.game.getNumMoves());
		} else if (key == KeyEvent.VK_D|| key == KeyEvent.VK_RIGHT) {
			this.game.update(1, 0, 'D');
			this.game.addMove();
			System.out.println("numMoves = " + this.game.getNumMoves());
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
		this.mapUI.repaint();
	}
}
