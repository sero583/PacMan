package sero583.pacman;

import java.awt.Color;

public class Bot extends Entity {
	public Bot(int id, Color color, double x, double y) {
		super(id, color, x, y);
	}

	@Override
	public boolean canCollide() {
		return false;
	}

	@Override
	public boolean harmfulCollision() {
		return true;
	}
}
