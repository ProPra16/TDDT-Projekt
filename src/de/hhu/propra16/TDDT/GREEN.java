package de.hhu.propra16.TDDT;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import vk.core.api.CompilerResult;
import vk.core.api.JavaStringCompiler;
import vk.core.api.TestResult;

public class GREEN {
    private JavaStringCompiler compiler;
    private UserCode UserTest;
    private boolean ready;
    @FXML private Text Import1;
    @FXML private Text Import2;
    @FXML private Text Klassenname;

    public GREEN(UserCode UserTest, JavaStringCompiler compiler) {
        this.compiler = compiler;
        this.UserTest=UserTest;
        checkifReady();
        isReady();
    }

    public void checkifReady() {
        CompilerResult UserCode = compiler.getCompilerResult();
        if (UserCode.hasCompileErrors()) {
            ready = true;
        }
        else {
            TestResult Tests = compiler.getTestResult();
            if (Tests.getNumberOfFailedTests() > 0)
                ready = true;
        }
    }

    public void isReady() {
        if (ready == false) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Tests klappen!");
            alert.setContentText("Deine Tests werden alle bestanden.\n" +
                    "Bitte sorge dafür, dass mind. ein Test fehlschlägt.");
            alert.showAndWait();
        }
    }

    public boolean isready() {return ready;}
}
