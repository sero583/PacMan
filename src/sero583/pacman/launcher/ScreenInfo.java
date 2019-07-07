package sero583.pacman.launcher;

public class ScreenInfo {
	private double width;
	private double height;
	
	public ScreenInfo(double width, double height) {
		this.width = width;
		this.height = height;
	}
	
	public void setWidth(double width) {
		this.width = width;
	}
	
	public void setHeight(double height) {
		this.height = height;
	}
	
	public double getWidth() {
		return this.width;
	}
	
	public double getHeight() {
		return this.height;
	}
	
	public double divideHeight(double divider) {
		return this.height / divider;
	}
	
	public double divideWidth(double divider) {
		return this.height / divider;
	}
	
	@Override
	public boolean equals(Object anotherObject) {
		if(anotherObject instanceof ScreenInfo) {
			ScreenInfo second = (ScreenInfo) anotherObject;
			
			return this.width == second.getWidth() && this.height == this.getHeight();
		}
		return false;
	}
	
	@Override
	public String toString() {
		return "ScreenInfo(width=" + this.width + ", height=" + this.height + ")";
	}
}
