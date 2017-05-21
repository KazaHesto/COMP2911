import java.util.Random;

public class LevelGenerator2 {

	private final int WALL = 0;
	private final int PLAYER = 1;
	private final int BOX = 2;
	private final int CROSS = 3;
	private final int EMPTY = 4;

	public int[][] generateLevel(int rows, int columns) {
		Random rand = new Random();
		int[][] level = new int[rows][columns];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				if (rand.nextInt(100) % 2 == 0) {
					level[i][j] = WALL;
				} else {
					level[i][j] = EMPTY;
				}
			}
		}
		level[0][0] = CROSS;
		printMatrix(level);
		return level;
	}
	
	private void printMatrix(int[][] matrix) {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				System.out.print(matrix[i][j] + " ");
			}
			System.out.println();
		}
	}
}
