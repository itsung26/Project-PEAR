package com.projectpear;

import com.github.kwhat.jnativehook.mouse.NativeMouseEvent;
import com.github.kwhat.jnativehook.mouse.NativeMouseListener;

/**
 * Thin JNativeHook mouse listener that forwards button-press events to
 * {@link GlobalInputService}.
 *
 * <p>Does not interpret capture mode or the bound hotkey; that logic lives
 * in the service. Events arrive on JNativeHook's dispatch thread.
 */
public class MouseButtonHook implements NativeMouseListener {
    /** Service that owns capture / press handling. */
    private final GlobalInputService service;

    /**
     * @param service the input service to notify on mouse button presses
     */
    public MouseButtonHook(GlobalInputService service) {
        this.service = service;
    }

    /**
     * Forwards the press to {@link GlobalInputService#onMousePressed(NativeMouseEvent)}.
     *
     * @param event the native mouse event
     */
    @Override
    public void nativeMousePressed(NativeMouseEvent event) {
        service.onMousePressed(event);
    }
}
