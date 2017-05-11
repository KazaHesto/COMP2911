import java.awt.event.KeyEvent;

public class Player extends Game{

	private int xCoord = 0;
	private int yCoord = 0;	

	public Player(int x, int y){
		super(x,y);
	}
	
	public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_W){
			this.yCoord = -1;
		} else if(key == KeyEvent.VK_A){
			this.xCoord = -1;
		} else if(key == KeyEvent.VK_S){
			this.xCoord = 1;
		} else if(key == KeyEvent.VK_D){
			this.xCoord = 1;
		}
	}
	
	public void keyReleased(KeyEvent e){
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_W){
			this.yCoord = 0;
		} else if(key == KeyEvent.VK_A){
			this.xCoord = 0;
		} else if(key == KeyEvent.VK_S){
			this.xCoord = 0;
		} else if(key == KeyEvent.VK_D){
			this.xCoord = 0;
		}
	}
}





