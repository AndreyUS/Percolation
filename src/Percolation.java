import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private static final int STATE_BLOCKED = 0;
    private static final int STATE_OPEN = 1;

    private static final int TOP_SIDE = 1;
    private static final int BOTTOM_SIDE = 1;

    private final WeightedQuickUnionUF weightedQuickUnionUF;
    private final int[] state;
    private final int sideSize;
    private final int topPosition;
    private final int bottomPosition;

    // create n-by-n grid, with all sites blocked
    public Percolation(final int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("N must be > 0");
        }
        sideSize = n;
        final int size = n * n + TOP_SIDE + BOTTOM_SIDE;
        topPosition = size - 2;
        bottomPosition = size - 1;
        weightedQuickUnionUF = new WeightedQuickUnionUF(size);
        state = new int[size];
        for (int i = 0; i < state.length; i++) {
            state[i] = STATE_BLOCKED;
        }
    }

    // open site (row, col) if it is not open already
    public void open(final int row, final int col) {
        checkRange(row, col);
        if (isOpen(row, col)) {
            return;
        }
        final int position = countPosition(row, col);
        state[position] = STATE_OPEN;
        if (!isTop(row) && isOpen(row - 1, col)) {
            weightedQuickUnionUF.union(position, countPosition(row - 1, col));
        } else if (isTop(row)) {
            weightedQuickUnionUF.union(topPosition, position);
        }

        if (hasRightElement(col) && isOpen(row, col + 1)) {
            weightedQuickUnionUF.union(position, countPosition(row, col + 1));
        }

        if (hasLeftElement(col) && isOpen(row, col - 1)) {
            weightedQuickUnionUF.union(position, countPosition(row, col - 1));
        }

        if (!isBottom(row) && isOpen(row + 1, col)) {
            weightedQuickUnionUF.union(position, countPosition(row + 1, col));
        } else if (isBottom(row)) {
            weightedQuickUnionUF.union(bottomPosition, position);
        }
    }

    // is site (row, col) open?
    public boolean isOpen(final int row, final int col) {
        checkRange(row, col);
        return state[countPosition(row, col)] == STATE_OPEN;
    }

    // is site (row, col) full?
    public boolean isFull(final int row, final int col) {
        checkRange(row, col);
        return weightedQuickUnionUF.connected(countPosition(row, col),
                topPosition);
    }

    // does the system percolate?
    public boolean percolates() {
        return weightedQuickUnionUF.connected(topPosition, bottomPosition);
    }

    private boolean isTop(final int row) {
        return row == 1;
    }

    private boolean hasRightElement(final int col) {
        return col < sideSize;
    }

    private boolean hasLeftElement(final int col) {
        return col > 1;
    }

    private boolean isBottom(final int row) {
        return row == sideSize;
    }

    private int countPosition(final int row, final int col) {
        return ((row - 1) * sideSize) + (col - 1);
    }

    private void checkRange(final int row, final int col) {
        if (row <= 0 || col <= 0 || row > sideSize || col > sideSize) {
            throw new IndexOutOfBoundsException();
        }
    }

    // test client (optional)
    public static void main(final String[] args) {
    }
}
