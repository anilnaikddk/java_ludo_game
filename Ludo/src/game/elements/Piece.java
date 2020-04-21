package game.elements;

import java.awt.Color;

public class Piece extends Box {

//	private boolean on_star;
//	private boolean inside_finish_zone;
	private boolean inside_home = true;
	private boolean finished;
	public int piece_no;
	// private boolean

	public Piece(int x, int y, Color color) {
		super(x, y, color);
	}

	public Piece(Box b) {
		super(b.xcord, b.ycord, b.color);
	}

	public Piece(Box b, int piece_no) {
		super(b.xcord, b.ycord, b.color);
		this.piece_no = piece_no;
	}
	
	public void returnToBase(Box b) {
		update(b);
		inside_home = true;
		super.pos = -1;
	}

	public void update(Box b) {
		super.xcord = b.xcord;
		super.ycord = b.ycord;
		super.pos = b.pos;
	}
	
	public boolean isInRange(int x,int y) {
		if(pos == -1) {
			return (x >= xcord && x <= xcord + size) && (y >= ycord && y <= ycord + size);
		}else {
			return (x >= xcord*size && x <= xcord*size + size) && (y >= ycord*size && y <= ycord*size + size);
		}
	}

	public boolean isFinished() {
		return finished;
	}

	public void reachedFinish() {
		finished = true;
	}

	public void setHomeStatus(boolean status) {
		inside_home = status;
	}

	public boolean isInsideHome() {
		return inside_home;
	}
}
