import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ArrowKeyListener implements KeyListener{
	private int xCoord = 0;
	private int yCoord = 0;
	private int numMoves = 0;
	
	public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_W || key == KeyEvent.VK_UP){
			this.yCoord = -1;
			System.out.println("yCoord = " + yCoord);
			numMoves++;
			System.out.println("numMoves = " + numMoves);
		} else if(key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT){
			this.xCoord = -1;
			System.out.println("XCoord = " + xCoord);
			numMoves++;
			System.out.println("numMoves = " + numMoves);
		} else if(key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN){
			this.yCoord = 1;
			System.out.println("yCoord = " + yCoord);
			numMoves++;
			System.out.println("numMoves = " + numMoves);
		} else if(key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT){
			this.xCoord = 1;
			System.out.println("XCoord = " + xCoord);
			numMoves++;
			System.out.println("numMoves = " + numMoves);
		}
	}
	
	public void keyReleased(KeyEvent e){
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_W || key == KeyEvent.VK_UP){
			this.yCoord = 0;
			System.out.println("yCoord = " + yCoord);
		} else if(key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT){
			this.xCoord = 0;
			System.out.println("XCoord = " + xCoord);
		} else if(key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN){
			this.yCoord = 0;
			System.out.println("yCoord = " + yCoord);
		} else if(key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT){
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
