package de.jellshock.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import java.util.HashMap;
import java.util.Map;

public class UiPrinter {

    public enum TextAlignment {
        LEFT,
        CENTER,
        RIGHt
    }

    private BitmapFont font;
    private static Map<String, Position> positions;

    private final float lineWidth;
    private final float FONT_SIZE = 0.02F;

    public UiPrinter() {
        positions = new HashMap<>();

        lineWidth = 1F / Gdx.graphics.getHeight();

        font = new BitmapFont(Gdx.files.internal("font-export.fnt"));
    }
}
