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
 * <p>Typical usage:
 * <pre>{@code
 * GlobalInputService input = new GlobalInputService(true); // start immediately
 * // ... later, when shutting down:
 * input.stopListening();
 * }</pre>
 */
public class GlobalInputService {
    private final KeyHook keyHook;
    private final MouseButtonHook mouseButtonHook;
    private final HotkeyPressListener pressListener;
    private final HotkeyCaptureListener captureListener;
    private boolean capturing;

    /**
     * Creates a new service with key and mouse listeners ready, but does not
     * begin listening until {@link #startListening()} is called.
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
     * @param startListeningImmediately if {@code true}, registers the native
     *                                  hook and attaches listeners right away
     */
    public GlobalInputService(HotkeyPressListener pressListener, HotkeyCaptureListener captureListener, boolean startListeningImmediately) {
        this(pressListener, captureListener);
        if (startListeningImmediately) {
            startListening();
        }
    }

    public void setCapturing(boolean capturing) {
        this.capturing = capturing;
    }

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
     * Receives a global key-press event forwarded from {@link KeyHook}.
     *
     * @param event the native key event
     */
    public void onKeyPressed(NativeKeyEvent event) {
        Hotkey binding = new Hotkey(event);

        if (capturing) {
            capturing = false;
            captureListener.onCapture(binding);
        }
    }

    /**
     * Receives a global mouse-button-press event forwarded from {@link MouseButtonHook}.
     *
     * @param event the native mouse event
     */
    public void onMousePressed(NativeMouseEvent event) {
        Hotkey binding = new Hotkey(event);

        if (capturing) {
            capturing = false;
            captureListener.onCapture(binding);
        }
    }
}
