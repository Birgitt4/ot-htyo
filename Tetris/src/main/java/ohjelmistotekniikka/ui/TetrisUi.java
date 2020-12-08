/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohjelmistotekniikka.ui;


import java.io.FileInputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
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
 * @author birgi
 */
//extends Application import javafx.application.Application; ei toimi
public class TetrisUi extends Application {
    
    private int fontSize = 72;
    private int blockSize = 35;
    private Scene startScene;
    private Scene playScene;
    private Tetris game;
    private Text points;
    private TetrisDao tetrisDao;
    private Stage stage;
    
    @Override
    public void init() throws Exception {
        Properties properties = new Properties();
        properties.load(new FileInputStream("config.properties"));
        String database = properties.getProperty("database");
        tetrisDao = new TetrisDao(database);
        game = new Tetris(tetrisDao);
    }
    
    
    public Scene createStartingScene(Stage stage) {
        BorderPane startPane = new BorderPane();
        startPane.setPrefSize(16*blockSize, 10*blockSize + 200);
        VBox vbox = new VBox();
        vbox.setSpacing(20);
        //Hifistelyä
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

        
        Button start = new Button("Start");
        start.setShape(new Circle(60));
        start.setMinSize(80, 60);

        Button scores = new Button("Highscores");
        scores.setShape(new Circle(60));
        scores.setMinSize(80, 60);
        
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(tetrisText, start, scores);
        startPane.setCenter(vbox);
        startScene = new Scene(startPane);

        start.setOnMouseClicked((event) -> {
            stage.setScene(createPlayingScene());
        });
        scores.setOnMouseClicked(event -> {
            stage.setScene(createScoreScene());
        });
        
        return startScene;
    }
    
    public Scene createScoreScene() {
        Pane scorePane = new Pane();
        scorePane.setPrefSize(16*blockSize, 10*blockSize + 200);
        Label topTree = new Label("Top 3: ");
        topTree.setTranslateX(100);
        topTree.setTranslateY(100);
        String top = "";
        try {
            top = game.getTopTree();
        } catch (SQLException e) {
            e.getMessage();
        }
        Label tops = new Label(top);
        tops.setTranslateX(150);
        tops.setTranslateY(100);
        
        Button back = new Button("Back");
        back.setTranslateX(500);
        back.setTranslateY(500);
        
        back.setOnMouseClicked(event -> {
            stage.setScene(startScene);
        });
        
        scorePane.getChildren().addAll(topTree, tops, back);
        
        return new Scene(scorePane);
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
    
    public void gameOver() {
        Stage saveScores = new Stage();
        GridPane pane = new GridPane();
        Scene scene = new Scene(pane);
        
        Label name = new Label("Name: ");
        TextField nameField = new TextField();
        Label pointsLabel = new Label("Points: ");
        Label pointsLabel2 = new Label(Integer.toString(game.getPoints()));
        Button cancel = new Button("cancel");
        
        Button ok = new Button("OK");
        
        pane.add(name, 0, 0);
        pane.add(nameField, 1, 0);
        pane.add(pointsLabel, 0, 1);
        pane.add(pointsLabel2, 1, 1);
        pane.add(ok, 0, 2);
        pane.add(cancel, 1, 2);
        
        pane.setHgap(20);
        pane.setVgap(20);
        pane.setPadding(new Insets(20));
        
        cancel.setOnMouseClicked(event -> {
            saveScores.close();
        });
        ok.setOnMouseClicked(event -> {
            try {
                game.savePoints(nameField.getText());
                saveScores.close();
            } catch (Exception e) {
                e.getMessage();
            }
        });
        
        saveScores.setScene(scene);
        saveScores.show();
    }
    
    public Scene createPlayingScene() {
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
        
        GraphicsContext gc = grid.getGraphicsContext2D();
        gc.setFill(Color.RED);
        
        
        
        playScene = new Scene(playPane);
        
        HashMap<KeyCode, Boolean> pushed = new HashMap<>();
        playScene.setOnKeyPressed(event -> {
            pushed.put(event.getCode(), Boolean.TRUE);
        });
        playScene.setOnKeyReleased(event -> {
            pushed.put(event.getCode(), Boolean.FALSE);
        });

        game.createShape();
        AnimationTimer timer = new AnimationTimer() {
            
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
                        gc.setFill(Color.BLACK);
                        gc.setFont(new Font(50));
                        gc.setTextBaseline(VPos.CENTER);
                        gc.fillText("GAME OVER", blockSize, 6*blockSize);
                        gc.setFill(Color.RED);
                        gameOver();
                        this.stop();
                    }
                    if (now-down >= 500000000) {
                        
                        game.moveDown();
                        gc.clearRect(0,0,10*blockSize, 16*blockSize);
                        update(gc);
                        down = now;
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
                        }
                        if (pushed.getOrDefault(KeyCode.D, false)) {
                            game.rotateRight();
                            gc.clearRect(0,0,10*blockSize, 16*blockSize);
                            update(gc);
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
                    System.out.println("tässä tulee arrayindexexception, kun"
                            + "samaa aikaa yrittää mennä sivuilta yli ja kiertää"
                            + "palaa. :( Voi aloittaa uuden pelin sitten.");
                }
                    
            }
            
        };
        timer.start();
        points = new Text();
        points.setText("Points: " + game.getPoints());
        points.setTranslateX(10*blockSize + 70);
        points.setTranslateY(200);
        
        Button pause = new Button("pause");
        pause.setTranslateX(10*blockSize + 75);
        pause.setTranslateY(50);
        pause.setFocusTraversable(false);
        Button cont = new Button("continue");
        cont.setTranslateX(10*blockSize + 75);
        cont.setTranslateY(100);
        cont.setFocusTraversable(false);
        Button again = new Button("again");
        again.setTranslateX(10*blockSize + 75);
        again.setTranslateY(150);
        again.setFocusTraversable(false);
        
        playPane.getChildren().addAll(cont, pause, again, points);
        pause.setOnMouseClicked(event -> {
            timer.stop();
        });
        cont.setOnMouseClicked(event -> {
            timer.start();
        });
        again.setOnMouseClicked(event -> {
            game = new Tetris(tetrisDao);
            gc.clearRect(0,0,10*blockSize, 16*blockSize);
            game.createShape();
            timer.start();
        });
        
        return playScene;
    }
    
    
    @Override
    public void start(Stage stage) {
        this.stage = stage;
        stage.setScene(createStartingScene(stage));
        stage.setTitle("TETRIS");
        stage.show();
    } 
    
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
