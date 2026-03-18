package Tetris;

public class Board {


    /*
    Cosas necesarias para entender este script:
    Funciones lambda: Es un tipo especial de función que se caracteriza por ser creada rápidamente para no tener que pasar
    por todo el proceso de declararla (normalmente se usa para cuando necesitas que algo funcione de forma
    parecida al formato de una función, pero no puedes o no quieres declararla como tal)
    la sintaxis es así: (parametros) -> { código de la función aquí}
     */

    public int[][] board = new int[20][10]; // Declara un board 10x20 y lo inicializa en 0s
    public Piece currentPiece = new Piece();
    Runnable currentLower;
    void probarTick() { System.out.println("La clase Board está reconociendo el Tick"); }

    public Piece generatePiece() { return new Piece(); }

    public void lowerPiece(Piece piece) {
        if (checkMoveDown(this.currentPiece)) {
            int len = this.currentPiece.shape.length;
            int row = this.currentPiece.row;
            int col = this.currentPiece.col;

            for (int i = this.currentPiece.offset; i < len; i++) {
                for (int j = 0; j < len; j++)  {
                    this.board[(row + len) - i + 1][col + j] = this.currentPiece.shape[len - 1 - i][j];
                    this.board[row + len  - i][col + j] = 0;
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
            this.currentPiece = generatePiece();
        }
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
    Board() {
        currentPiece = generatePiece();
        Runnable currentLower = () -> {lowerPiece(currentPiece);};
        Clock.INSTANCE.suscribe(currentLower);
    }


    public void main(String[] args) {

    Clock.INSTANCE.startGame();

    while (true) {
            assert true;

        }
    }

}
