package game.driver;

import javax.swing.JFrame;

import game.elements.BoardPanel;
import game.elements.Controls;
import game.elements.Dice;
import game.elements.Player;
import game.res.BoardData;
import game.res.Configurations;
import game.res.PlayData;

public class Main {
	private JFrame frame;
	private BoardData bd;
	private BoardPanel board;
	private PlayData pd;
	// public static final Object lock_hook = new Object();

	public Main() {
		initUI();
	}

	private void initUI() {
		bd = new BoardData();
		pd = new PlayData();
		board = new BoardPanel(bd, new Controls(bd, pd));

		frame = new JFrame("Ludo - Its Fun Time");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(board);
		frame.setAlwaysOnTop(true);
		frame.setVisible(true);
		frame.pack();
		frame.setLocationRelativeTo(null);
	}

	private void initPlayers() {
		// create and add all players to the list
		pd.addPlayer(new Player(board, bd, Configurations.P_G));
		pd.addPlayer(new Player(board, bd, Configurations.P_Y));
		pd.addPlayer(new Player(board, bd, Configurations.P_B));
		pd.addPlayer(new Player(board, bd, Configurations.P_R));
		pd.setCurrentPlayer(pd.getPlayer(0));
		pd.updateNoOfPlayers();
	}

	private void startGame() {
		initPlayers();
		gamePlay();
	}

	private void gamePlay() {
		int dice_no = 0;
		while (pd.getNo_of_players() > 0) {
			if (!pd.isDiceRolled()) {
				dice_no = Dice.roll();
				System.out.println("Dice - " + dice_no + ",Player - " + pd.getCurrent_player().getColor());
				pd.setDiceRolled(true);
			}
			if (dice_no == 6 && pd.getCurrent_player().getOutPieceCount() == 0) {
				pd.getCurrent_player().outToStart(0);
				pd.nextPlayerTurn();
				continue;
			}
			if (dice_no != 6 && pd.getCurrent_player().getOutPieceCount() == 1) {
				pd.setPieceToMove(pd.getCurrent_player().getFirstOutPiece());
				pd.getCurrent_player().move(dice_no, pd.getPieceToMove().piece_no);
				pd.nextPlayerTurn();
				continue;
			}
			if (dice_no != 6 && !pd.getCurrent_player().hasAnyPieceOut()) {
				pd.nextPlayerTurn();
				continue;
			}
			waitForClick();
			if (pd.isMoveAllowed()) {
				if (pd.getPieceToMove().pos != -1) {
					pd.getCurrent_player().move(dice_no, pd.getPieceToMove().piece_no);

				} else if (dice_no == 6 && pd.getPieceToMove().pos == -1) {
					pd.getCurrent_player().outToStart(pd.getPieceToMove().piece_no);

				}
				pd.nextPlayerTurn();
//				pd.setDiceRolled(false);
				// lock_hook.notify();
			}
		}
	}

	public void waitForClick() {
		synchronized (pd.lock_hook) {
			try {
				pd.lock_hook.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

//	private void test() {
//		// pd.getCurrent_player().outToStart(1);
////		p.outToStart(0);
////		p.outToStart(1);
////		p.rollDice(5,0);
////		p.kill(1);
//	}

	public static void main(String[] args) {
		Main m = new Main();
//		m.startGame();
		new Thread(() -> m.startGame()).start();
	}
}
