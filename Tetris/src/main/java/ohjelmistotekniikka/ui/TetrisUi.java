
package ohjelmistotekniikka.ui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import ohjelmistotekniikka.dao.TetrisDao;
import ohjelmistotekniikka.domain.Tetris;

/**
 *
 * GUI for Tetris
 */
public class TetrisUi extends Application {
    
    private int fontSize = 72;
    private int blockSize = 35;
    private Scene startScene;
    private Scene playScene;
    private Tetris game;
    private Text points;
    private TetrisDao tetrisDao;
    private Stage stage;
    private AnimationTimer timer;
    private GraphicsContext gc;
    private GridPane gameOver;
    private boolean over;
    private Text errorMessage;
    private boolean error;
    
    @Override
    public void init() {
        errorMessage = new Text("");
        error = false;
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("config.properties"));
        } catch (Exception e) {
            error = true;
            errorMessage.setText("Sovelluksen juuresta ei löydy tiedostoa config.properties.\n"
                    + "Pisteiden tallentaminen pois käytöstä.");
        }
        if (!error) {
            String database = properties.getProperty("database");
            try {
                tetrisDao = new TetrisDao(database);
            } catch (SQLException e) {
                errorMessage.setText("Onko tiedostoihin luku ja kirjoitusoikeudet?\n"
                        + "Jokin meni vikaan tietokannan kanssa. Voit pelata,\n"
                        + "mutta pisteiden tallentaminen on poissa käytöstä.");
                error = true;
            }
            if (!error) {
                game = new Tetris(tetrisDao);
            }
            
        }
        if (error) {
            game = new Tetris();
        }
        createStartingScene();
        createPlayingScene();
    }

    public TextFlow createHeadline() {
        TextFlow tetrisText = new TextFlow();
        tetrisText.setTextAlignment(TextAlignment.CENTER);
        Text t1 = new Text("T");
        t1.setFont(new Font(fontSize));
        t1.setFill(Color.RED);
        t1.setStroke(Color.BLACK);
        Text e = new Text("E");
        e.setFont(new Font(fontSize));
        e.setFill(Color.YELLOW);
        e.setStroke(Color.BLACK);
        Text t2 = new Text("T");
        t2.setFont(new Font(fontSize));
        t2.setFill(Color.GREEN);
        t2.setStroke(Color.BLACK);
        Text r = new Text("R");
        r.setFont(new Font(fontSize));
        r.setFill(Color.ORANGE);
        r.setStroke(Color.BLACK);
        Text i = new Text("I");
        i.setFont(new Font(fontSize));
        i.setFill(Color.BLUE);
        i.setStroke(Color.BLACK);
        Text s = new Text("S");
        s.setFont(new Font(fontSize));
        s.setFill(Color.LIGHTBLUE);
        s.setStroke(Color.BLACK);
        tetrisText.getChildren().addAll(t1, e, t2, r, i, s);
        return tetrisText;
    }
    
    public void createStartingScene() {
        BorderPane startPane = new BorderPane();
        startPane.setPrefSize(16*blockSize, 10*blockSize + 200);
        VBox vbox = new VBox();
        vbox.setSpacing(20);
        
        Button start = new Button("Start");
        start.setShape(new Circle(60));
        start.setMinSize(80, 60);

        Button scores = new Button("Highscores");
        scores.setShape(new Circle(60));
        scores.setMinSize(80, 60);
        
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(createHeadline(), start);
        if (!error) {
            vbox.getChildren().add(scores);
        } else {
            vbox.getChildren().add(errorMessage);
        }
        startPane.setCenter(vbox);
        startScene = new Scene(startPane);

        start.setOnMouseClicked((event) -> {
            toPlayScene();
        });
        scores.setOnMouseClicked(event -> {
            if (!error) {
                stage.setScene(createScoreScene());
            }
        });

    }
    
    public void toStartScene() {
        stage.setScene(startScene);
    }
    
    public Scene createScoreScene() {
        Pane scorePane = new Pane();
        scorePane.setPrefSize(16*blockSize, 10*blockSize + 200);
        Label topTree = labelMaker("Top 3: ", 100, 100);
        String top = "";
        try {
            top = game.getTopTree();
        } catch (SQLException e) {
            e.getMessage();
        }
        Label tops = labelMaker(top, 170, 100);
        
        Label search = labelMaker("Scores for a specific player", 100, 200);
        TextField nameSearch = new TextField();
        nameSearch.setTranslateX(100);
        nameSearch.setTranslateY(250);
        Button ok = buttonMaker("OK", 300, 250);
        Button back = buttonMaker("Back", 500, 500);
        NumberAxis x = new NumberAxis();
        NumberAxis y = new NumberAxis();
        y.setLabel("points");
        ScatterChart<Number, Number> chart = new ScatterChart<>(x, y);
        chart.setLegendVisible(false);
        pointsForOnePlayer(nameSearch.getText(), chart);
        back.setOnMouseClicked(event -> {
            stage.setScene(startScene);
        });
        ok.setOnMouseClicked(event -> {
            pointsForOnePlayer(nameSearch.getText(), chart);
        });
        scorePane.getChildren().addAll(topTree, tops, search, nameSearch, back, ok, chart);
        return new Scene(scorePane);
    }
    
    public void pointsForOnePlayer(String name, ScatterChart chart) {
        chart.getData().clear();
        ArrayList<Integer> scores = new ArrayList<>();
        try {
            scores = tetrisDao.getPointsFor(name);
        } catch (SQLException e) {
            e.getMessage();
        }

        XYChart.Series data = new XYChart.Series<>();
        for (int i = 0; i < scores.size(); i++) {
            data.getData().add(new XYChart.Data(i+1, scores.get(i)));
        }
        chart.getData().add(data);
        chart.setTranslateX(blockSize);
        chart.setTranslateY(280);
        chart.setPrefSize(12*blockSize, 6*blockSize);
    }
    
    public GridPane gameOver() {
        gameOver = new GridPane();
        gameOver.setVisible(false);
        gameOver.setTranslateX(10*blockSize);
        gameOver.setTranslateY(210);
        TextField nameField = new TextField();
        Button save = new Button("Save");
        save.setFocusTraversable(false);
        Label empty = new Label("Name cannot be empty");
        empty.setVisible(false);
        gameOver.add(new Label("Name: "), 0, 0);
        gameOver.add(nameField, 1, 0);
        gameOver.add(save, 0, 2);
        gameOver.add(empty, 1, 2);
        gameOver.setPadding(new Insets(5));
        gameOver.setVgap(5);
        gameOver.setHgap(10);
        save.setOnMouseClicked(event -> {
            if (nameField.getText().length() > 0) {
                empty.setVisible(false);
                try {
                    game.savePoints(nameField.getText());
                } catch (SQLException e) {
                    e.getMessage();
                }
                gameOver.setVisible(false);
            } else {
                empty.setVisible(true);
            }
        });
        return gameOver;
    }
    
    public void createPlayingScene() {
        over = false;
        Pane playPane = new Pane();
        playPane.setMinSize(16*blockSize, (10*blockSize) +200);
        Line horline = new Line();
        horline.setEndX(10*blockSize);
        horline.setStartX(0);
        horline.setEndY(16*blockSize);
        horline.setStartY(16*blockSize);
        Line verline = new Line();
        verline.setEndX(10*blockSize);
        verline.setStartX(10*blockSize);
        verline.setEndY(16*blockSize);
        verline.setStartY(0);
        Canvas grid = new Canvas(10*blockSize, 16*blockSize);
        playPane.getChildren().addAll(grid, horline, verline);
        
        gc = grid.getGraphicsContext2D();
        gc.setFill(Color.RED);
        
        points = new Text();
        points.setText("Points: " + game.getPoints());
        points.setTranslateX(10*blockSize + 70);
        points.setTranslateY(200);
        
        Button pause = buttonMaker("pause", 10*blockSize + 75, 50);
        Button cont = buttonMaker("continue", 10*blockSize + 75, 100);
        Button again = buttonMaker("again", 10*blockSize + 75, 150);
        Button back = buttonMaker("back", 10*blockSize + 75, 14*blockSize);
        playPane.getChildren().addAll(cont, pause, again, back, points);
        playPane.getChildren().add(gameOver());
        pause.setOnMouseClicked(event -> {
            timer.stop();
        });
        cont.setOnMouseClicked(event -> {
            if (!over) {
                timer.start();
            }
        });
        again.setOnMouseClicked(event -> {
            timer.stop();
            game = new Tetris(tetrisDao);
            gameOver.setVisible(false);
            gc.clearRect(0,0,10*blockSize, 16*blockSize);
            actualGame();
        });
        back.setOnMouseClicked(event -> {
            game = new Tetris(tetrisDao);
            gameOver.setVisible(false);
            gc.clearRect(0,0,10*blockSize, 16*blockSize);
            timer.stop();
            stage.setScene(startScene);
        });
        playScene = new Scene(playPane);
    }
    public void toPlayScene() {
        stage.setScene(playScene);
        actualGame();
    }
    public void update(GraphicsContext gc) {
        int[][] grid = game.getGame();
        for (int i=0; i<10; i++) {
            for (int j=0; j<16; j++) {
                if (grid[j][i] == 1) {
                    gc.fillRoundRect(i*blockSize, j*blockSize, blockSize, blockSize, blockSize/4, blockSize/4);
                    gc.strokeRoundRect(i*blockSize, j*blockSize, blockSize, blockSize, blockSize/4, blockSize/4);
                }
            }
        }
    }    
    public void actualGame() {
        over = false;
        HashMap<KeyCode, Boolean> pushed = new HashMap<>();
        playScene.setOnKeyPressed(event -> {
            pushed.put(event.getCode(), Boolean.TRUE);
        });
        playScene.setOnKeyReleased(event -> {
            if (!event.getCode().equals(KeyCode.A) && !event.getCode().equals(KeyCode.D)) {
                pushed.put(event.getCode(), Boolean.FALSE);
            }
        });

        game.createShape();
        timer = new AnimationTimer() {
            
            private long lastUpdate = 0;
            private long down = System.nanoTime();
            int[][] copygrid = new int[16][10];
            
            @Override
            public void start() {
                down = System.nanoTime();
                update(gc);
                super.start();
            }
            
            @Override
            public void handle(long now) {
                try {
                    if (game.getCurrentShape() == null) {
                        this.stop();
                        over = true;
                        gc.setFill(Color.BLACK);
                        gc.setFont(new Font(50));
                        gc.fillText("GAME OVER", blockSize, 6*blockSize);
                        gc.setFill(Color.RED);
                        if (!error) {
                            gameOver.setVisible(true);
                        }
                    }
                    if (now-down >= 500000000) {
                        game.moveDown();
                        gc.clearRect(0,0,10*blockSize, 16*blockSize);
                        update(gc);
                        down = now;
                        lastUpdate = now;
                    }
                    if (now-lastUpdate >= 100000000) {
                        
                        if (pushed.getOrDefault(KeyCode.LEFT, false)) {
                            game.moveLeft();
                            gc.clearRect(0,0,10*blockSize, 16*blockSize);
                            update(gc);
                        }
            
                        if (pushed.getOrDefault(KeyCode.RIGHT, false)) {
                            game.moveRight();
                            gc.clearRect(0,0,10*blockSize, 16*blockSize);
                            update(gc);
                        }

                        if (pushed.getOrDefault(KeyCode.DOWN, false)) {
                            game.moveDown();
                            gc.clearRect(0,0,10*blockSize, 16*blockSize);
                            update(gc);
                        }
                        if (pushed.getOrDefault(KeyCode.A, false)) {
                            game.rotateLeft();
                            gc.clearRect(0,0,10*blockSize, 16*blockSize);
                            update(gc);
                            pushed.put(KeyCode.A, Boolean.FALSE);
                        }
                        if (pushed.getOrDefault(KeyCode.D, false)) {
                            game.rotateRight();
                            gc.clearRect(0,0,10*blockSize, 16*blockSize);
                            update(gc);
                            pushed.put(KeyCode.D, Boolean.FALSE);
                        }
                        
                        for (int i=0; i<16; i++) {
                            for (int j=0; j<10; j++) {
                                copygrid[i][j] = game.getGame()[i][j];
                            }
                        }
                        lastUpdate = now;
                        points.setText("Points: " + game.getPoints());
                    }
                }
                catch (Exception e) {
                    this.stop();
                    gc.setFill(Color.BLACK);
                    gc.setFont(new Font(25));
                    gc.setTextBaseline(VPos.CENTER);
                    gc.fillText("Something went wrong.\nStart a new game.", blockSize, 6*blockSize);
                    gc.setFill(Color.RED);
                }
            }
        };
        timer.start();
    }
    
    public Button buttonMaker(String text, double x, double y) {
        Button button = new Button(text);
        button.setTranslateX(x);
        button.setTranslateY(y);
        button.setFocusTraversable(false);
        return button;
    }
    public Label labelMaker(String text, double x, double y) {
        Label label = new Label(text);
        label.setFont(new Font(20));
        label.setTranslateX(x);
        label.setTranslateY(y);
        return label;
    }
    
    @Override
    public void start(Stage stage) {
        this.stage = stage;
        stage.setScene(startScene);
        stage.setTitle("TETRIS");
        stage.show();
    } 
    
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
