package software.ulpgc.bigdata.matrixmultiplication.logic;

import software.ulpgc.bigdata.matrixmultiplication.entities.Matrix;
import software.ulpgc.bigdata.matrixmultiplication.entities.MatrixBlock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class MatrixMultiplication {

    private static final AtomicBoolean isInitialized = new AtomicBoolean(false);
    private static final Semaphore semaphore = new Semaphore(10); // Limita a 10 bloques simultáneos
    private static final Object lock = new Object();
    private static final AtomicBoolean atomicFlag = new AtomicBoolean(false);
    private static final AtomicInteger completedTasks = new AtomicInteger(0);
    private static double[][] resultArray;


    public static void setBlock(double[][] resultArray, double[][] blockData, int startRow, int startCol) {
        for (int i = 0; i < blockData.length; i++) {
            System.arraycopy(blockData[i], 0, resultArray[startRow + i], startCol, blockData[i].length);
        }
    }
    static MatrixBlock getMatrixBlock(Matrix a, Matrix b, int startRow, int startCol, int blockSize) {
        double[][] blockData = new double[blockSize][blockSize];

        for (int i = 0; i < blockSize; i++) {
            for (int j = 0; j < blockSize; j++) {
                double sum = 0;
                for (int k = 0; k < a.getCols(); k++) {
                    sum += a.getValue(startRow + i, k) * b.getValue(k, startCol + j);
                }
                blockData[i][j] = sum;
            }
        }
        return new MatrixBlock(blockData, startRow, startCol);
    }
    public static double[][] multiplyUsingStreams(Matrix a, Matrix b, int blockSize) {
        int n = a.getRows();
        double[][] resultArray = initializeResultArrayOnce(n);

        // Usar un rango adecuado para el paralelismo
        IntStream.range(0, n / blockSize).parallel().forEach(i -> {
            for (int j = 0; j < n / blockSize; j++) {
                try {
                    semaphore.acquire();
                    try {
                        MatrixBlock blockResult = getMatrixBlock(a, b, i * blockSize, j * blockSize, blockSize);
                        synchronized (lock) {
                            setBlock(resultArray, blockResult.getBlockData(), i * blockSize, j * blockSize);
                        }
                        atomicFlag.compareAndSet(false, true);
                        completedTasks.incrementAndGet();
                    } finally {
                        semaphore.release();
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); // Restaurar el estado interrumpido
                    throw new RuntimeException("Interrupted during matrix multiplication", e);
                }
            }
        });

        while (completedTasks.get() < (n / blockSize) * (n / blockSize)) {
            Thread.onSpinWait();
        }

        return resultArray;
    }

    public static double[][] multiplyUsingExecutors(Matrix a, Matrix b, int blockSize) {
        int n = a.getRows();
        double[][] resultArray = initializeResultArrayOnce(n);

        ExecutorService executor = Executors.newCachedThreadPool();
        List<Future<MatrixBlock>> futures = new ArrayList<>();

        for (int i = 0; i < n; i += blockSize) {
            for (int j = 0; j < n; j += blockSize) {
                int finalI = i;
                int finalJ = j;
                futures.add(executor.submit(() -> {
                    semaphore.acquire();
                    try {
                        return getMatrixBlock(a, b, finalI, finalJ, blockSize);
                    } finally {
                        semaphore.release();
                    }
                }));
            }
        }

        executor.shutdown();

        for (Future<MatrixBlock> future : futures) {
            try {
                MatrixBlock block = future.get();
                synchronized (lock) {
                    setBlock(resultArray, block.getBlockData(), block.getStartRow(), block.getStartCol());
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        return resultArray;
    }
    private static double[][] initializeResultArrayOnce(int size) {
        if (isInitialized.compareAndSet(false, true)) {
            resultArray = new double[size][size];        }
        return resultArray;
    }
}
