package software.ulpgc.bigdata.matrixmultiplication.entities;

public class MatrixBlock {
    private final double[][] blockData;
    private final int startRow;
    private final int startCol;

    public MatrixBlock(double[][] data, int startRow, int startCol) {
        this.blockData = data;
        this.startRow = startRow;
        this.startCol = startCol;
    }

    public double[][] getBlockData() {
        return blockData;
    }

    public int getStartRow() {
        return startRow;
    }

    public int getStartCol() {
        return startCol;
    }
}
