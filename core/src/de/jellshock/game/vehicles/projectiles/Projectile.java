package de.jellshock.game.vehicles.projectiles;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import de.jellshock.game.rendering.IRenderConsumer;

public abstract class Projectile implements IRenderConsumer<SpriteBatch>, Disposable {

    private Vector2 position;
    private Vector2 velocity;

    private float gravity = 9.81F;

    public void update(float delta) {
        velocity.y -= gravity * delta;

        position.x += velocity.x * delta;
        position.y += velocity.y * delta;
    }

    public Vector2 getPosition() {
        return position;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

    public void setGravity(float gravity) {
        this.gravity = gravity;
    }

    public float getGravity() {
        return gravity;
    }
}
