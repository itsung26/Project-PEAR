package com.projectpear;

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

/**
 * Thin JNativeHook keyboard listener that forwards key-press events to
 * {@link GlobalInputService}.
 *
 * <p>Does not interpret capture mode or the bound hotkey; that logic lives
 * in the service. Events arrive on JNativeHook's dispatch thread.
 */
public class KeyHook implements NativeKeyListener {
    /** Service that owns capture / press handling. */
    private final GlobalInputService service;

    /**
     * @param service the input service to notify on key presses
     */
    public KeyHook(GlobalInputService service) {
        this.service = service;
    }

    /**
     * Forwards the press to {@link GlobalInputService#onKeyPressed(NativeKeyEvent)}.
     *
     * @param event the native key event
     */
    @Override
    public void nativeKeyPressed(NativeKeyEvent event) {
        service.onKeyPressed(event);
    }
}
