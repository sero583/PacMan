package sero583.pacman;

import java.awt.Color;

public abstract class Entity extends Vector2 implements LevelObject {
	private int id;
	private Color color;
	private int velX, velY = 0;
	
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
	
	//Movement related
	
	public void setVelX(int x) {
		this.velX = x;
	}
	
	public void setVelY(int y) {
		this.velY = y;
	}
	
	public void addVelX(int x) {
		this.velX += x;
	}
	
	public void addVelY(int y) {
		this.velY += y;
	}
	public void substractVelX(int x) {
		this.velX -= x;
	}
	
	public void substractVelY(int y) {
		this.velY -= y;
	}
	
	public int getVelX() {
		return this.velX;
	}
	
	public int getVelY() {
		return this.velY;
	}
}
