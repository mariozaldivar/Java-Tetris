package Tetris;

public class Board {

  public int[][] board = new int[22][10]; // Declara un board 10x20 (con 2 de overhead) y lo inicializa en 0s
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

  public void generatePiece() {

    Piece piece = new Piece();
    // Para cada recuadro de la pieza
    for (int i = piece.offset; i < piece.shape.length; i++) {
      for (int j = 0; j < piece.shape.length; j++) {
        // Si alguna de las coordenadas en el mapa donde la nueva pieza ocuparía un
        // recuadro está ocupada
        if (board[piece.row + (piece.shape.length - 1 - i)][piece.col + j] != 0
            && piece.shape[(piece.shape.length - 1 - i)][j] != 0) {
          System.out.println("No se puede generar una nueva pieza porque hay una pieza en las coordenadas row: "
              + piece.row + i + "y col: " + piece.col + j);
          GameOver();
          return;
        }
      }

    }
    System.out.println("Se ha generado una nueva pieza");
    this.currentPiece = piece;
  }

  Board() {
    generatePiece();
    Clock.INSTANCE.suscribe(this::hardDrop);

  }

  public void lowerAndGeneratePiece() {
    if (lowerPiece() == false) {
      generatePiece();
    }
  }

  public boolean lowerPiece() {
    if (checkMoveDown(this.currentPiece)) {
      int len = this.currentPiece.shape.length;
      int row = this.currentPiece.row;
      int col = this.currentPiece.col;
      // Para cada fila que no sea solo una fila de ceros (cada fila después del
      // offset)
      for (int i = this.currentPiece.offset; i < len; i++) {
        for (int j = 0; j < len; j++) {
          int currentRow = (row + len) - i;
          int currentCol = col + j;
          // Cada coordenada del tablero donde estaba la pieza antes (ahora con la fila +
          // 1)
          // = cada recuadro de la pieza, empezando desde la fila de hasta abajo
          this.board[currentRow + 1][currentCol] = this.currentPiece.shape[len - 1 - i][j];
          // Despues de bajar la fila a la siguiente, la posición donde estaba antes = 0
          this.board[currentRow][currentCol] = 0;
        }
        System.out.println("Se está checando la row: " + (row + len - i));
      }

      currentPiece.row++;
      // System.out.println("Y: " + piece.row + "| X: " + piece.col);
      // Clock.INSTANCE.printIntMatrix(piece.shape);
      printBoard();
      return true;

    } else {
      System.out.println("La pieza no puede bajar");
      return false;
    }
  }

  /*
   * public void rotatePiece() {
   * // Lógica para esta función:
   * // Crear shapeBuffer, que es la forma de la pieza, pero rotada, y registrar
   * su offset
   * // Borrar la pieza original del mapa (hacer un for, y hacer que todas las
   * coordenadas donde la pieza debería
   * // tener pieza sean igual a cero)
   * // Revisar el offset de la pieza buffer, restarle el offset de la rotación
   * anterior, y ajustar las coordenadas
   * // que se van a revisar utilizando el offset ajustado
   * // Revisar si no hay colisiones con la posición en la que quedaría la pieza
   * rotada,
   * // detectar el caso de que la pieza salga del mapa, e implementar wall bounce
   * // En caso de que la rotación no sea posible, volver a dibujar la pieza
   * original en el tablero
   * 
   * if (checkRotationWithBuffer()) {
   * // TODO: Revisar el funcionamiento de esta función
   * int[][] buffer = Clock.INSTANCE.copyIntMatrix(this.currentPiece.shape);
   * int bufferOffset;
   * for (int i = 0; i < this.currentPiece.shape.length; i++) {
   * for (int j = 0; j < this.currentPiece.shape[i].length; j++) {
   * buffer[j][(this.currentPiece.shape.length - 1) - i] =
   * this.currentPiece.shape[i][j];
   * // Si haces la rotación de una pieza en sentido antihorario, puedes notar que
   * // las filas se intercambian por las columnas, y lsa columnas se invierten.
   * }
   * }
   * bufferOffset = this.currentPiece.checkOffset(buffer);
   * 
   * for (int i = 0; i < buffer.length - 1; i++) {
   * for (int j = 0; j < buffer.length - 1; j++) {
   * if ((board[this.currentPiece.row + i][this.currentPiece.col + j] != 0) &&
   * (buffer[i][j] != 0)) {
   * 
   * 
   * }
   * }
   * }
   * 
   * }
   */
  public int[][] getBoardState() {
    return Clock.INSTANCE.copyIntMatrix(this.board);
  }

  /*
   * public void rotate() {
   * int[][] buffer = Clock.INSTANCE.copyIntMatrix(this.shape);
   * for (int i = 0; i < shape.length; i++) {
   * for (int j = 0; j < shape[i].length; j++) {
   * buffer[j][(shape.length - 1) - i] = this.shape[i][j];
   * // Si haces la rotación de una pieza en sentido antihorario, puedes notar que
   * // las filas se intercambian por las columnas, y lsa columnas se invierten.
   * }
   * }
   * this.shape = buffer;
   * this.offset = checkOffset(buffer);
   * }
   */

  /*
   * public void movePieceLeft() {
   * if (!(this.currentPiece.col - 1 < 0)) {
   * for (int i = 0; i < this.currentPiece.shape.length; i++) {
   * // Revisar si hay alguna pieza en la col-1, si hay alguna pieza en la misma
   * área de la figura, y si la pieza en el
   * // área de la figura forma parte del shape
   * if (this.board[this.currentPiece.row + i][this.currentPiece.col - 1] != 0 &&
   * this.currentPiece.shape[i]) {
   * 
   * }
   * }
   * }
   * else { return; }
   * }
   * 
   * public void movePieceRight() {
   * 
   * }
   */

  public void hardDrop() {
    while (lowerPiece()) {
      assert true;
    }
    lowerAndGeneratePiece();
  }

  private boolean checkMoveDown(Piece piece) {

    if ((piece.row - piece.offset) + 1 + (piece.shape.length - 1) < 21) {
      for (int j = 0; j < piece.shape.length; j++) {
        if ((!(this.board[piece.row + piece.shape.length - piece.offset + 1][piece.col + j] == 0))
            && (this.board[piece.row + piece.shape.length - piece.offset][piece.col + j] != 0)) {
          System.out.println("Hay algun cuadrado abajo que no es 0");
          return false;
        }
      }
      System.out.println("La pieza si debería poderse mover");
      return true;
    } else {
      System.out.println("La pieza tocó fondo");
      return false;
    }
  }

  public void printBoard() {
    Clock.INSTANCE.printIntMatrix(this.board);
    System.out.println();
  }

  public void main(String[] args) {

    Clock.INSTANCE.startGame();

    while (Clock.INSTANCE.playing) {
    }
    return;
  }

  public void GameOver() {
    Clock.INSTANCE.gameOver();
    Clock.INSTANCE.unsubscribe(this::lowerPiece);
  }
}
