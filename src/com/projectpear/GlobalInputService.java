package com.projectpear;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;

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
    /** Listener that receives global keyboard events. */
    private KeyHook keyHook;

    /** Listener that receives global mouse-button events. */
    private MouseButtonHook mouseButtonHook;

    /**
     * Creates a new service with key and mouse listeners ready, but does not
     * begin listening until {@link #startListening()} is called.
     */
    public GlobalInputService(HotkeyPressListener pressListener, HotkeyCaptureListener captureListener) {
        keyHook = new KeyHook();
        mouseButtonHook = new MouseButtonHook();
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
}
