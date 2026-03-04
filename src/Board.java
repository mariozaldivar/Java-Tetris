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


    void probarTick() {
        System.out.println("La clase Board está reconociendo el Tick");
    }

    public Piece generatePiece() {
        return new Piece();
    }
    public void lowerPiece(Piece piece) {
    System.out.println("The board is lowering the Piece");
    }

    Board() {
        Clock.INSTANCE.suscribe(() -> {lowerPiece(currentPiece);});
    }

    public void main(String[] args) {

    Clock.INSTANCE.startGame();

    while (true) {
            assert true;
        }
    }

}
