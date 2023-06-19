package de.jellshock.game.weapon.abstraction;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import lombok.Getter;

public abstract class SingleProjectile extends AbstractWeapon {

    protected Texture texture;

    @Getter
    protected int damage;

    public SingleProjectile(String name, Color color, int damage) {
        super(name, color);
        this.texture = getTexture();
        this.damage = damage;
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
        batch.draw(texture, position.x, position.y, texture.getWidth(), texture.getHeight());
    }

    public void update(float delta) {
        velocity.y -= gravity * delta;

        position.x += velocity.x * delta;
        position.y += velocity.y * delta;
    }

    protected abstract void onMapCollision();

    private boolean checkMapCollision() {
        return position.y < world.getMap().getMapHeight((int) position.x);
    }


    @Override
    public void dispose() {
        texture.dispose();
    }
}
