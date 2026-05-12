package Tetris;

public abstract class AbstractPiece {
  public int[][] shape;
  public int row;
  public int col;
  public int size;

  public abstract void reset();

  public boolean hasShape() {
    return this.shape != null;
  }
}
