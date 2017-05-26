import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.JPanel;

public class Menu extends JPanel {

	private MenuController controller;
	private Resources resources;

	public Rectangle playButton = new Rectangle(150, 150, 200, 70);
	public Rectangle tutorialButton = new Rectangle(425, 150, 140, 100);
	public Rectangle helpButton = new Rectangle(150, 300, 140, 100);
	public Rectangle quitButton = new Rectangle(425, 300, 140, 100);

	public Menu() {
		super();
		this.resources = new Resources();
	}

	public void setController(MenuController controller) {
		this.controller = controller;
		addMouseListener(controller);
	}

	// Sets the size of this JPanel
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(704, 504);
	}

	@Override
	public void paintComponent(Graphics g) {
		Font fnt0 = new Font("ariel", Font.BOLD, 50);
		g.setFont(fnt0);
		g.drawString(Resources.GAME_TITLE, 120, 50);
		g.drawImage(this.resources.getImage(Resources.PLAY_BUTTON), 400, 100, 200, 70, null);
		g.drawImage(this.resources.getImage(Resources.TUTORIAL_BUTTON), 400, 200, 200, 70, null);
		g.drawImage(this.resources.getImage(Resources.HELP_BUTTON), 400, 300, 200, 70, null);
		g.drawImage(this.resources.getImage(Resources.QUIT_BUTTON), 400, 400, 200, 70, null);
	}
}
