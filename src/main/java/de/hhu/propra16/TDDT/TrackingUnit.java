package de.hhu.propra16.TDDT;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import java.util.concurrent.TimeUnit;

public class TrackingUnit {
    private long StartTime;
    private long elapsedTimed;
    private long splitTime;
    private long RedTime;
    private long GreenTime;
    private long RefactorTime;
    private double RedTimeInPreCent;
    private double GreenTimeInPreCent;
    private double RefactorTimeInPreCent;
    private TrackingTable TrackingStats=new TrackingTable();
    private String ExtraInfo="";
    private char actualPhase;
    private long trackerTime = 0;

    public TrackingUnit() {
        StartTime=System.currentTimeMillis();
        splitTime=StartTime;
    }

    public void stopTime(char Phase) {
        switch (Phase) {
            case 'R':RedTime+=(System.currentTimeMillis()-splitTime);
                splitTime=System.currentTimeMillis(); break;
            case 'G':GreenTime+=(System.currentTimeMillis()-splitTime);
                splitTime=System.currentTimeMillis();break;
            case 'F':RefactorTime+=(System.currentTimeMillis()-splitTime);
                splitTime=System.currentTimeMillis();break;
        }
    }


    public void addEvent(String Action) {
        TrackingStats.addEvent(System.currentTimeMillis(),Action);
    }

    public String getTrackingStats() {
        TrackingStats.addEvent(StartTime,"Anwendung gestartet");
        TrackingStats.fillStats();
        return TrackingStats.getStats()+"\n\n"+ExtraInfo;
    }

    public String getChartInfos(PieChart chart,char Phase) {
        actualPhase = Phase;
        stopTime(Phase);
        elapsedTimed=System.currentTimeMillis()-(StartTime+trackerTime);
        RedTimeInPreCent = Math.round(((double) RedTime/elapsedTimed)*100);
        GreenTimeInPreCent = Math.round(((double) GreenTime/elapsedTimed)*100);
        RefactorTimeInPreCent = Math.round(((double) RefactorTime/elapsedTimed)*100);
        ExtraInfo=setExtraInfo();
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("RED-Time", RedTimeInPreCent),
                        new PieChart.Data("GreenValidator-Time", GreenTimeInPreCent),
                        new PieChart.Data("REFACTOR-Time", RefactorTimeInPreCent));
        chart.setData(pieChartData);
        chart.setTitle("Phase Duration");
        return getTrackingStats();
    }

    public String setExtraInfo() {
        return "Du programmierst schon seit: "+convertTime(elapsedTimed)+".\n"+"" +
                "Davon warst du : \n"+convertTime(RedTime)+" (also "+RedTimeInPreCent+"%)"+" in RED."+
                "\n"+convertTime(GreenTime)+" (also "+GreenTimeInPreCent+"%)"+" in GREEN."+"\n"+
        convertTime(RefactorTime)+" (also "+RefactorTimeInPreCent+"%)"+" in REFACTOR.";
    }

    public String convertTime(long Time) {
        return String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(Time),
                TimeUnit.MILLISECONDS.toMinutes(Time) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(Time)),
                TimeUnit.MILLISECONDS.toSeconds(Time) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(Time)));
    }

    public char getActualPhase(){return actualPhase;}

    public void setCorrection(long pretime){
        long currentTime = System.currentTimeMillis();
        long diff = currentTime - pretime;
        switch (actualPhase){
            case 'R': RedTime -= diff;
                break;
            case 'G': GreenTime -= diff;
                break;
            case 'F': RefactorTime -= diff;
                break;
        }
       trackerTime += diff;
    }
}
