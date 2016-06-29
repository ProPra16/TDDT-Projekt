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

public class BabyStep extends Thread {
    private int min;
    private int sec;
    private String Time;
    private Label Clock;
    private volatile boolean expired;
    private Controller controller;

    public BabyStep(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void run() {
        while (!expired) {
            Platform.runLater(() -> {
                if (controller.getPhase() != 'F') {
                    Clock.setText("");
                    Clock.setText(formatTime());
                }
            });
            sec--;
            if (sec == -1) {
                sec = 59;
                min--;
            }
            ticktack();
            isTicking();
        }
    }

    public void ticktack() {
        try {
            TimeUnit.MILLISECONDS.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void isTicking() {
        Platform.runLater(() -> {
            if (min == 0 && sec == 0) {
                expired = true;
                controller.switchPhase();
            }
        });
    }

    public String formatTime() {
        if (sec < 10) {
            return min + ":" + "0" + sec;
        }
        return min + ":" + sec;
    }

    public void countDown(Label Display, String Time) {
        this.Time = Time;
        this.min = Integer.parseInt(Time.substring(0, 2));
        this.sec = Integer.parseInt(Time.substring(3)) + 1;
        this.Clock = Display;
        start();
    }

    public void restart(String Time) {
        expired = false;
        this.min = Integer.parseInt(Time.substring(0, 2));
        this.sec = Integer.parseInt(Time.substring(3)) + 1;
    }
}

