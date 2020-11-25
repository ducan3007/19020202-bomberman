package Bomberman;

import Bomberman.GlobalVariables.GlobalVariables;
import Bomberman.Scene.Board;
import Bomberman.Gamecontroller.Audio;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Bomberman extends Application {

    @Override
    public void start(Stage primaryStage) {
        Audio.ThemeMusic();
        primaryStage.setTitle(GlobalVariables.GAME_NAME );
        Board.setupScene();
        Scene s = Board.getScene();
        primaryStage.setScene(s);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
