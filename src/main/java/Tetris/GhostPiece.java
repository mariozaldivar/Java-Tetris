package Tetris;

public class GhostPiece extends AbstractPiece {
  public int[][] shape;
  public int row;
  public int col;
  public int size;

  public void makeGhostPiece(Piece piece) {
    shape = new int[piece.size][piece.size];
    for (int i = 0; i < piece.size; i++) {
      for (int j = 0; j < piece.size; j++) {
        if (piece.shape[i][j] != 0) {
          this.shape[i][j] = 0 - piece.shape[i][j];
        }
      }
    }
    this.size = piece.size;
    this.row = piece.row;
    this.col = piece.col;

  }

  @Override
  public void reset() {
    this.shape = null;
    this.row = 0;
    this.col = 0;
    this.size = 0;
  }

}
