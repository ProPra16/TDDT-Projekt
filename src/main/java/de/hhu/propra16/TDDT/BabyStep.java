package de.hhu.propra16.TDDT;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class BabyStep extends Thread {
    private String Time;
    private Label Clock;
    private volatile boolean expired;
    private Controller controller;
    private ArrayList<String> CountDowns=new ArrayList<>();
    private int ActualTime;

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
                Clock.setText("");
                Clock.setText(CountDowns.get(ActualTime));
            });
            ticktack();
            ActualTime += 1;
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
        Platform.runLater(() -> {
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
        expired = false;
        ActualTime=-1;
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
}

