package software.ulpgc.bigdata.matrixmultiplication.logic;

import software.ulpgc.bigdata.matrixmultiplication.entities.Matrix;
import software.ulpgc.bigdata.matrixmultiplication.entities.MatrixBlock;

import java.util.concurrent.Callable;

import static software.ulpgc.bigdata.matrixmultiplication.logic.MatrixMultiplication.getMatrixBlock;

public class BlockMultiplicationTask implements Callable<MatrixBlock> {
    private final int startRow, startCol, blockSize;
    private final Matrix a, b;

    public BlockMultiplicationTask(int startRow, int startCol, int blockSize, Matrix a, Matrix b) {
        this.startRow = startRow;
        this.startCol = startCol;
        this.blockSize = blockSize;
        this.a = a;
        this.b = b;
    }

    @Override
    public MatrixBlock call() {
        return getMatrixBlock(a, b, startRow, startCol, blockSize);
    }

}
