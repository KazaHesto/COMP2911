import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JOptionPane;

public class MenuController implements MouseListener {

	private Menu menu;
	private WarehouseBoss window;

	/**
	 * constructor for the menu controller
	 * @param menu -> renders the main menu
	 * @param window -> the jframe
	 */
	public MenuController(Menu menu, WarehouseBoss window) {
		this.menu = menu;
		this.window = window;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		
		int mx = arg0.getX();
		int my = arg0.getY();
		if (mx >= 150 && mx <= 290) {
			if (my >= 150 && my <= 250) {
				this.window.initGame();
			}
		}

		if (mx >= 425 && mx <= 565) {
			if (my >= 150 && my <= 250) {
				this.window.initTutorial();
			}
		}

		if (mx >= 150 && mx <= 290) {
			if (my >= 300 && my <= 400) {
				JOptionPane.showMessageDialog(null, Constants.HELP_TEXT, "Help", JOptionPane.INFORMATION_MESSAGE);
			}
		}

		if(mx >= 425 && mx <= 565){
			if(my >= 300 && my <= 400){
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
