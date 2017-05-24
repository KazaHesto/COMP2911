import java.util.Random;

public class LevelGenerator {

	private enum Side {
		TOP, BOTTOM, LEFT, RIGHT
	}

	private int[][][] templates = {
			{{5,5,5,5,5},
			{5,4,4,4,5},
			{5,4,4,4,5},
			{5,4,4,4,5},
			{5,5,5,5,5}},
			
			{{5,5,5,5,5},
			{5,1,4,4,5},
			{5,4,4,4,5},
			{5,4,4,4,5},
			{5,5,5,5,5}},
			
			{{5,5,5,4,4},
			{5,1,1,4,4},
			{5,4,4,4,5},
			{5,4,4,4,5},
			{5,5,5,5,5}},
			
			{{5,5,5,5,5},
			{5,1,1,1,5},
			{5,4,4,4,5},
			{5,4,4,4,5},
			{5,5,5,5,5}},
			
			{{5,5,5,5,5},
			{5,1,1,1,5},
			{5,1,4,4,5},
			{5,1,4,4,5},
			{5,5,5,5,5}},
			
			{{5,5,4,5,5},
			{5,1,4,4,5},
			{4,4,4,4,5},
			{5,4,4,1,5},
			{5,5,5,5,5}},
			
			{{5,5,5,5,5},
			{5,1,4,4,5},
			{4,4,4,4,5},
			{5,1,4,4,5},
			{5,5,5,5,5}},
			
			{{5,5,4,5,5},
			{5,1,4,4,5},
			{4,4,4,4,5},
			{5,1,4,1,5},
			{5,5,4,5,5}},
			
			{{5,5,4,5,5},
			{5,1,4,1,5},
			{4,4,4,4,4},
			{5,1,4,1,5},
			{5,5,4,5,5}},
			
			{{5,5,4,5,5},
			{5,1,4,1,5},
			{5,1,4,4,4},
			{5,1,1,1,5},
			{5,5,5,5,5}},
			
			{{5,5,5,5,5},
			{5,1,1,1,5},
			{4,4,4,4,4},
			{5,1,1,1,5},
			{5,5,5,5,5}},
			
			{{5,5,5,5,5},
			{5,4,4,4,4},
			{5,4,1,4,4},
			{5,4,4,4,5},
			{5,5,5,5,5}},
			
			{{5,5,5,5,5},
			{5,1,1,1,5},
			{5,1,1,1,5},
			{5,1,1,1,5},
			{5,5,5,5,5}},
			
			{{5,5,5,5,5},
			{5,1,1,1,5},
			{5,1,4,4,5},
			{4,4,4,4,5},
			{4,4,5,5,5}},
			
			{{5,4,5,4,5},
			{5,4,4,4,5},
			{5,1,4,1,5},
			{5,4,4,4,5},
			{5,4,5,4,5}},
			
			{{5,5,5,5,5},
			{5,1,1,1,5},
			{5,1,1,1,5},
			{5,4,4,4,5},
			{5,4,4,4,5}},
			
			{{5,5,5,5,5},
			{5,1,1,1,5},
			{4,4,1,4,4},
			{5,4,4,4,5},
			{5,4,4,5,5}}};

	/**
	 * Generates level
	 * 
	 * @param rows
	 *            Number of rows
	 * @pre rows % 3 == 0
	 * @param columns
	 *            Number of columns
	 * @pre columns % 3 == 0
	 * @return Returns a level grid
	 */
	public int[][] generateLevel(int rows, int columns) {
		int[][] level = new int[rows + 2][columns + 2];
		for (int i = 0; i < level.length; i++) {
			for (int j = 0; j < level[i].length; j++) {
				if (i == 0 || i == level.length - 1 || j == 0 || j == level[i].length - 1) {
					level[i][j] = Constants.WALL;
				}
			}
		}
		int[][] template = null;

		for (int i = rows / 3; i > 0; i--) {
			for (int j = columns / 3; j > 0; j--) {
				template = getRandomTemplate();
				while (!addTemplate(level, template)) {
					template = getRandomTemplate();
				}
			}
		}
		// Temporary so that checkwin doesn't keep popping up when moving in the
		// level.
		level[0][0] = Constants.CROSS;
		return level;
	}

	/**
	 * Adds specified template to level
	 * 
	 * @param level
	 *            Level to ad template to.
	 * @param template
	 *            Template to add to level.
	 * @return true if successful, false otherwise.
	 */
	private boolean addTemplate(int[][] level, int[][] template) {
		if (template == null) {
			return false;
		}
		if (isCompatible(level, template)) {
			for (int i = 0; i < level.length; i++) {
				for (int j = 0; j < level[i].length; j++) {
					if (level[i][j] == 0) {
						for (int k = 0; k < 3; k++) {
							for (int l = 0; l < 3; l++) {
								int temp = template[k + 1][l + 1];
								level[i + k][j + l] = temp;
							}
						}
						return true;
					}
				}
			}
		}
		return false;
	}

	private boolean isCompatible(int[][] level, int[][] template) {
		// TODO Auto-generated method stub
		return true;
	}

	private int[][] getRandomTemplate() {
		Random rand = new Random();
		int[][] template = copyMatrix(this.templates[rand.nextInt(16)]);
		int numFlips = rand.nextInt(10);
		int numRotates = rand.nextInt(10);
		while (numFlips != 0 || numRotates != 0) {
			if (numFlips != 0) {
				template = flipMatrix(template);
				numFlips--;
			}
			if (numRotates != 0) {
				template = rotateMatrix(template);
				numRotates--;
			}
		}
		return template;
	}

	private int[][] copyMatrix(int[][] original) {
		int[][] copy = new int[original.length][original[1].length];
		for (int i = 0; i < original.length; i++) {
			for (int j = 0; j < original[i].length; j++) {
				copy[i][j] = original[i][j];
			}
		}
		return copy;
	}

	private int[][] flipMatrix(int[][] original) {
		int[][] flipped = new int[original.length][original[1].length];
		for (int i = 0; i < original.length; i++) {
			flipped[i] = original[original.length - 1 - i].clone();
		}
		return flipped;
	}

	private int[][] rotateMatrix(int[][] original) {
		int[][] rotated = new int[original.length][original.length];
		for (int i = 0; i < original.length; i++) {
			for (int j = 0; j < original.length; j++) {
				rotated[j][i] = original[i][original.length - 1 - j];
			}
		}
		return rotated;
	}

	private int[] getEdge(int[][] matrix, Side side) {
		switch (side) {
		case TOP:
			return matrix[0].clone();
		case BOTTOM:
			return matrix[matrix.length - 1].clone();
		case LEFT:
			int[] edge1 = new int[matrix.length];
			for (int i = 0; i < matrix.length; i++) {
				edge1[i] = matrix[i][0];
			}
			return edge1;
		case RIGHT:
			int[] edge2 = new int[matrix.length];
			for (int i = 0; i < matrix.length; i++) {
				edge2[i] = matrix[i][matrix[i].length];
			}
			return edge2;
		}
		return null;
	}

	private boolean isMatching(int[] edge1, int[] edge2) {
		if (edge1 == null || edge2 == null) {
			return true;
		}
		for (int i = 0; i < edge1.length; i++) {
			if (edge1[i] != edge2[1]) {
				if (edge1[i] != 5 && edge2[1] != 5) {
					for (int j = 0; j < edge1.length; j++) {
						System.out.println(edge1[i]);
					}
					for (int j = 0; j < edge2.length; j++) {
						System.out.println(edge2[i]);
					}
					return false;
				}
			}
		}
		return true;
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
