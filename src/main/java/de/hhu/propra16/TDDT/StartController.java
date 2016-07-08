package de.hhu.propra16.TDDT;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

public class StartController {
    private TDDT m = new TDDT();
    private Uebung neueUbungen = new Uebung();
    private String [] buttons;
    private Tooltip buttonTooltip = new Tooltip("Button Tooltip");
    private WarningUnit Reporter=new WarningUnit();
    private UserCode UserEinstellung;
    @FXML private Button button1 = new Button();
    @FXML private Button button2 = new Button();
    @FXML private Button button3 = new Button();
    @FXML private Button button4 = new Button();

    @FXML
    private void initialize(){
        try {
            this.buttonTooltip.setText("");
            neueUbungen.buttonNamer();
            if (!neueUbungen.folderExists()) {m.stop();}
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
        String KlassenName = getKlassenName(UserChoice);
        neueUbungen.readFile(UserChoice + ".txt",true);
        String KlassenInhalt = neueUbungen.gibCode();
        String TestInhalt = neueUbungen.gibTestCode();
        String [] Inhalte = {KlassenInhalt,TestInhalt};
        if(!validClassName(KlassenName)){
            Reporter.fileError();
        }
        else {
            UserChoice = UserChoice.substring(16, 17);
            switch (Integer.parseInt(UserChoice)) {
                case 1:
                    startTDDT(stage, KlassenName, Inhalte);
                    break;
                case 2:
                    startTDDT(stage, KlassenName, Inhalte);
                    break;
                case 3:
                    startTDDT(stage, KlassenName, Inhalte);
                    break;
                case 4:
                    startTDDT(stage, KlassenName, Inhalte);
                    break;
            }
        }
    }

    public boolean validClassName(String KlassenName) {
        ActionUnit Validator=new ActionUnit(new UserCode(KlassenName));
        Validator.compile();
        return !Validator.compileErrors() && !KlassenName.equals("Test");
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
        if(!(neueUbungen.gibBeschr().equals(""))) {
            this.buttonTooltip.setText(neueUbungen.gibBeschr());
            b.setTooltip(this.buttonTooltip);
        }
        else{
            b.setTooltip(null);
        }
    }

    public void startTDDT(Stage stage, String Klassencode, String[] Inhalte) {
        String BabyStepWahl=Reporter.askForBabySteps();
        if (BabyStepWahl.equals("")) {return;}
        String Time =BabyStepWahl.substring(0,4);
        UserEinstellung=new UserCode(Klassencode,Inhalte,Time);
        stage.close();
        m.startProg(UserEinstellung);
    }

    public void zeigHandbuch(ActionEvent event)throws Exception {
        neueUbungen.clearAll();
        neueUbungen.readFile("Handbuch.txt", false);
        if (!(neueUbungen.gibInhalt().equals(""))) {
            {
                String handbuchInhalt = neueUbungen.gibInhalt();
                handbuchInhalt = neueUbungen.replacer(handbuchInhalt);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Benutzerhandbuch");
                alert.setHeaderText("Benutzerhandbuch");
                TextArea textArea = new TextArea(handbuchInhalt);
                textArea.setEditable(false);
                textArea.setWrapText(true);
                textArea.setMinWidth(550);
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
    }

    public void zeigInfo(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText("TDDT - Test Driven Development Tester");
        alert.setContentText("\u00A9" + "ProPra 2016\n" + "Team: Halt-Doch-Einfach-Mal-Dein-Maul");
        alert.showAndWait();
    }
}