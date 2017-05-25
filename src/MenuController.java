import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JOptionPane;

public class MenuController implements MouseListener {

	private Menu menu;
	private WarehouseBoss window;

	public MenuController(Menu menu, WarehouseBoss window) {
		this.menu = menu;
		this.window = window;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		int mx = arg0.getX();
		int my = arg0.getY();
		if (mx >= 295 && mx <= 395) {
			if (my >= 100 && my <= 150) {
				this.window.initGame();
			}
		}

		if (mx >= 295 && mx <= 395) {
			if (my >= 200 && my <= 250) {
//				JOptionPane.showMessageDialog(null, Constants.HELP_TEXT, "Help", JOptionPane.INFORMATION_MESSAGE);
				this.window.initTutorial();
			}
		}

		if (mx >= 295 && mx <= 395) {
			if (my >= 300 && my <= 350) {
				System.exit(1);
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}
}
