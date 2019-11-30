package simplify.finalGoose;

public class NrmCell extends Cell {
	int cellIndex = 0;
	boolean isBusy = false;
	Player playerInCell;

	public NrmCell(int cellIndex) {
		this.cellIndex = cellIndex;
	}

	public boolean isBusy() {
		return isBusy;
	}

	public int movePlayer(Player player, int movingIndex) {
		player.setCellIndex(movingIndex + player.getCellIndex());
		return player.getCellIndex();
	}
	
	public int getDestInx(Player player, int movingIndex) {
		return player.getCellIndex()+movingIndex;
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
