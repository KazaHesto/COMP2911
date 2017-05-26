
public class Coordinate {
	// declaring variables
	
	// the row component of the Coordinate class
	private int row;
	// the column component of the Coordinate class
	private int column;

	/**
	 * constructor
	 * 
	 * @param 	row			the row component to be set
	 * @param 	column		the column component to be set
	 * @return	-
	 * @throws	-
	 */
	public Coordinate(int row, int column) {
		super();
		// initialising the row component of this class
		this.row = row;
		// initialising the column component of this class
		this.column = column;
	}

	/**
	 * getter: getRow() -> returns the row of the Object
	 * @param	-
	 * @return	this.row		returns the row coordinate
	 * @throws	-
	 */
	public int getRow() {
		// return the row of this class
		return this.row;
	}

	/**
	 * setter: setRow() -> sets the row of the Object
	 * 
	 * @param 	row		the row coordinate to be set
	 * @return	-
	 * @throws	-
	 */
	public void setRow(int row) {
		// sets the row
		this.row = row;
	}
	
	/**
	 * getter: getColumn() -> returns the column of the Object
	 * @param	-
	 * @return	this.column		returns the column coordinate
	 * @throws	-
	 */
	public int getColumn() {
		// return the column of this class
		return this.column;
	}

	/**
	 * setter: setColumn() -> sets the column of the Object
	 * 
	 * @param 	column		the column coordinate to be set
	 * @return	-
	 * @throws	-
	 */
	public void setColumn(int column) {
		// sets the column
		this.column = column;
	}

	/**
	 * Override -> overrides the usual equals() method
	 */
	@Override
	public boolean equals(Object obj) {
		// if the Object is empty, it isn't equal to anything
		if (obj == null) {
			// return failure
			return false;
		}
		// finding the coordinates of the Object
		Coordinate coordinate = (Coordinate) obj;
		// if the coordinates of the two objects are equal, the objects are equal
		if (coordinate.getRow() == this.row && coordinate.getColumn() == this.column) {
			// return success
			return true;
		}
		// return failure
		return false;
	}
}
