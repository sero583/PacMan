package sero583.pacman.launcher.windows;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import sero583.pacman.*;
import sero583.pacman.launcher.BaseLauncher;
import sero583.pacman.launcher.Logger;

public class WindowsLauncher extends BaseLauncher implements KeyListener {
	public static void main(String[] args) {
		new Main(new WindowsLauncher()); //plot twist lul
	}
	
	
	private Main main;
	
	public WindowsLauncher() {
		super(new WindowsLogger());
		
		this.main = new Main(this);
		this.createScreen();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		
		if(key==KeyEvent.VK_W) {
			this.main.handleInput("w");
		} else if(key==KeyEvent.VK_S) {
			this.main.handleInput("s");
		} else if(key==KeyEvent.VK_A) {
			this.main.handleInput("d");
		} else if(key==KeyEvent.VK_D) {
			this.main.handleInput("a");
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void keyTyped(KeyEvent e) {}
	
	public void createScreen() {
		//TODO: JFrame
	}

	@Override
	public void startRenderer() {
		//schedule draw task
	}
}