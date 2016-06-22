package de.hhu.propra16.TDDT;


public class UserCode {
    private String KlassenName="Bar";
    private String TestContent="";
    private String ClassContent="public class Bar {}";

    public UserCode(String UserCode, boolean isTest) {
        if (isTest) {
            setTest(UserCode);
        }
        else {
            setClass(UserCode);
        }
    }

    public void setTest(String UserInput) {
        String Content="import static org.junit.Assert.*;\n"
                + "import org.junit.Test;\n"
                + "public class BarTest { \n";
        Content += UserInput+"}";
        TestContent=Content;
    }

    public void setClass(String UserInput) {
        String Content="public class Bar {";
        Content+=UserInput+"}";
        ClassContent=Content;
    }

    public String getTestContent() {
        return TestContent;
    }

    public String getClassContent() {
        return ClassContent;
    }

    public String setHeaderKlasse() {return "public class "+KlassenName+" {";}

    public String setHeaderTestKlasse() {
        return "public class "+KlassenName+"Test"+" {";
    }

    public String getKlassenName() {return KlassenName; }
    public String getTestName() {return KlassenName+"Test";}
}
