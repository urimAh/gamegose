package simplify.finalGoose;

public class GooseCell extends Cell {
	int cellIndex = 0;
	boolean isBusy = false;
	Player playerInCell;

	public GooseCell(int cellIndex) {
		this.cellIndex = cellIndex;
	}

	public boolean isBusy() {
		return isBusy;
	}

	public int movePlayer(Player player, int movingIndex) {
		System.out.println("---Player Passed In Goose----");
		player.setCellIndex(movingIndex * 2 + player.getCellIndex());
		return player.getCellIndex();

	}	public int getDestInx(Player player, int movingIndex) {
		return player.getCellIndex()+movingIndex*2;
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
