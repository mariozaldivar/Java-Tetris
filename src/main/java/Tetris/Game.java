package Tetris;

public class Game {
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
   *
   *
   */

  private void getNewPiece() {
    this.currentPiece = new Piece();

    // Antes de dibujar la nueva pieza, revisa si no hay nada en donde debería
    // dibujarse por default. En caso de que si, llama a GameOver
    if (canBeDrawn(this.currentPiece.row, this.currentPiece.col, this.currentPiece.shape)) {
      drawCurrentPiece(this.currentPiece.row, this.currentPiece.col);
    } else {
      System.out.println("Esta pieza tiene una pieza debajo");
      GameOver();
    }
  }

  public void undrawCurrentPiece() {
    int pieceSize = this.currentPiece.size;
    for (int i = 0; i < pieceSize; i++) {
      for (int j = 0; j < pieceSize; j++) {
        if (this.currentPiece.shape[i][j] != 0) {
          this.board[currentPiece.row + i][currentPiece.col + j] = 0;
        }
      }
    }
    currentPiece.drawn = false;
  }

  public boolean canBeDrawn(int row, int col, int[][] shape) {
    System.out.println("Se está checando si se puede dibujar en, row: " + row + "  col: " + col);
    undrawCurrentPiece();
    int pieceSize = this.currentPiece.size;
    for (int i = 0; i < pieceSize; i++) {
      for (int j = 0; j < pieceSize; j++) {
        if (shape[i][j] != 0) {
          // inBounds es un solo valor booleano que verifica que el row y col dado estén
          // dentro del tablero
          boolean inBounds = ((row + i) >= 0 && (row + i) < BOARD_HEIGHT && (col + j) >= 0 && (col + j) < BOARD_WIDTH);
          if (inBounds) {
            if (shape[i][j] != 0 && this.board[row + i][col + j] != 0) {
              return false;
            }
          } else {
            return false;
          }
        }
      }
    }
    return true;

  }

  // Checar que pasa cuando
  public void drawCurrentPiece(int row, int col) {
    this.currentPiece.row = row;
    this.currentPiece.col = col;
    for (int i = 0; i < this.currentPiece.size; i++) {
      for (int j = 0; j < this.currentPiece.size; j++) {
        if (this.currentPiece.shape[i][j] != 0) {
          this.board[row + i][col + j] = this.currentPiece.shape[i][j];
        }
      }
    }
    this.currentPiece.drawn = true;
  }

  public void pieceFall() {
    if (canBeDrawn(this.currentPiece.row + 1, this.currentPiece.col, this.currentPiece.shape)) {
      drawCurrentPiece(this.currentPiece.row + 1, this.currentPiece.col);
    } else {
      drawCurrentPiece(this.currentPiece.row, this.currentPiece.col);
      // TODO: Aquí implementar chequeo de líneas
      getNewPiece();
    }
  }

  public void hardDrop() {

    while (canBeDrawn(this.currentPiece.row + 1, this.currentPiece.col, this.currentPiece.shape)) {
      drawCurrentPiece(this.currentPiece.row + 1, this.currentPiece.col);
    }
    drawCurrentPiece(this.currentPiece.row, this.currentPiece.col);
    // TODO: Aquí implementar chequeo de líneas
    getNewPiece();

  }

  public void GameOver() {
    System.out.println("GAME IS SUPPOSED TO BE OVER!!!!!");
    Clock.INSTANCE.unsubscribe(this::pieceFall);

  }

  public int[][] getBoardState() {
    return Clock.INSTANCE.copyIntMatrix(this.board);
  }

  Game() {
    getNewPiece();
    Clock.INSTANCE.suscribe(this::pieceFall);
  }
}
