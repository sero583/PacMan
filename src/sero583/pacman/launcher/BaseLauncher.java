package sero583.pacman.launcher;

import sero583.pacman.Level;

public abstract class BaseLauncher {
	private Logger logger;
	
	public BaseLauncher(Logger logger) {
		this.logger = logger;
	}
	
	public Logger getLogger() {
		return this.logger;
	}
	
	public void onUpdate(Level level) {
		
	}
}
