package game.elements;

import java.util.function.BiConsumer;

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

	public void outToStart(int piece_no) {
		pause(1200);
		pieces[piece_no].update(bdata.getStartingBox(player_color));
		pieces[piece_no].setHomeStatus(false);
		board.updateBoard(false);
	}

	public void move(int steps, int piece_no) {
		if (steps > 51)
			steps = 51;
		//outToStart(piece_no);
		int pos = pieces[piece_no].pos;
		while (steps >= 0) {
			if (isEntryPoint(pos)) {
				update_piece.accept(piece_no, pos);
				pos = getPlayerFinishLine();
				System.out.println("entry - " + pos);
			}
			update_piece.accept(piece_no, pos);
			pos++;
			if (pos == 52)
				pos = 0;
			steps--;
		}
	}

	private boolean isEntryPoint(int pos) {
		return pos == bdata.getEntryPosition(player_color);
	}

	private int getPlayerFinishLine() {
		if (player_color == Configurations.P_G)
			return 52;
		if (player_color == Configurations.P_Y)
			return 57;
		if (player_color == Configurations.P_B)
			return 62;
		if (player_color == Configurations.P_R)
			return 67;
		return 0;
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
		board.updateBoard(false);
		pause(100);
	};
}
