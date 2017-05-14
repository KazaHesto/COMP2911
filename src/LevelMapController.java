import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Observable;
import java.util.Observer;

public class LevelMapController implements KeyListener, Observer {

	private Game game;
	private LevelMap mapUI;

	public LevelMapController(Game game, LevelMap mapUI) {
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
		if (key == KeyEvent.VK_W) {
			this.game.update(0, -1, 'W');
		} else if (key == KeyEvent.VK_A) {
			this.game.update(-1, 0, 'A');
		} else if (key == KeyEvent.VK_S) {
			this.game.update(0, 1, 'S');
		} else if (key == KeyEvent.VK_D) {
			this.game.update(1, 0, 'D');
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