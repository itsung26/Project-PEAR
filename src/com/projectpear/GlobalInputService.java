package com.projectpear;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;

public class GlobalInputService {
    private KeyHook keyHook;
    private MouseButtonHook mouseButtonHook;

    public GlobalInputService() {
        keyHook = new KeyHook();
        mouseButtonHook = new MouseButtonHook();
    }

    public GlobalInputService(boolean startListeningImmediately) {
        this();
        if (startListeningImmediately) {
            startListening();
        }
    }

    public void startListening() {
        try {
                GlobalScreen.registerNativeHook();
        } catch (NativeHookException e) {
			System.err.println("There was a problem registering the native hook.");
			e.printStackTrace();
        }

        GlobalScreen.addNativeKeyListener(keyHook);
        GlobalScreen.addNativeMouseListener(mouseButtonHook);
    }

    public void stopListening() {
        GlobalScreen.removeNativeKeyListener(keyHook);
        GlobalScreen.removeNativeMouseListener(mouseButtonHook);

        try {
            GlobalScreen.unregisterNativeHook();
        } catch (NativeHookException e) {
			System.err.println("There was a problem unregistering the native hook.");
            e.printStackTrace();
        }
    }
}
