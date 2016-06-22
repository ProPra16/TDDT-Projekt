package de.hhu.propra16.TDDT;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TDDT extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/TDDT" +
                ".fxml"));
        primaryStage.setTitle("TDDT");
        primaryStage.setScene(new Scene(root,700,600));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
