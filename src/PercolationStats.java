import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.Stopwatch;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats {
    private double[] results;

    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        results = new double[trials];

        for (int i = 0; i < trials; i++) {
            Percolation percolation = new Percolation(n);
            testPercolation(percolation, n, i);
        }
    }


    private void testPercolation(Percolation percolation, int n, int resultIndex) {
        int openSides = 0;
        while (!percolation.percolates()) {
            int row = StdRandom.uniform(1, n + 1);
            int col = StdRandom.uniform(1, n + 1);
            if (!percolation.isOpen(row, col)) {
                percolation.open(row, col);
                openSides++;
            }
            results[resultIndex] = (double) openSides / (n * n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(results);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(results);
    }

    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        return StdStats.mean(results) - 1.96 * StdStats.stddev(results)
                / Math.sqrt(results.length);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return StdStats.mean(results) - 1.96 * StdStats.stddev(results)
                / Math.sqrt(results.length);
    }

    // test client (described below)
    public static void main(String[] args) {
        while (!StdIn.isEmpty()) {
            int n = StdIn.readInt();
            int trials = StdIn.readInt();
            Stopwatch time = new Stopwatch();
            PercolationStats stats = new PercolationStats(n, trials);
            StdOut.println("mean                    = " + stats.mean());
            StdOut.println("stddev                  = " + stats.stddev());
            StdOut.println("95% confidence interval = "
                    + stats.confidenceLo()
                    + ", " + stats.confidenceHi());
            StdOut.println("Elapsed time            = " + time.elapsedTime());
        }
    }
}
