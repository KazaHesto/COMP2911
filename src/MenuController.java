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
		if (mx >= 400 && mx <= 600) {
			if (my >= 100 && my <= 170) {
				this.window.initGame();
			}
			if (my >= 200 && my <= 270) {
				this.window.initTutorial();
			}
			if (my >= 300 && my <= 370) {
				JOptionPane.showMessageDialog(null, Resources.HELP_TEXT, "Help", JOptionPane.INFORMATION_MESSAGE);
			}
			if(my >= 400 && my <= 470){
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
