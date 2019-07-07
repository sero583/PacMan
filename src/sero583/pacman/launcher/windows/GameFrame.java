package sero583.pacman.launcher.windows;

import javax.swing.JFrame;

public class GameFrame extends JFrame {
	private static final long serialVersionUID = 6504866982958947529L;
	
	public GameFrame(int width, int height, String title) {
		this.setSize(width, height);
		this.setTitle(title);
	}
}