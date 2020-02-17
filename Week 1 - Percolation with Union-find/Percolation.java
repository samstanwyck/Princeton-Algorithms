/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final WeightedQuickUnionUF quickUnionStructure;
    private final WeightedQuickUnionUF quickUnionStructureBackwash;

    private final int gridsize;
    private final boolean[][] grid;

    private final int virtualTopSite;
    private final int virtualBottomSite;

    private int openSites = 0;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("N must be at least 1");
        }

        gridsize = n;
        grid = new boolean[gridsize][gridsize];

        quickUnionStructure = new WeightedQuickUnionUF(n * n + 2);
        quickUnionStructureBackwash = new WeightedQuickUnionUF(n * n + 1);

        virtualTopSite = 0;
        virtualBottomSite = gridsize * gridsize + 1;


    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row < 1 || row > gridsize || col < 1 || col > gridsize) {
            throw new IllegalArgumentException();
        }

        if (!isOpen(row, col)) {
            grid[row - 1][col - 1] = true;
            openSites = openSites + 1;


            if (row == 1) {
                quickUnionStructure.union(virtualTopSite, toIndex(row, col));
                quickUnionStructureBackwash.union(virtualTopSite, toIndex(row, col));
            }

            if (row == gridsize) {
                quickUnionStructure.union(virtualBottomSite, toIndex(row, col));
            }

            if (row > 1 && isOpen(row - 1, col)) {
                quickUnionStructure.union(toIndex(row, col), toIndex(row - 1, col));
                quickUnionStructureBackwash.union(toIndex(row, col), toIndex(row - 1, col));
            }

            if (row < gridsize && isOpen(row + 1, col)) {
                quickUnionStructure.union(toIndex(row, col), toIndex(row + 1, col));
                quickUnionStructureBackwash.union(toIndex(row, col), toIndex(row + 1, col));
            }

            if (col > 1 && isOpen(row, col - 1)) {
                quickUnionStructure.union(toIndex(row, col), toIndex(row, col - 1));
                quickUnionStructureBackwash.union(toIndex(row, col), toIndex(row, col - 1));
            }

            if (col < gridsize && isOpen(row, col + 1)) {
                quickUnionStructure.union(toIndex(row, col), toIndex(row, col + 1));
                quickUnionStructureBackwash.union(toIndex(row, col), toIndex(row, col + 1));
            }
        }
    }

    private int toIndex(int row, int col) {
        if (row < 1 || row > gridsize || col < 1 || col > gridsize) {
            throw new IllegalArgumentException();
        }

        return (row - 1) * gridsize + col;

    }

    public boolean isOpen(int row, int col) {
        if (row < 1 || row > gridsize || col < 1 || col > gridsize) {
            throw new IllegalArgumentException();
        }
        return grid[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row < 1 || row > gridsize || col < 1 || col > gridsize) {
            throw new IllegalArgumentException();
        }

        return quickUnionStructureBackwash.connected(virtualTopSite, toIndex(row, col));

    }

    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return quickUnionStructure.connected(virtualTopSite, virtualBottomSite);
    }

}
