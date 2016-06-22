package de.hhu.propra16.TDDT;
import javafx.scene.control.TextArea;
import junit.framework.Test;

import java.sql.Ref;

public class UserCode {
    private String KlassenName="Bar";
    private String TestContent="";
    private String ClassContent="public class Bar {}";
    private String ClassCode="";
    private String TestCode="";

    public void setTest(String UserInput) {
        TestCode=UserInput;
        TestContent="import static org.junit.Assert.*;\n"
                + "import org.junit.Test;\n"
                + "public class BarTest { \n"+TestCode+"\n}";
    }

    public void setClass(String UserInput) {
        ClassCode=UserInput;
        ClassContent="public class Bar { \n"+ ClassCode+ "\n}";
    }

    public String getTestContent() {
        return TestContent;
    }

    public String getClassContent() {
        return ClassContent;
    }

    public String getTestCode() {return TestCode;}

    public String setHeaderKlasse() {return "public class "+KlassenName+" {";}

    public String setHeaderTestKlasse() {
        return "public class "+KlassenName+"Test"+" {";
    }

    public String getKlassenName() {return KlassenName; }
    public String getTestName() {return KlassenName+"Test";}

    public boolean isEmpty() {
        return TestCode.equals("");
    }

    public String getClassCode() {
        return ClassCode;
    }
}
