import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.JPanel;

public class Menu extends JPanel {
	// declaring variables
	
	// a MenuController instance
	private MenuController controller;
	// a Resources instance
	private Resources resources;

	// the buttons for the different options
	public Rectangle playButton = new Rectangle(150, 150, 200, 70);
	public Rectangle tutorialButton = new Rectangle(425, 150, 140, 100);
	public Rectangle helpButton = new Rectangle(150, 300, 140, 100);
	public Rectangle quitButton = new Rectangle(425, 300, 140, 100);

	/**
	 * constructor
	 * 
	 * @param	-
	 * @return	-
	 * @throws	-
	 */
	public Menu() {
		super();
		// create new resources
		this.resources = new Resources();
	}

	/**
	 * setter: setController() -> sets mouse listener for clicking on main menu options
	 * 
	 * @param 	controller		controller for the main menu
	 * @return	-
	 * @throws	-
	 */
	public void setController(MenuController controller) {
		// set controller
		this.controller = controller;
		// add a listener
		addMouseListener(controller);
	}

	/**
	 * override: getPreferredSize() -> returns a Dimension instance
	 * 
	 * @param	-
	 * @return	new_Dimension(704, 504)		a Dimension instance
	 * @throws	-
	 */
	@Override
	public Dimension getPreferredSize() {
		// return the dimension
		return new Dimension(704, 504);
	}

	/**
	 * override: paintComponent() -> paints a panel
	 * 
	 * @param	g	a Graphics instance
	 * @return	-
	 * @throws	-
	 */
	@Override
	public void paintComponent(Graphics g) {
		// set the font
		Font fnt0 = new Font("ariel", Font.BOLD, 50);
		g.setFont(fnt0);
		// draw the panel
		g.drawString(Resources.GAME_TITLE, 120, 50);
		g.drawImage(this.resources.getImage(Resources.PLAY_BUTTON), 400, 100, 200, 70, null);
		g.drawImage(this.resources.getImage(Resources.TUTORIAL_BUTTON), 400, 200, 200, 70, null);
		g.drawImage(this.resources.getImage(Resources.HELP_BUTTON), 400, 300, 200, 70, null);
		g.drawImage(this.resources.getImage(Resources.QUIT_BUTTON), 400, 400, 200, 70, null);
	}
}
