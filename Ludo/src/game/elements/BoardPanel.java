package game.elements;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

import game.res.BoardData;
import game.res.Configurations;

public class BoardPanel extends JPanel {

	BoardData bdata;

	public BoardPanel(BoardData bd, Controls ctrls) {
		this.bdata = bd;
		setPreferredSize(new Dimension(Configurations.W, Configurations.H));
		this.addMouseListener(ctrls);
	}

	public void updateBoard(boolean immediate) {
		if (immediate)
			if (bdata.isOnlyUpdateDiceArea())
				paintImmediately(Dice.xcoord, Dice.ycoord, Dice.size, Dice.size);
			else
				paintImmediately(0, 0, Configurations.W, Configurations.H);
		else
			repaint();
	}

	private void drawRunway(Graphics g) {
		// runway = bdata.getRunway();
		int s = Configurations.S;
		bdata.getRunway().forEach(b -> {
			if (b != null) {
				g.setColor(b.color);
				g.fillRect(b.xcord * s, b.ycord * s, b.size, b.size);
				g.setColor(Color.black);
				g.drawRect(b.xcord * s, b.ycord * s, b.size, b.size);
				drawCoordinates(g, b);
			}
		});
	}

	private void clearScreen(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(0, 0, Configurations.W, Configurations.H);
	}

	private void drawBases(Graphics g) {
		int s = Configurations.S;
		bdata.getBases().forEach(b -> {
			g.setColor(b.color);
			g.fillRect(b.xcord * s, b.ycord * s, b.size * 6, b.size * 6);
//			g.fillRoundRect(b.xcord * s, b.ycord * s, b.size * 6, b.size * 6,50,50);
			g.setColor(Color.black);
			g.drawRect(b.xcord * s, b.ycord * s, b.size * 6, b.size * 6);
//			g.drawRoundRect(b.xcord * s, b.ycord * s, b.size * 6, b.size * 6,50,50);
			g.setColor(Color.white);
			g.fillRect((b.xcord + 1) * s, (b.ycord + 1) * s, b.size * 4, b.size * 4);
			g.setColor(Color.black);
			g.drawRect((b.xcord + 1) * s, (b.ycord + 1) * s, b.size * 4, b.size * 4);
			// drawCoordinates(g, b);
		});

		bdata.getBaseInternals().values().forEach(boxes -> {
//		bdata.getBaseInternals().forEach(b -> {
			for (Box b : boxes) {
				int x = b.xcord;// * s + s / 2;
				int y = b.ycord;// * s + s / 2;
				g.setColor(b.color);
				g.fillRoundRect(x, y, b.size, b.size, 1, 1);
				g.setColor(Color.black);
				g.drawRect(x, y, b.size, b.size);
				// drawCoordinates(g, b);
			}
		});
	}

	private void drawPieces(Graphics g) {
		bdata.getPieces().values().forEach(ps -> {
			for (Piece p : ps) {
				drawPiece(g, p, 2, 3, Color.black, true);
				drawPiece(g, p, 5, 2, Color.white, false);
				drawPiece(g, p, 8, 1, p.color, true);
				drawPiece(g, p, 11, 2, Color.white, false);
			}
		});
	}

	private void drawPiece(Graphics g, Piece p, int start, int stroke, Color c, boolean fill) {
		g.setColor(c);
		int x = p.xcord * p.size;
		int y = p.ycord * p.size;
		if (p.isInsideHome()) {
			x = p.xcord;// * p.size + p.size / 2;
			y = p.ycord;// * p.size + p.size / 2;
		}
		while (stroke > 0) {
			if (fill)
				g.fillOval(x + start, y + start, p.size - start * 2, p.size - start * 2);
			else
				g.drawOval(x + start, y + start, p.size - start * 2, p.size - start * 2);

			start++;
			stroke--;
		}
	}

//	private void drawStartingPoints(Graphics g) {
//		drawPoints(g, bdata.getStartingPoints());
//	}
//
//	private void drawEntryPoints(Graphics g) {
//		drawPoints(g, bdata.getEntryPoints());
//	}

//	private void drawPoints(Graphics g, HashMap<Integer, Box> p) {
//		int s = Configurations.S;
//		p.forEach((cc, b) -> {
//			g.setColor(b.color);
//			g.fillRect(b.xcord * s, b.ycord * s, b.size, b.size);
//			g.setColor(Color.black);
//			g.drawRect(b.xcord * s, b.ycord * s, b.size, b.size);
//		});
//	}

	@Override
	public void paint(Graphics g) {
		if(bdata.isOnlyUpdateDiceArea()) {
			drawDiceArea(g);
			drawRunway(g);
			return;
		}
		clearScreen(g);
		drawDiceArea(g);
		drawBases(g);
		drawRunway(g);
		// drawStartingPoints(g);
		drawPieces(g);
		// drawEntryPoints(g);
	}

	private void drawDiceArea(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(Dice.xcoord, Dice.ycoord, Dice.size, Dice.size);
	}

	private void drawCoordinates(Graphics g, Box b) {
		g.setColor(Color.black);
		g.setFont(new Font("Arial", Font.BOLD, 10));
//		String co = b.xcord + "," + b.ycord;
		String co = b.pos + "";
		g.drawString(co, b.xcord * b.size + 6, b.ycord * b.size + 20);
	}
}
