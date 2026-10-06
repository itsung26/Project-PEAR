package com.projectpear;

import java.nio.file.Path;

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * JavaFX entry point for Project PEAR, a Windows lag-switch utility.
 *
 * <p>Requires elevated (administrator) privileges. Owns {@link Settings} and
 * {@link GlobalInputService}, builds the UI, and toggles Windows Firewall
 * policy when the bound hotkey is pressed.
 */
public class App extends Application {
    /** Global keyboard/mouse input service. */
    private GlobalInputService Input;

    /** Persisted hotkey and related settings. */
    private Settings settings;

    /** Cached hotkey button for enable/disable during capture. */
    private Button guiHotkeyButton;

    /** Cached hotkey display field. */
    private TextField guiHotkeyField;

    /** Cached status bar ({@code statusDot}, {@code statusLabel} as children). */
    private HBox guiStatusBar;

    /** Whether lag (firewall block) is currently active. */
    private boolean lagActive = false;


    /**
     * Starts the app: checks elevation, loads settings, builds UI, and begins
     * global input listening.
     *
     * @param stage primary window
     * @throws SecurityException if the process is not running elevated
     */
    @Override
    public void start(Stage stage) {
        if (!isRunningElevated()) {
            throw new SecurityException("Application requires elevated permissions.");
        }

        settings = new Settings();
        if (settings.saveFileExists()) {
            settings.load();
        } else {
            settings.setBoundHotkey(new Hotkey(false, NativeKeyEvent.VC_0));
            settings.save();
        }

        buildUi(stage);
        // Create and configure the main input handler.
        // Pass event handlers into the input handler.
        Input = new GlobalInputService(
            this::onHotkeyPressed, this::onHotKeyCaptured, true
        );
        // Tell the input handler what the initial hotkey is.
        Input.setHotkey(settings.getBoundHotkey());

        // --- Initial UI updates --------------------------------------
        // Update the hotkey readout.
        Platform.runLater(
            () -> guiHotkeyField.setText(settings.getBoundHotkey().getBindingName())
        );
        Platform.runLater(
            () -> updateGuiStatusBar(false)
        );

    }

    /**
     * Stops global input listening and clears lag/firewall block on exit.
     */
    @Override
    public void stop() {
        Input.stopListening();
        setLagActive(false);
        System.out.println("Application exit. (Exit code 0)");
    }

    /**
     * Application entry point; launches JavaFX.
     *
     * @param args unused
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Builds and shows the main window UI.
     *
     * @param stage primary window
     */
    private void buildUi(Stage stage) {
        Path assets = Path.of("assets");
        Image pearImage = new Image(assets.resolve("pear.png").toUri().toString());
        Image pearIcon = new Image(assets.resolve("pear32.png").toUri().toString());

        // Brand colors
        String surface = "#F7F8F4";
        String card = "#FFFFFF";
        String ink = "#1B1F16";
        String muted = "#6B7265";
        String stroke = "#D9DDD2";

        // --- Logo (transparent pear, no frame) ---
        ImageView logo = new ImageView(pearImage);
        logo.setFitWidth(78);
        logo.setFitHeight(78);
        logo.setPreserveRatio(true);
        logo.setSmooth(true);

        Label title = new Label("Project PEAR");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 28));
        title.setTextFill(Color.web(ink));

        Label subtitle = new Label("Lightweight lag switch utility");
        subtitle.setFont(Font.font("Segoe UI", 12));
        subtitle.setTextFill(Color.web(muted));

        VBox header = new VBox(6, logo, title, subtitle);
        header.setAlignment(Pos.CENTER);

        // --- Hotkey row: button + read-only field (visual only) ---
        Button hotkeyButton = new Button("Hotkey");
        hotkeyButton.setPrefHeight(36);
        hotkeyButton.setMinWidth(80);
        hotkeyButton.setFont(Font.font("Segoe UI", FontWeight.MEDIUM, 13));
        // Uses the default style.
        hotkeyButton.setFocusTraversable(false);
        hotkeyButton.setOnAction(this::onSetHotkeyButtonPressed);
        // Cache the button for later ui updates.
        guiHotkeyButton = hotkeyButton;

        TextField hotkeyField = new TextField("Not set");
        hotkeyField.setEditable(false);
        // Immidiately consume context menu events to prevent the menu from opening on the hotkey field.
        hotkeyField.addEventFilter(ContextMenuEvent.CONTEXT_MENU_REQUESTED, e -> e.consume());
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
        // Cache the field for later ui updates.
        guiHotkeyField = hotkeyField;

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
        // Cache the status bar for later ui updates.
        guiStatusBar = statusBar;

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

    /**
     * Enters hotkey-capture mode and disables the hotkey button until capture completes.
     *
     * @param event button action event
     */
    private void onSetHotkeyButtonPressed(ActionEvent event) {
        Input.setCapturing(true);

        // All UI changes must be deferred in order to be thread safe.
        // Disable the button to prevent double pressing.
        Platform.runLater(
            () -> guiHotkeyButton.setDisable(true)
        );
    }

    /**
     * Toggles lag when the bound hotkey is pressed (JNativeHook dispatch thread).
     *
     * @param hotkey the hotkey that matched
     */
    private void onHotkeyPressed(Hotkey hotkey) {
        setLagActive(!isLagActive());
    }

    /**
     * Persists a newly captured hotkey and refreshes the UI.
     *
     * @param hotkey the captured binding
     */
    private void onHotKeyCaptured(Hotkey hotkey) {
        settings.setBoundHotkey(hotkey);
        settings.save();
        // Update the hotkey readout.
        Platform.runLater(
            () -> guiHotkeyField.setText(settings.getBoundHotkey().getBindingName())
        );
        // Re-enable the button.
        Platform.runLater(
            () -> guiHotkeyButton.setDisable(false)
        );
    }

    /**
     * @return {@code true} if this process is running with elevated privileges
     */
    private boolean isRunningElevated() {
        String ret = WindowsCommandLine.run(WindowsCommandLine.ELEVATED_QUERY).trim();
        if (ret != null) {
            return ret.equals("Elevated");
        }
        return false;
    }

    /**
     * @return {@code true} if lag (firewall block) is active
     */
    private boolean isLagActive() {
        return lagActive;
    }

    /**
     * Enables or disables lag by updating the UI and applying firewall policy.
     *
     * @param lagActive {@code true} to block traffic, {@code false} to restore policy
     */
    private void setLagActive(boolean lagActive) {
        this.lagActive = lagActive;
        Platform.runLater(() -> updateGuiStatusBar(lagActive));
        if (lagActive) {
            WindowsCommandLine.run(WindowsCommandLine.FIREWALL_BLOCK_ALL);
        } else {
            WindowsCommandLine.run(WindowsCommandLine.FIREWALL_ALLOW_ALL);
        }
    }

    /**
     * Updates the status bar label, dot color, and tint for the given lag state.
     *
     * <p>Must be called on the JavaFX application thread.
     *
     * @param lagState {@code true} for active (green), {@code false} for inactive (red)
     */
    private void updateGuiStatusBar(boolean lagState) {
        if (guiStatusBar == null || guiStatusBar.getChildren().size() < 2) {
            return;
        }

        Label statusDot = (Label) guiStatusBar.getChildren().get(0);
        Label statusLabel = (Label) guiStatusBar.getChildren().get(1);

        if (lagState) {
            statusLabel.setText("Lag active");
            statusDot.setTextFill(Color.web("#2E7D32"));
            guiStatusBar.setStyle(
                "-fx-background-color: #E8F5E9;"
                + "-fx-border-color: #A5D6A7;"
                + "-fx-border-width: 1.5;"
            );
        } else {
            statusLabel.setText("Lag inactive");
            statusDot.setTextFill(Color.web("#C62828"));
            guiStatusBar.setStyle(
                "-fx-background-color: #FFEBEE;"
                + "-fx-border-color: #EF9A9A;"
                + "-fx-border-width: 1.5;"
            );
        }
    }
}
