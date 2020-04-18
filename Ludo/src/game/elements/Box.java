package game.elements;

import java.awt.Color;

import game.res.Configurations;

public class Box {
	public int xcord;
	public int ycord;
	public int size = Configurations.S;
	public Color color = Color.white;
	public int pos;

	public Box(int x, int y) {
		this.xcord = x;
		this.ycord = y;
	}

	public Box(int x, int y, Color color) {
		this.xcord = x;
		this.ycord = y;
		this.color = color;
	}

	public Box(int x, int y, Color color, int pos) {
		this.xcord = x;
		this.ycord = y;
		this.color = color;
		this.pos = pos;
	}

	public Box(int x, int y, int pos) {
		this.xcord = x;
		this.ycord = y;
		this.pos = pos;
	}
}
