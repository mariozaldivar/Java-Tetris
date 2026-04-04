package Tetris;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class Piece {

  public int[][] shape;
  public int row = 0;
  public int col;
  public int offset;
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
          { 0, 0, 0 },
          { 3, 3, 0 },
          { 0, 3, 3 }
      },

      {
          { 0, 0, 0 },
          { 0, 4, 4 },
          { 4, 4, 0 }
      },
      {
          { 0, 0, 0 },
          { 5, 0, 0 },
          { 5, 5, 5 }
      },
      {
          { 0, 0, 0 },
          { 0, 0, 6 },
          { 6, 6, 6 }
      },
      {
          { 0, 0, 0 },
          { 0, 7, 0 },
          { 7, 7, 7 }
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
    this.offset = checkOffset(this.shape);
  }

  public int checkOffset(int[][] figure) {
    int currentOffset = 0;
    int len = figure.length - 1;
    for (int i = 0; i < figure.length; i++) {
      for (int j = 0; j < figure.length; j++) {
        if (this.shape[len - i][j] > 0) {
          return currentOffset;
        }

      }
      currentOffset++;
    }
    return currentOffset;
  }

  public void main() {

    Clock.INSTANCE.printIntMatrix(this.shape);
    this.rotate();
    Clock.INSTANCE.printIntMatrix(this.shape);
  }

}
