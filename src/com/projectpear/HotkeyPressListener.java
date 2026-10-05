package com.projectpear;

@FunctionalInterface 
public interface HotkeyPressListener {
    public abstract void onPress(Hotkey binding);
}
