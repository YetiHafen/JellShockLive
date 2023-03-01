package de.jellshock.game.vehicles.projectiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class TestProjectile extends Projectile {

    private Texture testTexture = new Texture("tank/chassis_classic.png");

    public TestProjectile() {
        setGravity(400);
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.draw(testTexture, getPosition().x, getPosition().y);
    }

    @Override
    public void dispose() {
        testTexture.dispose();
    }
}
