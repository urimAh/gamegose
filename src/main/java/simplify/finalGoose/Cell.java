package simplify.finalGoose;

abstract class Cell {
	public abstract int movePlayer(Player player, int movingIndex);
	public abstract int getDestInx(Player player, int movingIndex);
	abstract boolean isBusy();
	abstract void setBusy(boolean isBusy);
}
