/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohjelmistotekniikka.domain;

import static java.lang.Math.abs;
import java.util.ArrayList;
import java.util.Random;

/**
 * Takes care of the features in the game. Tetris.
 * 
 */
public class Tetris {
    private int[][] game;
    private Shape currentShape;
    private int[][][] allShapes;
    private Random rand;
    private int down;
    private int side;
    private int[][] place;
    
    
    /**
     * constructor, creates a Tetris object.
     */
    public Tetris() {
        game = new int[16][10];
        currentShape = new Shape(new int[4][2]);
        allShapes = new int[][][]{{{1, 1}, {1, 0}, {0, 0}, {-1, 0}},
                                  {{1, 0}, {0, 0}, {0, -1}, {-1, -1}},
                                  {{1, -1}, {1, 0}, {0, 0}, {-1, 0}},
                                  {{1, 0}, {0, 0}, {0, 1}, {-1, 1}},
                                  {{1, 0}, {0, 0}, {-1, 0}, {-2, 0}},
                                  {{0, 0}, {0, 1}, {1, 1}, {1, 0}},
                                  {{0, -1}, {0, 0}, {1, 0}, {0, 1}}};
        rand = new Random();
        
        down = 0;
        side = 4;
        place = new int[4][2];
    }
    
    public void setGame(int[][] game) {
        this.game = game;
    }
    public int[][] getGame() {
        return game;
    }
    public void setPlace(int[][] place) {
        this.place = place;
    }
    public Shape getCurrentShape() {
        return currentShape;
    }

    /**
     * Creates a new piece for tetris and puts it on the top of the gameboard.
     */
    public void createShape() {
        int rnd = rand.nextInt(7);
        Shape next = new Shape(allShapes[rnd]);
        currentShape = next;
        down = 0;
        side = 4;
        updatePlace();
        if (!validMove()) {
            putOnBoardWhatFits();
            currentShape = null;
        }
    }
    
    /**
     * When a whole tetromino does not fit, only put part what fits.
     */
    public void putOnBoardWhatFits() {
        int miny = 4;
        for (int i = 0; i < 4; i++) {
            if (game[place[i][0]][place[i][1]] == 2) {
                if (place[i][0] < miny) {
                    miny = place[i][0];
                }
            }
        }
        for (int i = 0; i < 4; i++) {
            if (place[i][0] >= miny) {
                game[place[i][0]][place[i][1]]--;
            }
        }
    }

    /**
     * places tetris piece on the board.
     */
    public void putPiece() {
        for (int i = 0; i < 4; i++) {
            game[place[i][0]][place[i][1]] += 1;
        }
    }
    
    /**
     * removes a piece from the board.
     */
    public void removePiece() {
        for (int i = 0; i < 4; i++) {
            game[place[i][0]][place[i][1]] -= 1;
        }
    }
    
    /**
     * When moving or rotating the piece you have to update it on the gameboard
     * also, this does that to the board.
     */
    public void updatePlace() {
        place = new int[4][2];
        for (int i = 0; i < 4; i++) {
            int y = currentShape.getShape()[i][0] + currentShape.minY() + down;
            int x = currentShape.getShape()[i][1] + currentShape.minX() + side;
            place[i][0] = y;
            place[i][1] = x;
        }
        currentShape.setPlaceOnBoard(place);
        putPiece();
    }
    
    /**
     * Checks lines where new piece has just landed.
     * @return array that tells rows that are full.
     */
    public ArrayList<Integer> lineScan() {
        ArrayList<Integer> fullrows = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            boolean full = true;
            int y = place[i][0];
            for (int j = 0; j < 10; j++) {
                if (game[y][j] != 1) {
                    full = false;
                }
            }
            if (full && !fullrows.contains(y)) {
                fullrows.add(y);
            }
        }
        return fullrows;
    }
    
    /**
     * if a row is full, this removes the whole row.
     * @param fullrows arraylist of rows that are full
     */
    public void emptyFullRows(ArrayList<Integer> fullrows) {
        fullrows.sort((a, b) -> a - b);
        for (int i = 0; i < fullrows.size(); i++) {
            int row = fullrows.get(i);
            for (int j = 0; j < 10; j++) {
                game[row][j] = 0;
            }
            moveEverythingDown(row);
        }
    }
    
    /**
     * From the given row to top, this moves every piece one down.
     * Used when a row in the middle has been erased.
     * @param row erased row
     */
    public void moveEverythingDown(int row) {
        for (int i = row; i > 0; i--) {
            for (int j = 0; j < 10; j++) {
                game[i][j] = game[i - 1][j];
            }
        }
        for (int j = 0; j < 10; j++) {
            game[0][j] = 0;
        }
    }
    
    
    
    /**
     * Checks if after some move there are values over one in the grid.
     * If there is there has to be more than one piece -> collision.
     * @return false if there is a collision, true if there isn't
     */
    public boolean validMove() {

        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 10; j++) {
                if (game[i][j] > 1) {
                    return false;
                }
            }
        }
        return true;  
    }
    
    /**
     * Rotates the piece (shape) 90 degrees to the right.
     */
    public void rotateRight() {
        int[][] temp = new int[4][2];
        for (int i = 0; i < 4; i++) {
            temp[i][0] = currentShape.getShape()[i][1];
            temp[i][1] = -currentShape.getShape()[i][0];
        }
        if (validRotation(temp) == 1) {
            moveLeft();
            rotateLeft();
        } else if (validRotation(temp) == -1) {
            moveRight();
            rotateLeft();
        } else {
            removePiece();
            currentShape.setShape(temp);
            updatePlace();
            if (!validMove()) {
                rotateLeft();
            }
        }
    }
    
    /**
     * Rotates the piece (shape) 90 degrees to the left.
     */
    public void rotateLeft() {
        int[][] temp = new int[4][2];
        for (int i = 0; i < 4; i++) {
            temp[i][0] = -currentShape.getShape()[i][1];
            temp[i][1] = currentShape.getShape()[i][0];
        }
        if (validRotation(temp) == 1) {
            moveLeft();
            rotateLeft();
        } else if (validRotation(temp) == -1) {
            moveRight();
            rotateLeft();
        } else {
            removePiece();
            currentShape.setShape(temp);
            updatePlace();
            if (!validMove()) {
                rotateRight();
            }
        }
    }
    
    /**
     * checks if a rotation would go over the edge.
     * @param temp shape coordinates after rotation
     * @return 0 if valid move, 1 if it goes over on the right side and
     *  -1 on the left side.
     */
    public int validRotation(int[][] temp) {
        int minX = 10;
        int maxX = 10;
        for (int i = 0; i < 4; i++) {
            if (temp[i][1] < minX) {
                minX = temp[i][1];
            } else if (temp[i][1] > maxX) {
                maxX = temp[i][1];
            }
        }
        for (int i = 0; i < 4; i++) {
            if ((temp[i][1] + abs(minX) + side) > 9) {
                return 1;
            } else if ((temp[i][1] + abs(minX) + side) < 0) {
                return -1;
            }
        }
        return 0;
    }
    
    /**
     * Moves the piece (shape) one to the right on the gameboard.
     */
    public void moveRight() {
        int max = 0;
        for (int i = 0; i < 4; i++) {
            if (max < place[i][1]) {
                max = place[i][1];
            }
        }
        if (max < 9) {
            removePiece();
            side++;
            updatePlace();
            if (!validMove()) {
                moveLeft();
            }
        }
    }
    
    /**
     * Moves the piece (shape) one to the left on the gameboard.
     */
    public void moveLeft() {
        int min = 10;
        for (int i = 0; i < 4; i++) {
            if (min > place[i][1]) {
                min = place[i][1];
            }
        }
        if (min > 0) {
            removePiece();
            side--;
            updatePlace();
            if (!validMove()) {
                moveRight();
            }
        }
    }
    
    /**
     * Moves the piece (shape) one to the down on the gameboard.
     */
    public void moveDown() {
        if (maxPlaceY() < 15) {
            removePiece();
            down++;
            updatePlace();
            if (!validMove()) {
                moveUp();
                afterLanding();
            }
        } else {
            afterLanding();
        }
    }
    
    /**
     * calls what is needed to check after a piece has landed. Then creates a
     * new piece
     */
    public void afterLanding() {
        ArrayList<Integer> fullrows = lineScan();
        if (!fullrows.isEmpty()) {
            emptyFullRows(fullrows);
        }
        createShape();
    }

    /**
     * If down was not valid, this cancels it.
     */
    private void moveUp() {
        removePiece();
        down--;
        updatePlace();
    }
    
    /**
     * Checks the max value of y in the board (cause it can't be more than 15),
     * so the piece does not go over board.
     * @return max y where this shape is on the board.
     */
    public int maxPlaceY() {
        int max = 0;
        for (int i = 0; i < 4; i++) {
            if (max < place[i][0]) {
                max = place[i][0];
            }
        }
        return max;
    }
    
    /**
     * used for testing.
     * @return gameboard as a string.
     */
    public String toString() {
        String board = "";
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 10; j++) {
                board += game[i][j] + ",";
            }
        }
        return board;
    }
}
