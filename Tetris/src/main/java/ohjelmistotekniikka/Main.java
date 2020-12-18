
package ohjelmistotekniikka;

import ohjelmistotekniikka.ui.TetrisUi;

/**
 * "Fake" main class that only calls actual main.
 * Avoiding problems with javafx.
 */
public class Main {
    public static void main(String[] args) {
        TetrisUi.main(args);
    }
}
