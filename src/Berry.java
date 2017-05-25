
public class Berry implements java.io.Serializable {
	private int row;
	private int column;
	
	public Berry(int row, int column){
		super();
		this.row = row;
		this.column = column;
	}
	
	public Berry(Berry berry) {
		super();
		this.row = berry.getRow();
		this.column = berry.getColumn();
	}
	
	public int getRow(){
		return this.row;
	}
	
	public int getColumn(){
		return this.column;
	}
	
	public void setPosition(int row, int column){
		this.row = row;
		this.column = column;
	}
}
