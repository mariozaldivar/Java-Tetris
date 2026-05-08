package Tetris;

public class Game {

  // ════════════════════════════════════════════════
  // [PUNTUACIÓN - LUGAR 1] Variables de estado del sistema de puntuación.
  // Viven aquí en Game porque es esta clase la que sabe cuándo y cuántas
  // líneas se borran. No hace falta una clase aparte para esto.
  // ════════════════════════════════════════════════
  private int score = 0;
  private int level = 1;
  private int totalLinesCleared = 0;
  // ════════════════════════════════════════════════

  public static final int BOARD_HEIGHT = 20;
  public static final int BOARD_WIDTH = 10;
  public int[][] board = new int[BOARD_HEIGHT][BOARD_WIDTH];
  public Piece currentPiece = new Piece();
  public GhostPiece ghostPiece = new GhostPiece();

  /*
   * Cosas necesarias para entender este script:
   * Funciones lambda: Es un tipo especial de función que se caracteriza por ser
   * creada rápidamente para no tener que pasar por todo el proceso de declararla
   * (normalmente se usa para cuando necesitas que algo funcione de forma
   * parecida al formato de una función, pero no puedes o no quieres declararla
   * como tal)
   * la sintaxis es así: (parametros) -> { código de la función aquí}
   */

  // Revisa si una línea está completa. Si hay algún cero en alguna parte de la
  // línea, quiere decir que no está completa
  public boolean checkIfCleared(int row) {
    for (int j = 0; j < BOARD_WIDTH; j++) {
      if (this.board[row][j] <= 0) {
        return false;
      }
    }
    return true;
  }

  public void checkLineClears() {
    int linesRemoved = 0; // Contador local de líneas borradas en este turno

    for (int i = BOARD_HEIGHT - 1; i >= 0; i--) {
      if (checkIfCleared(i)) {
        removeFullRow(i);
        linesRemoved++;
        i = BOARD_HEIGHT;
      }

      System.out.println("Board being checked: ");
      Clock.INSTANCE.printIntMatrix(this.board);
      System.out.println("i se revisó para los valores: " + i);
    }

    // ════════════════════════════════════════════════
    // [PUNTUACIÓN - LUGAR 2] Llamada al cálculo de puntos.
    // Se hace DESPUÉS del loop, no dentro, para que se llame UNA sola vez
    // por turno con el total de líneas borradas en ese turno.
    // Si no se borró nada, no se llama.
    // ════════════════════════════════════════════════
    if (linesRemoved > 0) {
      calcularPuntos(linesRemoved);
    }
    // ════════════════════════════════════════════════
  }

  // ════════════════════════════════════════════════
  // [PUNTUACIÓN - LUGAR 3A] Método que aplica la tabla de puntos de la imagen.
  // Recibe cuántas líneas se borraron en un turno y actualiza score,
  // totalLinesCleared y level.
  // Es privado porque solo Game necesita llamarlo internamente.
  // ════════════════════════════════════════════════
  private void calcularPuntos(int lineas) {
    // Tabla de puntos según la imagen (nivel siempre es el nivel ANTES del clear):
    // Single  → 100 × nivel
    // Double  → 300 × nivel
    // Triple  → 500 × nivel
    // Tetris  → 800 × nivel
    int puntosGanados = switch (lineas) {
      case 1 -> 100 * level;
      case 2 -> 300 * level;
      case 3 -> 500 * level;
      case 4 -> 800 * level; // ¡Tetris!
      default -> 0;
    };

    this.score += puntosGanados;
    this.totalLinesCleared += lineas;

    // Criterio de subida de nivel: cada 10 líneas borradas sube un nivel
    if (this.totalLinesCleared >= this.level * 10) {
      this.level++;
    }
  }

  // ════════════════════════════════════════════════
  // [PUNTUACIÓN - LUGAR 3B] Getters para que GUI pueda leer los valores
  // sin poder modificarlos directamente (buena práctica de encapsulamiento).
  // ════════════════════════════════════════════════
  public int getScore() { return score; }
  public int getLevel() { return level; }
  // ════════════════════════════════════════════════

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

    if (canBeDrawnWithoutUndrawing(this.currentPiece.row, this.currentPiece.col, this.currentPiece.shape)) {
      drawCurrentPiece(this.currentPiece.row, this.currentPiece.col);
      calculateGhostPiece();
    } else {
      System.out.println("Esta pieza tiene una pieza debajo");
      GameOver();
    }
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
          boolean inBounds = ((row + i) >= 0 && (row + i) < BOARD_HEIGHT
                  && (col + j) >= 0 && (col + j) < BOARD_WIDTH);
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
          boolean inBounds = ((row + i) >= 0 && (row + i) < BOARD_HEIGHT
                  && (col + j) >= 0 && (col + j) < BOARD_WIDTH);
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
          boolean inBounds = ((row + i) >= 0 && (row + i) < BOARD_HEIGHT
                  && (col + j) >= 0 && (col + j) < BOARD_WIDTH);
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
    getNewPiece();
  }

  public void GameOver() {
    System.out.println("GAME IS SUPPOSED TO BE OVER!!!!!");
    Clock.INSTANCE.unsubscribe(this::pieceFall);
  }

  public int[][] getBoardState() {
    return Clock.INSTANCE.copyIntMatrix(this.board);
  }

  // [PUNTUACIÓN] El constructor ya no recibe ScoreManager porque Game
  // maneja su propio estado de puntuación internamente.
  Game() {
    getNewPiece();
    Clock.INSTANCE.suscribe(this::pieceFall);
  }
}