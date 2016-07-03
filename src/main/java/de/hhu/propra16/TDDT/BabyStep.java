package de.hhu.propra16.TDDT;
import javafx.application.Platform;
import javafx.scene.control.Label;
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
                if (controller.getPhase()!='F')
                Clock.setText(CountDowns.get(ActualTime));
                String minuten = CountDowns.get(ActualTime).substring(0,1);
                System.out.println(minuten);
                String sekunden = CountDowns.get(ActualTime).substring(2,CountDowns.get(ActualTime).length());
                System.out.println(sekunden);
                if(sekunden.equals("09") == true){
                    Clock.setStyle("-fx-text-fill:red;-fx-font-size:30");
                }
                if(minuten.equals("0") == false){
                    Clock.setStyle("-fx-text-fill:black;-fx-font-size:30");
                }
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

