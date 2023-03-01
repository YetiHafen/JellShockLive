package de.jellshock.game.vehicles.projectiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class TestProjectile extends Projectile {

    private Texture testTexture = new Texture("hexenmeister.png");

    public TestProjectile() {
        setGravity(400);
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.draw(testTexture, getPosition().x, getPosition().y, testTexture.getWidth() * 0.1F, testTexture.getHeight() * 0.1F);
    }

    @Override
    public void dispose() {
        testTexture.dispose();
    }
}
