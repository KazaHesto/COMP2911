
public class Player {
	private int row;
	private int column;
	private int direction;
	private int prevRow;
	private int prevColumn;

	public Player(int row, int column) {
		super();
		this.row = row;
		this.column = column;
		this.prevRow = row;
		this.prevColumn = column;
	}

	public int getRow() {
		return this.row;
	}

	public int getColumn() {
		return this.column;
	}
	
	public int getPrevRow(){
		return this.prevRow;
	}
	
	public int getPrevColumn(){
		return this.prevColumn;
	}

	public void setPosition(int row, int column) {
		this.row = row;
		this.column = column;
	}
	
	public void setPrevPosition(int prevRow, int prevColumn){
		this.prevRow = prevRow;
		this.prevColumn = prevColumn;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public int getdirection() {
		return this.direction;
	}
}
