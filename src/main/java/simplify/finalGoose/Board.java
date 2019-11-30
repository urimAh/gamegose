package simplify.finalGoose;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Board {
	static List<String> cmdOperation;
	List<Player> playerList;
	List<Cell> board;

	public Board(List<Player> player, List<Cell> cells) {
		super();
		this.playerList = player;
		this.board = cells;
	}

	public Board() {
	}

	public List<Player> getPlayer() {
		return playerList;
	}

	public void setPlayer(List<Player> player) {
		this.playerList = player;
	}

	public List<Cell> getCells() {
		return board;
	}

	public void setCells(List<Cell> cells) {
		this.board = cells;
	}

	public List<Cell> fillBoard() {
		int i = 0;
		List<Cell> cellss = new ArrayList<Cell>();
		while (i < 90) {
			if (i == 5 || i == 9 || i == 14 || i == 18 || i == 23 || i == 27) {
				Cell cells = new GooseCell(i);
				cellss.add(cells);
			} else if (i == 7) {
				Cell cells = new BridgeCell(i);
				cellss.add(cells);
			} else {
				Cell cells = new NrmCell(i);
				cellss.add(cells);
			}
			i++;
		}
		return cellss;
	}

	static Player addPlayer(String name, int cellindex) {
		Player f = new Player(name);
		f.setCellIndex(cellindex);
		f.setListIndex(0);
		return f;
	}

	public void WellcomeMessage() {

		String n = System.getProperty("line.separator");
		System.out.println(
				"Welcome, player! To play refer to below commands" + n + "'add player->' : add player 'playername'" + n
						+ "'move player manually->' :move player  'playername' 'num1,num2" + n
						+ "'move automatically->' :move player 'playername'" + n);

	}

	public static int throwDice() {
		return ((int) (Math.random() * 10000) % 6) + 1;
	}
	public void setActivePlayer(Player player) {
		this.playerM = player;
	}

	Player playerM = null;

	public Player getActivePlayer(List<Player> playerList, String activePlayer) {
		Player playerM = null;
		for (int k = 0; k < playerList.size(); k++) {
			if (playerList.get(k).getPlayerName().equals(activePlayer)) {
				playerM = playerList.get(k);
				playerM.setListIndex(k);
			}
		}
		return playerM;
	}

	public void addPlayer(String playerNm) throws UserExistsException {
		playerM = getActivePlayer(playerList, playerNm);
		if (playerM != null) {
			System.out.println("Player " + playerM.playerName + " not added");
			throw new UserExistsException();
		} else {
			playerList.add(addPlayer(playerNm, 0));
			for (Player players : playerList) {
				System.out.println("Players :" + players.getPlayerName());
			}
		}
	}

	public boolean EXEC(String cmd) throws CommandException {
		boolean endgame = false;
		cmdOperation = executecommand(cmd);
		String playername = cmdOperation.get(1);
		switch (cmdOperation.get(0)) {
		case "add":
			try {
				addPlayer(playername);
			} catch (UserExistsException e) {
				System.out.println("Dublicated user, please try another name");
			}
			break;
		case "move":
			if (!existsPlayer(playername))
				try {
					endgame = movePlayer(cmdOperation);
				} catch (CommandException e) {
					System.out.println("Invalid command");
				}
			;
			break;
		case "end":
			System.out.println("Game is ended");
			endgame = true;
		default:
			System.out.println("Not right syntax used for command");
			break;
		}
		return endgame;
	}

	public List<String> executecommand(String cmd) throws CommandException {
		List<String> cmdOperation = new ArrayList<String>();
		switch (validatecommand(cmd)) {
		case "move":
			boolean cmdMoveAutomatic = Pattern.matches("moveplayer\\w*", cmd);
			String stringRoll = cmd.substring(cmd.length() - 3);
			cmdOperation.add("move");

			String[] dicerolls = stringRoll.split(",");
			if (cmdMoveAutomatic) {
				String playername = cmd.substring(10, cmd.length());
				cmdOperation.add(playername);
				cmdOperation.add(null);
			} else {
				String playername = cmd.substring(10, cmd.length() - 3);
				cmdOperation.add(playername);
				int sum = Integer.parseInt(dicerolls[0]) + Integer.parseInt(dicerolls[1]);
				cmdOperation.add(String.valueOf(sum));
			}
			break;
		case "add":
			String playernameAdd = cmd.substring(9, cmd.length());
			if (playernameAdd.length() != 0) {
				cmdOperation.add("add");
				cmdOperation.add(playernameAdd);
			} else {
				throw new CommandException();
			}
			break;
		case "end":
			cmdOperation.add("end");
			cmdOperation.add("end");
			break;
		default:
			throw new CommandException();
		}
		return cmdOperation;
	}

	private static String validatecommand(String cmd) {
		String commandIs = "null";
		boolean cmdMove = Pattern.matches("moveplayer\\w*[1-6]{1},[1-6]{1}", cmd);
		boolean cmdMoveAutomatic = Pattern.matches("moveplayer\\w*", cmd);
		boolean cmdAddPlayer = (Pattern.matches("addplayer\\w*$", cmd));
		boolean endGame = (Pattern.matches("end", cmd));

		if (cmdMove || cmdMoveAutomatic)
			commandIs = "move";
		if (cmdAddPlayer)
			commandIs = "add";
		if (endGame)
			commandIs = "end";
		return commandIs;
	}

	public boolean existsPlayer(String name) {
		Player playerM = getActivePlayer(playerList, name);
		boolean playerIs = false;
		if (playerM == null) {
			System.out.println("Cant move player who doesnt exist");
			playerIs = true;
		}
		return playerIs;
	}

	public boolean movePlayer(List<String> cmdOperation) throws CommandException {
		boolean endgame = false;
		int sum;
		playerM = getActivePlayer(playerList, cmdOperation.get(1));
		if (playerM == null) {
			throw new CommandException();
		}
		if (cmdOperation.get(2) == null) {
			sum = throwDice();
		} else {
			sum = Integer.valueOf(cmdOperation.get(2));
		}
		int playerIndx = playerList.get(playerM.getListIndex()).getCellIndex();
		int arrivedIndex = board.get(sum + playerIndx).getDestInx(playerM, sum);
		if (arrivedIndex > 63)
			board.get(sum + playerIndx).movePlayer(playerM, -sum);
		if (board.get(arrivedIndex).isBusy()) {
			Player playerDowngrade = null;
			for (int k = 0; k < playerList.size(); k++) {
				if (playerList.get(k).getCellIndex() == arrivedIndex) {
					playerDowngrade = playerList.get(k);
					playerDowngrade.setCellIndex(playerIndx);
					System.out.println("Player " + playerDowngrade.getPlayerName() + " return from " + arrivedIndex
							+ " --> " + playerDowngrade.getCellIndex());
				}
			}
		}
		board.get(sum + playerIndx).movePlayer(playerM, sum);
		board.get(arrivedIndex).setBusy(true);
		board.get(playerM.getCellIndex() - sum).setBusy(false);

		if (playerM.getCellIndex() == 63) {
			endgame = true;
			System.out.println("***************************");
			System.out.println("     " + playerM.getPlayerName() + " won the game ");
			System.out.println("***************************");
		}
		System.out.println("Player " + playerM.getPlayerName() + " moved from " + playerIndx + " to --> "
				+ playerM.getCellIndex());
		return endgame;
	}
}
