/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohjelmistotekniikka.ui;


import java.util.HashMap;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 *
 * @author birgi
 */
//extends Application import javafx.application.Application; ei toimi
public class TetrisUi extends Application {
    
    @Override
    public void start(Stage stage) {
        Label label = new Label("Tetris");
        Button start = new Button("Start");
        Button scores = new Button("Highscores");
        
        
        
        
        BorderPane components = new BorderPane();
        components.getChildren().add(label);
        components.getChildren().add(start);
        components.getChildren().add(scores);
        
        Scene firstScene = new Scene(components);
        Scene playScene = new Scene(start);
        Scene scoreScene = new Scene(scores);
        
        
        
        start.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                stage.setScene(playScene);
            }
        });
        scores.setOnAction((event) -> {
                stage.setScene(scoreScene);
        });
        
        
        //Playscenessä näppäimistöllä pelataan
        HashMap<KeyCode, Boolean> pushed = new HashMap<>();
        
        
        playScene.setOnKeyPressed(event -> {
            pushed.put(event.getCode(), Boolean.TRUE);
        });
        playScene.setOnKeyReleased(event -> {
            pushed.put(event.getCode(), Boolean.FALSE);
        });
        
        
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (pushed.getOrDefault(KeyCode.LEFT, Boolean.TRUE)) {
                    // move left
                }
            
                if (pushed.getOrDefault(KeyCode.RIGHT, false)) {
                    // move right
                }
            
                if (pushed.getOrDefault(KeyCode.DOWN, false)) {
                    // move down
                }
                if (pushed.getOrDefault(KeyCode.A, false)) {
                    //rotate left
                }
                if (pushed.getOrDefault(KeyCode.D, false)) {
                    //rotate right
                }
            }
        }.start();
        
        
        stage.setScene(firstScene);
        stage.setTitle("TETRIS");
        stage.show();
    } 
    
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
