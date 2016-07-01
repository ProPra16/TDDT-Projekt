package de.hhu.propra16.TDDT;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class TDDT extends Application {

    private Parent rooty = new Pane();
    public Stage myStage = new Stage();
    private Controller controller;
    private ArrayList<String> files = new ArrayList<String>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage){
        try {
            this.rooty = FXMLLoader.load(getClass().getResource("/MainScreen.fxml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        myStage.setTitle("TDDT");
        myStage.setScene(new Scene(rooty,700,600));
        myStage.setResizable(false);


        show();
    }

    public void show(){
        myStage.setTitle("TDDT");
        myStage.show();
    }

    public void startProg(UserCode UserEinstellungen) {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/TDDT.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.controller = loader.getController();
        controller.init(controller,UserEinstellungen);
        this.myStage.setScene(new Scene(root,800,675));
        this.myStage.setResizable(false);
        show();
    }

    @Override
    public void stop() {
        System.exit(0);
    }
}