package game.elements;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;

public class Runway{

	ArrayList<Box> runway;
	HashMap<Integer,Integer> piece_positions;
	
	public Runway() {
		runway = new ArrayList<>();
		piece_positions = new HashMap<>();
		initRunway();
	}
	
	//public Predicate<Integer>
	
	private void initRunway() {
		// xop/yop 1-inc,2-dec,3-nothing
		createRunway(1, 6, 1, 3, 5, null);
		createRunway(6, 5, 3, 2, 5, null);
		createRunway(6, 0, 1, 3, 3, null);
		createRunway(8, 1, 3, 1, 5, null);
		createRunway(9, 6, 1, 3, 5, null);
		createRunway(14, 6, 3, 1, 3, null);
		createRunway(13, 8, 2, 3, 5, null);
		createRunway(8, 9, 3, 1, 5, null);
		createRunway(8, 14, 2, 3, 3, null);
		createRunway(6, 13, 3, 2, 5, null);
		createRunway(5, 8, 2, 3, 5, null);
		createRunway(0, 8, 3, 2, 3, null);
		initFinalRunway();
	}
	
	private void initFinalRunway() {
		// xop/yop 1-inc,2-dec,3-nothing
		createRunway(1, 7, 1, 3, 5, Color.green);
		createRunway(7, 1, 3, 1, 5, Color.yellow);
		createRunway(13, 7, 2, 3, 5, Color.blue);
		createRunway(7, 13, 3, 2, 5, Color.red);
	}
	
	private void createRunway(int x, int y, int xop, int yop, int len, Color c) {
		while (len > 0) {
			Box b = new Box(x, y);
			if (c != null)
				b.color = c;
			b.pos = runway.size();
			runway.add(b);
			if (xop == 1)
				x++;
			else if (xop == 2)
				x--;
			if (yop == 1)
				y++;
			else if (yop == 2)
				y--;
			len--;
		}
	}
	
	public HashMap<Integer,Integer> getPiecePositions() {
		return piece_positions;
	}
	
	public Box get(int i) {
		return runway.get(i);
	}

	public ArrayList<Box> getBoxes() {
		return runway;
	}
}
