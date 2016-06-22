package de.hhu.propra16.TDDT;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.File;


public class StartController {
    private boolean ready;
    private Main m = new Main();
    private Übung neueÜbungen = new Übung();
    @FXML private Button button1 = new Button();
    @FXML private Button button2 = new Button();
    @FXML private Button button3 = new Button();
    @FXML private Button button4 = new Button();


    @FXML
    private void initialize() {
        switch (neueÜbungen.anzahlÜbungen()){
            case 0:
                System.out.print("bitte nicht");
                button1.setVisible(false);
                button2.setVisible(false);
                button3.setVisible(false);
                button4.setVisible(false);
                break;
            case 1:
                button2.setVisible(false);
                button3.setVisible(false);
                button4.setVisible(false);
                break;
            case 2:
                button3.setVisible(false);
                button4.setVisible(false);
                break;
            case 3:
                button4.setVisible(false);
                break;
            case 4:
                break;
        }
    }

    public void buttonDescription(MouseEvent event){
        Tooltip buttonTooltip = new Tooltip("Button Tooltip");
        String s = event.getSource().toString();
        s=s.substring(16,17);
        switch (Integer.parseInt(s)){
            case 1:
                buttonTooltip.setText("Button 1 is cool");
                button1.setTooltip(buttonTooltip);
                break;
            case 2:
                buttonTooltip.setText("Button 2 is cool");
                button2.setTooltip(buttonTooltip);
                break;
            case 3:
                buttonTooltip.setText("Button 3 is cool");
                button3.setTooltip(buttonTooltip);
                break;
            case 4:
                buttonTooltip.setText("Button 4 is cool");
                button4.setTooltip(buttonTooltip);
                break;
        }
    }

    public void buttonAction(ActionEvent event){
        String s = event.getSource().toString();
        s=s.substring(16,17);
        switch (Integer.parseInt(s)){
            case 1:
                button1.setText("Dies ist Button1");
                break;
            case 2:
                button2.setText("Dies ist Button2");
                break;
            case 3:
                button3.setText("Dies ist Button3");
                break;
            case 4:
                button4.setText("Dies ist Button4");
                break;
        }
    }

    public void iterateUp(ActionEvent event){
        File[] files = neueÜbungen.finder();
        String t = files[0].toString();
        System.out.print(t);
    }
    public void iterateDown(ActionEvent event){
        final Node source = (Node) event.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
        m.startProg();
    }
}
