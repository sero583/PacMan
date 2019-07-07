package sero583.pacman.launcher.windows;

import sero583.pacman.*;
import sero583.pacman.launcher.BaseLauncher;
import sero583.pacman.launcher.Logger;

public class WindowsLauncher extends BaseLauncher {
	public WindowsLauncher() {
		super(new WindowsLogger());
		
		//Create JFrame here and mulitply with screen destiny..
	}

	public static void main(String[] args) {
		new Main(new WindowsLauncher()); //plot twist lul
	}
	
	public void onUpdate(Level level) {
		//render here
	}
}