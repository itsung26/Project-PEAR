package com.projectpear;

/**
 * Callback invoked when the currently saved hotkey is pressed.
 *
 * <p>Implementations typically perform the application's primary action
 * (for example toggling lag simulation). Provided to
 * {@link GlobalInputService} at construction; invoked on a matching
 * global key or mouse press when not in capture mode.
 */
@FunctionalInterface
public interface HotkeyPressListener {
    /**
     * Called when the saved hotkey is activated.
     *
     * @param binding the hotkey that was pressed
     */
    void onPress(Hotkey binding);
}
