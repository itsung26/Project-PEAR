package com.projectpear;

public class GlobalInputService {
    private KeyHook keyHook;
    private MouseButtonHook mouseButtonHook;

    public GlobalInputService() {
        keyHook = new KeyHook();
        mouseButtonHook = new MouseButtonHook();
    }
}
