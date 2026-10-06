package com.projectpear;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;

/**
 * Persists application settings to a local {@code Config.cfg} properties file.
 *
 * <p>Currently stores the bound hotkey as {@code boundHotkeyType}
 * ({@code Keyboard} or {@code Mouse}) and {@code boundHotkeyCode} (native
 * key or mouse button code). Load into memory with {@link #load()}, mutate
 * with {@link #setBoundHotkey(Hotkey)}, then {@link #save()} to disk.
 *
 * <p>Typical usage:
 * <pre>{@code
 * Settings settings = new Settings();
 * if (settings.saveFileExists()) {
 *     settings.load();
 * } else {
 *     settings.setBoundHotkey(defaultHotkey);
 *     settings.save();
 * }
 * Hotkey hotkey = settings.getBoundHotkey();
 * }</pre>
 */
public class Settings {
    /** On-disk settings file name (relative to the process working directory). */
    private final String FILENAME = "Config.cfg";

    /** In-memory property bag loaded from / written to {@link #FILENAME}. */
    private final Properties properties;

    /**
     * Creates an empty in-memory settings store. Call {@link #load()} to read
     * from disk when the file exists.
     */
    public Settings() {
        this.properties = new Properties();
    }

    /**
     * Loads properties from {@code Config.cfg} into memory.
     *
     * <p>On failure, prints the stack trace and leaves {@link #properties}
     * unchanged aside from any partial load.
     */
    public void load() {
        try {
            properties.load(new FileInputStream(FILENAME));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Writes the in-memory properties to {@code Config.cfg}.
     *
     * <p>On failure, prints the stack trace.
     */
    public void save() {
        try {
            properties.store(new FileOutputStream(FILENAME), null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Builds a {@link Hotkey} from the loaded {@code boundHotkeyType} and
     * {@code boundHotkeyCode} properties.
     *
     * <p>Defaults to a keyboard digit {@code 0} ({@link NativeKeyEvent#VC_0})
     * when properties are missing.
     *
     * @return the reconstructed bound hotkey
     */
    public Hotkey getBoundHotkey() {
        String hotkeyType = properties.getProperty("boundHotkeyType", "Keyboard");
        int hotKeyCode = Integer.parseInt(
            properties.getProperty("boundHotkeyCode", String.valueOf(NativeKeyEvent.VC_0))
        );
        boolean isMouseButton = hotkeyType.equals("Mouse");
        return new Hotkey(isMouseButton, hotKeyCode);
    }

    /**
     * Updates the in-memory hotkey properties from {@code hotkey}.
     *
     * <p>Does not write to disk; call {@link #save()} afterward. Ignores
     * {@code null}.
     *
     * @param hotkey the hotkey to persist in properties
     */
    public void setBoundHotkey(Hotkey hotkey) {
        if (hotkey == null) {
            return;
        }

        if (hotkey.isMouseButton()) {
            properties.setProperty("boundHotkeyType", "Mouse");
        } else {
            properties.setProperty("boundHotkeyType", "Keyboard");
        }

        properties.setProperty("boundHotkeyCode", String.valueOf(hotkey.getCode()));
    }

    /**
     * @return {@code true} if {@code Config.cfg} exists on disk
     */
    public boolean saveFileExists() {
        return Files.exists(Path.of(FILENAME));
    }
}
