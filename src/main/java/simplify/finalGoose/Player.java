package simplify.finalGoose;

public class Player{
	String playerName;
	int cellIndex;
	int listIndex;
	public Player(String playerName, int cellIndex) {
		super();
		this.playerName = playerName;
		this.cellIndex = cellIndex;
	}
	public int getListIndex() {
		return listIndex;
	}

	public void setListIndex(int listIndex) {
		this.listIndex = listIndex;
	}

	public Player(String playerName) {
		this.playerName = playerName;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public int getCellIndex() {
		return cellIndex;
	}

	public void setCellIndex(int cellIndex) {
		this.cellIndex = cellIndex;
	}

}
