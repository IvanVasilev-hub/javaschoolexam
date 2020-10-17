package com.tsystems.javaschool.tasks.pyramid;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PyramidBuilder {

  /**
   * Builds a pyramid with sorted values (with minimum value at the top line and maximum at the bottom,
   * from left to right). All vacant positions in the array are zeros.
   *
   * @param inputNumbers to be used in the pyramid
   * @return 2d array with pyramid inside
   * @throws {@link CannotBuildPyramidException} if the pyramid cannot be build with given input
   */
  public int[][] buildPyramid(List<Integer> inputNumbers) {
    double rows = numOfRows(inputNumbers.size());
    int cols = numOfColumns(inputNumbers.size());

    if (!isInputCorrect(rows) || inputNumbers.contains(null)) {
      throw new CannotBuildPyramidException();
    }

    List<Integer> sorted = inputNumbers.stream()
        .sorted()
        .collect(Collectors.toList());

    int[][] matrix = new int[(int) rows][cols];
    for (int[] row : matrix) {
      Arrays.fill(row, 0);
    }

    int center = (cols / 2);
    int count = 1;
    int arrIdx = 0;

    for (int i = 0, offset = 0; i < rows; i++, offset++, count++) {
      int start = center - offset;
      for (int j = 0; j < count * 2; j += 2, arrIdx++) {
        matrix[i][start + j] = sorted.get(arrIdx);
      }
    }
    return matrix;
  }

  private double numOfRows(int inputSize) {
    return Math.sqrt((double) inputSize * 2 + 0.25) - 0.5;
  }

  private int numOfColumns(int inputSize) {
    int n = (int) ((-1 + Math.sqrt(1 + 8 * inputSize)) / 2);
    int col = 2 * n - 1;
    return col;
  }

  private boolean isInputCorrect(double numberOfRows) {
    return numberOfRows == Math.round(numberOfRows);
  }


}
