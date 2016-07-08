package de.hhu.propra16.TDDT;

import java.util.Calendar;
import java.util.TreeMap;

public class TrackingTable {
    private TreeMap<Long,String> TrackingStats=new TreeMap<>();
    private Calendar calendar=Calendar.getInstance();
    private String Stats="";

    public TrackingTable() {}

    public void addEvent(long Time, String Action) {
            TrackingStats.put(Time, Action);
    }

    public void fillStats() {
        Stats="";
        TrackingStats.forEach((Time,Action)-> Stats+=getUTCTime(Time)+" - "+Action+"\n");
    }

    public String getStats() {
        return Stats;
    }

    public String getUTCTime(long Time) {
        calendar.setTimeInMillis(Time);
        String hh = calendar.get(Calendar.HOUR_OF_DAY)+"";
        String mm = calendar.get(Calendar.MINUTE)+"";
        String ss = calendar.get(Calendar.SECOND)+"";
        if (hh.length()==1) {hh="0"+hh;}
        if (mm.length()==1) {mm="0"+mm;}
        if (ss.length()==1) {ss="0"+ss;}
        return hh+":"+mm+":"+ss;
    }
}
