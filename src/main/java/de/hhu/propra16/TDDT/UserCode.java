package de.hhu.propra16.TDDT;

public class UserCode {
    private String KlassenName="";
    private String TestContent="";
    private String ClassContent="";
    private String ClassCode="";
    private String TestCode="";
    private String Time;
    private String [] Inhalte = {"",""};

    public UserCode(String KlassenName, String [] Inhalte) {
        this.KlassenName=KlassenName;
        this.Inhalte = Inhalte;
        setClass("");
        setTest("");
        if(!(this.Inhalte[0].equals(""))) {
            setClass(Inhalte[0]);
        }
        if(!(this.Inhalte[1].equals(""))) {
            setTest(Inhalte[1]);
        }
    }

    public UserCode(String KlassenName, String [] Inhalte, String Time) {
        this.KlassenName = KlassenName;
        this.Time = Time;
        this.Inhalte = Inhalte;
        setClass("");
        setTest("");
        if(!(this.Inhalte[0].equals(""))) {
            setClass(this.Inhalte[0]);
        }
        if(!(this.Inhalte[1].equals(""))) {
            setTest(this.Inhalte[1]);
        }
    }

    public UserCode(String KlassenName) {
        this.KlassenName=KlassenName;
        setClass("");
        setTest("");
    }

    public void setTest(String UserInput) {
        TestCode=UserInput;
        TestContent="import static org.junit.Assert.*;\n"
                + "import org.junit.Test;\n"+
                "public class "  + KlassenName + "Test { \n"+TestCode+"\n}";
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
        return !(Time.equals("Kein"));
    }

    public String getTime() {return Time;}

}