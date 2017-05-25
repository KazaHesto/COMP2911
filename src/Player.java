import java.io.Serializable;

public class Player implements Serializable{
	private int row;
	private int column;
	private int direction;
	private boolean isBox;

	public Player(int row, int column) {
		super();
		this.row = row;
		this.column = column;
	}

	public Player(Coordinate coordinates) {
		super();
		this.row = coordinates.getRow();
		this.column = coordinates.getColumn();
	}

	public Player(Player player) {
		super();
		this.row = player.getRow();
		this.column = player.getColumn();
	}

	public int getRow() {
		return this.row;
	}

	public int getColumn() {
		return this.column;
	}

	public void setPosition(int row, int column) {
		this.row = row;
		this.column = column;
	}
	
	public int getDirection(){
		return this.direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}
	
	public boolean getIsBox(){
		return this.isBox;
	}
	
	public void setIsBox(Boolean isBox){
		this.isBox = isBox; 
	}
}
