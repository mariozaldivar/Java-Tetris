package Tetris;

public class Game {
  public static final int BOARD_HEIGHT = 20;
  public static final int BOARD_WIDTH = 10;
  public int[][] board = new int[BOARD_HEIGHT][BOARD_WIDTH]; // Declara un board 10x20 (con 2 de overhead) y lo
                                                             // inicializa en 0s
  public Piece currentPiece = new Piece();
  public GhostPiece ghostPiece = new GhostPiece();
  public Piece holdPiece;
  public boolean holdedThisTurn;

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

  // Revisa si una línea está comleta. Si hay algún cero en alguna parte de la
  // línea,
  // quiere decir que no está completa
  public boolean checkIfCleared(int row) {
    for (int j = 0; j < BOARD_WIDTH; j++) {
      if (this.board[row][j] <= 0) {
        return false;
      }
    }
    return true;
  }

  public void checkLineClears() {
    for (int i = BOARD_HEIGHT - 1; i >= 0; i--) {
      // Empezando desde abajo, revisa si alguna línea está completa
      if (checkIfCleared(i)) {
        // Si si está, va uno por uno copiando el valor de la línea de arriba de la
        // actual, y cuando llega a la última, la vuelve toda ceros
        removeFullRow(i);
        i = BOARD_HEIGHT;
      }
      System.out.println("Board being checked: ");
      Clock.INSTANCE.printIntMatrix(this.board);

      System.out.println("i se revisó para los valores: " + i);
    }
  }

  public void removeFullRow(int fullRow) {
    for (int currentRow = fullRow; currentRow > 0; currentRow--) {
      for (int j = 0; j < BOARD_WIDTH; j++) {

        this.board[currentRow][j] = this.board[currentRow - 1][j];
      }
      for (int j = 0; j < BOARD_WIDTH; j++) {
        this.board[0][j] = 0;

      }
    }
  }

  private void getNewPiece() {
    checkLineClears();
    this.currentPiece = new Piece();

    // Antes de dibujar la nueva pieza, revisa si no hay nada en donde debería
    // dibujarse por default. En caso de que si, llama a GameOver

    // Importante notar que CanBeDrawn elimina la pieza del tablero, pero no la
    // redibuja
    if (canBeDrawnWithoutUndrawing(this.currentPiece.row, this.currentPiece.col, this.currentPiece.shape)) {
      drawCurrentPiece(this.currentPiece.row, this.currentPiece.col);
      calculateGhostPiece();
    } else {
      System.out.println("Esta pieza tiene una pieza debajo");
      GameOver();
    }
    this.holdedThisTurn = false;
  }

  public void undrawPiece(int row, int col, int[][] shape) {
    int pieceSize = shape.length;
    for (int i = 0; i < pieceSize; i++) {
      for (int j = 0; j < pieceSize; j++) {
        if (shape[i][j] != 0 && this.board[row + i][col + j] == shape[i][j]) {
          this.board[row + i][col + j] = 0;
        }
      }
    }
    currentPiece.drawn = false;
  }

  public boolean canBeDrawn(int row, int col, int[][] shape) {
    System.out.println("Se está checando si se puede dibujar en, row: " + row + "  col: " + col);
    undrawPiece(this.currentPiece.row, this.currentPiece.col, this.currentPiece.shape);
    int pieceSize = this.currentPiece.size;
    for (int i = 0; i < pieceSize; i++) {
      for (int j = 0; j < pieceSize; j++) {
        if (shape[i][j] != 0) {
          // inBounds es un solo valor booleano que verifica que el row y col dado estén
          // dentro del tablero
          boolean inBounds = ((row + i) >= 0 && (row + i) < BOARD_HEIGHT && (col + j) >= 0 && (col + j) < BOARD_WIDTH);
          if (inBounds) {
            if (shape[i][j] != 0 && this.board[row + i][col + j] > 0) {
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

  public boolean canBeDrawnWithoutUndrawing(int row, int col, int[][] shape) {

    int pieceSize = this.currentPiece.size;
    for (int i = 0; i < pieceSize; i++) {
      for (int j = 0; j < pieceSize; j++) {
        if (shape[i][j] != 0) {
          // inBounds es un solo valor booleano que verifica que el row y col dado estén
          // dentro del tablero
          boolean inBounds = ((row + i) >= 0 && (row + i) < BOARD_HEIGHT && (col + j) >= 0 && (col + j) < BOARD_WIDTH);
          if (inBounds) {
            if (shape[i][j] != 0 && this.board[row + i][col + j] > 0) {
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

  public boolean canGhostPieceBeDrawn(int row, int col, int[][] shape) {
    int pieceSize = shape.length;
    for (int i = 0; i < pieceSize; i++) {
      for (int j = 0; j < pieceSize; j++) {
        if (shape[i][j] != 0) {
          // inBounds es un solo valor booleano que verifica que el row y col dado estén
          // dentro del tablero
          boolean inBounds = ((row + i) >= 0 && (row + i) < BOARD_HEIGHT && (col + j) >= 0 && (col + j) < BOARD_WIDTH);
          if (inBounds) {
            if (shape[i][j] != 0 && this.board[row + i][col + j] > 0) {
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

  public void calculateGhostPiece() {

    undrawPiece(this.currentPiece.row, this.currentPiece.col, this.currentPiece.shape);
    if (ghostPiece.shape != null) {

      undrawPiece(this.ghostPiece.row, this.ghostPiece.col, this.ghostPiece.shape);
    }

    ghostPiece.makeGhostPiece(this.currentPiece);
    while (canGhostPieceBeDrawn(this.ghostPiece.row + 1, this.ghostPiece.col, this.ghostPiece.shape)) {
      this.ghostPiece.row++;
    }
    drawGhostPiece(this.ghostPiece.row, this.ghostPiece.col, this.ghostPiece.shape);
    drawCurrentPiece(this.currentPiece.row, this.currentPiece.col);

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

  public void drawGhostPiece(int row, int col, int[][] shape) {
    this.ghostPiece.row = row;
    this.ghostPiece.col = col;
    for (int i = 0; i < this.ghostPiece.size; i++) {
      for (int j = 0; j < this.ghostPiece.size; j++) {
        if (this.ghostPiece.shape[i][j] != 0) {
          this.board[row + i][col + j] = this.ghostPiece.shape[i][j];
        }
      }
    }
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

  public void pieceRotate() {
    int[][] rotation = this.currentPiece.getNextRotation();
    if (canBeDrawn(this.currentPiece.row, this.currentPiece.col, rotation)) {
      this.currentPiece.shape = rotation;
      drawCurrentPiece(this.currentPiece.row, this.currentPiece.col);
      calculateGhostPiece();
    } else {
      drawCurrentPiece(this.currentPiece.row, this.currentPiece.col);
    }

  }

  public void movePiece(int plusrow, int pluscol) {
    int targetRow = this.currentPiece.row + plusrow;
    int targetCol = this.currentPiece.col + pluscol;
    if (canBeDrawn(targetRow, targetCol, this.currentPiece.shape)) {
      drawCurrentPiece(targetRow, targetCol);
      calculateGhostPiece();
    } else {
      drawCurrentPiece(this.currentPiece.row, this.currentPiece.col);
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

  public void holdPiece() {
    if (!holdedThisTurn) {
      if (this.holdPiece != null) {
        Piece buffer;
        buffer = this.currentPiece;
        undrawPiece(this.currentPiece.row, this.currentPiece.col, this.currentPiece.shape);
        this.currentPiece = this.holdPiece;

        this.holdPiece = buffer;

        this.currentPiece.reset();
        this.holdPiece.reset();
        drawCurrentPiece(this.currentPiece.row, this.currentPiece.col);
        calculateGhostPiece();
        holdedThisTurn = true;
      } else {
        undrawPiece(this.currentPiece.row, this.currentPiece.col, this.currentPiece.shape);
        this.holdPiece = this.currentPiece;
        this.holdPiece.reset();

        this.currentPiece = new Piece();

        if (canBeDrawnWithoutUndrawing(this.currentPiece.row, this.currentPiece.col, this.currentPiece.shape)) {
          drawCurrentPiece(this.currentPiece.row, this.currentPiece.col);
          calculateGhostPiece();
          holdedThisTurn = true;
        } else {
          System.out.println("Esta pieza tiene una pieza debajo");
          GameOver();
        }
      }
    }
  }

  public void GameOver() {
    System.out.println("GAME IS SUPPOSED TO BE OVER!!!!!");

    Clock.INSTANCE.unsubscribe(this::pieceFall);
    Clock.INSTANCE.gameOver();

  }

  public int[][] getBoardState() {
    return Clock.INSTANCE.copyIntMatrix(this.board);
  }

  public void startGame() {
    this.board = new int[BOARD_HEIGHT][BOARD_WIDTH];
    this.holdPiece = null;
    getNewPiece();
    Clock.INSTANCE.suscribe(this::pieceFall);
  }

  Game() {
    getNewPiece();
    Clock.INSTANCE.suscribe(this::pieceFall);
  }
}
