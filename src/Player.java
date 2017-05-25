
public class Player implements java.io.Serializable{
	private int row;
	private int column;
	private int direction;
	private boolean isBox;

	public Player(int row, int column) {
		super();
		this.row = row;
		this.column = column;
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
	
	public void setIsBox(Boolean isBox){
		this.isBox = isBox; 
	}

	public int getdirection() {
		return this.direction;
	}
	
	public boolean getIsBox(){
		return this.isBox;
	}
}
