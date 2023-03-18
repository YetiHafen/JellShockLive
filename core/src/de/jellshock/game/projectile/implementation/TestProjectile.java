package de.jellshock.game.projectile.implementation;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import de.jellshock.game.projectile.ProjectileCategory;
import de.jellshock.game.projectile.abstraction.Projectile;
import de.jellshock.game.world.World;

public class TestProjectile extends Projectile {

    private final Texture testTexture = new Texture("hexenmeister.png");

    public TestProjectile() {
        super("Test", Color.BLUE, ProjectileCategory.SINGLE_SHOT, false);
        setGravity(400);
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        Vector2 pos = getPosition();
        Vector2 vel = getVelocity();
        World world = getWorld();

        if (pos.x >= world.getMapWidth() || pos.x < 0) return;
        if(pos.y < world.getMapHeight((int) pos.x)) {
            // stop movement and move to top
            vel.x = 0;
            vel.y = 0;
            pos.y = Integer.MAX_VALUE;
            // add damage to map
            int x = (int) pos.x;
            for(int i = 0; i < 20; i++) {
                if (x + i >= world.getMapWidth() || x + i < 0) continue;
                world.setMapHeight(x + i, world.getMapHeight(x) - 20);
            }
        }

        spriteBatch.draw(testTexture, getPosition().x, getPosition().y, testTexture.getWidth() * 0.1F, testTexture.getHeight() * 0.1F);
    }

    @Override
    public void dispose() {
        testTexture.dispose();
    }
}
