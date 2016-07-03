package de.hhu.propra16.TDDT;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
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
        starter();
    }

    public void show(){
        myStage.setTitle("TDDT");
        myStage.show();
    }

    public void starter() {
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
        this.myStage.setScene(new Scene(root,850,700));
        this.myStage.setResizable(false);
        show();
    }
}