
public class Coordinate {

	private int row;
	private int column;

	public Coordinate(int row, int column) {
		super();
		this.row = row;
		this.column = column;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		Coordinate coordinate = (Coordinate) obj;
		if (coordinate.getRow() == this.row && coordinate.getColumn() == this.column) {
			return true;
		}
		return false;
	}
}
