package Tetris;

public class Board {
  public static final int BOARD_HEIGHT = 20;
  public static final int BOARD_WIDTH = 10;
  public int[][] board = new int[BOARD_HEIGHT][BOARD_WIDTH]; // Declara un board 10x20 (con 2 de overhead) y lo
                                                             // inicializa en 0s
  public Piece currentPiece = new Piece();

  /*
   * Cosas necesarias para entender este script:
   * Funciones lambda: Es un tipo especial de función que se caracteriza por ser
   * creada rápidamente para no tener que pasar
   * por todo el proceso de declararla (normalmente se usa para cuando necesitas
   * que algo funcione de forma
   * parecida al formato de una función, pero no puedes o no quieres declararla
   * como tal)
   * la sintaxis es así: (parametros) -> { código de la función aquí}
   */

  private void getNewPiece() {
    this.currentPiece = new Piece();

    // Antes de dibujar la nueva pieza, revisa si no hay nada en donde debería
    // dibujarse por default. En caso de que si, llama a GameOver
    if (canBeDrawn(this.currentPiece.row, this.currentPiece.col)) {
      drawCurrentPiece(this.currentPiece.row, this.currentPiece.col);
    } else {
      GameOver();
    }
  }

  public void undrawCurrentPiece() {
    int pieceSize = this.currentPiece.size;
    for (int i = 0; i < pieceSize; i++) {
      for (int j = 0; j < pieceSize; j++) {
        this.board[currentPiece.row + i][currentPiece.col + j] = 0;
      }
    }
    currentPiece.drawn = false;
  }

  public boolean canBeDrawn(int row, int col) {
    undrawCurrentPiece();
    int pieceSize = this.currentPiece.size;
    for (int i = 0; i < pieceSize; i++)
      for (int j = 0; j < pieceSize; j++) {
        if (row + (pieceSize - 1) < BOARD_HEIGHT || this.currentPiece.shape[i][j] == 0) {
          if (this.currentPiece.shape[i][j] != 0
              && this.board[row + i][col + j] != 0) {
            return false;
          }
        } else {
          System.out.println("La pieza va a salir del tablero");
        }
      }
  }

  public void drawCurrentPiece(int col, int row) {
    for (int i = 0; i < this.currentPiece.size; i++) {
      for (int j = 0; j < this.currentPiece.size; j++) {

      }
    }
    this.currentPiece.drawn = true;
  }

  public void pieceFall() {
    if (canBeDrawn(this.currentPiece.row + 1, this.currentPiece.col)) {
      drawCurrentPiece(this.currentPiece.row + 1, this.currentPiece.col);
    } else {
      getNewPiece();
    }

  }

  public void GameOver() {

  }

  Board() {
  }
}
