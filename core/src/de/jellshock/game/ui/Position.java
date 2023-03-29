package de.jellshock.game.ui;

import com.badlogic.gdx.Gdx;
import lombok.Setter;

@Setter
public class Position {

    private float x;
    private float y;

    public Position(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return (int) (x * Gdx.graphics.getWidth());
    }

    public int getY() {
        return (int) (y * Gdx.graphics.getHeight());
    }
}
