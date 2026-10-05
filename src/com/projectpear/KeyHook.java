package com.projectpear;

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

public class KeyHook implements NativeKeyListener {
    private final GlobalInputService service;

    public KeyHook(GlobalInputService service) {
        this.service = service;
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent event) {
        service.onKeyPressed(event);
    }
}
