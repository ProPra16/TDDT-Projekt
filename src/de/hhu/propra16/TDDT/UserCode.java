package de.hhu.propra16.TDDT;
import javafx.scene.control.TextArea;
import junit.framework.Test;

public class UserCode {
    private String KlassenName="Bar";
    private String TestContent="";
    private String ClassContent="public class Bar {}";
    private String ClassCode="";
    private String TestCode="";

    public UserCode(String UserCode, boolean isTest) {
        if (isTest) {
            setTest(UserCode);
        }
        else {
            setClass(UserCode);
        }
    }

    public void setTest(String UserInput) {
        TestCode=UserInput;
        String Content="import static org.junit.Assert.*;\n"
                + "import org.junit.Test;\n"
                + "public class BarTest { \n";
        Content += UserInput+"}";
        TestContent=Content;
    }

    public void editCode(TextArea Code) {
        ClassCode=Code.getText();
        ClassContent="public class Bar { "+ClassCode+"}";
    }

    public void setClass(String UserInput) {
        ClassCode=UserInput;
        String Content="public class Bar {";
        Content+=UserInput+"}";
        ClassContent=Content;
    }

    public String getTestContent() {
        System.out.println(TestContent);
        return TestContent;
    }

    public String getClassContent() {
        System.out.println(ClassContent);
        return ClassContent;
    }

    public String setHeaderKlasse() {return "public class "+KlassenName+" {";}

    public String setHeaderTestKlasse() {
        return "public class "+KlassenName+"Test"+" {";
    }

    public String getKlassenName() {return KlassenName; }
    public String getTestName() {return KlassenName+"Test";}

    public boolean isEmpty() {
        return TestCode.equals("");
    }
}
