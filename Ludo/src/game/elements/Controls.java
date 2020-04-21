package game.elements;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import game.res.BoardData;
import game.res.PlayData;

public class Controls extends MouseAdapter {

	private BoardData bdata;
	public PlayData pd;
//	public final Object lock_hook;// = new Object();

	public Controls(BoardData bdata, PlayData pd) {
//		this.lock_hook = lock_hook;
		this.bdata = bdata;
		this.pd = pd;// new PlayData();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// System.out.println(e.getX());
		checkClick(e.getPoint());
	}

	private void checkClick(Point point) {
		for (Piece p : bdata.getPieces().get(pd.getCurrent_player().getColor())) {
			if (isClickedOnAPiece(p, point)) {
				pd.setPieceToMove(p);
				pd.setMoveAllowed(true);
				synchronized (pd.lock_hook) {
					pd.lock_hook.notify();
				}
				break;
			}
			pd.setMoveAllowed(false);
		}
//		bdata.getPiecesAsList().forEach(p -> {
//			if (isClickedOnAPiece(p, point)) {
//				pd.setPieceToMove(p);
//				pd.setMoveAllowed(true);
////				System.out.println("Clicked Correct");
//				synchronized (pd.lock_hook) {
//					pd.lock_hook.notify();
//				}
//			}
////			else pd.setMoveAllowed(false);
//		});
	}

	private boolean isClickedOnAPiece(Piece piece, Point p) {
		return piece.isInRange(p.x, p.y);
	}
}
