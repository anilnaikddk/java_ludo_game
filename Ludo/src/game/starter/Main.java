package game.starter;
import javax.swing.JFrame;

import game.elements.BoardPanel;
import game.elements.Player;
import game.res.BoardData;
import game.res.Configurations;

public class Main {
	private JFrame frame ;
	private BoardData bd;
	private BoardPanel board;
	public Main() {
		initUI();
	}
	private void initUI() {
		frame = new JFrame("Ludo - Its Fun Time");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		bd = new BoardData();
		board = new BoardPanel(bd);
		frame.add(board);
		
		frame.setVisible(true);
		frame.pack();
		frame.setLocationRelativeTo(null);
	}
	
	private void startGame() {
		Player p1 = new Player(board,bd,Configurations.P_B);
		//p1.move(50, 0);
		//p1.move(50, 1);
		p1.outToStart(0);
		p1.move(2, 0);
		p1.move(2, 0);
		//p1.move(28, 2);
	}

	public static void main(String[] args) {
		Main m = new Main();
		m.startGame();
	}
}
