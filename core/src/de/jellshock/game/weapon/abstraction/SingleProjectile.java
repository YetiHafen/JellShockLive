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
        if (position.x >= world.getMap().getMapWidth() || position.x < 0) return;
        if (checkMapCollision()) {
            // stop movement and move to top
            velocity.x = 0;
            velocity.y = 0;
            position.y = Integer.MAX_VALUE;

            onMapCollision();
        }
        batch.draw(texture, getPosition().x, getPosition().y, texture.getWidth(), texture.getHeight());
    }

    protected abstract void onMapCollision();

    private boolean checkMapCollision() {
        return position.y < world.getMap().getMapHeight((int) position.x);
    }

    @Override
    public void dispose() {
    }
}
