package tdd.training.session1;

public class Coordinate {
	private int x;
	private int y;
	
	Coordinate(int x, int y){
		this.x=x;
		this.y=y;
	}
	
	Coordinate(){
		this(0,0);
	}
	
	public void addX(){
		x++;
	}
	
	public void addY(){
		y++;
	}
	
	public void subX(){
		x--;
	}
	
	public void subY(){
		y--;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public void setX(int x){
		this.x = x;
	}
	
	public void setY(int y){
		this.y = y;
	}
	
	public String toString(){
		return "(" + x +"," + y +")";
	}
	
	public boolean equals(Object o){
		Coordinate other = (Coordinate)o;
		return other.getX() == getX() && other.getY() == getY();
	}
}
