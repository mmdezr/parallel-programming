package software.ulpgc.bigdata.matrixmultiplication.entities;

import java.util.Arrays;
import java.util.Random;

public class Matrix {
    private final double[][] data;

    public Matrix(int rows, int cols) {
        data = new double[rows][cols];
    }

    public static Matrix random(int rows, int cols) {
        Matrix randomMatrix = new Matrix(rows, cols);
        Random random = new Random();

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                randomMatrix.setValue(i, j, random.nextDouble()); // Usa random.nextInt() para enteros
            }
        }

        return randomMatrix;
    }

    public void setValue(int row, int col, double value) {
        data[row][col] = value;
    }

    public double getValue(int row, int col) {
        return data[row][col];
    }

    public int getRows() {
        return data.length;
    }
    public int getCols() {
        return data[0].length;
    }

    public double[][] getMatrix() {
        double[][] copy = new double[data.length][];
        for (int i = 0; i < data.length; i++) {
            copy[i] = data[i].clone();
        }
        return copy;
    }

    public static Matrix generateRandomMatrix(int rows, int cols) {
        Matrix matrix = new Matrix(rows, cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix.setValue(i, j, Math.random());
            }
        }
        return matrix;
    }
}
