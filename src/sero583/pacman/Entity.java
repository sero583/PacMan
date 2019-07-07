package sero583.pacman;

import java.awt.Color;

public abstract class Entity extends Vector2 implements LevelObject {
	private int id;
	private Color color;
	
	public Entity(int id, Color color, int x, int y) {
		super(x, y);
		this.id = id;
		this.color = color;
	}
	
	
	public int getId() {
		return this.id;
	}
	
	public Color getColor() {
		return this.color;
	}
}
