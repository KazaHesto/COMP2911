import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ArrowKeyListener implements KeyListener{
	private int xCoord = 0;
	private int yCoord = 0;
	public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_W){
			this.yCoord = -1;
			System.out.println("yCoord = " + yCoord);
		} else if(key == KeyEvent.VK_A){
			this.xCoord = -1;
			System.out.println("XCoord = " + xCoord);
		} else if(key == KeyEvent.VK_S){
			this.yCoord = 1;
			System.out.println("yCoord = " + yCoord);
		} else if(key == KeyEvent.VK_D){
			this.xCoord = 1;
			System.out.println("XCoord = " + xCoord);
		}
	}
	
	public void keyReleased(KeyEvent e){
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_W){
			this.yCoord = 0;
			System.out.println("yCoord = " + yCoord);
		} else if(key == KeyEvent.VK_A){
			this.xCoord = 0;
			System.out.println("XCoord = " + xCoord);
		} else if(key == KeyEvent.VK_S){
			this.yCoord = 0;
			System.out.println("yCoord = " + yCoord);
		} else if(key == KeyEvent.VK_D){
			this.xCoord = 0;
			System.out.println("XCoord = " + xCoord);
		}
	}
	
	public int getX(){
		return this.xCoord;
	}
	
	public int getY(){
		return this.yCoord;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
