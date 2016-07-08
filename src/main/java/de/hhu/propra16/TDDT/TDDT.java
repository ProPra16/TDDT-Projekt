package de.hhu.propra16.TDDT;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.IOException;

public class TDDT extends Application {

    private Parent rooty = new Pane();
    public Stage myStage = new Stage();

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
        try {this.rooty = FXMLLoader.load(getClass().getResource("/MainScreen.fxml"));}
        catch (Exception e) {}
        myStage.setTitle("TDDT");
        myStage.setScene(new Scene(rooty,700,600));
        myStage.setResizable(false);
        show();
    }

    public void startProg(UserCode UserEinstellungen) {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/TDDT.fxml"));
        Parent root = null;
        try {root = loader.load();} catch (IOException e){}
        Controller controller = loader.getController();
        controller.setStage(myStage);
        controller.init(controller,UserEinstellungen);
        this.myStage.setScene(new Scene(root,850,700));
        this.myStage.setResizable(false);
        show();
    }

    @Override
    public void stop() {
        System.exit(0);
    }
}