package sero583.pacman;

import java.awt.Color;

public interface LevelObject {
	public boolean canCollide();
	public boolean harmfulCollision();
	public double getX(); public double getY(); public boolean isValidVector(); //just so we can use in syntax
	
	public default Vector2 toVector2() {
		return new Vector2(this.getX(), this.getY());
	}
	
	public boolean hasColor();
	
	public Color getColor();
}
