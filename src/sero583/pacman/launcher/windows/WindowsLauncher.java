package sero583.pacman.launcher.windows;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import sero583.pacman.*;
import sero583.pacman.launcher.BaseLauncher;
import sero583.pacman.launcher.ScreenInfo;

public class WindowsLauncher extends BaseLauncher implements KeyListener {
	public static void main(String[] args) {
		WindowsLauncher winLauncher = new WindowsLauncher();
		Main main = new Main(winLauncher); //plot twist lul
		winLauncher.setMain(main);
		winLauncher.createScreen();
		winLauncher.showScreen();
		winLauncher.setRunning(true);
		winLauncher.startThread();
	}

	public static final int DEFAULT_WIDTH = 720;
	public static final int DEFAULT_HEIGHT = 500;
	public static final boolean UPPER_ACTIONBAR_ENABLED = true;
	
	private Main main;
	private GameFrame frame;
	
	public WindowsLauncher() {
		super(new WindowsLogger());
	}
	
	public void setMain(Main main) {
		this.main = main;
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
		this.frame = new GameFrame(DEFAULT_WIDTH, DEFAULT_HEIGHT, TITLE);
		this.frame.setResizable(this.isResizeable());
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setBackground(Color.BLACK);
		this.frame.setLocationRelativeTo(null);
		this.frame.setUndecorated((UPPER_ACTIONBAR_ENABLED ? false : true));
		
		/*Dimension actualSize = getContentPane().getSize();
		Game.TITLE_BAR_HEIGHT = DefaultLauncher.DEFAULT_HEIGHT - actualSize.height;*/
		this.setScreenInfo(new ScreenInfo(DEFAULT_WIDTH, DEFAULT_HEIGHT));
	}
	
	public void showScreen() {
		this.showScreen(true);
	}
	
	public void showScreen(boolean state) {
		this.frame.setVisible(state);
	}

	/*@Override
	public void startRenderer() {
		//schedule draw task
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}*/
	
	public void render() {
		BufferStrategy bs = this.frame.getBufferStrategy();
		if(bs==null) {
			this.frame.createBufferStrategy(2);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		try {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, (int) this.getScreenInfo().getWidth(), (int) this.getScreenInfo().getHeight()); //TODO: Change hack for removing content to normal method

			this.main.getLevel().grahicsRender(g);
			
			if(this.showFPS()==true&&this.renderableFrameCount!=null) {
				g.setColor(Color.RED);
				g.setFont(new Font("Arial Black", Font.BOLD, 16));
				g.drawString("FPS: " + this.renderableFrameCount, 50, 50);
			}
			//g.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
			/*g.setColor(Color.CYAN);
			g.drawString("FPS: " + this.totalFrameCount, 500, 50);
			System.out.println("FPS:" + this.totalFrameCount);
			*/
			//g.drawImage(tests, 25, 25, 300, 300, null);
			bs.show();
		} finally {
			g.dispose();
		}
	}
	
	@Override
	public void onScreenUpdate() {
		this.getScreenInfo().setWidth(this.frame.getWidth());
		this.getScreenInfo().setHeight(this.frame.getHeight());
	}

	@Override
	public boolean hasFPSLimit() {
		return false;
	}

	@Override
	public int getFPSLimit() {
		return 0;
	}

	@Override
	public boolean isResizeable() {
		return true;
	}
	
	@Override
	public boolean showFPS() {
		return true;
	}
}