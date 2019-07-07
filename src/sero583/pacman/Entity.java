package sero583.pacman;

import java.awt.Color;

public abstract class Entity extends Vector2 {
	
	private int id;
	private Color color;
	private double velX, velY = 0;
	
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
	
	public void setVelX(double x) {
		this.velX = x;
	}
	
	public void setVelY(double y) {
		this.velY = y;
	}
	
	public void addVelX(double x) {
		this.velX += x;
	}
	
	public void addVelY(double y) {
		this.velY += y;
	}
	public void substractVelX(double x) {
		this.velX -= x;
	}
	
	public void substractVelY(double y) {
		this.velY -= y;
	}
	
	public double getVelX() {
		return this.velX;
	}
	
	public double getVelY() {
		return this.velY;
	}


	public boolean canCollide() {
		return false;
	}


	public boolean harmfulCollision() {
		return false;
	}
}
