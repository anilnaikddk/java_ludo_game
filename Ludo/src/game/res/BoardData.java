package game.res;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.BiFunction;
import java.util.function.Supplier;

import game.elements.Box;
import game.elements.Piece;

public class BoardData {
	private Box grid[][];
	private ArrayList<Box> runway; // the outer run ways
//	private ArrayList<Box> final_runway; // the finishing line run ways
	private ArrayList<Box> bases;
	private ArrayList<Box> base_internals;
//	private ArrayList<Box> stars;
	private HashMap<Integer, Piece[]> pieces;
	private HashMap<Integer, Integer> entry_points;
	private HashMap<Integer, Integer> starting_points;

	private Supplier<ArrayList<Box>> newArrayList = ArrayList<Box>::new;

	public BoardData() {
		grid = new Box[15][15];
		runway = newArrayList.get();
//		final_runway = newArrayList.get();
		bases = newArrayList.get();
		base_internals = newArrayList.get();
//		stars = new newArrayList.get();
		pieces = new HashMap<Integer, Piece[]>();
		starting_points = new HashMap<Integer, Integer>();
		entry_points = new HashMap<Integer, Integer>();
		initPlayerBases();
		initRunway();
		initStartingPoints();
		initEntryPoints();
		initPieces();
	}

	private void initPlayerBases() {
		createBaseInternal(0, 0, Color.green);
		createBaseInternal(9, 0, Color.yellow);
		createBaseInternal(9, 9, Color.blue);
		createBaseInternal(0, 9, Color.red);
	}

	private void createBaseInternal(int i, int j, Color c) {
		bases.add(new Box(i, j, c));
		// j++;
		int s = Configurations.S;
		i = ++i * s + s / 2;
		j = ++j * s + s / 2;
		base_internals.add(new Box(i, j, c));
		base_internals.add(new Box(i, j + 2 * s, c));
		base_internals.add(new Box(i + 2 * s, j, c));
		base_internals.add(new Box(i + 2 * s, j + 2 * s, c));
	}

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

	private void initStartingPoints() {
		BiFunction<Color, Integer, Integer> setColor = (c, i) -> {
			runway.get(i).color = c;
			return runway.get(i).pos;
		};
		int i = 0;
		starting_points.put(Configurations.P_G, setColor.apply(Color.green, i));
		starting_points.put(Configurations.P_Y, setColor.apply(Color.yellow, i += 13));
		starting_points.put(Configurations.P_B, setColor.apply(Color.blue, i += 13));
		starting_points.put(Configurations.P_R, setColor.apply(Color.red, i += 13));
	}

	private void initEntryPoints() {
		int i = 11;
		entry_points.put(Configurations.P_Y, i);
		entry_points.put(Configurations.P_B, i += 13);
		entry_points.put(Configurations.P_R, i += 13);
		entry_points.put(Configurations.P_G, i += 13);
	}

	private void initPieces() {
		int k = 0;
		for (int i = 1; i <= 4; i++) {
			Piece tp[] = new Piece[4];
			for (int j = 0; j < 4; j++) {
				tp[j] = new Piece(base_internals.get(k++));
			}
			pieces.put(i, tp);
		}
	}
	
	public Box getStartingBox(int player_color) {
		return runway.get(getStartingPosition(player_color));
	}
	
	public int getStartingPosition(int player_color) {
		return runway.get(starting_points.get(player_color)).pos;
	}
	
	public int getEntryPosition(int player_color) {
		return runway.get(entry_points.get(player_color)).pos;
	}

	public HashMap<Integer, Integer> getStartingPoints() {
		return starting_points;
	}

	public HashMap<Integer, Integer> getEntryPoints() {
		return entry_points;
	}

	public HashMap<Integer, Piece[]> getPieces() {
		return pieces;
	}

	public ArrayList<Box> getRunway() {
		return runway;
	}

	public ArrayList<Box> getBases() {
		return bases;
	}

	public ArrayList<Box> getBaseInternals() {
		return base_internals;
	}

	public Box[][] getGrid() {
		return grid;
	}
}
