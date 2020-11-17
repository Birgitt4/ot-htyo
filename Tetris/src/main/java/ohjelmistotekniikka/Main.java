/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohjelmistotekniikka;

import ohjelmistotekniikka.domain.Tetris;
import ohjelmistotekniikka.ui.TetrisUi;

/**
 *
 * @author birgi
 */
public class Main {
    public static void main(String[] args) {
        Tetris tetris = new Tetris();
        int[][] peli = tetris.getGame();
        tetris.createShape();
        
        tulostus(peli);
        
        tetris.rotateRight();
        
        tulostus(peli);
        
        tetris.rotateLeft();
        tulostus(peli);

        for (int i=0; i<16; i++) {
            tetris.move('r');
        }

        tulostus(peli);
        
        tetris.move('l');
        for (int i=0; i<20; i++) {
            tetris.move('d');
        }
        
        tulostus(peli);
        
        
        tetris.createShape();
        
        tulostus(peli);
        
        for (int i=0; i<20; i++) {
            tetris.move('d');
        }
        
        tulostus(peli);
    }
    
    public static void tulostus(int[][] peli) {
        for (int i=0; i<16; i++) {
            for (int j=0; j<10; j++) {
                System.out.print(peli[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }
    
}
