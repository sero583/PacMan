package sero583.pacman.launcher;

import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class Logger {
	public void log(String text) {
		System.out.println("[" + this.getTimeString() + "] " + text);
	}
	
	public void info(String text) {
		this.log("INFO: " + text); 
	}
	
	public void warning(String text) {
		this.log("WARNING: " + text); 
	}
	
	public void error(String text) {
		this.log("ERROR: " + text); 
	}
	
	public String getTimeString() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy/HH:mm:ss.SSS");
		return sdf.format(new Date());
	}
}
