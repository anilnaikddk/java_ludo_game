package game.res;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.BiFunction;

import game.elements.Box;
import game.elements.Piece;
import game.elements.Runway;

public class BoardData {
	private Box grid[][];
	private Runway runway; // the outer run ways
	private PlayData play_data;
//	private ArrayList<Box> final_runway; // the finishing line run ways
	private ArrayList<Box> bases;
	private HashMap<Integer, Box[]> base_internals;
//	private ArrayList<Box> base_internals;
//	private ArrayList<Box> stars;
	private HashMap<Integer, Piece[]> pieces;
	private HashMap<Integer, Integer> entry_points;
	private HashMap<Integer, Integer> starting_points;
	private boolean draw_only_dice;// = true;
	
	public final Object lock_hook = new Object();

//	private Supplier<ArrayList<Box>> newArrayList = ArrayList<Box>::new;

	public BoardData() {
		grid = new Box[15][15];
		runway = new Runway();
		play_data = new PlayData();
//		final_runway = newArrayList.get();
		bases =  new ArrayList<Box>();
		base_internals = new HashMap<Integer, Box[]>();
//		stars = new newArrayList.get();
		pieces = new HashMap<Integer, Piece[]>();
		starting_points = new HashMap<Integer, Integer>();
		entry_points = new HashMap<Integer, Integer>();
		initPlayerBases();
		//initRunway();
		initStartingPoints();
		initEntryPoints();
		initPieces();
	}

	private void initPlayerBases() {
		createBaseInternal(0, 0, Color.green, Configurations.P_G);
		createBaseInternal(9, 0, Color.yellow, Configurations.P_Y);
		createBaseInternal(9, 9, Color.blue, Configurations.P_B);
		createBaseInternal(0, 9, Color.red, Configurations.P_R);
	}

	private void createBaseInternal(int i, int j, Color c, int cc) {
		bases.add(new Box(i, j, c));
		// j++;
		int s = Configurations.S;
		i = ++i * s + s / 2;
		j = ++j * s + s / 2;
//		Box[] boxes = new Box[]{
//				new Box(i, j, c),
//				new Box(i, j + 2 * s, c),
//				new Box(i + 2 * s, j, c),
//				new Box(i + 2 * s, j + 2 * s, c)
//		};
		base_internals.put(cc, new Box[] { 
				new Box(i, j, c), 
				new Box(i, j + 2 * s, c), 
				new Box(i + 2 * s, j, c),
				new Box(i + 2 * s, j + 2 * s, c) 
				}
		);
//		base_internals.add(new Box(i, j, c));
//		base_internals.add(new Box(i, j + 2 * s, c));
//		base_internals.add(new Box(i + 2 * s, j, c));
//		base_internals.add(new Box(i + 2 * s, j + 2 * s, c));
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
		for(int cc : base_internals.keySet()) {
			Piece tp[] = new Piece[4];
			for(int i = 0; i<tp.length;i++) {
				tp[i] = new Piece(base_internals.get(cc)[i], i);
			}
			pieces.put(cc, tp);
		}
	}
	
	public List<Piece> getPiecesAsList(){
		List<Piece> pl = new ArrayList<Piece>();
		pieces.values().forEach(ps -> {for(Piece p : ps) pl.add(p);});
		return pl;
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
		return runway.getBoxes();
	}

	public ArrayList<Box> getBases() {
		return bases;
	}

	public HashMap<Integer, Box[]> getBaseInternals() {
		return base_internals;
	}

	public Box[][] getGrid() {
		return grid;
	}
	
	public PlayData getPlayData() {
		return play_data;
	}
	
	public void setUpdateOnlyDiceArea(boolean draw_dice) {
		draw_only_dice = draw_dice;
	}
	
	public boolean isOnlyUpdateDiceArea() {
		return draw_only_dice;
	}
}
