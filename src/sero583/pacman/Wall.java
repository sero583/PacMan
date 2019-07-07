package sero583.pacman;

import java.awt.Color;

public class Wall extends Vector2 implements LevelObject {
	//private int id;
	private Color color;
	
	public Wall(Color color, double x, double y) {
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
		return false;
	}

	@Override
	public boolean harmfulCollision() {
		return false;
	}


	@Override
	public boolean hasColor() {
		return true;
	}
}
