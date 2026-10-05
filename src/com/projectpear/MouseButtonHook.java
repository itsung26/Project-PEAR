package com.projectpear;

import com.github.kwhat.jnativehook.mouse.NativeMouseEvent;
import com.github.kwhat.jnativehook.mouse.NativeMouseListener;

public class MouseButtonHook implements NativeMouseListener {
    @Override
    public void nativeMousePressed(NativeMouseEvent event) {
        System.out.println("Mouse Pressed: " + event.getButton());
    }
}
