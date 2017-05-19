import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JOptionPane;

public class MenuController implements MouseListener{
	
	private LevelMap levelMap;
	
	public MenuController(LevelMap levelMap){
		this.levelMap = levelMap;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
//		public Rectangle playButton = new Rectangle(175+120,100,100,50);
//		public Rectangle helpButton = new Rectangle(175+120,200,100,50);
//		public Rectangle quitButton = new Rectangle(175+120,300,100,50);
		int mx = e.getX();
		int my = e.getY();
		if(mx >= 295 && mx <= 395){
			if(my >= 100 && my <= 150){
				levelMap.setState();
				levelMap.repaint();
			}
		}
		
		if(mx >= 295 && mx <= 395){
			if(my >= 200 && my <= 250){
				JOptionPane.showMessageDialog(null, "W - Forward\nA - Left\n"
						+ "S - Backwards\nD - Right\nR - Reset Game\nAim : To successfully move all the Warehouse Boxes onto the Green Cross", "Help", JOptionPane.INFORMATION_MESSAGE);
			}
		}
		
		if(mx >= 295 && mx <= 395){
			if(my >= 300 && my <= 350){
				System.exit(1);
			}
		}
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
