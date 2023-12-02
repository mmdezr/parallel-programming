package software.ulpgc.bigdata.matrixmultiplication.benchmark;

import software.ulpgc.bigdata.matrixmultiplication.entities.Matrix;
import software.ulpgc.bigdata.matrixmultiplication.logic.MatrixMultiplication;
import org.openjdk.jmh.annotations.*;

@State(Scope.Thread)
public class MatrixMultiplicationBenchmark {
    private Matrix a;
    private Matrix b;
    private final int blockSize = 10;

    @Setup
    public void setUp() {
        int size = 100;
        a = Matrix.random(size, size);
        b = Matrix.random(size, size);
    }

    @Benchmark
    public void benchmarkMultiplyUsingExecutors() {
        MatrixMultiplication.multiplyUsingExecutors(a, b, blockSize);
    }

    @Benchmark
    public void benchmarkMultiplyUsingStreams() {
        MatrixMultiplication.multiplyUsingStreams(a, b, blockSize);
    }
}


