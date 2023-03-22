package de.jellshock.game.weapon.abstraction;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class SingleProjectile extends AbstractWeapon {

    protected Texture texture;

    public SingleProjectile(String name, Color color) {
        super(name, color);
        this.texture = getTexture();
    }

    protected abstract Texture getTexture();

    @Override
    public void render(SpriteBatch batch) {
        if (position.x >= world.getMapWidth() || position.x < 0) return;
        if (checkMapCollision()) {
            // stop movement and move to top
            velocity.x = 0;
            velocity.y = 0;
            position.y = Integer.MAX_VALUE;
            // add damage to map
            int x = (int) position.x;
            for(int i = 0; i < 20; i++) {
                if (x + i >= world.getMapWidth() || x + i < 0) continue;
                world.setMapHeight(x + i, world.getMapHeight(x) - 20);
            }
        }
        batch.draw(texture, getPosition().x, getPosition().y, texture.getWidth() * 3, texture.getHeight() * 3);
    }

    private boolean checkMapCollision() {
        return position.y < world.getMapHeight((int) position.x);
    }

    @Override
    public void dispose() {
        texture.dispose();
    }
}
