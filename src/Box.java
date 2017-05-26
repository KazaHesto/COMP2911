import java.io.Serializable;

public class Box implements Serializable {
	// declaring variables
	
	// an int to represent the box's row
	private int row;
	// an int to represent the box's column
	private int column;

	/**
	 * constructor
	 * 
	 * @param 	row			the box's row
	 * @param 	column		the box's column
	 * @return	-
	 * @throws	-
	 */
	public Box(int row, int column) {
		super();
		// initialising the box's row
		this.row = row;
		// initialising the box's column
		this.column = column;
	}

	/**
	 * alternate constructor
	 * 
	 * @param 	box		a Box instance
	 * @return	-
	 * @throws	-
	 */
	public Box(Box box) {
		super();
		// initialising the box's row
		this.row = box.getRow();
		// initialising the box's column
		this.column = box.getColumn();
	}

	/**
	 * getter: getRow() -> gets a box's row
	 * 
	 * @param	-
	 * @return	this.row	the box's row
	 * @throws	-
	 */
	public int getRow() {
		// returns the box's row
		return this.row;
	}

	/**
	 * getter: getColumn() -> gets a box's column
	 * 
	 * @param	-
	 * @return	this.column		the box's column
	 * @throws	-
	 */
	public int getColumn() {
		// returns the box's column
		return this.column;
	}

	/**
	 * setter: setPosition() -> set's a box's position
	 * 
	 * @param 	row			an int representing the box's row
	 * @param 	column		an int representing the box's column
	 * @return	-
	 * @throws	-
	 */
	public void setPosition(int row, int column) {
		// setting the box's row
		this.row = row;
		// setting the box's column
		this.column = column;
	}
}
