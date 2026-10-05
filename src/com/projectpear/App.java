package com.projectpear;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class App extends Application {
    private GlobalInputService Input;

    @Override
    public void start(Stage stage) {
        buildUi(stage);
        Input = new GlobalInputService();
        Input.startListening();
    }

    @Override
    public void stop() {
        Input.stopListening();
        System.out.println("Application exit. (Exit code 0)");
    }


    public static void main(String[] args) {
        launch(args);
    }

    private void buildUi(Stage stage) {
        stage.setScene(new Scene(new Label("Project PEAR"), 320, 240));
        stage.setTitle("PEAR");
        stage.show();
    }
}
