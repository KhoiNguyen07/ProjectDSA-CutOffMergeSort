import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

public class BenchmarkRunner {
    public static int[] generateRandomArray(int n, Random rand) {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = rand.nextInt(1_000_000);
        }
        return arr;
    }
    public static void main(String[] args) throws IOException {
        int[] testSizes = {50_000, 100_000, 200_000, 500_000};
        int[] cutoffs = {0, 3, 5, 7, 8, 9, 10, 12, 14, 16, 20, 25, 30};
        int trials = 15;
        Random rand = new Random(42);
        System.out.println("Đang chạy warm-up...");
        for (int i = 0; i < 20; i++) {
            int[] dummy = generateRandomArray(10_000, rand);
            OptimizedMergeSort.sort(dummy, 10);
        }
        try (PrintWriter writer = new PrintWriter(new FileWriter("benchmark_results.csv"))) {
            writer.println("ArraySize,Cutoff,Trial,TimeNano,TimeMs");
            for (int size : testSizes) {
                System.out.println("Đang đo với kích thước N = " + size);
                int[][] trialArrays = new int[trials][];
                for (int t = 0; t < trials; t++) {
                    trialArrays[t] = generateRandomArray(size, rand);
                }
                for (int cutoff : cutoffs) {
                    for (int t = 0; t < trials; t++) {
                        int[] a = trialArrays[t].clone();
                        long start = System.nanoTime();
                        OptimizedMergeSort.sort(a, cutoff);
                        long elapsed = System.nanoTime() - start;
                        writer.printf("%d,%d,%d,%d,%.4f\n", size, cutoff, t, elapsed, elapsed / 1e6);
                    }
                }
            }
        }
        System.out.println("Kết quả đã lưu vào benchmark_results.csv");
    }
}