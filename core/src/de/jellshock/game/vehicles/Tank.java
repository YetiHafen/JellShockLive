package de.jellshock.game.vehicles;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

public class Tank implements Disposable {

    private final Color color;
    private final Texture chassisTexture;
    private final Texture trackTexture;

    private final TextureRegion chassis;
    private final TextureRegion track;

    private float scale = 0.2F;

    private float rotation = 0;

    private Vector2 position = new Vector2(100, 100);

    public Tank(Color color) {
        this.color = color;
        chassisTexture = new Texture("tank/chassis_round.png");
        trackTexture = new Texture("tank/track_classic.png");
        track = new TextureRegion(trackTexture);
        chassis = new TextureRegion(chassisTexture);
    }

    public void render(SpriteBatch batch) {
        batch.setColor(color);

        float chassisWidth = chassisTexture.getWidth() * scale;
        float chassisHeight = chassisTexture.getHeight() * scale;

        float trackWidth = trackTexture.getWidth() * scale;
        float trackHeight = trackTexture.getHeight() * scale;

        float chassisXOffset = trackWidth / 2 - chassisWidth / 2;

        batch.draw(track, position.x, position.y, trackWidth / 2, 0, trackWidth, trackHeight, 1, 1, rotation);
        batch.draw(chassis, position.x + chassisXOffset, position.y + trackHeight, chassisWidth / 2, - trackHeight, chassisWidth, chassisHeight, 1, 1, rotation);
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

    public void setRotation(float rotation) {
        this.rotation = rotation % 360;
    }

    public float getRotation() {
        return rotation;
    }

    @Override
    public void dispose() {
        chassisTexture.dispose();
        trackTexture.dispose();
    }
}
