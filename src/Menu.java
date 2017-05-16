import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Menu {
	
	public Rectangle playButton = new Rectangle(175+120,100,100,50);
	public Rectangle helpButton = new Rectangle(175+120,200,100,50);
	public Rectangle quitButton = new Rectangle(175+120,300,100,50);
	
	public void render (Graphics g){
		Graphics2D g2D = (Graphics2D) g;
		Font fnt0 = new Font("ariel", Font.BOLD, 50);
		g.setFont(fnt0);
		g.setColor(Color.black);
		g.drawString("PUZZLE GAME",175, 65);
		Font fnt1 = new Font("ariel", Font.BOLD,30);
		g.setFont(fnt1);
		g.drawString("Play", playButton.x + 19, playButton.y + 35);
		g2D.draw(playButton);
		g.drawString("Help", helpButton.x + 19, helpButton.y + 35);
		g2D.draw(helpButton);
		g.drawString("Quit", quitButton.x + 19, quitButton.y + 35);
		g2D.draw(quitButton);
	}
}
