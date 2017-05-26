import java.io.Serializable;

public class Berry implements Serializable {
	// declaring variables
	
	// the row in which the berry is located
	private int row;
	// the columnn in which the berry is located
	private int column;

	// constructor
	/**
	 * constructor for Berry class
	 * 
	 * @param 	row			an int that represents the berry's row
	 * @param 	column		an int that represents the berry's column
	 * @return	-
	 * @throws	-
	 */
	public Berry(int row, int column) {
		super();
		// initialising the berry's row
		this.row = row;
		// initialising the berry's column
		this.column = column;
	}

	// alternate constructor
	/**
	 * alternate constructor for Berry class
	 * 
	 * @param	berry		a Berry instance
	 * @return	-
	 * @throws	-
	 */
	public Berry(Berry berry) {
		super();
		// initialising the berry's row
		this.row = berry.getRow();
		// initialising the berry's column
		this.column = berry.getColumn();
	}

	/**
	 * getter: getRow() -> returns the berry's row
	 * 
	 * @param	-
	 * @return	this.row	the berry's row
	 * @throws	-
	 */
	public int getRow() {
		// returns the berry's row
		return this.row;
	}

	/**
	 * getter: getColumn() -> returns the berry's column
	 * 
	 * @param	-
	 * @return	this.column		the berry's column
	 * @throws	-
	 */
	public int getColumn() {
		// returns the berry's column
		return this.column;
	}

	/**
	 * setter: setPosition() -> sets the berry's position
	 * 
	 * @param 	row			the row in which the berry should be
	 * @param 	column		the column in which the berry should be
	 * @return	-
	 * @throws	-
	 */
	public void setPosition(int row, int column) {
		// sets this berry's row variable
		this.row = row;
		// sets this berry's column variable
		this.column = column;
	}
}
