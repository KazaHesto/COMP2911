
public class PathTracer {

	private int[][] matrix;
	private int randx;
	private int x;
	private int y;
	private int randy;
	private int pathLength;
	private int direction;
	private int oldD;

	public PathTracer(int xsize, int ysize) {
		this.matrix = new int[ysize][xsize];

	}

	public int[][] makePath(int xsize, int ysize) {
		int length = 0;
		int i = 0;
		int j = 0;
		while (i < ysize) {
			j = 0;
			while (j < xsize) {
				this.matrix[i][j] = 1;
				j++;
			}
			i++;
		}

		this.randx = 2 + (int)(Math.random() * ((xsize - 5) + 1));
		this.randy = 2 + (int)(Math.random() * ((ysize - 5) + 1));
		this.x = this.randx;
		this.y = this.randy;
		this.pathLength = 10 + (int)(Math.random() * ((20 - 10) + 1));
		this.direction = 1 + (int)(Math.random() * ((3 - 1) + 1));
		while (length <= ysize/2) {
		
		//this.matrix[this.randy][this.randx] = 2;
		
		this.matrix[this.randy+1][this.randx+1] = 4;
		this.matrix[this.randy-1][this.randx-1] = 4;
		this.matrix[this.randy+1][this.randx-1] = 4;
		this.matrix[this.randy-1][this.randx+1] = 4;
		this.matrix[this.randy+1][this.randx] = 4;
		this.matrix[this.randy-1][this.randx] = 4;
		this.matrix[this.randy][this.randx-1] = 4;
		this.matrix[this.randy][this.randx+1] = 4;
		
		if (this.randy > ysize/2) {
			this.oldD = 1;
		} else {
			this.oldD = 3;
		}
		/*
		int i = 0;
		while (i < this.pathLength) {
			switch (this.direction) {
			case 1:
				if (this.randy == 1) {
					i--;
					this.randy++;
				} else {
					this.randy--;
				}
				this.matrix[this.randy + 2][this.randx] = 4;
				break;
			case 2:
				if (this.randx == xsize - 2) {
					i--;
					this.randx--;
				} else {
					this.randx++;
				}
				this.matrix[this.randy][this.randx - 2] = 4;
				break;
			case 3:
				if (this.randy == ysize - 2) {
					i--;
					this.randy--;
				} else {
					this.randy++;
				}
				this.matrix[this.randy - 2][this.randx] = 4;
				break;
			case 4:
				if (this.randx == 1) {
					i--;
					this.randx++;
				} else {
					this.randx--;
				}
				this.matrix[this.randy][this.randx + 2] = 4;
				break;
			}
			this.matrix[this.randy][this.randx] = 4;
			this.direction = 1 + (int) (Math.random() * ((4 - 1) + 1));

			i++;
		}
		this.matrix[this.randy][this.randx] = 3;
		this.matrix[this.y][this.x] = 2;
		return matrix;
		*/
		
		
		/*
		 * oldD: 1,2,3,4
		 * 		up,right,down,left
		 *  direction: 1,2,3
		 *  	forward,right,left
		 */
		
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
			this.matrix[this.randy][this.randx] = 4;
			this.direction = 1 + (int)(Math.random() * ((3 - 1) + 1));

			i++;
		}
		this.matrix[this.randy][this.randx] = 3;
		this.matrix[this.y][this.x] = 2;
				
		
		
		length = (int) Math.sqrt((double)((y-randy)*(y-randy)+(x-randx)*(x-randx)));
		
		
		}
		return matrix;
		
	}
	
}
