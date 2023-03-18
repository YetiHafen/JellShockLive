package de.jellshock.game.projectile.implementation.single;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import de.jellshock.game.projectile.ProjectileCategory;
import de.jellshock.game.projectile.abstraction.SingleProjectile;
import de.jellshock.game.world.World;

public class ShotProjectile extends SingleProjectile {

    public ShotProjectile() {
        super("Shot", Color.CYAN, ProjectileCategory.SINGLE_SHOT);
        setGravity(400);
    }

    @Override
    protected Texture getTexture() {
        return new Texture(Gdx.files.internal("shot.png"));
    }

    @Override
    public void render(SpriteBatch batch) {
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

        batch.draw(texture, getPosition().x, getPosition().y, texture.getWidth() * 3, texture.getHeight() * 3);
    }

    @Override
    public void dispose() {
    }
}
