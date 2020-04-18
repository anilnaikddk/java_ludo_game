package game.elements;

import java.awt.Color;
import java.util.function.Function;

public class Piece extends Box {

//	private boolean on_star;
//	private boolean inside_finish_zone;
	private boolean inside_home = true;
	private boolean finished;
	//private boolean 

	public Piece(int x, int y, Color color) {
		super(x, y, color);
	}

	public Piece(Box b) {
		super(b.xcord, b.ycord, b.color);
//		this.x = b.xcord;// * super.size;
//		this.y = b.ycord;// * super.size;
	}
	
	public void update(Box b) {
		super.xcord = b.xcord;
		super.ycord = b.ycord;
		super.pos = b.pos;
	}
	
	public Function<Integer,Void> move = step -> {
		return null;
	};

	public boolean isFinished() {
		return finished;
	}

	public void reachedFinish() {
		finished = true;
	}
	
	public void setHomeStatus(boolean status) {
		inside_home = status;
	}
	public void sendToHome() {
		inside_home = true;
	}
	public boolean isInsideHome() {
		return inside_home;
	}
}
