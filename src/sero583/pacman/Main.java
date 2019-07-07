package sero583.pacman;

import sero583.pacman.launcher.BaseLauncher;

public class Main {
	public static Main instance;
	protected Level level;
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
}
