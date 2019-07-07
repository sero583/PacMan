package sero583.pacman;

public class Vector2 {
	private int x;
	private int y;
	
	public Vector2(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
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
