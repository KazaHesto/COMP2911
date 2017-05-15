import java.util.Observable;

public class Game2 extends Observable {
	
	//local stuff for the game
	private int[][] matrix;
	private int[][] originalState;
	private int numMoves;
	
	//constructor
	public Game2() {
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
	}
	
	public int[][] getMatrix() {
		return matrix;
	}

	//update the game state
	//probs gets passed user key input
	//then changes the game state
	public void update(int xCoord, int yCoord, char keyPress) {
		
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
		setChanged();
		notifyObservers();
		//parse input - menu,info,quit,reset,move
		
		//pass move to player -> player checks for collision, moves accordingly and updates position
		// if collsion -> if box -> box checks for collision and player&box move and update positions
		// if collision with box and box cant move on changes are made
		
		//get coords for player and boxes, update the adj matrix with new coords
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
	
	public void addMove(){
		this.numMoves++;
	}
	
	public int getNumMoves(){
		return this.numMoves;
	}
}