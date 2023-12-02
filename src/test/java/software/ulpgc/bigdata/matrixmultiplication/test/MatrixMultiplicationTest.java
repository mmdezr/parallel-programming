package software.ulpgc.bigdata.matrixmultiplication.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import software.ulpgc.bigdata.matrixmultiplication.entities.Matrix;
import software.ulpgc.bigdata.matrixmultiplication.logic.MatrixMultiplication;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MatrixMultiplicationTest {
    private Matrix a;
    private Matrix b;
    private final int blockSize = 10;

    @BeforeEach
    public void setUp() {
        int size = 100;
        a = Matrix.random(size, size);
        b = Matrix.random(size, size);
    }

    @Test
    public void testMultiplyUsingExecutors() {
        double[][] result = MatrixMultiplication.multiplyUsingExecutors(a, b, blockSize);
        assertNotNull(result);
    }

    @Test
    public void testMultiplyUsingStreams() {
        double[][] result = MatrixMultiplication.multiplyUsingStreams(a, b, blockSize);
        assertNotNull(result);
    }
}
