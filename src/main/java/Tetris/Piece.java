package Tetris;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class Piece {

  public int[][] shape;
  public int row = 0;
  public int col;
  public int size;
  public boolean drawn;
  Random random = new Random();
  public int[][][] allShapes = {
      {
          { 1, 1 },
          { 1, 1 }
      },

      {
          { 0, 0, 0, 0 },
          { 2, 2, 2, 2 },
          { 0, 0, 0, 0 },
          { 0, 0, 0, 0 }
      },

      {
          { 3, 3, 0 },
          { 0, 3, 3 },
          { 0, 0, 0 }
      },

      {
          { 0, 4, 4 },
          { 4, 4, 0 },
          { 0, 0, 0 }
      },
      {
          { 5, 0, 0 },
          { 5, 5, 5 },
          { 0, 0, 0 }
      },
      {
          { 0, 0, 6 },
          { 6, 6, 6 },
          { 0, 0, 0 }
      },
      {
          { 0, 7, 0 },
          { 7, 7, 7 },
          { 0, 0, 0 }
      },

  };

  Piece() {
    int newSelect = random.nextInt(7);
    this.shape = this.allShapes[newSelect];
    this.size = this.shape.length;
    this.drawn = false;
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
  }

  public int[][] getNextRotation() {
    int[][] buffer = new int[this.size][this.size];
    for (int i = 0; i < this.size; i++) {
      for (int j = 0; j < this.size; j++) {
        buffer[i][j] = this.shape[j][(this.size - 1) - i];
      }
    }
    return buffer;
  }

  public void main() {

    Clock.INSTANCE.printIntMatrix(this.shape);
    Clock.INSTANCE.printIntMatrix(this.shape);
  }

}
