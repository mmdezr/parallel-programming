package software.ulpgc.bigdata.matrixmultiplication;

import software.ulpgc.bigdata.matrixmultiplication.entities.Matrix;
import software.ulpgc.bigdata.matrixmultiplication.logic.MatrixMultiplication;

public class Main {
    public static void main(String[] args) {
        final int size = 100;
        final int blockSize = 10;

        Matrix a = Matrix.random(size, size);
        Matrix b = Matrix.random(size, size);

        long executorsStartTime = System.nanoTime();
        double[][] resultUsingExecutors = MatrixMultiplication.multiplyUsingExecutors(a, b, blockSize);
        long executorsEndTime = System.nanoTime();

        long streamsStartTime = System.nanoTime();
        double[][] resultUsingStreams = MatrixMultiplication.multiplyUsingStreams(a, b, blockSize);
        long streamsEndTime = System.nanoTime();

        System.out.println("Result using Executors (time in seconds): " + (executorsEndTime - executorsStartTime) / 1_000_000_000.0);
        printSample(resultUsingExecutors);

        System.out.println("Result using Streams (time in seconds): " + (streamsEndTime - streamsStartTime) / 1_000_000_000.0);
        printSample(resultUsingStreams);
    }

    private static void printSample(double[][] matrix) {
        System.out.println("Sample of the resultant matrix:");
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                System.out.printf("%9.2f", matrix[i][j]);
            }
            System.out.println();
        }
    }
}
