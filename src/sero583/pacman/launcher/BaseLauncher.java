package sero583.pacman.launcher;

import javax.swing.JFrame;

import sero583.pacman.Main;

public abstract class BaseLauncher implements Runnable {
	public static final String TITLE = "PacMan by sero583";
	
	protected Main main;
	protected Thread thread;
	private boolean running = false;
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
	
	public abstract boolean hasFPSLimit();
	public abstract int getFPSLimit();
	
	private Long startTime = null;
	protected Integer renderableFrameCount = null;
	private int frameTotal = 0;
	//Display of FPS
	private Long frameTimeRenderStartTime = null;
	
	@Override
	public void run() {
		while(this.isRunning()) {
			if(this.isResizeable()==true) {
				//TODO: Update screen info
				this.onScreenUpdate();
			}
			
			if(this.startTime==null) this.startTime = System.nanoTime();
			//requestFocus();
			//this.main tick(); Dont tick here
			this.render();//displays to the screen unrestricted time
			this.frameTotal++;
			if(this.startTime!=null) {
				long estimation = System.nanoTime() - this.startTime;
				double timeInSeconds = estimation / 1E9;
				
				if(timeInSeconds>=1) {
					this.startTime = null;
					this.renderableFrameCount = this.frameTotal;
					this.frameTotal = 0;
				} else if(this.hasFPSLimit()==true&&this.frameTotal>=this.getFPSLimit()) {
					try {
						Thread.sleep((long) timeInSeconds);
					} catch (InterruptedException e) {
						e.printStackTrace();
					} finally {
						this.renderableFrameCount = this.getFPSLimit();
						this.frameTotal = 0;
						this.startTime = null;
					}
				}
			}
		}
	}
	
	public boolean isRunning() {
		return this.running;
	}
	
	public void setRunning() {
		this.setRunning(true);
	}
	
	public void setRunning(boolean state) {
		this.running = state;
	}
	
	public abstract boolean isResizeable();
	public abstract boolean showFPS();
	
	public abstract void render();
	
	public void onScreenUpdate() {
		
	}
	
	public void startThread() {
		this.thread = new Thread(this);
		this.thread.start();
	}
	
	public void interruptThread() {
		this.thread.interrupt();
	}
}
