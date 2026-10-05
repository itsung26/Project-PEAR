package com.projectpear;

@FunctionalInterface 
public interface HotkeyCaptureListener {
    public abstract void onCapture(Hotkey binding);
}
