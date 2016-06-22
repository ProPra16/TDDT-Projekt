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
        Parent root = FXMLLoader.load(getClass().getResource("/TDDT" +
                ".fxml"));
        primaryStage.setTitle("TDDT");
        primaryStage.setScene(new Scene(root));
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }
    //test


    public static void main(String[] args) {
        launch(args);
        System.out.println("testcommit");
    }
}
