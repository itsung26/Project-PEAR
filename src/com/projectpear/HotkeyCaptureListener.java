package com.projectpear;

/**
 * Callback invoked when a new hotkey is captured during bind mode.
 *
 * <p>Implementations typically update the UI and persist the binding
 * (for example via {@link Settings}). Provided to
 * {@link GlobalInputService} at construction; invoked when
 * {@linkplain GlobalInputService#setCapturing(boolean) capturing} is
 * enabled and the user presses a key or mouse button.
 */
@FunctionalInterface
public interface HotkeyCaptureListener {
    /**
     * Called with the newly captured hotkey.
     *
     * @param binding the key or mouse button that was captured
     */
    void onCapture(Hotkey binding);
}
