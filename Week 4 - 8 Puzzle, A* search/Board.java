import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;

public class Board {
    private final int n;
    private int[][] blocks;
    private int blankRow = -1;
    private int blankCol = -1;


    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        if (tiles == null) {
            throw new NullPointerException();
        }
        n = tiles.length;
        this.blocks = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                blocks[i][j] = tiles[i][j];
                if (tiles[i][j] == 0) {
                    blankRow = i;
                    blankCol = j;
                }
            }
        }

    }

    // string representation of this board
    public String toString() {
        String str = "";
        str += this.dimension() + "\n";
        for (int i = 0; i < this.dimension(); i++) {
            for (int j = 0; j < this.dimension(); j++) {
                str += blocks[i][j] + " ";
            }
            str += "\n";
        }
        return str;
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of tiles out of place
    public int hamming() {
        int dist = 0;
        int iter = 1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (this.blocks[i][j] != iter) {
                    dist += 1;
                }
                iter++;
            }
        }
        --dist;
        return dist;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int dist = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int r = (blocks[i][j] - 1) / n;
                int c = (blocks[i][j] - 1) % n;
                if (i == blankRow && j == blankCol) {
                    continue;
                }
                dist += Math.abs(i - r) + Math.abs(j - c);
            }
        }
        return dist;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return this.hamming() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        if (this.n != that.n) return false;
        else {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (that.blocks[i][j] != this.blocks[i][j]) return false;
                }
            }
        }
        return true;
    }

    private void swap(int i1, int j1, int i2, int j2) {
        int tmp = this.blocks[i1][j1];
        this.blocks[i1][j1] = this.blocks[i2][j2];
        this.blocks[i2][j2] = tmp;

    }


    public Iterable<Board> neighbors() {
        // all neighboring boards
        ArrayList<Board> boards = new ArrayList<Board>();
        // Vertical
        if (blankRow > 0) {
            Board up = new Board(this.blocks);
            up.swap(this.blankRow, this.blankCol, this.blankRow - 1, this.blankCol);
            up.blankRow = this.blankRow - 1;
            boards.add(up);
        }
        if (blankRow < n - 1) {
            Board down = new Board(this.blocks);
            down.swap(this.blankRow, this.blankCol, this.blankRow + 1, this.blankCol);
            down.blankRow = this.blankRow + 1;
            boards.add(down);
        }

        if (blankCol > 0) {
            Board left = new Board(this.blocks);
            left.swap(this.blankRow, this.blankCol, this.blankRow, this.blankCol - 1);
            left.blankCol = this.blankCol - 1;
            boards.add(left);
        }
        if (blankCol < n - 1) {
            Board right = new Board(this.blocks);
            right.swap(this.blankRow, this.blankCol, this.blankRow, this.blankCol + 1);
            right.blankCol = this.blankCol + 1;
            boards.add(right);
        }


        return boards;
    }

    public Board twin() {
        int[][] twinArr = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                twinArr[i][j] = blocks[i][j];
            }
        }

        if (twinArr[0][0] * twinArr[0][1] != 0) {
            int tmp = twinArr[0][0];
            twinArr[0][0] = twinArr[0][1];
            twinArr[0][1] = tmp;
        }
        else {
            int tmp = twinArr[1][0];
            twinArr[1][0] = twinArr[1][1];
            twinArr[1][1] = tmp;

        }

        Board twin = new Board(twinArr);
        return twin;
    }

    public static void main(String[] args) {

        int[][] testArr = new int[3][3];
        testArr[0][0] = 0;
        testArr[0][1] = 5;
        testArr[0][2] = 8;
        testArr[1][0] = 6;
        testArr[1][1] = 2;
        testArr[1][2] = 4;
        testArr[2][0] = 3;
        testArr[2][1] = 1;
        testArr[2][2] = 7;

        Board testBoard = new Board(testArr);
        StdOut.println(testBoard.toString());
        StdOut.println(testBoard.manhattan());


    }
}


