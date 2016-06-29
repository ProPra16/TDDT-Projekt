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
    private TDDT m = new TDDT();
    private Ubung neueÜbungen = new Ubung();
    private String [] buttons;
    private Tooltip buttonTooltip = new Tooltip("Button Tooltip");
    @FXML private Button button1 = new Button();
    @FXML private Button button2 = new Button();
    @FXML private Button button3 = new Button();
    @FXML private Button button4 = new Button();


    @FXML
    private void initialize() {
        neueÜbungen.filer();
        buttons = neueÜbungen.fillArray();
        button1.setText(    buttons[0]. substring(0,buttons[0].length()-4)  );
        button2.setText(    buttons[1]. substring(0,buttons[1].length()-4)  );
        button3.setText(    buttons[2]. substring(0,buttons[2].length()-4)  );
        button4.setText(    buttons[3]. substring(0,buttons[3].length()-4)  );
        switch(neueÜbungen.anzahlUbungen()){
            case 0:
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
        }
    }

    public void buttonDescription(MouseEvent event){
        String s = event.getSource().toString();
        s=s.substring(16,17);
        switch (Integer.parseInt(s)){
            case 1:
                neueÜbungen.clearAll();
                setzeBeschreibung(button1);
                break;
            case 2:
                neueÜbungen.clearAll();
                setzeBeschreibung(button2);
                break;
            case 3:
                neueÜbungen.clearAll();
                setzeBeschreibung(button3);
                break;
            case 4:
                neueÜbungen.clearAll();
                setzeBeschreibung(button4);
                break;
        }
    }

    public void buttonAction(ActionEvent event){
        final Node source = (Node) event.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        String s = event.getSource().toString();
        String dateiName = neueÜbungen.gibBeschr();
        s=s.substring(16,17);
        switch (Integer.parseInt(s)){
            case 1:
                stage.close();
                m.startProg(dateiName);
                break;
            case 2:
                stage.close();
                m.startProg(dateiName);
                break;
            case 3:
                stage.close();
                m.startProg(dateiName);
                break;
            case 4:
                stage.close();
                m.startProg(dateiName);
                break;
        }
    }

    public void iterateUp(ActionEvent event){
        buttons = neueÜbungen.up(buttons);
        button1.setText(    buttons[0]. substring(0,buttons[0].length()-4)  );
        button2.setText(    buttons[1]. substring(0,buttons[1].length()-4)  );
        button3.setText(    buttons[2]. substring(0,buttons[2].length()-4)  );
        button4.setText(    buttons[3]. substring(0,buttons[3].length()-4)  );
    }
    public void iterateDown(ActionEvent event){
        buttons = neueÜbungen.down(buttons);
        button1.setText(    buttons[0]. substring(0,buttons[0].length()-4)  );
        button2.setText(    buttons[1]. substring(0,buttons[1].length()-4)  );
        button3.setText(    buttons[2]. substring(0,buttons[2].length()-4)  );
        button4.setText(    buttons[3]. substring(0,buttons[3].length()-4)  );
    }

    public void setzeBeschreibung(Button b){
        neueÜbungen.trenneTeile(b.getText()+".txt");
        this.buttonTooltip.setText(neueÜbungen.gibBeschr());
        b.setTooltip(this.buttonTooltip);
    }
}