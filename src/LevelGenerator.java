import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class LevelGenerator {

	private int[][][] templates = {
			// Tweaked (added conditions) because this made unreachable holes in the wall
			{{5,5,5,5,5},
			{4,4,4,4,5},
			{4,4,4,4,5},
			{5,4,4,4,5},
			{5,5,5,5,5}},
			
			// Tweaked (added conditions) because this made unreachable holes in the wall
			{{5,5,4,4,5},
			{5,1,4,4,5},
			{5,4,4,4,5},
			{5,4,4,4,5},
			{5,5,5,5,5}},
			
			{{5,5,5,4,4},
			{5,1,1,4,4},
			{5,4,4,4,5},
			{5,4,4,4,5},
			{5,5,5,5,5}},
			
			// Tweaked (added conditions) because this made unreachable holes in the wall
			{{5,5,5,5,5},
			{5,1,1,1,5},
			{4,4,4,4,5},
			{5,4,4,4,5},
			{5,5,5,5,5}},
			
			// Tweaked (added conditions) because this made unreachable holes in the wall
			{{5,5,5,5,5},
			{5,1,1,1,5},
			{5,1,4,4,5},
			{5,1,4,4,5},
			{5,5,4,4,5}},
			
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

	private int[][] level;
	private int[] topConditions;
	private int[] leftConditions;
	private ArrayList<Coordinate> boxes;

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
		level = new int[rows + 2][columns + 2];
		topConditions = new int[columns + 2];
		leftConditions = new int[rows + 2];
		boxes = new ArrayList<Coordinate>();
		// Creates a border of wall around the level
		for (int i = 0; i < level.length; i++) {
			for (int j = 0; j < level[i].length; j++) {
				if (i == 0 || i == level.length - 1 || j == 0 || j == level[i].length - 1) {
					level[i][j] = Resources.WALL;
				}
			}
		}
		int[][] template = null;

		// Each template is 3x3, so there are (row/3)*(column/3) templates to
		// complete one level
		for (int i = rows / 3; i > 0; i--) {
			for (int j = columns / 3; j > 0; j--) {
				template = getRandomTemplate();
				while (!addTemplate(level, template)) {
					template = getRandomTemplate();
				}
			}
		}
		if (isShallow()) {
			level = generateLevel(rows, columns);
		}

		// This is given the size excluding the outer walls because the
		// pathtracer doesn't take it into account
		PathTracer path = new PathTracer(columns, rows);
		int[][] pathedMatrix = path.makePath(columns, rows);

		mergeMatrix(pathedMatrix);

		return level;
	}

	public Coordinate getPlayer() {
		return getFirstFloor();
	}

	public ArrayList<Coordinate> getBox() {
		return boxes;
	}

	private void mergeMatrix(int[][] pathedMatrix) {
		for (int i = 0; i < pathedMatrix.length; i++) {
			for (int j = 0; j < pathedMatrix[i].length; j++) {
				if (pathedMatrix[i][j] != Resources.WALL) {
					// the coordinates below have + 1 because pathtracer doesn't
					// take into account the outer wall
					this.level[i + 1][j + 1] = Resources.FLOOR;
					if (pathedMatrix[i][j] == Resources.BOX) {
						boxes.add(new Coordinate(i + 1, j + 1));
					}
					if (pathedMatrix[i][j] == Resources.CROSS) {
						this.level[i + 1][j + 1] = Resources.CROSS;
					}
				}
			}
		}
	}

	/**
	 * Returns the first floor tile's coordinates
	 * 
	 * @return first floor tile's coordinates, null if not found
	 */
	private Coordinate getFirstFloor() {
		for (int i = 0; i < level.length; i++) {
			for (int j = 0; j < level[i].length; j++) {
				if (level[i][j] == Resources.FLOOR) {
					Coordinate coord = new Coordinate(i, j);
					return coord;
				}
			}
		}
		return null;
	}

	private boolean isShallow() {
		Stack<Coordinate> toDo = new Stack<Coordinate>();
		ArrayList<Coordinate> seen = new ArrayList<Coordinate>();

		toDo.add(getFirstFloor());
		Coordinate curr = null;
		while (!toDo.isEmpty()) {
			curr = toDo.pop();
			seen.add(curr);
			for (Coordinate neighbour : getFloorNeighbours(curr)) {
				if (!seen.contains(neighbour)) {
					toDo.push(neighbour);
				}
			}
		}
		// hopefully prevent cases where over half the map is inaccessible
		if (seen.size() < (this.level.length * this.level[1].length / 2)) {
			return true;
		}
		return false;
	}

	private ArrayList<Coordinate> getFloorNeighbours(Coordinate coord) {
		ArrayList<Coordinate> neighbourList = new ArrayList<Coordinate>();
		int row = coord.getRow();
		int column = coord.getColumn();

		if (this.level[row - 1][column] == Resources.FLOOR) {
			Coordinate neighbour = new Coordinate(row - 1, column);
			neighbourList.add(neighbour);
		}
		if (this.level[row + 1][column] == Resources.FLOOR) {
			Coordinate neighbour = new Coordinate(row + 1, column);
			neighbourList.add(neighbour);
		}
		if (this.level[row][column - 1] == Resources.FLOOR) {
			Coordinate neighbour = new Coordinate(row, column - 1);
			neighbourList.add(neighbour);
		}
		if (this.level[row][column + 1] == Resources.FLOOR) {
			Coordinate neighbour = new Coordinate(row, column + 1);
			neighbourList.add(neighbour);
		}
		return neighbourList;
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
			for (int i = 1; i <= level.length - 4; i += 3) {
				for (int j = 1; j <= level[i].length - 4; j += 3) {
					if (level[i][j] == 0) {
						// Add template to level
						for (int k = 0; k < 3; k++) {
							for (int l = 0; l < 3; l++) {
								int temp = template[k + 1][l + 1];
								level[i + k][j + l] = temp;
							}
						}
						// Add template to conditions
						for (int k = 0; k < 5; k++) {
							topConditions[j - 1 + k] = template[4][k];
							leftConditions[i - 1 + k] = template[k][4];
						}
						return true;
					}
				}
			}
		}
		return false;
	}

	private boolean isCompatible(int[][] level, int[][] template) {
		for (int i = 1; i <= level.length - 4; i += 3) {
			for (int j = 1; j <= level[i].length - 4; j += 3) {
				if (level[i][j] == 0) {
					for (int k = 0; k < 3; k++) {
						// Checking if existing level meets template left
						// requirements
						if (level[i + k][j - 1] != template[k + 1][0] && template[k + 1][0] != 5) {
							return false;
						}
						// Checking if template meets existing left requirements
						if (leftConditions[i + k] != template[k + 1][1] && leftConditions[i + k] != 5
								&& leftConditions[i + k] != 0) {
							return false;
						}
						// Checking if existing level meets template left
						// requirements
						if (level[i - 1][j + k] != template[0][k + 1] && template[0][k + 1] != 5) {
							return false;
						}
						// Checking if template meets existing left requirements
						if (topConditions[j + k] != template[1][k + 1] && topConditions[j + k] != 5
								&& topConditions[j + k] != 0) {
							return false;
						}
						// Checks right conditions if the right-most column
						if (j == level[i].length - 4 && template[k + 1][4] != Resources.WALL
								&& template[k + 1][0] != 5) {
							return false;
						}
						// Checks top conditions if the bottom-most row
						if (i == level.length - 4 && template[4][k + 1] != Resources.WALL && template[0][k + 1] != 5) {
							return false;
						}
					}
					return true;
				}
			}
		}
		return false;
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

	private void printMatrix(int[][] matrix) {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				System.out.print(matrix[i][j] + " ");
			}
			System.out.println();
		}
	}
}
