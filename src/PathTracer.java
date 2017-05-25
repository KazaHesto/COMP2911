
public class PathTracer {

	private int[][] matrix;
	private int randx;
	private int x;
	private int y;
	private int randy;
	private int pathLength;
	private int direction;
	
	public PathTracer(int xsize,int ysize) {
		this.matrix = new int[ysize][xsize]; 	
		
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
			
		this.randx = 2 + (int)(Math.random() * ((xsize - 4) + 1));
		this.randy = 2 + (int)(Math.random() * ((ysize - 4) + 1));
		this.x = this.randx;
		this.y = this.randy;
		this.pathLength = 15 + (int)(Math.random() * ((20 - 10) + 1));
		this.direction = 1 + (int)(Math.random() * ((4 - 1) + 1));
	//	this.print(xsize,ysize);
	}
	
	public int[][] makePath(int xsize, int ysize) {
		this.matrix[this.randy][this.randx] = 2;
		int i = 0;
		while (i < this.pathLength) {			
			switch (this.direction) {
			case 1: if (this.randy == 1) {
						i--;
						this.randy++;
					} else {
						this.randy--;
					}
					this.matrix[this.randy+2][this.randx] = 4;
					break;
			case 2: if (this.randx == xsize-2) {
						i--;
						this.randx--;
					} else {
						this.randx++;
					}
					this.matrix[this.randy][this.randx-2] = 4;
					break;
			case 3: if (this.randy == ysize-2) {
						i--;
						this.randy--;
					} else {
						this.randy++;
					}
					this.matrix[this.randy-2][this.randx] = 4;
					break;
			case 4: if (this.randx == 1) {
						i--;
						this.randx++;
					} else {
						this.randx--;
					}
					this.matrix[this.randy][this.randx+2] = 4;
					break;
			}
			this.matrix[this.randy][this.randx] = 4;
			this.direction = 1 + (int)(Math.random() * ((4 - 1) + 1));

			i++;
		}
		this.matrix[this.randy][this.randx] = 3;
		this.matrix[this.y][this.x] = 2;
		return matrix;
	}
	
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
