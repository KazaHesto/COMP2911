import java.io.Serializable;

public class Player implements Serializable{
	private int row;
	private int column;
	private int direction;
	private boolean isBox;

	/**
	 * constructor for the player
	 * @param row -> row to set the player at
	 * @param column -> column to set the player at
	 */
	public Player(int row, int column) {
		super();
		this.row = row;
		this.column = column;
	}
	
	/**
	 *Constructor set player at a specific coordinate
	 * @param coordinates -> coordinate of the player
	 */

	public Player(Coordinate coordinates) {
		super();
		this.row = coordinates.getRow();
		this.column = coordinates.getColumn();
	}
	
	/**
	 * Constructor to set player based on row and column from previous player object
	 * @param player
	 */
	public Player(Player player) {
		super();
		this.row = player.getRow();
		this.column = player.getColumn();
	}
	/**
	 * get the row the player is on
	 * @return -> the row the player is on
	 */
	public int getRow() {
		return this.row;
	}
	
	/**
	 * get the column the player is on 
	 * @return -> the column the player is on
	 */

	public int getColumn() {
		return this.column;
	}
	
	/**
	 * set the position fo the player based on row and column
	 * @param row -> row you want the player to be set at 
	 * @param column -> column you want the player to be set at
	 */

	public void setPosition(int row, int column) {
		this.row = row;
		this.column = column;
	}
	
	/**
	 * getter for the direction of the player
	 * @return -> returns the direction of the player
	 */
	public int getDirection(){
		return this.direction;
	}
	
	/**
	 * set the direction of the player
	 * @param direction -> direction of the player
	 */

	public void setDirection(int direction) {
		this.direction = direction;
	}
	
	/**
	 * get if the the player is colliding with a box used for animmation
	 * @return -> if the player is colliding with the box or not
	 */
	
	public boolean getIsBox(){
		return this.isBox;
	}
	
	/**
	 * set if the player is colliding with box
	 * @param isBox -> the boolean that checks if the player is colliding with box
	 */
	public void setIsBox(Boolean isBox){
		this.isBox = isBox; 
	}
}
