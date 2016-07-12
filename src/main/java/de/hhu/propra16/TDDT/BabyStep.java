package de.hhu.propra16.TDDT;

import javafx.application.Platform;
import javafx.scene.control.Label;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class BabyStep extends Thread {
    private String Time;
    private Label Clock = new Label();
    private WarningUnit TimeUpInfo;
    private volatile boolean expired;
    private Controller controller;
    private ArrayList<String> CountDowns=new ArrayList<>();
    private int ActualTime;
    private boolean visible=true;
    private boolean lastSeconds;

    public BabyStep(String Time, Controller controller) {
        this.Time=Time;
        this.controller = controller;
        this.TimeUpInfo=controller.getWarningUnit();
        fill();
    }

    @Override
    public void run() {
        while (!expired) {
            Platform.runLater(()-> {
                if (ActualTime == -1) {
                    ActualTime = 0;
                }
                if (visible) {Clock.setText(""); }
                if (controller.getPhase()!='F') {
                    if (visible) {Clock.setText(CountDowns.get(ActualTime));}
                    if(!Clock.getText().equals("0:00")){
                        Clock.setVisible(true);
                    }
                    setStyle();
                }});
            ActualTime+=1;
            ticktack();
            isExpired();
        }
    }

    public void ticktack() {
        try {
            TimeUnit.MILLISECONDS.sleep(1000);
        } catch (InterruptedException ignored) {}
    }

    public void isExpired() {
        Platform.runLater(()-> {
            if (ActualTime==CountDowns.size()) {
                expired = true;
                showInfo();
                controller.switchPhase();
            }
        });
    }

    public void setStyle() {
        if (Clock.getText().contains("0:0") || Clock.getText().equals("0:10")) {
            Clock.setStyle("-fx-text-fill:red;-fx-font-size:30");
            lastSeconds=true;
        }
        if (!lastSeconds) {
            Clock.setStyle("-fx-text-fill:black;-fx-font-size:30");
        }
    }

    public void showInfo() {
        if (controller.getPhase()!='F' && !TimeUpInfo.TimeUpisShowing()) {
            restart();
            setUnvisible();
            Clock.setVisible(false);
            TimeUpInfo.timeUp();
            while (TimeUpInfo.TimeUpisShowing()) {
                try {TimeUnit.SECONDS.sleep(1000);}
                catch (InterruptedException ignored) {}
            }
        }
    }

    public void countDown(Label Display) {
        this.Clock = Display;
        start();
    }

    public void restart() {
        visible=!TimeUpInfo.TimeUpisShowing();
        expired=false;
        lastSeconds=false;
        ActualTime=-1;
    }

    public void restart(int Pos) {
        visible=true;
        expired=false;
        lastSeconds=false;
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

    public void clear() {
        Clock.setText("");
    }
}
