package game.elements;

import java.util.Arrays;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

import game.res.BoardData;
import game.res.Configurations;

public class Player {

	private BoardPanel board;
	private BoardData bdata;
	private Piece pieces[];
	private int player_color;

	public Player(BoardPanel board, BoardData bdata, int player_color) {
		this.board = board;
		this.bdata = bdata;
		this.player_color = player_color;
		pieces = bdata.getPieces().get(player_color);
	}

	public int rollDice() {
		return Dice.roll();
	}

	public void kill(int piece_no) {
		pieces[piece_no].returnToBase(bdata.getBaseInternals().get(player_color)[piece_no]);
		board.updateBoard(false);
	}

	public void outToStart(int piece_no) {
//		pause(500);
		pieces[piece_no].update(bdata.getStartingBox(player_color));
//		pieces[piece_no].update(bdata.getRunway().get(24));
		pieces[piece_no].setHomeStatus(false);
		board.updateBoard(true);
	}

	public void move(int steps, int piece_no) {
		if (steps <= 0)
			return;
		if (steps == 6 && pieces[piece_no].pos == -1) {
			return;
		}
		int pos = pieces[piece_no].pos;
		if (pos == 51)
			pos = 0;
		if (isEntryPoint(pos)) {
			pos = getPlayerInsideFinishLine();
		} else {
			pos++;
		}
		update_piece.accept(piece_no, pos);
		move(--steps, piece_no);
	}

	private boolean isEntryPoint(int pos) {
		return pos == bdata.getEntryPosition(player_color);
	}

	private int getPlayerInsideFinishLine() {
		int i = 52;
		if (player_color == Configurations.P_G)
			return i;
		if (player_color == Configurations.P_Y)
			return i + 1 * 5;
		if (player_color == Configurations.P_B)
			return i + 2 * 5;
		if (player_color == Configurations.P_R)
			return i + 3 * 5;
		return i;
	}

	private void pause(int time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private BiConsumer<Integer, Integer> update_piece = (pno, pos) -> {
		pieces[pno].update(bdata.getRunway().get(pos));
		board.updateBoard(true);
		pause(50);
	};
	
	public int getColor() {
		return player_color;
	}
	
	public boolean hasAnyPieceOut() {
		return getOutPieceCount() > 0;
	}
	
	public int getOutPieceCount() {
		int i = 0;
		for(Piece p : pieces) {
			if(p.pos != -1)
				i++;
		}
		return i;
	}
	
	public Piece getFirstOutPiece() {
		return Arrays.asList(pieces).stream().filter(p -> p.pos != -1).collect(Collectors.toList()).get(0);
	}
}
