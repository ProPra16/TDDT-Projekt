package de.hhu.propra16.TDDT;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class BabyStep extends Thread  {
    private int min;
    private int sec;
    private String Time;
    private Label Clock;
    private volatile boolean expired;
    private Controller controller;

    public BabyStep(boolean expired, Controller controller) {
        this.expired=expired;
        this.controller=controller;
    }

    @Override
    public void run() {
           while (!expired) {
               sec--;
               if (sec == -1) {
                   sec = 59;
                   min--;
               }
               ticktack();
               showTime();
               if (min == 0 && sec == 0) {
                   expired = true;
                   controller.switchRED();
                   BabyStep NextStep=new BabyStep(false,controller);
                   NextStep.countDown(Clock,Time);
                   System.out.println(Time);
               }
           }
    }

    @Override
    public void interrupt() {
        expired=true;
    }

    public void ticktack() {
        try {
            TimeUnit.MILLISECONDS.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void showTime() {
        Platform.runLater(() -> {
            Clock.setText("");
            Clock.setText(formatTime());
        });
    }

    public String formatTime() {
        if (sec<10) {
            return min+":"+"0"+sec;
        }
        return min+":"+sec;
    }

    public void countDown(Label Display,String Time) {
        this.Time=Time;
        this.min = Integer.parseInt(Time.substring(0, 1));
        this.sec = Integer.parseInt(Time.substring(2)) + 1;
        this.Clock = Display;
        start();
    }
}

