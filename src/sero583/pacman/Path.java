package sero583.pacman;

import java.awt.Color;

public class Path extends Vector2 implements LevelObject {
	private Color color;
	private boolean coin = true;
	
	public Path(Color color, int x, int y) {
		super(x, y);
		this.color = color;
	}
	
	public boolean hasCoin() {
		return this.coin;
	}
	
	public void setCoin(boolean state) {
		this.coin = state;
	}
	
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

	@Override
	public boolean hasColor() {
		return true;
	}
}