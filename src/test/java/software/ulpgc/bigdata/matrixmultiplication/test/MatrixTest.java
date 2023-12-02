package software.ulpgc.bigdata.matrixmultiplication.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import software.ulpgc.bigdata.matrixmultiplication.entities.Matrix;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MatrixTest {
    private Matrix matrix;
    private final int size = 100;

    @BeforeEach
    public void setUp() {
        matrix = Matrix.random(size, size);
    }

    @Test
    public void testMatrixIsNotNull() {
        assertNotNull(matrix);
    }

    @Test
    public void testMatrixHasNonZeroValues() {
        boolean hasNonZero = false;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (matrix.getValue(i, j) != 0) {
                    hasNonZero = true;
                    break;
                }
            }
        }
        assertNotEquals(false, hasNonZero);
    }
}
