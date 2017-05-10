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
	
	
	//constructor
	public Game(String title, int width, int height) {
		this.width = width;
		this.height = height;
		this.title = title;
		
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
	
		//changes graphics color based on matrix value
		private void changeImage(int z, int x, int y) {
			switch(z) {
			case 0: testImage = Game.loadImage("/textures/Wall.png.png");
					break;
			case 1: testImage = Game.loadImage("/textures/ManU1.png.png");
					break;
			case 2: testImage = Game.loadImage("/textures/Box2.png.png");
					break;
			case 3: testImage = Game.loadImage("/textures/Cross.png.png");
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
		int[][] matrix = new int[][] {
			{0,0,0,0,0,0,0,0,0,0,0},
			{0,1,4,4,4,4,4,4,4,0,0},
			{0,0,0,0,2,0,4,4,0,0,0},
			{0,0,0,0,4,0,4,2,4,3,0},
			{0,0,0,3,4,4,4,2,4,3,0},
			{0,0,0,0,0,0,0,0,0,0,0}
		};
		int i = 0;
		int j = 0;
		while (i <= 10) {
			j = 0;
			while (j <= 5) {
				changeColor(matrix[j][i]);
				g.fillRect(i*64, j*64, 64, 64);
				/*
				changeImage(matrix[j][i], j, i);
				if (matrix[j][i] < 4) {
					g.drawImage(testImage, i*64, j*64, null);
				} else {
					g.fillRect(i*64, j*64, 64, 64);
				}
				*/
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
