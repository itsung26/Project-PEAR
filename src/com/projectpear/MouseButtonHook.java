package com.projectpear;

import com.github.kwhat.jnativehook.mouse.NativeMouseEvent;
import com.github.kwhat.jnativehook.mouse.NativeMouseListener;

public class MouseButtonHook implements NativeMouseListener {
    @Override
    public void nativeMousePressed(NativeMouseEvent event) {
        System.out.println("Mouse Pressed: " + event.getButton());
    }

    @Override 
    public void nativeMouseClicked(NativeMouseEvent event) {
        // System.out.println("Mouse Clicked: " + event.getButton());
    }

    @Override
    public void nativeMouseReleased(NativeMouseEvent event) {
    }
}
