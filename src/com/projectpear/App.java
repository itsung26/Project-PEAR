package com.projectpear;

import java.nio.file.Path;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
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
        Path assets = Path.of("assets");
        Image pearImage = new Image(assets.resolve("pear.png").toUri().toString());
        Image pearIcon = new Image(assets.resolve("pear32.png").toUri().toString());

        // Brand colors
        String pearGreen = "#8BC34A";
        String pearGreenDark = "#689F38";
        String surface = "#F7F8F4";
        String card = "#FFFFFF";
        String ink = "#1B1F16";
        String muted = "#6B7265";
        String stroke = "#D9DDD2";

        // --- Logo (square frame, smaller pear) ---
        ImageView logo = new ImageView(pearImage);
        logo.setFitWidth(30);
        logo.setFitHeight(30);
        logo.setPreserveRatio(true);
        logo.setSmooth(true);

        StackPane logoFrame = new StackPane(logo);
        logoFrame.setPrefSize(48, 48);
        logoFrame.setMaxSize(48, 48);
        logoFrame.setStyle(
            "-fx-background-color: " + pearGreen + ";"
            + "-fx-border-color: " + pearGreenDark + ";"
            + "-fx-border-width: 2;"
            + "-fx-padding: 6;"
        );
        logoFrame.setEffect(new DropShadow(10, Color.web("#00000022")));

        Label title = new Label("Project PEAR");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 28));
        title.setTextFill(Color.web(ink));

        Label subtitle = new Label("Placeholder subtitle");
        subtitle.setFont(Font.font("Segoe UI", 12));
        subtitle.setTextFill(Color.web(muted));

        VBox header = new VBox(6, logoFrame, title, subtitle);
        header.setAlignment(Pos.CENTER);

        // --- Hotkey row: button + read-only field (visual only) ---
        Button hotkeyButton = new Button("Hotkey");
        hotkeyButton.setPrefHeight(36);
        hotkeyButton.setMinWidth(80);
        hotkeyButton.setFont(Font.font("Segoe UI", FontWeight.MEDIUM, 13));
        // Uses the default style.
        hotkeyButton.setFocusTraversable(false);
        // No action wired — visual only

        TextField hotkeyField = new TextField("Not set");
        hotkeyField.setEditable(false);
        hotkeyField.setFocusTraversable(false);
        hotkeyField.setPrefHeight(36);
        hotkeyField.setFont(Font.font("Segoe UI", FontWeight.MEDIUM, 13));
        hotkeyField.setStyle(
            "-fx-background-color: " + card + ";"
            + "-fx-text-fill: " + muted + ";"
            + "-fx-border-color: " + stroke + ";"
            + "-fx-border-width: 1.5;"
            + "-fx-background-radius: 0;"
            + "-fx-border-radius: 0;"
            + "-fx-padding: 0 10 0 10;"
        );
        HBox.setHgrow(hotkeyField, Priority.ALWAYS);

        HBox hotkeyRow = new HBox(0, hotkeyButton, hotkeyField);
        hotkeyRow.setAlignment(Pos.CENTER_LEFT);
        hotkeyRow.setPrefWidth(260);
        hotkeyRow.setMaxWidth(260);

        // --- Status / action bar ---
        Label statusDot = new Label("●");
        statusDot.setTextFill(Color.web(muted));
        statusDot.setFont(Font.font(14));

        Label statusLabel = new Label("Lag inactive");
        statusLabel.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 14));
        statusLabel.setTextFill(Color.web(ink));

        HBox statusBar = new HBox(10, statusDot, statusLabel);
        statusBar.setAlignment(Pos.CENTER);
        statusBar.setPrefHeight(48);
        statusBar.setPrefWidth(280);
        statusBar.setMaxWidth(280);
        statusBar.setStyle(
            "-fx-background-color: " + card + ";"
            + "-fx-border-color: " + stroke + ";"
            + "-fx-border-width: 1.5;"
        );
        statusBar.setEffect(new DropShadow(8, Color.web("#00000012")));

        VBox root = new VBox(28, header, hotkeyRow, statusBar);
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(36, 28, 36, 28));
        root.setStyle("-fx-background-color: " + surface + ";");

        Scene scene = new Scene(root, 340, 320);
        stage.setTitle("Project PEAR");
        stage.getIcons().addAll(pearIcon, pearImage);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
}
