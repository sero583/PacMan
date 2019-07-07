package sero583.pacman;

import java.awt.Color;
import java.awt.event.KeyEvent;

public class Player extends Entity {
	private String name;
	
	public Player(String name, int id, Color color) {
		super(id, color, -1, -1);
		
		this.name = name;
	}
	
	public Player(String name, int id, Color color, int x, int z) {
		super(id, color, x, z);
		
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}

	@Override
	public boolean canCollide() {
		return false;
	}

	@Override
	public boolean harmfulCollision() {
		return false;
	}
}
