import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

public class Game implements Runnable{
	
	//local stuff for the game
	private Display display;
	public int width, height;
	public String title;
	private boolean running = false;
	//stuff for graphics
	private BufferStrategy bs;
	private Graphics g;	
	private Thread thread;
	
	private int[][] matrix;
	private int xCoord;
	private int yCoord;
	//constructor
	public Game(String title, int width, int height) {
		this.width = width;
		this.height = height;
		this.title = title;
		this.matrix = new int[][] {
			{0,0,0,0,0,0,0,0,0,0,0},
			{0,1,4,4,4,4,4,4,4,0,0},
			{0,0,0,0,2,0,4,4,0,0,0},
			{0,0,0,0,4,0,4,2,4,3,0},
			{0,0,0,3,4,4,4,2,4,3,0},
			{0,0,0,0,0,0,0,0,0,0,0}
		};
		//needs
		//map - adj matrix
		//player
		//linkedlist of boxes
		//linkedlist of crosses
		
	}
	
	//Initializes the game state and visuals
	private void init(){
		display = new Display(title,width,height);
	}
	
	//update the game state
	//probs gets passed user key input
	//then changes the game state
	private void update() {
		
		//maybe check for win first -> check if crosses and boxes share coords
		
		//get user input
		int x = getXCoordinate();
		int tempX = x;
		int y = getYCoordinate();
		int tempY = y;
		x += xCoord;
		y+= yCoord;
		int temp = this.matrix[x][y];
		matrix[x][y] = 1;
		matrix[tempX][tempY] = temp;
		
		
		//parse input - menu,info,quit,reset,move
		
		//pass move to player -> player checks for collision, moves accordingly and updates position
		// if collsion -> if box -> box checks for collision and player&box move and update positions
		// if collision with box and box cant move on changes are made
		
		//get coords for player and boxes, update the adj matrix with new coords
	}
	
	//changes graphics color based on matrix value
	private void changeColor(int x) {
		switch(x) {
		case 0: g.setColor(Color.BLACK);
				break;
		case 1: g.setColor(Color.RED);
				break;
		case 2: g.setColor(Color.YELLOW);
				break;
		case 3: g.setColor(Color.GREEN);
				break;
		case 4: g.setColor(Color.WHITE);
				break;
		}
	}
	
	//takes the game state and puts it on the canvas
	private void render() {
		bs = display.getCanvas().getBufferStrategy();
		if(bs == null) {
			display.getCanvas().createBufferStrategy(3);
			return;
		}
		g = bs.getDrawGraphics();
		//clear the screen
		g.clearRect(0, 0, width, height);		
		//draw here
		g.setColor(Color.RED);

		int i = 0;
		int j = 0;
		while (i <= 10) {
			j = 0;
			while (j <= 5) {
				changeColor(this.matrix[j][i]);
				g.fillRect(i*100, j*100, 100, 100);
				j++;
			}
			
			i++;
		}
		
		//g.setColor(Color.RED); //change the color of the "brush"
		//g.fillRect(10, 50, 50, 70); //draws a filled color rectangle
		//g.setColor(Color.BLACK);
		//g.drawRect(60,50,50,70);
		
		
		//end draw
		bs.show();
		g.dispose();
	}
	public int getXCoordinate(){
	int x = 0;
	for(int row = 0; row<this.matrix.length; row++){
		for(int col = 0; col<this.matrix.length;col++){
			if(this.matrix[row][col] == 1){
				x = col;
				break;
			}
		}
	}
	return x;
	}
	
	public int getYCoordinate(){
	int y = 0;
	for(int row = 0; row<this.matrix.length; row++){
		for(int col = 0; col<this.matrix.length;col++){
			if(this.matrix[row][col] == 1){
				y = row;
				break;
			}
		}
	}
	return y;
	}
	
	
	public void run(){
		
		init();

		//game loop
		while(running) {
			update();
			render();
		}
		stop();
		
	}
	
	//used to start the game
	public synchronized void start(){
		if (running)
			return;
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	//used to close the game
	public synchronized void stop(){
		if(!running)
			return;
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}