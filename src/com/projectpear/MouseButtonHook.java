package com.projectpear;

import com.github.kwhat.jnativehook.mouse.NativeMouseEvent;
import com.github.kwhat.jnativehook.mouse.NativeMouseListener;

public class MouseButtonHook implements NativeMouseListener {
    private final GlobalInputService service;

    public MouseButtonHook(GlobalInputService service) {
        this.service = service;
    }

    @Override
    public void nativeMousePressed(NativeMouseEvent event) {
        service.onMousePressed(event);
    }
}
