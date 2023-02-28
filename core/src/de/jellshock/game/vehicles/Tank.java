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
    private final Texture gunTexture;

    private final TextureRegion chassis;
    private final TextureRegion track;
    private final TextureRegion gun;

    private float scale = 0.2F;

    private float rotation = 0;
    private float gunRotation = 0;

    private Vector2 position = new Vector2(100, 100);

    public Tank(Color color) {
        this.color = color;
        chassisTexture = new Texture("tank/chassis_round.png");
        trackTexture = new Texture("tank/track_classic.png");
        gunTexture = new Texture("tank/gun_dainty.png");
        track = new TextureRegion(trackTexture);
        chassis = new TextureRegion(chassisTexture);
        gun = new TextureRegion(gunTexture);
    }

    public void render(SpriteBatch batch) {
        batch.setColor(color);

        float chassisWidth = chassisTexture.getWidth() * scale;
        float chassisHeight = chassisTexture.getHeight() * scale;

        float trackWidth = trackTexture.getWidth() * scale;
        float trackHeight = trackTexture.getHeight() * scale;

        float gunHeight = gunTexture.getHeight() * scale;
        float gunLength = gunTexture.getWidth() * scale;

        float chassisXOffset = -chassisWidth / 2;
        float trackXOffset = -trackWidth / 2;

        double rot = Math.toRadians(rotation);
        float gunCenterX = (float) (position.x + (trackHeight) * -Math.sin(rot));
        float gunCenterY = (float) (position.y + (trackHeight) * Math.cos(rot));

        batch.draw(gun, gunCenterX, gunCenterY, 0, gunHeight / 2, gunLength, gunHeight, 1, 1, gunRotation);
        batch.draw(track, position.x + trackXOffset, position.y, trackWidth / 2, 0, trackWidth, trackHeight, 1, 1, rotation);
        batch.draw(chassis, position.x + chassisXOffset, position.y + trackHeight, chassisWidth / 2, - trackHeight, chassisWidth, chassisHeight, 1, 1, rotation);
        batch.setColor(Color.WHITE);
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

    public float getGunRotation() {
        return gunRotation;
    }

    public void setGunRotation(float gunRotation) {
        this.gunRotation = gunRotation;
    }

    @Override
    public void dispose() {
        chassisTexture.dispose();
        trackTexture.dispose();
        gunTexture.dispose();
    }
}
