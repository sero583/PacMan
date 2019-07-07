package sero583.pacman;

public interface LevelObject {
	public boolean canCollide();
	public boolean harmfulCollision();
	public int getX(); public int getY(); public boolean isValidVector(); //just so we can use in syntax
	
	public default Vector2 toVector2() {
		return new Vector2(this.getX(), this.getY());
	}
	
	public default void onCollide(LevelObject hitObject) {
		
	}
}
