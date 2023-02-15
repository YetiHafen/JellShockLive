package de.jellshock.game.vehicles;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

public class Tank implements Disposable {

    private final Color color;
    private final Texture chassis;
    private final Texture track;

    private float scale = 0.2F;

    private Vector2 position = new Vector2(100, 100);

    public Tank(Color color) {
        this.color = color;
        chassis = new Texture("tank/chassis_round.png");
        track = new Texture("tank/track_classic.png");
    }

    public void render(SpriteBatch batch) {
        batch.setColor(color);

        float chassisWidth = chassis.getWidth() * scale;
        float chassisHeight = chassis.getHeight() * scale;

        float trackWidth = track.getWidth() * scale;
        float trackHeight = track.getHeight() * scale;

        float chassisXOffset = trackWidth / 2 - chassisWidth / 2;
        float chassisYOffset = trackHeight - 4;

        batch.draw(track, position.x, position.y, trackWidth, trackHeight);
        batch.draw(chassis, position.x + chassisXOffset, position.y + chassisYOffset, chassisWidth, chassisHeight);
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public void moveX(float amount) {
        this.position.x += amount;
    }

    public void moveY(float amount) {
        this.position.y += amount;
    }

    @Override
    public void dispose() {
        chassis.dispose();
        track.dispose();
    }
}
