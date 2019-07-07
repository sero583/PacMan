package sero583.pacman.launcher;

import sero583.pacman.Level;

public abstract class BaseLauncher {
	private Logger logger;
	private ScreenInfo screenInfo;
	
	public BaseLauncher(Logger logger) {
		this.logger = logger;
	}
	
	public Logger getLogger() {
		return this.logger;
	}
	
	public void setScreenInfo(ScreenInfo screenInfo) {
		this.screenInfo = screenInfo;
	}
	
	public ScreenInfo getScreenInfo() {
		return this.screenInfo;
	}
	
	public abstract void startRenderer();
}
