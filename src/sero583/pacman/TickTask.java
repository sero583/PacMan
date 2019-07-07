package sero583.pacman;

import java.util.TimerTask;

public class TickTask extends TimerTask {
	private Level level;
	private int secondCounter = 0;
	private int velocityCounter = 0;
	
	public TickTask(Level level) {
		this.level = level;
	}
	
	@Override
	public void run() {
		if(this.secondCounter==20) {
			this.level.tick();
			this.secondCounter = 0;
		} else this.secondCounter++;
		
		/*if(this.velocityCounter==10) {
			
			this.velocityCounter = 0;
		} else this.velocityCounter++;*/
	}
}
