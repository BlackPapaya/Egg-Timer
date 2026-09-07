package com.example.eggtimer;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.media.AudioClip;

public class Main extends Application {

    private int secondsLeft = 180;
    private Label timerLabel = new Label("03:00");
    private ImageView eggImage = new ImageView();
    private Timeline timeline;


    @Override
    public void start(Stage primaryStage) {

        Image softEgg = new Image(getClass().getResourceAsStream("/soft.png"));
        Image hardEgg = new Image(getClass().getResourceAsStream("/hard.png"));
        Image Egg = new Image(getClass().getResourceAsStream("/egg.png"));
        AudioClip clickSound = new AudioClip(getClass().getResource("/sounds/click.wav").toExternalForm());
        AudioClip alarmSound = new AudioClip(getClass().getResource("/sounds/alarm.wav").toExternalForm());
        ImageView bildAnzeige = new ImageView(Egg);
        getClass().getResource("/style.css");
        bildAnzeige.setFitWidth(150);
        bildAnzeige.setPreserveRatio(true);

        eggImage.setImage(softEgg);
        eggImage.setFitWidth(200);
        eggImage.setPreserveRatio(true);
        timerLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");


        Button btnSoft = new Button("Soft (3 Min)");
        btnSoft.setOnAction(e -> setTimer(180, softEgg));

        Button btnHard = new Button("Hard (6 Min)");
        btnHard.setOnAction(e -> setTimer(360, hardEgg));

        Button btnStart = new Button("Start!");
        btnStart.setOnAction(e -> {
            clickSound.play();
            startTimer();
        });




        VBox root = new VBox(15, bildAnzeige, eggImage, timerLabel, btnSoft, btnHard, btnStart);
        root.setAlignment(Pos.CENTER);


        Scene scene = new Scene(root, 450, 650);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        primaryStage.setTitle("Egg Timer");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    private void setTimer(int seconds, Image image) {
        if (timeline != null) timeline.stop();
        this.secondsLeft = seconds;
        this.eggImage.setImage(image);
        updateLabel();
    }


    private void startTimer() {
        if (timeline != null) timeline.stop();

        timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            secondsLeft--;
            updateLabel();
            if (secondsLeft <= 0) {
                timeline.stop();
                timerLabel.setText("Fertig! 🔔");
                AudioClip alarmSound = new AudioClip(getClass().getResource("/sounds/alarm.wav").toExternalForm());
                alarmSound.play();
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void updateLabel() {
        int minutes = secondsLeft / 60;
        int seconds = secondsLeft % 60;
        timerLabel.setText(String.format("%02d:%02d", minutes, seconds));
    }

    public static void main(String[] args) {
        launch(args);
    }
}