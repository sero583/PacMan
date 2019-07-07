package sero583.pacman;

import java.awt.Color;

public class Path extends Vector2 implements LevelObject {
	//private int id;
	private Color color;
	
	public Path(Color color, int x, int y) {
		super(x, y);
		//this.id = id;
		this.color = color;
	}
	
	
	/*public int getId() {
		return this.id;
	}*/
	
	public Color getColor() {
		return this.color;
	}

	@Override
	public boolean canCollide() {
		return true;
	}

	@Override
	public boolean harmfulCollision() {
		return false;
	}
}