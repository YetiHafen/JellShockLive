package de.jellshock.game.vehicle;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import de.jellshock.Constants;
import de.jellshock.JellShock;
import de.jellshock.game.rendering.IRenderConsumer;
import de.jellshock.game.weapon.abstraction.AbstractWeapon;
import de.jellshock.game.world.World;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class Tank implements IRenderConsumer<SpriteBatch>, Disposable {

    private final Color color;
    private final Texture chassisTexture;
    private final Texture trackTexture;
    private final Texture gunTexture;

    private final TextureRegion chassis;
    private final TextureRegion track;
    private final TextureRegion gun;
    private float gunRotation = 0;

    private float position = 0;
    private final World world;

    private static final float SCALE = 0.16F;
    private static final int SLOPE_05DX = 10;

    public Tank(Color color, World world) {
        this.color = color;
        this.world = world;
        AssetManager manager = JellShock.getInstance().getAssetManager();
        chassisTexture = manager.get(Constants.CHASSIS_PATH, Texture.class);
        trackTexture = manager.get(Constants.TRACK_PATH, Texture.class);
        gunTexture = manager.get(Constants.GUN_PATH, Texture.class);
        track = new TextureRegion(trackTexture);
        chassis = new TextureRegion(chassisTexture);
        gun = new TextureRegion(gunTexture);
    }

    public AbstractWeapon shootProjectile(float power, Class<? extends AbstractWeapon> projectileType) {
        try {
            Constructor<? extends AbstractWeapon> constructor = projectileType.getDeclaredConstructor();
            AbstractWeapon projectile = constructor.newInstance();
            projectile.setPosition(new Vector2(position, world.getMap().getMapHeight((int) position)));

            double rot = Math.toRadians(gunRotation);
            float vx = (float) Math.cos(rot);
            float vy = (float) Math.sin(rot);
            Vector2 v = new Vector2(vx * power * 100, vy * power * 100);
            projectile.setVelocity(v);
            projectile.setWorld(world);
            return projectile;
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.setColor(color);

        float x = position;
        float y = world.getMap().getMapHeight((int) x);

        float chassisWidth = chassisTexture.getWidth() * SCALE;
        float chassisHeight = chassisTexture.getHeight() * SCALE;

        float trackWidth = trackTexture.getWidth() * SCALE;
        float trackHeight = trackTexture.getHeight() * SCALE;

        float gunHeight = gunTexture.getHeight() * SCALE;
        float gunLength = gunTexture.getWidth() * SCALE;

        float chassisXOffset = -chassisWidth / 2;
        float trackXOffset = -trackWidth / 2;

        double rotationRad = calculateRotation();
        float rotationDeg = (float) Math.toDegrees(rotationRad);

        float gunCenterX = (float) (x + (trackHeight) * -Math.sin(rotationRad));
        float gunCenterY = (float) (y + (trackHeight) * Math.cos(rotationRad));

        batch.draw(gun, gunCenterX, gunCenterY, 0, gunHeight / 2, gunLength, gunHeight, 1, 1, gunRotation);
        batch.draw(track, x + trackXOffset, y, trackWidth / 2, 0, trackWidth, trackHeight, 1, 1, rotationDeg);
        batch.draw(chassis, x + chassisXOffset, y + trackHeight, chassisWidth / 2, - trackHeight, chassisWidth, chassisHeight, 1, 1, rotationDeg);
        batch.setColor(Color.WHITE);
    }

    /**
     * @return the tank rotation in rad
     */
    private double calculateRotation() {
        if(position < SLOPE_05DX || position > world.getMap().getMapWidth() - SLOPE_05DX) return 0;
        int y0 = world.getMap().getMapHeight((int) (position - SLOPE_05DX));
        int y1 = world.getMap().getMapHeight((int) (position + SLOPE_05DX));
        return Math.atan2(y1 - y0, SLOPE_05DX * 2.0);
    }

    public void setPosition(float position) {
        this.position = Math.max(SLOPE_05DX, Math.min(world.getMap().getMapWidth() - SLOPE_05DX - 1, position));
    }

    public float getPosition() {
        return position;
    }

    public void moveX(float amount) {
        setPosition(position + amount);
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
