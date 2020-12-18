
package ohjelmistotekniikka.dao;

import java.io.File;
import java.util.ArrayList;
import ohjelmistotekniikka.domain.Tetris;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

/**
 *
 * @author birgi
 */
public class TetrisDaoTest {
    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    File folder;
    TetrisDao dao;
    Tetris game;
    
    @Before
    public void setUp() throws Exception {
        folder = testFolder.newFolder();
        String path = folder.getAbsolutePath();
        path = path + "testDB";
        dao = new TetrisDao(path);
        game = new Tetris(dao);
    }
    
    @After
    public void tearDown() {
        folder.deleteOnExit();
    }

    @Test
    public void pointsAreAddedCorrectly() throws Exception {
        game.setPoints(15000);
        game.savePoints("Anton");
        dao.addPoints("Anton", 100);
        game.setPoints(2000);
        game.savePoints("Otto");
        ArrayList<Integer> results = dao.getPointsFor("Anton");
        String result = "";
        for (int i = 0; i < results.size(); i++) {
            result += results.get(i) + " ";
        }
        assertEquals("100 15000 ", result);
        results = dao.getPointsFor("Otto");
        result = "";
        for (int i = 0; i < results.size(); i++) {
            result += results.get(i) + " ";
        }
        assertEquals("2000 ", result);
        results = dao.getPointsFor("Mikko");
        result = "";
        for (int i = 0; i < results.size(); i++) {
            result += results.get(i) + " ";
        }
        assertEquals("", result);
    }
    
    @Test
    public void topTreeGivesActualTopTree() throws Exception {
        game.setPoints(15000);
        game.savePoints("Anton");
        game.setPoints(2000);
        game.savePoints("Otto");
        String result = game.getTopTree();
        assertEquals("Anton: 15000\nOtto: 2000\n", result);
        game.setPoints(100);
        game.savePoints("Anton");
        dao.addPoints("Mikko", 0);
        dao.addPoints("Tuuri", 21000);
        ArrayList<String[]> topTree = dao.getTopTree();
        result = "";
        for (int i = 0; i < topTree.size(); i++) {
            result += topTree.get(i)[0] + ": " + topTree.get(i)[1] + "\n";
        }
        assertEquals("Tuuri: 21000\nAnton: 15000\nOtto: 2000\n", result);
    }
}
