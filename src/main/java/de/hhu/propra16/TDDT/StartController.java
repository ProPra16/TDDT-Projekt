package de.hhu.propra16.TDDT;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.StringWriter;

public class StartController {
    private TDDT m = new TDDT();
    private Ubung neueUbungen = new Ubung();
    private String [] buttons;
    private Tooltip buttonTooltip = new Tooltip("Button Tooltip");
    private WarningUnit Reporter=new WarningUnit();
    @FXML private MenuBar menubar = new MenuBar();
    @FXML private Menu tracker = new Menu();
    @FXML private Menu hilfe = new Menu();
    @FXML private MenuItem handbuch = new MenuItem();
    @FXML private MenuItem info = new MenuItem();
    @FXML private Button button1 = new Button();
    @FXML private Button button2 = new Button();
    @FXML private Button button3 = new Button();
    @FXML private Button button4 = new Button();

    @FXML
    private void initialize(){
        try {
            neueUbungen.buttonNamer();
            this.buttons = neueUbungen.fillArray();
            switch (neueUbungen.anzahlUbungen()) {
                case 0:
                    button1.setVisible(false);
                    button2.setVisible(false);
                    button3.setVisible(false);
                    button4.setVisible(false);
                    break;
                case 1:
                    button1.setText(this.buttons[0].substring(0, buttons[0].length() - 4));
                    button2.setVisible(false);
                    button3.setVisible(false);
                    button4.setVisible(false);
                    break;
                case 2:
                    button1.setText(this.buttons[0].substring(0, buttons[0].length() - 4));
                    button2.setText(this.buttons[1].substring(0, buttons[1].length() - 4));
                    button3.setVisible(false);
                    button4.setVisible(false);
                    break;
                case 3:
                    button1.setText(this.buttons[0].substring(0, buttons[0].length() - 4));
                    button2.setText(this.buttons[1].substring(0, buttons[1].length() - 4));
                    button3.setText(this.buttons[2].substring(0, buttons[2].length() - 4));
                    button4.setVisible(false);
                    break;
                default:
                    button1.setText(this.buttons[0].substring(0, buttons[0].length() - 4));
                    button2.setText(this.buttons[1].substring(0, buttons[1].length() - 4));
                    button3.setText(this.buttons[2].substring(0, buttons[2].length() - 4));
                    button4.setText(this.buttons[3].substring(0, buttons[3].length() - 4));
                    break;
            }
        }catch(Exception e){e.printStackTrace();}
    }

    public void buttonDescription(MouseEvent event){
        String s = event.getSource().toString();
        s=s.substring(16,17);
        switch (Integer.parseInt(s)){
            case 1:
                neueUbungen.clearAll();
                setzeBeschreibung(button1);
                break;
            case 2:
                neueUbungen.clearAll();
                setzeBeschreibung(button2);
                break;
            case 3:
                neueUbungen.clearAll();
                setzeBeschreibung(button3);
                break;
            case 4:
                neueUbungen.clearAll();
                setzeBeschreibung(button4);
                break;
        }
    }

    public void buttonAction(ActionEvent event) {
        final Node source = (Node) event.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        String UserChoice = event.getSource().toString();
        String Klassenname=getKlassenName(UserChoice);
        UserCode UserEinstellungen=null;
        UserChoice=UserChoice.substring(16,17);
        switch (Integer.parseInt(UserChoice)){
            case 1:
                UserEinstellungen=getUserEinstellungen(Klassenname);
                stage.close();
                m.startProg(UserEinstellungen);
                break;
            case 2:
                UserEinstellungen=getUserEinstellungen(Klassenname);
                stage.close();
                m.startProg(UserEinstellungen);
                break;
            case 3:
                UserEinstellungen=getUserEinstellungen(Klassenname);
                stage.close();
                m.startProg(UserEinstellungen);
                break;
            case 4:
                UserEinstellungen=getUserEinstellungen(Klassenname);
                stage.close();
                m.startProg(UserEinstellungen);
                break;
        }
    }

    public String getKlassenName(String UserChoice) {
        String Klassenname=UserChoice.substring(37);
        Klassenname=Klassenname.replace("'","");
        Klassenname=Klassenname.replace(" ","");
        return Klassenname;
    }

    public void iterateUp(ActionEvent event){

        String [] buttonsTemp = neueUbungen.up(buttons);
        if(buttonsTemp != null) {
            buttons = buttonsTemp;
            button1.setText(buttons[0].substring(0, buttons[0].length() - 4));
            button2.setText(buttons[1].substring(0, buttons[1].length() - 4));
            button3.setText(buttons[2].substring(0, buttons[2].length() - 4));
            button4.setText(buttons[3].substring(0, buttons[3].length() - 4));
        }
    }
    public void iterateDown(ActionEvent event){
        String [] buttonsTemp = neueUbungen.down(buttons);
        if(buttonsTemp != null) {
            buttons = buttonsTemp;
            button1.setText(buttons[0].substring(0, buttons[0].length() - 4));
            button2.setText(buttons[1].substring(0, buttons[1].length() - 4));
            button3.setText(buttons[2].substring(0, buttons[2].length() - 4));
            button4.setText(buttons[3].substring(0, buttons[3].length() - 4));
        }
    }

    public void setzeBeschreibung(Button b){

        try {
            neueUbungen.readFile(b.getText()+".txt" , true);
            neueUbungen.trenneTeile();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.buttonTooltip.setText(neueUbungen.gibBeschr());
        b.setTooltip(this.buttonTooltip);
    }

    public UserCode getUserEinstellungen(String Klassenname) {
        String BabyStepWahl=Reporter.askForBabySteps();
        char Minuten=BabyStepWahl.charAt(0);
        switch (Minuten) {
            case '1':return new UserCode(Klassenname,"1:00");
            case '2':return new UserCode(Klassenname,"2:00");
            case '3':return new UserCode(Klassenname,"3:00");
        }
        return new UserCode(Klassenname);
    }

    public void zeigHandbuch(ActionEvent event)throws Exception{
        neueUbungen.clearAll();
        neueUbungen.readFile("Handbuch.txt",false);
        String handbuchInhalt = neueUbungen.gibInhalt();
        handbuchInhalt = neueUbungen.replacer(handbuchInhalt);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Benutzerhandbuch");
        alert.setHeaderText("Benutzerhandbuch");
        TextArea textArea = new TextArea(handbuchInhalt);
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setMinWidth(500);
        textArea.setMinHeight(350);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);
        GridPane expContent = new GridPane();
        expContent.setMaxWidth(150);
        expContent.add(textArea, 0, 0);
        alert.getDialogPane().setContent(expContent);
        alert.showAndWait();
    }
}