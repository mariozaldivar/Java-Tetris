import java.util.ArrayList;
import java.util.Random;

public class Piece {

    public int[][] shape;
    Random random = new Random();
    public int[][][] allShapes =
            {
                    {
                        {1, 1},
                        {1, 1}
                    },


                    {
                            {0, 0, 0, 0},
                            {1, 1, 1, 1},
                            {0, 0, 0, 0},
                            {0, 0, 0, 0}
                    },

                    {
                            {0, 0, 0},
                            {1, 1, 0},
                            {0, 1, 1}
                    },

                    {
                            {0, 0, 0},
                            {0, 1, 1},
                            {1, 1, 0}
                    },
                    {
                            {0, 0, 0},
                            {1, 0, 0},
                            {1, 1, 1}
                    },
                    {
                            {0, 0, 0},
                            {0, 0, 1},
                            {1, 1, 1}
                    },
                    {
                            {0, 0, 0},
                            {0, 1, 0},
                            {1, 1, 1}
                    },

            };

    Piece() {
        int newSelect = random.nextInt(7);
        this.shape = this.allShapes[newSelect];
    }

    public void rotate() {

    }

    public void main()
    {

    }


}
