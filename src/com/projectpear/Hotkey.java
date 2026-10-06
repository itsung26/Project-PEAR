package com.projectpear;

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.mouse.NativeMouseEvent;

public class Hotkey {

    /**
     * Binding labels keyed by native key codes ({@link NativeKeyEvent} {@code VC_*}
     * constants) or mouse button codes ({@link NativeMouseEvent} {@code BUTTON*}).
     */
    private enum BindingNames {
        // --- Keyboard (NativeKeyEvent.VC_*) ---
        ESCAPE(NativeKeyEvent.VC_ESCAPE, "Escape", false),

        F1(NativeKeyEvent.VC_F1, "F1", false),
        F2(NativeKeyEvent.VC_F2, "F2", false),
        F3(NativeKeyEvent.VC_F3, "F3", false),
        F4(NativeKeyEvent.VC_F4, "F4", false),
        F5(NativeKeyEvent.VC_F5, "F5", false),
        F6(NativeKeyEvent.VC_F6, "F6", false),
        F7(NativeKeyEvent.VC_F7, "F7", false),
        F8(NativeKeyEvent.VC_F8, "F8", false),
        F9(NativeKeyEvent.VC_F9, "F9", false),
        F10(NativeKeyEvent.VC_F10, "F10", false),
        F11(NativeKeyEvent.VC_F11, "F11", false),
        F12(NativeKeyEvent.VC_F12, "F12", false),
        F13(NativeKeyEvent.VC_F13, "F13", false),
        F14(NativeKeyEvent.VC_F14, "F14", false),
        F15(NativeKeyEvent.VC_F15, "F15", false),
        F16(NativeKeyEvent.VC_F16, "F16", false),
        F17(NativeKeyEvent.VC_F17, "F17", false),
        F18(NativeKeyEvent.VC_F18, "F18", false),
        F19(NativeKeyEvent.VC_F19, "F19", false),
        F20(NativeKeyEvent.VC_F20, "F20", false),
        F21(NativeKeyEvent.VC_F21, "F21", false),
        F22(NativeKeyEvent.VC_F22, "F22", false),
        F23(NativeKeyEvent.VC_F23, "F23", false),
        F24(NativeKeyEvent.VC_F24, "F24", false),

        BACK_QUOTE(NativeKeyEvent.VC_BACKQUOTE, "Back Quote", false),

        DIGIT_1(NativeKeyEvent.VC_1, "1", false),
        DIGIT_2(NativeKeyEvent.VC_2, "2", false),
        DIGIT_3(NativeKeyEvent.VC_3, "3", false),
        DIGIT_4(NativeKeyEvent.VC_4, "4", false),
        DIGIT_5(NativeKeyEvent.VC_5, "5", false),
        DIGIT_6(NativeKeyEvent.VC_6, "6", false),
        DIGIT_7(NativeKeyEvent.VC_7, "7", false),
        DIGIT_8(NativeKeyEvent.VC_8, "8", false),
        DIGIT_9(NativeKeyEvent.VC_9, "9", false),
        DIGIT_0(NativeKeyEvent.VC_0, "0", false),

        MINUS(NativeKeyEvent.VC_MINUS, "Minus", false),
        EQUALS(NativeKeyEvent.VC_EQUALS, "Equals", false),
        BACKSPACE(NativeKeyEvent.VC_BACKSPACE, "Backspace", false),

        TAB(NativeKeyEvent.VC_TAB, "Tab", false),
        CAPS_LOCK(NativeKeyEvent.VC_CAPS_LOCK, "Caps Lock", false),

        A(NativeKeyEvent.VC_A, "A", false),
        B(NativeKeyEvent.VC_B, "B", false),
        C(NativeKeyEvent.VC_C, "C", false),
        D(NativeKeyEvent.VC_D, "D", false),
        E(NativeKeyEvent.VC_E, "E", false),
        F(NativeKeyEvent.VC_F, "F", false),
        G(NativeKeyEvent.VC_G, "G", false),
        H(NativeKeyEvent.VC_H, "H", false),
        I(NativeKeyEvent.VC_I, "I", false),
        J(NativeKeyEvent.VC_J, "J", false),
        K(NativeKeyEvent.VC_K, "K", false),
        L(NativeKeyEvent.VC_L, "L", false),
        M(NativeKeyEvent.VC_M, "M", false),
        N(NativeKeyEvent.VC_N, "N", false),
        O(NativeKeyEvent.VC_O, "O", false),
        P(NativeKeyEvent.VC_P, "P", false),
        Q(NativeKeyEvent.VC_Q, "Q", false),
        R(NativeKeyEvent.VC_R, "R", false),
        S(NativeKeyEvent.VC_S, "S", false),
        T(NativeKeyEvent.VC_T, "T", false),
        U(NativeKeyEvent.VC_U, "U", false),
        V(NativeKeyEvent.VC_V, "V", false),
        W(NativeKeyEvent.VC_W, "W", false),
        X(NativeKeyEvent.VC_X, "X", false),
        Y(NativeKeyEvent.VC_Y, "Y", false),
        Z(NativeKeyEvent.VC_Z, "Z", false),

        OPEN_BRACKET(NativeKeyEvent.VC_OPEN_BRACKET, "Open Bracket", false),
        CLOSE_BRACKET(NativeKeyEvent.VC_CLOSE_BRACKET, "Close Bracket", false),
        BACK_SLASH(NativeKeyEvent.VC_BACK_SLASH, "Back Slash", false),

        SEMICOLON(NativeKeyEvent.VC_SEMICOLON, "Semicolon", false),
        QUOTE(NativeKeyEvent.VC_QUOTE, "Quote", false),
        ENTER(NativeKeyEvent.VC_ENTER, "Enter", false),

        COMMA(NativeKeyEvent.VC_COMMA, "Comma", false),
        PERIOD(NativeKeyEvent.VC_PERIOD, "Period", false),
        SLASH(NativeKeyEvent.VC_SLASH, "Slash", false),

        SPACE(NativeKeyEvent.VC_SPACE, "Space", false),

        PRINT_SCREEN(NativeKeyEvent.VC_PRINTSCREEN, "Print Screen", false),
        SCROLL_LOCK(NativeKeyEvent.VC_SCROLL_LOCK, "Scroll Lock", false),
        PAUSE(NativeKeyEvent.VC_PAUSE, "Pause", false),

        INSERT(NativeKeyEvent.VC_INSERT, "Insert", false),
        DELETE(NativeKeyEvent.VC_DELETE, "Delete", false),
        HOME(NativeKeyEvent.VC_HOME, "Home", false),
        END(NativeKeyEvent.VC_END, "End", false),
        PAGE_UP(NativeKeyEvent.VC_PAGE_UP, "Page Up", false),
        PAGE_DOWN(NativeKeyEvent.VC_PAGE_DOWN, "Page Down", false),

        UP(NativeKeyEvent.VC_UP, "Up", false),
        LEFT(NativeKeyEvent.VC_LEFT, "Left", false),
        CLEAR(NativeKeyEvent.VC_CLEAR, "Clear", false),
        RIGHT(NativeKeyEvent.VC_RIGHT, "Right", false),
        DOWN(NativeKeyEvent.VC_DOWN, "Down", false),

        NUM_LOCK(NativeKeyEvent.VC_NUM_LOCK, "Num Lock", false),
        NUMPAD_COMMA(NativeKeyEvent.VC_SEPARATOR, "NumPad ,", false),

        SHIFT(NativeKeyEvent.VC_SHIFT, "Shift", false),
        CONTROL(NativeKeyEvent.VC_CONTROL, "Control", false),
        ALT(NativeKeyEvent.VC_ALT, "Alt", false),
        META(NativeKeyEvent.VC_META, "Meta", false),
        CONTEXT_MENU(NativeKeyEvent.VC_CONTEXT_MENU, "Context Menu", false),

        POWER(NativeKeyEvent.VC_POWER, "Power", false),
        SLEEP(NativeKeyEvent.VC_SLEEP, "Sleep", false),
        WAKE(NativeKeyEvent.VC_WAKE, "Wake", false),

        PLAY(NativeKeyEvent.VC_MEDIA_PLAY, "Play", false),
        STOP(NativeKeyEvent.VC_MEDIA_STOP, "Stop", false),
        PREVIOUS(NativeKeyEvent.VC_MEDIA_PREVIOUS, "Previous", false),
        NEXT(NativeKeyEvent.VC_MEDIA_NEXT, "Next", false),
        SELECT(NativeKeyEvent.VC_MEDIA_SELECT, "Select", false),
        EJECT(NativeKeyEvent.VC_MEDIA_EJECT, "Eject", false),

        MUTE(NativeKeyEvent.VC_VOLUME_MUTE, "Mute", false),
        VOLUME_UP(NativeKeyEvent.VC_VOLUME_UP, "Volume Up", false),
        VOLUME_DOWN(NativeKeyEvent.VC_VOLUME_DOWN, "Volume Down", false),

        APP_MAIL(NativeKeyEvent.VC_APP_MAIL, "App Mail", false),
        APP_CALCULATOR(NativeKeyEvent.VC_APP_CALCULATOR, "App Calculator", false),
        APP_MUSIC(NativeKeyEvent.VC_APP_MUSIC, "App Music", false),
        APP_PICTURES(NativeKeyEvent.VC_APP_PICTURES, "App Pictures", false),

        BROWSER_SEARCH(NativeKeyEvent.VC_BROWSER_SEARCH, "Browser Search", false),
        BROWSER_HOME(NativeKeyEvent.VC_BROWSER_HOME, "Browser Home", false),
        BROWSER_BACK(NativeKeyEvent.VC_BROWSER_BACK, "Browser Back", false),
        BROWSER_FORWARD(NativeKeyEvent.VC_BROWSER_FORWARD, "Browser Forward", false),
        BROWSER_STOP(NativeKeyEvent.VC_BROWSER_STOP, "Browser Stop", false),
        BROWSER_REFRESH(NativeKeyEvent.VC_BROWSER_REFRESH, "Browser Refresh", false),
        BROWSER_FAVORITES(NativeKeyEvent.VC_BROWSER_FAVORITES, "Browser Favorites", false),

        KATAKANA(NativeKeyEvent.VC_KATAKANA, "Katakana", false),
        UNDERSCORE(NativeKeyEvent.VC_UNDERSCORE, "Underscore", false),
        FURIGANA(NativeKeyEvent.VC_FURIGANA, "Furigana", false),
        KANJI(NativeKeyEvent.VC_KANJI, "Kanji", false),
        HIRAGANA(NativeKeyEvent.VC_HIRAGANA, "Hiragana", false),
        YEN(NativeKeyEvent.VC_YEN, "\u00A5", false),

        SUN_HELP(NativeKeyEvent.VC_SUN_HELP, "Sun Help", false),
        SUN_STOP(NativeKeyEvent.VC_SUN_STOP, "Sun Stop", false),
        SUN_PROPS(NativeKeyEvent.VC_SUN_PROPS, "Sun Props", false),
        SUN_FRONT(NativeKeyEvent.VC_SUN_FRONT, "Sun Front", false),
        SUN_OPEN(NativeKeyEvent.VC_SUN_OPEN, "Sun Open", false),
        SUN_FIND(NativeKeyEvent.VC_SUN_FIND, "Sun Find", false),
        SUN_AGAIN(NativeKeyEvent.VC_SUN_AGAIN, "Sun Again", false),
        SUN_UNDO(NativeKeyEvent.VC_SUN_UNDO, "Sun Undo", false),
        SUN_COPY(NativeKeyEvent.VC_SUN_COPY, "Sun Copy", false),
        SUN_INSERT(NativeKeyEvent.VC_SUN_INSERT, "Sun Insert", false),
        SUN_CUT(NativeKeyEvent.VC_SUN_CUT, "Sun Cut", false),

        UNDEFINED(NativeKeyEvent.VC_UNDEFINED, "Undefined", false),

        // --- Mouse (NativeMouseEvent.BUTTON*) ---
        MOUSEBUTTON1(NativeMouseEvent.BUTTON1, "MOUSEBUTTON1", true),
        MOUSEBUTTON2(NativeMouseEvent.BUTTON2, "MOUSEBUTTON2", true),
        MOUSEBUTTON3(NativeMouseEvent.BUTTON3, "MOUSEBUTTON3", true),
        MOUSEBUTTON4(NativeMouseEvent.BUTTON4, "MOUSEBUTTON4", true),
        MOUSEBUTTON5(NativeMouseEvent.BUTTON5, "MOUSEBUTTON5", true);

        private final int code;
        private final String text;
        private final boolean mouse;

        BindingNames(int code, String text, boolean mouse) {
            this.code = code;
            this.text = text;
            this.mouse = mouse;
        }

        public String getText() {
            return text;
        }

        private static BindingNames fromKeyCode(int keyCode) {
            for (BindingNames name : values()) {
                if (!name.mouse && name.code == keyCode) {
                    return name;
                }
            }
            return null;
        }

        private static BindingNames fromMouseButton(int button) {
            for (BindingNames name : values()) {
                if (name.mouse && name.code == button) {
                    return name;
                }
            }
            return null;
        }
    }

    private final int code;
    private final String bindingName;
    private final boolean isMouseButton;

    public Hotkey(NativeMouseEvent mouseEvent) {
        this.code = mouseEvent.getButton();
        BindingNames name = BindingNames.fromMouseButton(this.code);
        if (name == null) {
            throw new IllegalArgumentException("Unsupported mouse button: " + this.code);
        }
        this.bindingName = name.getText();
        this.isMouseButton = true;
    }

    public Hotkey(NativeKeyEvent keyEvent) {
        this.code = keyEvent.getKeyCode();
        BindingNames name = BindingNames.fromKeyCode(this.code);
        if (name == null) {
            throw new IllegalArgumentException("Unsupported key code: " + this.code);
        }
        this.bindingName = name.getText();
        this.isMouseButton = false;
    }

    public Hotkey(boolean isMouseButton, int code) {
        BindingNames name = isMouseButton ? BindingNames.fromMouseButton(code) : BindingNames.fromKeyCode(code);
        if (name == null) {
            throw new IllegalArgumentException("Unsupported key code: " + code);
        }
        this.code = code;
        this.isMouseButton = isMouseButton;
        this.bindingName = name.getText();
    }

    public int getCode() {
        return code;
    }

    public String getBindingName() {
        return bindingName;
    }

    public boolean isMouseButton() {
        return isMouseButton;
    }

    @Override
    public String toString() {
        String type = isMouseButton ? "mouse" : "key";
        return "Hotkey{name='" + bindingName + "', type=" + type + ", code=" + code + "}";
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }

        if (other.getClass() != this.getClass()) {
            return false;
        }

        Hotkey otherHotkey = (Hotkey) other;
        return otherHotkey.getCode() == this.getCode()
            && otherHotkey.isMouseButton() == this.isMouseButton()
            && otherHotkey.getBindingName().equals(this.getBindingName());
    }
}
