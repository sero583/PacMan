package sero583.pacman;

public class Vector2 {
	private double x;
	private double y;
	
	public Vector2(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public double getX() {
		return this.x;
	}
	
	public double getY() {
		return this.y;
	}
	
	public void setX(double x) {
		this.x = x;
	}
	
	public void setY(double y) {
		this.y = y;
	}
	
	public Vector2 add(double x) {
		return this.add(x, 0);
	}
	
	public Vector2 add(double x, double y) {
		return new Vector2(this.x + x, this.y + y);
	}
	
	public Vector2 substract(double x) {
		return this.substract(x, 0);
	}
	
	public Vector2 substract(double x, double y) {
		return new Vector2(this.x - x, this.y - y);
	}
	
	public boolean isValidVector() {
		return this.x != -1 && this.y != -1;
	}
	
	@Override
	public boolean equals(Object anotherOne) {
		if(anotherOne instanceof Vector2) {
			Vector2 anotherVec = (Vector2) anotherOne;
			return this.x == anotherVec.getX() && this.y == anotherVec.getY();
		}
		return false;
	}
}
