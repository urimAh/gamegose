package simplify.finalGoose;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import static java.util.Arrays.asList;
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TestMainCommand {
	private Board board;
	@Rule
	public ExpectedException expectedException = ExpectedException.none();
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
	private final PrintStream originalOut = System.out;
	private final PrintStream originalErr = System.err;

	@Before
	public void setUp() {
		board = new Board();
		board.setCells(board.fillBoard());
		board.setPlayer(new ArrayList<Player>());
		System.setOut(new PrintStream(outContent));
		System.setErr(new PrintStream(errContent));

	}

	@After
	public void restoreStreams() {
		System.setOut(originalOut);
		System.setErr(originalErr);
	}

	@Test
	public void AddPlayer() throws CommandException, UserExistsException {
		String playerName = "urim";
		board.addPlayer(playerName);
		Player urim = new Player("urim", 0);
		List<Player> players = asList(urim);
		assertEquals(board.getPlayer().get(0).getPlayerName(), players.get(0).getPlayerName());
	}

	@Test
	public void AddPlayerDublicte() throws CommandException, UserExistsException {
		String playerName = "urim";
		board.addPlayer(playerName);
		expectedException.expect(UserExistsException.class);
		board.addPlayer(playerName);

	}

	@Test
	public void movePlayerWrongCommand() throws CommandException {
		List<String> cmdOperation = new ArrayList<String>();
		cmdOperation.add("movse");
		cmdOperation.add("urimd");
		cmdOperation.add("3s");
		expectedException.expect(CommandException.class);
		board.movePlayer(cmdOperation);
	}

	@Test
	public void movePlayerSuccess() throws CommandException {
		List<Player> playerList = new ArrayList<Player>();
		playerList.add(new Player("urim", 0));
		List<String> cmdOperation = new ArrayList<String>();
		cmdOperation.add("move");
		cmdOperation.add("urim");
		cmdOperation.add("3");
		board.setPlayer(playerList);
		board.playerList = playerList;
		board.setActivePlayer(new Player("Urim", 0));
		board.movePlayer(cmdOperation);
		assertTrue(outContent.toString().contains("Player urim moved from 0 to --> 3"));
	}

	@Test
	public void movePlayerGoose() throws CommandException {
		List<Player> playerList = new ArrayList<Player>();
		playerList.add(new Player("urim", 60));
		List<String> cmdOperation = new ArrayList<String>();
		cmdOperation.add("move");
		cmdOperation.add("urim");
		cmdOperation.add("3");
		board.setPlayer(playerList);
		board.playerList = playerList;
		board.setActivePlayer(new Player("Urim", 60));
		board.movePlayer(cmdOperation);
		assertTrue(outContent.toString().contains("     urim won the game "));
	}

	@Test
	public void movePlayerGooseCell() throws CommandException {
		List<Player> playerList = new ArrayList<Player>();
		playerList.add(new Player("urim", 0));
		List<String> cmdOperation = new ArrayList<String>();
		cmdOperation.add("move");
		cmdOperation.add("urim");
		cmdOperation.add("5");
		board.setPlayer(playerList);
		board.playerList = playerList;
		board.setActivePlayer(new Player("Urim", 0));
		board.movePlayer(cmdOperation);
		assertTrue(outContent.toString().contains("10"));
	}

	@Test
	public void movePlayerBridgeCell() throws CommandException {
		List<Player> playerList = new ArrayList<Player>();
		playerList.add(new Player("urim", 0));
		List<String> cmdOperation = new ArrayList<String>();
		cmdOperation.add("move");
		cmdOperation.add("urim");
		cmdOperation.add("7");
		board.setPlayer(playerList);
		board.playerList = playerList;
		board.setActivePlayer(new Player("Urim", 0));
		board.movePlayer(cmdOperation);
		assertTrue(outContent.toString().contains("12"));
	}

	@Test
	public void movePlayerSwapCells() throws CommandException {
		List<Player> playerList = new ArrayList<Player>();
		playerList.add(new Player("urim", 0));
		playerList.add(new Player("mandi", 0));
		List<String> cmdOperation = new ArrayList<String>();
		cmdOperation.add("move");
		cmdOperation.add("urim");
		cmdOperation.add("7");
		board.setPlayer(playerList);
		board.setActivePlayer(new Player("urim", 0));
		board.movePlayer(cmdOperation);
		List<String> cmdOperation1 = new ArrayList<String>();
		cmdOperation1.add("move");
		cmdOperation1.add("mandi");
		cmdOperation1.add("7");
		board.setActivePlayer(new Player("mandi", 0));
		board.movePlayer(cmdOperation1);
		assertTrue(outContent.toString().contains("Player urim return from 12 --> 0"));
	}
}
