
package ohjelmistotekniikka.domain;


/**
 * Shape implements the pieces in Tetris.
 * 
 */

public class Shape {
    
    private int[][] shape;
    private int[][] placeOnBoard;
    private final int[][] l = {{1, 1}, {1, 0}, {0, 0}, {-1, 0}};
    private final int[][] s = {{1, 0}, {0, 0}, {0, -1}, {-1, -1}};
    private final int[][] j = {{1, -1}, {1, 0}, {0, 0}, {-1, 0}};
    private final int[][] z = {{1, 0}, {0, 0}, {0, 1}, {-1, 1}};
    private final int[][] i = {{1, 0}, {0, 0}, {-1, 0}, {-2, 0}};
    private final int[][] o = {{0, 0}, {0, 1}, {1, 1}, {1, 0}};
    private final int[][] t = {{0, -1}, {0, 0}, {1, 0}, {0, 1}};

    /**
     * constructor creates Shape object.
     * @param r some number from 0-6 to determine the shape of a tetromino
     */
    public Shape(int  r) {
        if (r == 0) {
            this.shape = l;
        } else if (r == 1) {
            this.shape = s;
        } else if (r == 2) {
            this.shape = j;
        } else if (r == 3) {
            this.shape = z;
        } else if (r == 4) {
            this.shape = i;
        } else if (r == 5) {
            this.shape = o;
        } else {
            this.shape = t;
        }
        placeOnBoard = new int[4][2];
    }
    public int[][] getShape() {
        return shape;
    }
    public void setShape(int[][] shape) {
        this.shape = shape;
    }
    public int[][] getPlaceOnBoard() {
        return placeOnBoard;
    }
    public void setPlaceOnBoard(int[][] place) {
        this.placeOnBoard = place;
    }
}
