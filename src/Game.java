import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

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
	
	private Image testImage;
	
	private int[][] matrix;
	private int[][] originalState;
	
	private KeyListener listener;
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
		this.originalState = new int[][] {
				{0,0,0,0,0,0,0,0,0,0,0},
				{0,4,4,4,4,4,4,4,4,0,0},
				{0,0,0,0,4,0,4,4,0,0,0},
				{0,0,0,0,4,0,4,4,4,3,0},
				{0,0,0,3,4,4,4,4,4,3,0},
				{0,0,0,0,0,0,0,0,0,0,0}
			};
		//needs
		//map - adj matrix
		//player
		//linkedlist of boxes
		//linkedlist of crosses
		this.listener = new ArrowKeyListener();
	}
	
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
			case 4: g.setColor(Color.white);
					break;
		}
	}
	
	//Initializes the game state and visuals
	private void init(){
		display = new Display(title,width,height, this.listener);
		display.getListener();
	}

	public class ArrowKeyListener implements KeyListener{
		private int xCoord = 0;
		private int yCoord = 0;
		private char keyPres;
		public void keyPressed(KeyEvent e){
			int key = e.getKeyCode();
			
			if(key == KeyEvent.VK_W){
				keyPres = 'W';
				update(0, -1, keyPres);
				this.yCoord = -1;
				System.out.println("yCoord = " + yCoord);
			} else if(key == KeyEvent.VK_A){
				keyPres = 'A';
				update(-1, 0,keyPres);
				this.xCoord = -1;
				System.out.println("XCoord = " + xCoord);
			} else if(key == KeyEvent.VK_S){
				keyPres = 'S';
				update(0, 1,keyPres);
				this.yCoord = 1;
				System.out.println("yCoord = " + yCoord);
			} else if(key == KeyEvent.VK_D){
				keyPres = 'D';
				update(1, 0, keyPres);
				this.xCoord = 1;
				System.out.println("XCoord = " + xCoord);
			}
		}
		
		public void keyReleased(KeyEvent e){

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
	//update the game state
	//probs gets passed user key input
	//then changes the game state
	private void update(int xCoord, int yCoord, char keyPress) {
		
		//maybe check for win first -> check if crosses and boxes share coords
		
		//get user input
		int x = getXCoordinate();
		int tempX = x;
		int y = getYCoordinate();
		int tempY = y;
		System.out.println(x + " " + y);
		x += xCoord;
		y += yCoord;
		char key = keyPress;
		if(matrix[y][x] == 0){
			return;
		} else if(matrix[y][x] == 2){
			
			if(key == 'W'){
				if(matrix[y-1][x] == 0 || matrix[y-1][x] == 2){
					return;
				}
				matrix[y-1][x] = matrix[y][x];
				matrix[y][x] = 1;
				matrix[tempY][tempX] = originalState[tempY][tempX];
				
			} else if(key == 'A'){
				if(matrix[y][x-1] == 0 || matrix[y][x-1] == 2){
					return;
				}
				matrix[y][x-1] = matrix[y][x];
				matrix[y][x] = 1;
				matrix[tempY][tempX] = originalState[tempY][tempX];
			} else if(key == 'S'){
				if(matrix[y+1][x] == 0 || matrix[y+1][x] == 2){
					return;
				}
				matrix[y+1][x] = matrix[y][x];
				matrix[y][x] = 1;
				matrix[tempY][tempX] = originalState[tempY][tempX];
			} else if(key == 'D'){
				if(matrix[y][x+1] == 0 || matrix[y][x+1] == 2){
					return;
				}
				matrix[y][x+1] = matrix[y][x];
				matrix[y][x] = 1;
				matrix[tempY][tempX] = originalState[tempY][tempX];
			}
		} else {
			matrix[y][x] = 1;
			matrix[tempY][tempX] = originalState[tempY][tempX];
		}
		
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
				//changeColor(this.matrix[j][i]);
				//g.fillRect(i*100, j*100, 100, 100);
				g.setColor(Color.white);
				changeImage(matrix[j][i], j, i);
				if (matrix[j][i] < 4) {
					g.drawImage(testImage, i*64, j*64, null);
				} else {
					g.fillRect(i*64, j*64, 64, 64);
				}
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
		for(int col = 0; col<this.matrix[0].length;col++){
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
		for(int col = 0; col<this.matrix[0].length;col++){
			if(this.matrix[row][col] == 1){
				y = row;
				break;
			}
		}
	}
	return y;
	}
	
	public static BufferedImage loadImage(String path) {
		try {
			return ImageIO.read(Game.class.getResource(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}
		return null;
	}
	
	public void run(){
		
		init();

		//game loop
		while(running) {
//			update(); update called directly by keylistener, should probably change this entirely when we have time			
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