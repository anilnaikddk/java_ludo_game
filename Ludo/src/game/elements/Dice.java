package game.elements;

import java.util.Random;

import game.res.Configurations;

public class Dice {

	private static final int x = 6;
	private static final int y = 6;
	private static final int s = 3;
	
	public static int xcoord = x * Configurations.S;
	public static int ycoord = y * Configurations.S;
	public static int size = s * Configurations.S;
	
	private static Random r = new Random();

	public static int roll() {
		return r.nextInt(6) + 1;
	}
}
