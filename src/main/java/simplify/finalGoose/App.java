package simplify.finalGoose;

import java.util.ArrayList;
import java.util.Scanner;

public class App {
	public static void main(String[] args) throws Exception, CommandException {
		Board boardGame = new Board();
		boardGame.setCells(boardGame.fillBoard());
		boardGame.WellcomeMessage();
		boardGame.setPlayer(new ArrayList<Player>());
		boolean endgame = false;
		Scanner input = new Scanner(System.in);
		while (!endgame) {
			String cmd = input.nextLine().replaceAll("\\s+", "").toLowerCase();
			try {
				endgame = boardGame.EXEC(cmd);
			} catch (CommandException e) {
				System.out.println("please check command syntax!");
			}
		}
	}
}
