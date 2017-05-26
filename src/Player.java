import java.io.Serializable;

public class Player implements Serializable{
	// declaring variables
	
	// the player's row
	private int row;
	// the player's column
	private int column;
	// the player's direction
	private int direction;
	// the player's interaction with a box
	private boolean isBox;

	/**
	 * constructor
	 * 
	 * @param 	row			row to set the player at
	 * @param 	column		column to set the player at
	 * @return	-
	 * @throws	-
	 */
	public Player(int row, int column) {
		super();
		// initialise the player's row
		this.row = row;
		// initialise the player's column
		this.column = column;
		this.direction = 180;
	}
	
	/**
	 *	alternative constructor
	 *
	 * @param 	coordinates		coordinate of the player
	 * @return	-
	 * @throws	-
	 */
	public Player(Coordinate coordinates) {
		super();
		// set the player's coordinates
		this.row = coordinates.getRow();
		this.column = coordinates.getColumn();
		this.direction = 180;
	}
	
	/**
	 * another alternative constructor
	 * 
	 * @param 	player		a Player instance
	 * @return	-
	 * @throws	-
	 */
	public Player(Player player) {
		super();
		// set the player's position
		this.row = player.getRow();
		this.column = player.getColumn();
		this.direction = player.getDirection();
	}
	
	/**
	 * getter: getRow() -> gets the row the player is on
	 * 
	 * @param	-
	 * @return	this.row	the row the player is on
	 * @throws	-
	 */
	public int getRow() {
		// return the player's row
		return this.row;
	}
	
	/**
	 * getter: getColumn() -> gets the column the player is on 
	 * 
	 * @param	-
	 * @return	this.column		the column the player is on
	 * @throws	-
	 */
	public int getColumn() {
		// return the player's column
		return this.column;
	}
	
	/**
	 * setter: setPosition() -> sets the position fo the player based on row and column
	 * 
	 * @param 	row			row you want the player to be set at 
	 * @param 	column		column you want the player to be set at
	 * @return	-
	 * @throws	-
	 */
	public void setPosition(int row, int column) {
		// set the player's row and column (i.e position)
		this.row = row;
		this.column = column;
	}
	
	/**
	 * getter: getDirection() -> gets the direction of the player
	 * 
	 * @param	-
	 * @return	this.direction	returns the direction of the player
	 * @throws	-
	 */
	public int getDirection(){
		// return the player's directoin
		return this.direction;
	}
	
	/**
	 * setter: setDirection() -> set the direction of the player
	 * 
	 * @param 	direction	direction of the player
	 * @return	-
	 * @throws	-
	 */
	public void setDirection(int direction) {
		// sets the direction of the player
		this.direction = direction;
	}
	
	/**
	 * getter: getIsBox() -> gets if the the player is colliding with a box used for animation
	 * 
	 * @param	-
	 * @return	this.isBox		if the player is colliding with the box or not
	 * @throws	-
	 */
	public boolean getIsBox(){
		// return required variable
		return this.isBox;
	}
	
	/**
	 * setter: setIsBox() -> sets if the player is colliding with box
	 * 
	 * @param 	isBox	the boolean that checks if the player is colliding with box
	 * @return	-
	 * @throws	-
	 */
	public void setIsBox(Boolean isBox){
		// sets required variable within this class
		this.isBox = isBox; 
	}
}
