import java.io.Serializable;

public class Box implements Serializable {
	private int row;
	private int column;

	public Box(int row, int column) {
		super();
		this.row = row;
		this.column = column;
	}

	public Box(Box box) {
		super();
		this.row = box.getRow();
		this.column = box.getColumn();
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
}
