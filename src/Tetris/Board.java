package Tetris;

public class Board {

    public int[][] board = new int[20][10]; // Declara un board 10x20 y lo inicializa en 0s
    public Piece currentPiece = new Piece();

    /*
    Cosas necesarias para entender este script:
    Funciones lambda: Es un tipo especial de función que se caracteriza por ser creada rápidamente para no tener que pasar
    por todo el proceso de declararla (normalmente se usa para cuando necesitas que algo funcione de forma
    parecida al formato de una función, pero no puedes o no quieres declararla como tal)
    la sintaxis es así: (parametros) -> { código de la función aquí}
     */

    public void generatePiece() { 

      Piece piece = new Piece();

      for (int i = 0; i < piece.shape.length; i++) {
        for (int j = 0; j < piece.shape.length; j++) {
          if (board[piece.row + i][piece.col + j] != 0 && piece.shape[i][j] != 0) {   
            System.out.println("No se puede generar una nueva pieza porque hay una pieza en las coordenadas row: " + piece.row+i + "y col: " + piece.row+j);
            GameOver();
            return;
          }
        }

      }
      this.currentPiece = piece;
    }

    Board() {
        generatePiece();
        Clock.INSTANCE.suscribe(this::lowerPiece);
        
    }


    public void lowerPiece() {
        if (checkMoveDown(this.currentPiece)) {
            int len = this.currentPiece.shape.length;
            int row = this.currentPiece.row;
            int col = this.currentPiece.col;

            for (int i = this.currentPiece.offset; i < len; i++) {
                for (int j = 0; j < len; j++)  {
                    int currentRow = (row + len) - i;
                    int currentCol = col + j;
                    this.board[currentRow + 1][currentCol] = this.currentPiece.shape[len - 1 - i][j];
                    this.board[currentRow][currentCol] = 0;
                }
                System.out.println("Se está checando la row: " + (row+len - i));
            }

            currentPiece.row++;
            // System.out.println("Y: " + piece.row + "| X: " + piece.col);
            // Clock.INSTANCE.printIntMatrix(piece.shape);
            printBoard();

        }
        else
        {
            System.out.println("La pieza no puede bajar");
            generatePiece();
        }
    }


    public void rotatePiece() {

      if (checkExactRotation()) {
        this.currentPiece.rotate();
      }

      else {

        // TODO: Introducir Wall Bounce

      }

    }

    public boolean checkExactRotation() {
        // TODO: Revisar el funcionamiento de esta función
        int[][] buffer = Clock.INSTANCE.copyIntMatrix(this.currentPiece.shape);
        for (int i = 0; i < this.currentPiece.shape.length; i++) {
            for (int j = 0; j < this.currentPiece.shape[i].length; j++) {
                buffer[j][(this.currentPiece.shape.length - 1) - i] = this.currentPiece.shape[i][j];
                // Si haces la rotación de una pieza en sentido antihorario, puedes notar que
                // las filas se intercambian por las columnas, y lsa columnas se invierten.
            }
        }

        for (int i = 0; i < buffer.length - 1; i++) {
          for (int j = 0; j < buffer.length - 1; j++) {
            if ((board[this.currentPiece.row + i][this.currentPiece.col + j] != 0) && (buffer[i][j] != 0)) {

              return false;

            }
          }
        }


        return true;
    }



    private boolean checkMoveDown(Piece piece) {

        if ((piece.row - piece.offset) + 1 + (piece.shape.length - 1) < 19) {
            for (int j = 0; j < piece.shape.length; j++ ) {
                if ((!(this.board[piece.row + piece.shape.length - piece.offset + 1][piece.col + j] == 0)) && (this.board[piece.row + piece.shape.length - piece.offset][piece.col + j] != 0) ){
                    System.out.println("Hay algun cuadrado abajo que no es 0");
                    return false;
                }
            }
            System.out.println("La pieza si debería poderse mover");
            return true;
        }
        else {
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

    while (true) {
            assert true;

        }
    }

    public void GameOver() {
        Clock.INSTANCE.gameOver();
        Clock.INSTANCE.unsubscribe(this::lowerPiece);
    }
}
