import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;

public class PercolationStats {

    private static final double conf = 1.96;
    private final double[] results;
    private double mean;
    private double stddev;


    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n < 1 || trials < 1) {
            throw new IllegalArgumentException();
        }
        results = new double[trials];
        for (int i = 0; i < trials; i++) {
            results[i] = runExperiment(n);
        }
    }

    private double runExperiment(int n) {
        Percolation p = new Percolation(n);
        do {
            int row = StdRandom.uniform(1, n + 1);
            int col = StdRandom.uniform(1, n + 1);
            if (!p.isOpen(row, col)) {
                p.open(row, col);
            }
        } while (!p.percolates());
        int openSites = p.numberOfOpenSites();
        return (double) openSites / ((double) n * n);
    }

    // sample mean of percolation threshold
    public double mean() {
        mean = StdStats.mean(results);
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        stddev = StdStats.stddev(results);
        return stddev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean - (conf * stddev / Math.sqrt(results.length));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean + (conf * stddev / Math.sqrt(results.length));
    }

    // test client (see below)
    public static void main(String[] args) {
        Stopwatch watch = new Stopwatch();
        PercolationStats stats = new PercolationStats(Integer.parseInt(args[0]),
                                                      Integer.parseInt(args[1]));
        double progTime = watch.elapsedTime();
        System.out.println("mean = " + stats.mean());
        System.out.println("St. Dev = " + stats.stddev());
        System.out.println(
                "95% Confidence Interval = " + stats.confidenceLo() + ", " + stats.confidenceHi());
        System.out.println("Runtime = " + progTime + " seconds.");


    }

}
