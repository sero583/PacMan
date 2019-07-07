package sero583.pacman;

import sero583.pacman.launcher.BaseLauncher;

public class Main {
	private int score = 0;
	public static Main instance;
	private Level level;
	public BaseLauncher launcher;
	
	public Main(BaseLauncher launcher) {
		if(Main.instance!=null) {
			launcher.getLogger().error("Another instance is already running.");
			return;
		}
		launcher.getLogger().info("Starting PacMan...");
		Main.instance = this;
		this.launcher = launcher;
		this.level = new Level();
		launcher.getLogger().info("Started PacMan!");
	}
	
	public void addScore(int score) {
		this.score += score;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	
	public int getScore() {
		return this.score;
	}
	
	public Level getLevel() {
		return this.level;
	}
	
	public void handleInput(String input) {
		input = input.toLowerCase();
		
		switch(input) {
			case "w":
				this.level.getPlayer().setVelY(Level.DEFAULT_VELOCITY);
			break;
			case "s":
				this.level.getPlayer().setVelY(-Level.DEFAULT_VELOCITY);
			break;
			case "a":
				this.level.getPlayer().setVelX(-Level.DEFAULT_VELOCITY);
			break;
			case "d":
				this.level.getPlayer().setVelX(Level.DEFAULT_VELOCITY);
			break;
		}
	}
}
