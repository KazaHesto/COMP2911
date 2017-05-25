import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JPanel;

public class Menu extends JPanel {

	private MenuController controller;

	public Rectangle playButton = new Rectangle(175 + 120, 100, 100, 50);
	public Rectangle helpButton = new Rectangle(175 + 120, 200, 100, 50);
	public Rectangle quitButton = new Rectangle(175 + 120, 300, 100, 50);

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
		Graphics2D g2D = (Graphics2D) g;
		Font fnt0 = new Font("ariel", Font.BOLD, 50);
		g.setFont(fnt0);
		g.setColor(Color.black);
		g.drawString(Constants.GAME_TITLE, 120, 50);
		Font fnt1 = new Font("ariel", Font.BOLD, 30);
		g.setFont(fnt1);
		g.drawString("Play", playButton.x + 19, playButton.y + 35);
		g2D.draw(playButton);
		g.drawString("Help", helpButton.x + 19, helpButton.y + 35);
		g2D.draw(helpButton);
		g.drawString("Quit", quitButton.x + 19, quitButton.y + 35);
		g2D.draw(quitButton);
	}
}
