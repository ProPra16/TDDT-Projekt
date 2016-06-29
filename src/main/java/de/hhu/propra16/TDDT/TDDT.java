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

public class TDDT extends Application {

    private Pane rooty = new Pane();
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
        try{
        String path = "build/resources/main/musik.mp3";
        Media media = new Media(new File(path).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        MediaView mediaView = new MediaView(mediaPlayer);
        }
        catch (Exception e){
            System.out.print("Die musik bleibt!");
        }
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