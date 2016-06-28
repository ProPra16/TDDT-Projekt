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

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.rooty.getChildren().add(FXMLLoader.load(getClass().getResource("MainScreen" + ".fxml")));
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
           // this.rooty.getChildren().add(FXMLLoader.load(getClass().getResource("/TDDT" + ".fxml")));
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/TDDT" + ".fxml"));
            this.rooty = loader.load();
            Controller controller = loader.getController();
            controller.setName(dateiName);
            this.myStage.setScene(new Scene(rooty,700,600));
            this.myStage.setResizable(false);
        }
        catch (Exception e){
            System.out.println("fxml problem");
        }
        show();
    }
}