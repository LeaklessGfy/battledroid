package fr.battledroid.core;

import fr.battledroid.core.adaptee.AssetColor;

public final class Colors {
    private static Colors instance;

    private AssetColor red;
    private AssetColor green;
    private AssetColor blue;
    private AssetColor black;

    private Colors() {
    }

    public static Colors instance() {
        if (instance == null) {
            instance = new Colors();
        }
        return instance;
    }

    public AssetColor getRed() {
        return red;
    }

    public void setRed(AssetColor red) {
        this.red = red;
    }

    public AssetColor getGreen() {
        return green;
    }

    public void setGreen(AssetColor green) {
        this.green = green;
    }

    public AssetColor getBlue() {
        return blue;
    }

    public void setBlue(AssetColor blue) {
        this.blue = blue;
    }

    public AssetColor getBlack() {
        return black;
    }

    public void setBlack(AssetColor black) {
        this.black = black;
    }
}
