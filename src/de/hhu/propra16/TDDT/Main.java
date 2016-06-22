package de.hhu.propra16.TDDT;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import vk.core.api.*;
import vk.core.internal.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        RED REDPhase= new RED(primaryStage,FXMLLoader.load(getClass().getResource("/TDDT" +
                ".fxml")));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
