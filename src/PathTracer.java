
public class PathTracer {
	
	//declaring variables
	
	//matrix to hold the levels information
	private int[][] matrix;
	//the x coord of the current position of the path
	private int randx;
	//the x coord of the start of the path
	private int x;
	//the y coord of the start of the path
	private int y;
	//the y coord of the current position of the path
	private int randy;
	//the length that the path will be
	private int pathLength;
	//the direction the path will turn
	private int direction;
	//the direction the path was facing 
	private int oldD;

	
	//Constructor
	/**
	 * Constructor for the PathTracer class
	 * 
	 * @param xsize		an int for the "x" length of the level's matrix
	 * @param ysize		an int for the "y" length of the level's matrix
	 * @return -
	 * @throws - 
	 */
	public PathTracer(int xsize, int ysize) {
		this.matrix = new int[ysize][xsize];

	}

	//fucntion to generate the path of the boxes
	/**
	 * this function randomly generate the path a box must go to get to a cross
	 * 
	 * @param xsize		an int for the "x" length of the level's matrix
	 * @param ysize		an int for the "y" length of the level's matrix
	 * @return matrix	the 2d matrix of the level, populated 1's,2's,3's and 4's to represent
	 * 					elements in the level
	 * @throws - 
	 */
	public int[][] makePath(int xsize, int ysize) {
		int length = 0;
		int i;
		int j;

		
		//while loop to ensure cross is sufficiently far away from box
		while (length <= ysize/2) {
			//fully populate the matrix with 1's
			i = 0;
			j = 0;
			while (i < ysize) {
				j = 0;
				while (j < xsize) {
					this.matrix[i][j] = 1;
					j++;
				}
				i++;
			}
			//randomly set the start of the path, path length and direction of first move
			this.randx = 2 + (int)(Math.random() * ((xsize - 5) + 1));
			this.randy = 2 + (int)(Math.random() * ((ysize - 5) + 1));
			this.x = this.randx;
			this.y = this.randy;
			this.pathLength = 10 + (int)(Math.random() * ((20 - 10) + 1));
			this.direction = 1 + (int)(Math.random() * ((3 - 1) + 1));

		//clear a free space around the start of the path
		this.matrix[this.randy+1][this.randx+1] = 4;
		this.matrix[this.randy-1][this.randx-1] = 4;
		this.matrix[this.randy+1][this.randx-1] = 4;
		this.matrix[this.randy-1][this.randx+1] = 4;
		this.matrix[this.randy+1][this.randx] = 4;
		this.matrix[this.randy-1][this.randx] = 4;
		this.matrix[this.randy][this.randx-1] = 4;
		this.matrix[this.randy][this.randx+1] = 4;
		
		//"face up" if start position is on lower half of matrix otherwise "face downwards"
		if (this.randy > ysize/2) {
			this.oldD = 1;
		} else {
			this.oldD = 3;
		}
		
		
		
		/*
		 * oldD: 1,2,3,4
		 * 		up,right,down,left
		 *  direction: 1,2,3
		 *  	forward,right,left
		 */
		
		
		//logic of path creation
		//oldD is the direction the the path faces after the last move it has done
		//i.e. if oldD = 2, path has moved right in the matrix, x coord has increased
		//direction dictates which way you turn 1,2,3-> go forward,turn right, turn left
		i = 0;
		while (i < this.pathLength) {			
			switch (this.oldD) {
			case 1: switch(this.direction) {
						case 1: //move up
								if (this.randy == 2) {
									i--;
									this.randy++;
								} else {
									this.randy--;
								}
								this.matrix[this.randy+2][this.randx] = 4;
								this.oldD = 1;
								break;
						case 2: //move right
								if (this.randx == xsize-3) {
									i--;
									this.randx--;
								} else {
									this.randx++;
								}
								this.matrix[this.randy][this.randx-2] = 4;
								this.oldD = 2;
								break;
						case 3: //move left
								if (this.randx == 2) {
									i--;
									this.randx++;
								} else {
									this.randx--;
								}
								this.matrix[this.randy][this.randx+2] = 4;
								this.oldD = 4;
								break;
					}
					break;
			case 2: switch(this.direction) {
						case 1: //move right
								if (this.randx == xsize-3) {
									i--;
									this.randx--;
								} else {
									this.randx++;
								}
								this.matrix[this.randy][this.randx-2] = 4;
								this.oldD = 2;
								break;
						case 2: //move down
								if (this.randy == ysize-3) {
									i--;
									this.randy--;
								} else {
									this.randy++;
								}
								this.matrix[this.randy-2][this.randx] = 4;
								this.oldD = 3;
								break;
						case 3: //move up
								if (this.randy == 2) {
									i--;
									this.randy++;
								} else {
									this.randy--;
								}
								this.matrix[this.randy+2][this.randx] = 4;
								this.oldD = 1;
								break;
					}
					break;
			case 3: switch(this.direction) {
						case 1: //move down
								if (this.randy == ysize-3) {
									i--;
									this.randy--;
								} else {
									this.randy++;
								}
								this.matrix[this.randy-2][this.randx] = 4;
								this.oldD = 3;
								break;
						case 2: //move left
								if (this.randx == 2) {
									i--;
									this.randx++;
								} else {
									this.randx--;
								}
								this.matrix[this.randy][this.randx+2] = 4;
								this.oldD = 4;
								break;
						case 3: //move right
								if (this.randx == xsize-3) {
									i--;
									this.randx--;
								} else {
									this.randx++;
								}
								this.matrix[this.randy][this.randx-2] = 4;
								this.oldD = 2;
								break;
					}
					break;
			case 4: switch(this.direction) {
						case 1: //move left
								if (this.randx == 2) {
									i--;
									this.randx++;
								} else {
									this.randx--;
								}
								this.matrix[this.randy][this.randx+2] = 4;
								this.oldD = 4;
								break;
						case 2: //move up
								if (this.randy == 2) {
									i--;
									this.randy++;
								} else {
									this.randy--;
								}
								this.matrix[this.randy+2][this.randx] = 4;
								this.oldD = 1;
								break;
						case 3: //move down
								if (this.randy == ysize-3) {
									i--;
									this.randy--;
								} else {
									this.randy++;
								}
								this.matrix[this.randy-2][this.randx] = 4;
								this.oldD = 3;
								break;
					}
					break;
			}
			//"delete" a wall and add a free path space in the matrix
			this.matrix[this.randy][this.randx] = 4;
			//choose a new direction to go in
			this.direction = 1 + (int)(Math.random() * ((3 - 1) + 1));

			i++;
		}
		//at the end of path creation, mark 3 where the cross will go
		this.matrix[this.randy][this.randx] = 3;
		//mark 2 at the start of the path to indicate box location
		this.matrix[this.y][this.x] = 2;
				
		
		//find the distance between the cross and box
		length = (int) Math.sqrt((double)((y-randy)*(y-randy)+(x-randx)*(x-randx)));
		
		
		}
		return matrix;
		
	}
	//debugging print function
	/**
	 * print function which outputs the levels matrix to the console for debuggin purposes
	 * @param xsize		an int for the "x" length of the level's matrix
	 * @param ysize		an int for the "y" length of the level's matrix
	 * @return -
	 * @throws -
	 */
	public void print(int xsize, int ysize) {
		int i = 0;
		int j = 0;
		while (i < ysize) {
			j = 0;
			while (j < xsize) {
				System.out.print(matrix[i][j]+ " ");
				j++;
			}
			System.out.println();
			i++;
		}
	}
	
}
