package de.hhu.propra16.TDDT;
import com.sun.org.apache.regexp.internal.RE;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class BabyStep extends Thread {
    private String Time;
    private Label Clock;
    private volatile boolean expired;
    private Controller controller;
    private ArrayList<String> CountDowns=new ArrayList<>();
    private int ActualTime;
    private boolean visible=true;

    public BabyStep(String Time, Controller controller) {
        this.Time=Time;
        this.controller = controller;
        fill();
    }

    @Override
    public void run() {
        while (!expired) {
            Platform.runLater(()-> {
                if (ActualTime == -1) {
                    ActualTime = 0;
                }
             if (visible)  { Clock.setText(""); }
                if (controller.getPhase()!='F') {
                   if (visible) { Clock.setText(CountDowns.get(ActualTime));}
                    String minuten = CountDowns.get(ActualTime).substring(0, 1);
                    String sekunden = CountDowns.get(ActualTime).substring(2, CountDowns.get(ActualTime).length());
                    if (sekunden.equals("09") == true) {
                        Clock.setStyle("-fx-text-fill:red;-fx-font-size:30");
                    }
                    if (minuten.equals("0") == false) {
                        Clock.setStyle("-fx-text-fill:black;-fx-font-size:30");
                    }
                }
            });
            ActualTime+=1;
            ticktack();
            isExpired();
        }
    }

    public void ticktack() {
        try {
            TimeUnit.MILLISECONDS.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void isExpired() {
       Platform.runLater(()-> {
           if (ActualTime==CountDowns.size()) {
               expired = true;
               controller.switchPhase();
           }
       });
    }

    public void countDown(Label Display) {
        this.Clock = Display;
        start();
    }

    public void restart() {
        expired=false;
        ActualTime=-1;
    }

    public void restart(int Pos) {
        expired = false;
        visible=true;
        ActualTime=Pos;
    }

    public void fill() {
        int min=Integer.parseInt(Time.substring(0,1));
        int sec=Integer.parseInt(Time.substring(2));
        while (!(sec==0 && min==0)) {
            if (sec < 10) {
                CountDowns.add(min+":0"+sec);
            }
            else {
                CountDowns.add(min+":"+sec);
            }
            sec--;
            if (sec==-1) {
                min--;
                sec=59;
            }
        }
        CountDowns.add("0:00");
    }

    public void stopTimer(){
        expired = true;
    }

    public int getTime() {
        return ActualTime;
    }

    public void setUnvisible() {
        visible=false;
    }
}

