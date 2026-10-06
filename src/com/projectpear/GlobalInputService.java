package com.projectpear;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.mouse.NativeMouseEvent;

/**
 * Manages system-wide keyboard and mouse input listening via JNativeHook.
 *
 * <p>Registers a native hook on the {@link GlobalScreen} and attaches
 * {@link KeyHook} and {@link MouseButtonHook} listeners so that input events
 * are received even when the application does not have focus.
 *
 * <p>When {@linkplain #setCapturing(boolean) capturing}, the next key or mouse
 * button press becomes the bound hotkey and is reported via
 * {@link HotkeyCaptureListener}. Otherwise, presses that match the bound
 * {@link Hotkey} are reported via {@link HotkeyPressListener}.
 *
 * <p>Typical usage:
 * <pre>{@code
 * GlobalInputService input = new GlobalInputService(onPress, onCapture, true);
 * input.setHotkey(settings.getBoundHotkey());
 * // ... later, when shutting down:
 * input.stopListening();
 * }</pre>
 *
 * <p>Listener callbacks run on JNativeHook's dispatch thread; UI updates from
 * those callbacks must be marshalled to the JavaFX application thread
 * (for example with {@code Platform.runLater}).
 */
public class GlobalInputService {
    /** Forwards global keyboard presses into this service. */
    private final KeyHook keyHook;

    /** Forwards global mouse button presses into this service. */
    private final MouseButtonHook mouseButtonHook;

    /** Invoked when the bound hotkey is pressed outside capture mode. */
    private final HotkeyPressListener pressListener;

    /** Invoked when a new hotkey is chosen in capture mode. */
    private final HotkeyCaptureListener captureListener;

    /** {@code true} while waiting for the next press to set a new hotkey. */
    private boolean capturing;

    /** Currently bound hotkey used for press matching. */
    private Hotkey hotkey;

    /**
     * Creates a new service with key and mouse listeners ready, but does not
     * begin listening until {@link #startListening()} is called.
     *
     * @param pressListener   callback for bound-hotkey activation
     * @param captureListener callback for newly captured hotkeys
     */
    public GlobalInputService(HotkeyPressListener pressListener, HotkeyCaptureListener captureListener) {
        keyHook = new KeyHook(this);
        mouseButtonHook = new MouseButtonHook(this);
        this.pressListener = pressListener;
        this.captureListener = captureListener;
        capturing = false;
    }

    /**
     * Creates a new service and optionally starts listening immediately.
     *
     * @param pressListener               callback for bound-hotkey activation
     * @param captureListener             callback for newly captured hotkeys
     * @param startListeningImmediately   if {@code true}, registers the native
     *                                    hook and attaches listeners right away
     */
    public GlobalInputService(HotkeyPressListener pressListener, HotkeyCaptureListener captureListener, boolean startListeningImmediately) {
        this(pressListener, captureListener);
        if (startListeningImmediately) {
            startListening();
        }
    }

    /**
     * Enables or disables hotkey-capture mode.
     *
     * <p>When enabled, the next key or mouse button press updates the bound
     * hotkey and notifies {@link HotkeyCaptureListener}.
     *
     * @param capturing {@code true} to wait for a new bind
     */
    public void setCapturing(boolean capturing) {
        this.capturing = capturing;
    }

    /**
     * @return {@code true} if the service is waiting for a new hotkey bind
     */
    public boolean isCapturing() {
        return capturing;
    }

    /**
     * Registers the JNativeHook native hook and attaches the key and mouse
     * listeners to the {@link GlobalScreen}.
     *
     * <p>If the native hook fails to register, an error is printed to
     * {@code System.err} and the stack trace is written; listeners are still
     * added afterward.
     */
    public void startListening() {
        try {
                GlobalScreen.registerNativeHook();
        } catch (NativeHookException e) {
			System.err.println("There was a problem registering the native hook.");
			e.printStackTrace();
        }

        GlobalScreen.addNativeKeyListener(keyHook);
        GlobalScreen.addNativeMouseListener(mouseButtonHook);
    }

    /**
     * Detaches the key and mouse listeners and unregisters the native hook.
     *
     * <p>Should be called when the application is shutting down to release
     * system resources. If unregistering fails, an error is printed to
     * {@code System.err} and the stack trace is written.
     */
    public void stopListening() {
        GlobalScreen.removeNativeKeyListener(keyHook);
        GlobalScreen.removeNativeMouseListener(mouseButtonHook);

        try {
            GlobalScreen.unregisterNativeHook();
        } catch (NativeHookException e) {
			System.err.println("There was a problem unregistering the native hook.");
            e.printStackTrace();
        }
    }

    /**
     * @return the currently bound hotkey, or {@code null} if none is set
     */
    public Hotkey getHotkey() {
        return hotkey;
    }

    /**
     * Sets the hotkey used for press matching outside capture mode.
     *
     * @param hotkey the bound hotkey
     */
    public void setHotkey(Hotkey hotkey) {
        this.hotkey = hotkey;
    }

    /**
     * Receives a global key-press event forwarded from {@link KeyHook}.
     *
     * <p>In capture mode, stores the binding and notifies
     * {@link HotkeyCaptureListener}. Otherwise, if it matches
     * {@link #getHotkey()}, notifies {@link HotkeyPressListener}.
     *
     * @param event the native key event
     */
    public void onKeyPressed(NativeKeyEvent event) {
        Hotkey binding = new Hotkey(event);

        if (capturing) {
            capturing = false;
            hotkey = binding;
            captureListener.onCapture(binding);
        } else if (binding.equals(hotkey)) {
            pressListener.onPress(binding);
        }
    }

    /**
     * Receives a global mouse-button-press event forwarded from {@link MouseButtonHook}.
     *
     * <p>In capture mode, stores the binding and notifies
     * {@link HotkeyCaptureListener}. Otherwise, if it matches
     * {@link #getHotkey()}, notifies {@link HotkeyPressListener}.
     *
     * @param event the native mouse event
     */
    public void onMousePressed(NativeMouseEvent event) {
        Hotkey binding = new Hotkey(event);

        if (capturing) {
            capturing = false;
            hotkey = binding;
            captureListener.onCapture(binding);
        } else if (binding.equals(hotkey)) {
            pressListener.onPress(binding);
        }
    }
}
