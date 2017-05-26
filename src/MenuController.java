import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JOptionPane;

public class MenuController implements MouseListener {
	// declaring variables
	
	// a Menu instance
	private Menu menu;
	// a WarehouseBoss instance
	private WarehouseBoss window;

	/**
	 * constructor
	 * 
	 * @param 	menu		renders the main menu
	 * @param 	window		the jframe
	 */
	public MenuController(Menu menu, WarehouseBoss window) {
		// initialise variables
		this.menu = menu;
		this.window = window;
	}

	/**
	 * override: mouseClicked() -> decides what to do based on mouse input
	 * 
	 * @param	arg0	a MouseEvent instance
	 * @return	-
	 * @throws	-
	 */
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// get coordinates of mouse click
		int mx = arg0.getX();
		int my = arg0.getY();
		// do stuff based on input
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
