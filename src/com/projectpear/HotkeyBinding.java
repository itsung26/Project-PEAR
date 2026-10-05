package com.projectpear;

public class HotkeyBinding {
    private final int code = -1;

    @Override
    public String toString() {
        return "HotkeyBinding [code=" + code + "]";
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
