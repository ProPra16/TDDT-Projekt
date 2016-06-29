package de.hhu.propra16.TDDT;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TDDT extends Application {

    private Pane rooty = new Pane();
    public Stage myStage = new Stage();
    private Controller controller;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.rooty.getChildren().add(FXMLLoader.load(getClass().getResource("/MainScreen.fxml")));
        myStage.setTitle("TDDT");
        myStage.setScene(new Scene(rooty,700,600));
        myStage.setResizable(false);
        show();
    }

    public void show(){
        myStage.setTitle("TDDT");
        myStage.show();
    }

    public void startProg(String dateiName){
        try{
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/TDDT.fxml"));
            Parent root = loader.load();
            this.controller = loader.getController();
            UserCode userCode = new UserCode("BlaBla",true,"0:10");
            controller.init(controller,userCode);
            this.myStage.setScene(new Scene(root,700,600));
            this.myStage.setResizable(false);
        }
        catch (Exception e){
            System.out.println("fxml problem");
        }
        show();
    }

    @Override
    public void stop() {
        System.exit(0);
    }
}