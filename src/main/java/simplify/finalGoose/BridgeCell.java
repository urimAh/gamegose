package simplify.finalGoose;

public class BridgeCell extends Cell {
	int cellIndex = 0;
	boolean isBusy = false;
	Player playerInCell;

	public BridgeCell(int cellIndex) {
		this.cellIndex = cellIndex;
	}

	public boolean isBusy() {
		return isBusy;
	}

	public int movePlayer(Player player, int movingIndex) {
		System.out.println("----Player Passed Bridge----");
		player.setCellIndex(12);
		return player.getCellIndex();

	}	public int getDestInx(Player player, int movingIndex) {
		return 12;
	}

	public Player getPlayer() {
		return this.playerInCell;

	}

	public void setPlayer(Player player) {
		this.playerInCell = player;
	}

	@Override
	void setBusy(boolean isBusy) {
		this.isBusy = isBusy;

	}
}
