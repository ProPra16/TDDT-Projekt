package de.hhu.propra16.TDDT;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import vk.core.api.*;
import vk.core.internal.*;


public class Main extends Application {

    private Pane rooty = new Pane();
    private Parent root;
    public Stage myStage = new Stage();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.rooty.getChildren().add(FXMLLoader.load(getClass().getResource("/MainScreen" + ".fxml")));
        myStage.setTitle("TDDT");
        myStage.setScene(new Scene(rooty,700,600));
        myStage.setResizable(false);
        show();
    }

    public void show(){
        myStage.setTitle("TDDT");
        myStage.show();
    }

    public void startProg(){
        try{
            this.rooty.getChildren().add(FXMLLoader.load(getClass().getResource("/TDDT.fxml")));
            this.myStage.setScene(new Scene(rooty,700,600));
            this.myStage.setResizable(false);
        }
        catch (Exception e){}
        show();
        }
}
