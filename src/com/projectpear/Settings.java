package com.projectpear;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;

public class Settings {
    private final String FILENAME = "Config.cfg";
    private final Properties properties;

    public Settings() {
        this.properties = new Properties();
    }

    public void load() {
        try {
            properties.load(new FileInputStream(FILENAME));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void save() {
        try {
            properties.store(new FileOutputStream(FILENAME), null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Hotkey getBoundHotkey() {
        String hotkeyType = properties.getProperty("BoundHotkeyType", "Keyboard");
        int hotKeyCode = Integer.parseInt(
            properties.getProperty("BoundHotkeyCode", String.valueOf(NativeKeyEvent.VC_0))
        );
        boolean isMouseButton = hotkeyType.equals("Mouse");
        return new Hotkey(isMouseButton, hotKeyCode);
    }

    public void setBoundHotkey(Hotkey hotkey) {
        if (hotkey == null) {
            return;
        }

        if (hotkey.isMouseButton()) {
            properties.setProperty("BoundHotkeyType", "Mouse");
        } else {
            properties.setProperty("BoundHotkeyType", "Keyboard");
        }

        properties.setProperty("BoundHotkeyCode", String.valueOf(hotkey.getCode()));
    }

    public boolean saveFileExists() {
        return Files.exists(Path.of(FILENAME));
    }
}
