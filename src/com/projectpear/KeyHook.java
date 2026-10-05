package com.projectpear;

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

public class KeyHook implements NativeKeyListener {
    @Override
    public void nativeKeyPressed(NativeKeyEvent event) {
        System.out.println("Key pressed: " + NativeKeyEvent.getKeyText(event.getKeyCode()));
    }
}
