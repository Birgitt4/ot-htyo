/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohjelmistotekniikka.domain;

import java.util.Random;

/**
 *
 * @author birgi
 */
public class Tetris {
    private int[][] game;
    private Shape currentShape;
    private int[][][] allShapes;
    private Random rand;
    private int print;
    
    public Tetris() {
        game = new int[16][10];
        currentShape = new Shape(new int[4][2]);
        allShapes = new int[][][]{{{1,1},{1,0},{0,0},{-1,0}},
                                 {{1,0},{0,0},{0,-1},{-1,-1}},
                                 {{1,-1},{1,0},{0,0},{-1,0}},
                                 {{1,0},{0,0},{0,1},{-1,1}},
                                 {{1,0},{0,0},{-1,0},{-2,0}},
                                 {{0,0},{0,1},{1,1},{1,0}},
                                 {{0,-1},{0,0},{1,0},{0,1}}};
        rand = new Random();
        print = 0;
    }
    
    public void setGame(int[][] game) {
        this.game = game;
    }
    public int[][] getGame() {
        return game;
    }
    public Shape getCurrentShape() {
        return currentShape;
    }
    
    public void createShape() {
        int rnd = rand.nextInt(7);
        print = rnd + 1;
        Shape next = new Shape(allShapes[rnd]);
        currentShape = next;
        
        putPiece(currentShape.getPlaceOnBoard());
    }

    public void putPiece(int[][] temp) {
        for (int i=0; i<4; i++) {
            game[temp[i][0]][temp[i][1]] = print;
        }
    }
    public void removePiece(int[][] temp) {
        for (int i=0; i<4; i++) {
            game[temp[i][0]][temp[i][1]] = 0;
        }
    }
    public void move(char way) {
        int[][] temp = currentShape.getPlaceOnBoard();
        removePiece(temp);
        if (way == 'l') {
            currentShape.moveLeft();
            
        }
        else if (way == 'r') {
            currentShape.moveRight();
        }
        else if (way == 'd') {
            currentShape.moveDown();
        }
        putPiece(temp);
    }

    public void rotateLeft() {
        int[][] temp = currentShape.getPlaceOnBoard();
        removePiece(temp);
        currentShape.rotateLeft();
        putPiece(temp);
    }
    public void rotateRight() {
        int[][] temp = currentShape.getPlaceOnBoard();
        removePiece(temp);
        currentShape.rotateRight();
        putPiece(temp);
    }
    public String toString() {
        String board = "";
        for (int i=0; i<16; i++) {
            for (int j=0; j<10; j++) {
                board += game[i][j] + ",";
            }
        }
        return board;
    }
}
