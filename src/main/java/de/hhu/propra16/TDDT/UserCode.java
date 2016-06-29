package de.hhu.propra16.TDDT;

public class UserCode {
    private String KlassenName="";
    private String TestContent="";
    private String ClassContent="";
    private String ClassCode="";
    private String TestCode="";
    private boolean BabyStep;
    private String Time;

    public UserCode(String KlassenName) {
        this.KlassenName=KlassenName;
        setClass("");
        setTest("");
    }

    public UserCode(String KlassenName,boolean BabyStep, String Time) {
        this.KlassenName = KlassenName;
        this.BabyStep = BabyStep;
        this.Time = Time;
        setClass("");
        setTest("");
    }

    public void setTest(String UserInput) {
        TestCode=UserInput;
        TestContent="import static org.junit.Assert.*;\n"
                + "import org.junit.Test;\n"
                + KlassenName + "Test { \n"+TestCode+"\n}";
    }

    public void setClass(String UserInput) {
        ClassCode=UserInput;
        ClassContent="public class "+KlassenName+" { \n"+ ClassCode+"\n}";
    }


    public String getTestContent() {
        return TestContent;
    }

    public String getClassContent() {
        return ClassContent;
    }

    public String getClassCode() {
        return ClassCode;
    }

    public String getTestCode() {return TestCode;}

    public String getKlassenName() {return KlassenName; }

    public String getTestName() {return KlassenName+"Test";}

    public boolean isEmpty() {
        return TestCode.equals("");
    }

    public boolean hasBabySteps() {
        return BabyStep;
    }

    public String getTime() {return Time;}
}