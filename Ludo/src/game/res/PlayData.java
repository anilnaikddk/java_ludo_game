package game.res;

import java.util.ArrayList;

import game.elements.Piece;
import game.elements.Player;

public class PlayData {
	private ArrayList<Player> players = new ArrayList<>();
	private boolean move_allowed = false;
	private boolean dice_rolled = false;
	private Piece piece_to_move;
	private int no_of_moves;
	private Player current_player;
	private int no_of_players;
	public final Object lock_hook = new Object();

	public void nextPlayerTurn() {
		current_player = getNextPlayer();
		setDiceRolled(false);
	}

	public Player getNextPlayer() {
		int i = players.indexOf(getCurrent_player());
		i++;
		if (i >= players.size()) {
			i = 0;
		}
		return players.get(i);
	}

	public void setCurrentPlayer(Player player) {
		current_player = player;
	}

	public void addPlayer(Player player) {
		players.add(player);
	}

	public Player getPlayer(int pno) {
		return players.get(pno);
	}

	public Player getCurrent_player() {
		return current_player;
	}

	public boolean isMoveAllowed() {
		return move_allowed;
	}

	public void setMoveAllowed(boolean move_allow) {
		this.move_allowed = move_allow;
	}

	public Piece getPieceToMove() {
		return piece_to_move;
	}

	public void setPieceToMove(Piece piece_to_move) {
		this.piece_to_move = piece_to_move;
	}

	public int getNoOfMoves() {
		return no_of_moves;
	}

	public void setNoOfMoves(int no_of_moves) {
		this.no_of_moves = no_of_moves;
	}

	public void setDiceRolled(boolean dr) {
		dice_rolled = dr;
	}

	public boolean isDiceRolled() {
		return dice_rolled;
	}

	public int getNo_of_players() {
		return no_of_players;
	}

	public void updateNoOfPlayers() {
		this.no_of_players = players.size();
	}
}
