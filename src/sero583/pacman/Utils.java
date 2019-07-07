package sero583.pacman;

import java.util.Random;

public class Utils {
	private static Random rand = new Random();
	
	public static int generateNumber(int min, int max) {
		return min + rand.nextInt(max);
	}
}
