package Tetris;

import java.util.ArrayList;
import java.util.Random;

public class Piece {

    public int[][] shape;
    public int row = 0;
    public int col;
    public int offset;
    Random random = new Random();
    public int[][][] allShapes =
            {
                    {
                        {1, 1},
                        {1, 1}
                    },


                    {
                            {0, 0, 0, 0},
                            {2, 2, 2, 2},
                            {0, 0, 0, 0},
                            {0, 0, 0, 0}
                    },

                    {
                            {0, 0, 0},
                            {3, 3, 0},
                            {0, 3, 3}
                    },

                    {
                            {0, 0, 0},
                            {0, 4, 4},
                            {4, 4, 0}
                    },
                    {
                            {0, 0, 0},
                            {5, 0, 0},
                            {5, 5, 5}
                    },
                    {
                            {0, 0, 0},
                            {0, 0, 6},
                            {6, 6, 6}
                    },
                    {
                            {0, 0, 0},
                            {0, 7, 0},
                            {7, 7, 7}
                    },

            };

    Piece() {
        int newSelect = random.nextInt(7);
        this.shape = this.allShapes[newSelect];
        switch (this.shape.length) {
            case 2:
                this.col = 4;
                break;
            case 3:
                this.col = 3;
                break;
            case 4:
                this.col = 3;
                break;
        }
        this.checkOffset();
    }
    public void checkOffset() {
        this.offset = 0;
        int len = this.shape.length - 1;
        for (int i = 0; i < this.shape.length; i++) {
            for (int j = 0; j < this.shape.length; j++) {
                if (this.shape[len - i][j] > 0) {
                    return;
                }

            }
            this.offset++;
        }
    }

    public void rotate() {
        int[][] buffer = Clock.INSTANCE.copyIntMatrix(this.shape);
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                buffer[j][(shape.length - 1) - i] = this.shape[i][j];
                // Si haces la rotación de una pieza en sentido antihorario, puedes notar que
                // las filas se intercambian por las columnas, y lsa columnas se invierten.
            }
        }
        this.shape = buffer;
        this.checkOffset();
    }

    public void main() {

        Clock.INSTANCE.printIntMatrix(this.shape);
        this.rotate();
        Clock.INSTANCE.printIntMatrix(this.shape);
    }


}
