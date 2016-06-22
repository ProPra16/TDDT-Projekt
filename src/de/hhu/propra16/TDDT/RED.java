package de.hhu.propra16.TDDT;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by Asri on 22.06.2016.
 */
public class RED {
    private Parent REDPhase;
    private Stage Fenster;

    public RED(Stage Fenster, Parent REDPhase) {
        this.Fenster=Fenster;
        this.REDPhase=REDPhase;
        showRedPhase();
    }

    public void showRedPhase() {
        Fenster.setScene(new Scene(REDPhase,600,600));
        Fenster.show();
    }
}
