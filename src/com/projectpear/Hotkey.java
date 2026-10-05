package com.projectpear;

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.mouse.NativeMouseEvent;

public class Hotkey {
    private final int code = -1;

    public Hotkey(NativeMouseEvent mouseEvent) {

    }

    public Hotkey(NativeKeyEvent keyEvent) {
        
    }

    @Override
    public String toString() {
        return "Hotkey [code=" + code + "]";
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }

        if (other.getClass() != this.getClass()) {
            return false;
        }

        if (other.getClass() == this.getClass()) {
            // placeholder
        }
        return false;
    }
}
