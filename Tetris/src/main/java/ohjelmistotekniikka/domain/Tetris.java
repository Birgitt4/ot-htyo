
package ohjelmistotekniikka.domain;

import static java.lang.Math.abs;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import ohjelmistotekniikka.dao.TetrisDao;

/**
 * Takes care of the features in the game. Tetris.
 * 
 */
public class Tetris {
    private int[][] game;
    private Shape currentShape;
    private Random rand;
    private int down;
    private int side;
    private int points;
    private TetrisDao tetrisDao;
    
    /**
     * constructor, creates a Tetris object.
     * @param tetrisDao memory
     */
    public Tetris(TetrisDao tetrisDao) {
        this.tetrisDao = tetrisDao;
        initialize();
    }
    /**
     * constructor without tetrisdao.
     */
    public Tetris() {
        initialize();
    }
    /**
     * common thing for both constructors.
     */
    public void initialize() {
        game = new int[16][10];
        currentShape = new Shape(0);
        rand = new Random();
        points = 0;
        down = 0;
        side = 4;
    }
    public void setGame(int[][] game) {
        this.game = game;
    }
    public int[][] getGame() {
        return game;
    }
    public void setPoints(int points) {
        this.points = points;
    }
    public int getPoints() {
        return points;
    }
    public void setCurrentShape(Shape shape) {
        this.currentShape = shape;
    }
    public Shape getCurrentShape() {
        return currentShape;
    }
    public void setDown(int down) {
        this.down = down;
    }
    public void setSide(int side) {
        this.side = side;
    }
    /**
     * Creates a new piece for tetris and puts it on the top of the gameboard.
     */
    public void createShape() {
        int rnd = rand.nextInt(7);
        Shape next = new Shape(rnd);
        currentShape = next;
        down = 0;
        side = 4;
        updatePlace(currentShape.getShape());
        if (!validMove()) {
            putOnBoardWhatFits();
            currentShape = null;
        }
    }
    /**
     * When a whole tetromino does not fit, only put part what fits.
     */
    public void putOnBoardWhatFits() {
        int maxy = 4;
        int[][] place = currentShape.getPlaceOnBoard();
        for (int i = 0; i < 4; i++) {
            if (game[place[i][0]][place[i][1]] == 2) {
                if (place[i][0] < maxy) {
                    maxy = place[i][0];
                }
            }
        }
        for (int i = 0; i < 4; i++) {
            if (place[i][0] >= maxy) {
                game[place[i][0]][place[i][1]]--;
            }
        }
    }
    /**
     * places tetris piece on the board.
     */
    public void putPiece() {
        for (int i = 0; i < 4; i++) {
            game[currentShape.getPlaceOnBoard()[i][0]][currentShape.getPlaceOnBoard()[i][1]] += 1;
        }
    }
    /**
     * removes a piece from the board.
     */
    public void removePiece() {
        for (int i = 0; i < 4; i++) {
            game[currentShape.getPlaceOnBoard()[i][0]][currentShape.getPlaceOnBoard()[i][1]] -= 1;
        }
    }
    /**
     * When moving or rotating the piece you have to update it on the gameboard
     * also, this does that to the board.
     * @param temp tells coordinates for tetrominos shape
     */
    public void updatePlace(int[][] temp) {
        int[][] place = new int[4][2];
        for (int i = 0; i < 4; i++) {
            int y = temp[i][0] + minY(temp) + down;
            int x = temp[i][1] + minX(temp) + side;
            place[i][0] = y;
            place[i][1] = x;
        }
        if (!inArray(place)) {
            putPiece();
            return;
        }
        currentShape.setShape(temp);
        currentShape.setPlaceOnBoard(place);
        putPiece();
    }
    
    private boolean inArray(int[][] place) {
        for (int i = 0; i < place.length; i++) {
            if (place[i][0] > 15 || place[i][0] < 0) {
                return false;
            }
            if (place[i][1] > 9 || place[i][1] < 0) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Checks lines where new piece has just landed.
     * @return array that tells rows that are full.
     */
    public ArrayList<Integer> lineScan() {
        ArrayList<Integer> fullrows = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            boolean full = true;
            int y = currentShape.getPlaceOnBoard()[i][0];
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
        points += fullrows.size() * 100;
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
                if (game[i][j] != 1 && game[i][j] != 0) {
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
        if (currentShape != null) {
            int[][] temp = new int[4][2];
            for (int i = 0; i < 4; i++) {
                temp[i][0] = currentShape.getShape()[i][1];
                temp[i][1] = -currentShape.getShape()[i][0];
            }
            if (validRotation(temp) == 1) {
                moveLeft();
                rotateRight();
            } else if (validRotation(temp) == -1) {
                moveRight();
                rotateRight();
            } else {
                removePiece();
                updatePlace(temp);
                if (!validMove()) {
                    rotateLeft();
                }
            }
        }
    }
    
    /**
     * Rotates the piece (shape) 90 degrees to the left.
     */
    public void rotateLeft() {
        if (currentShape != null) {
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
                updatePlace(temp);
                if (!validMove()) {
                    rotateRight();
                }
            }
        }
    }
    /**
     * checks if a rotation would go over the edge.
     * @param temp shape coordinates after rotation
     * @return 0 if valid move, 1 if it goes over on the right side and
     *  -1 on the left side.
     */
    private int validRotation(int[][] temp) {
        int minX = minX(temp);
        int maxX = 0;
        for (int i = 0; i < 4; i++) {
            if (temp[i][1] > maxX) {
                maxX = temp[i][1];
            }
        }
        for (int i = 0; i < 4; i++) {
            if ((temp[i][1] + maxX + side) > 9) {
                return 1;
            } else if ((temp[i][1] + minX + side) < 0) {
                return -1;
            }
        }
        return 0;
    }
    /**
     * Moves the piece (shape) one to the right on the gameboard.
     */
    public void moveRight() {
        if (currentShape != null) {
            int max = 0;
            for (int i = 0; i < 4; i++) {
                if (max < currentShape.getPlaceOnBoard()[i][1]) {
                    max = currentShape.getPlaceOnBoard()[i][1];
                }
            }
            if (max < 9) {
                removePiece();
                side++;
                updatePlace(currentShape.getShape());
                if (!validMove()) {
                    moveLeft();
                }
            }
        }
    }
    /**
     * Moves the piece (shape) one to the left on the gameboard.
     */
    public void moveLeft() {
        if (currentShape != null) {
            int min = 10;
            for (int i = 0; i < 4; i++) {
                if (min > currentShape.getPlaceOnBoard()[i][1]) {
                    min = currentShape.getPlaceOnBoard()[i][1];
                }
            }
            if (min > 0) {
                removePiece();
                side--;
                updatePlace(currentShape.getShape());
                if (!validMove()) {
                    moveRight();
                }
            }
        }
    }
    
    /**
     * Moves the piece (shape) one to the down on the gameboard.
     */
    public void moveDown() {
        if (currentShape != null) {
            if (maxPlaceY() < 15) {
                removePiece();
                down++;
                updatePlace(currentShape.getShape());
                if (!validMove()) {
                    moveUp();
                    afterLanding();
                }
            } else {
                afterLanding();
            }
        }
    }
    /**
     * calls what is needed to check after a piece has landed. Then creates a
     * new piece
     */
    private void afterLanding() {
        ArrayList<Integer> fullrows = lineScan();
        if (!fullrows.isEmpty()) {
            emptyFullRows(fullrows);
        }
        points += 25;
        createShape();
    }

    /*
     * If down was not valid, this cancels it.
     */
    private void moveUp() {
        removePiece();
        down--;
        updatePlace(currentShape.getShape());
    }
    private int minY(int[][] temp) {
        int min = 16;
        for (int i = 0; i < 4; i++) {
            if (min > temp[i][0]) {
                min = temp[i][0];
            }
        }
        return abs(min);
    }

    private int minX(int[][] temp) {
        int min = 10;
        for (int i = 0; i < 4; i++) {
            if (min > temp[i][1]) {
                min = temp[i][1];
            }
        }
        return abs(min);
    }
    /*
    * Checks the max value of y in the board (cause it can't be more than 15),
    * so the piece does not go over board.
    */
    private int maxPlaceY() {
        int max = 0;
        for (int i = 0; i < 4; i++) {
            if (max < currentShape.getPlaceOnBoard()[i][0]) {
                max = currentShape.getPlaceOnBoard()[i][0];
            }
        }
        return max;
    }
    /**
     * Saves current points and given name to memory.
     * @param name player that got those points
     * @throws SQLException 
     */
    public void savePoints(String name) throws SQLException {
        tetrisDao.addPoints(name, points);
    }
    /**
     * Gets tree best players from memory.
     * @return String best: points \n second: points \n third:points
     * @throws SQLException 
     */
    public String getTopTree() throws SQLException {
        ArrayList<String[]> results = tetrisDao.getTopTree();
        String result = "";
        for (int i = 0; i < results.size(); i++) {
            result += results.get(i)[0] + ": " + results.get(i)[1] + "\n";
        }
        return result;
    }
}
