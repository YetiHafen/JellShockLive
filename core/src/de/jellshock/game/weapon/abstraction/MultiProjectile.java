package de.jellshock.game.weapon.abstraction;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import de.jellshock.JellShock;
import de.jellshock.game.player.Entity;
import de.jellshock.game.player.Player;
import de.jellshock.util.Pair;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;

public abstract class MultiProjectile extends AbstractWeapon {

    // entry <position, velocity>
    protected final HashMap<Texture, Pair<Vector2, Vector2>> projectiles;

    @Getter
    protected int damage;

    public MultiProjectile(Color color, int index, int damage) {
        super(color);
        this.projectiles = new HashMap<>();
        for (int i = 0; i < index; i++) {
            this.projectiles.put(getBaseTexture(), Pair.pair(new Vector2(0, 20 * i), new Vector2(0 ,0)));
        }
        this.damage = damage;
    }

    protected abstract Texture getBaseTexture();

    @Override
    public void render(SpriteBatch batch) {
        projectiles.forEach((texture, vectorPair) -> {
            Vector2 position = vectorPair.getKey();
            Vector2 velocity = vectorPair.getValue();
            if (position.x >= world.getMap().getMapWidth() || position.x < 0) return;
            if (checkMapCollision(vectorPair.getKey())) {
                velocity.x = 0;
                velocity.y = 0;
                vectorPair.setValue(velocity);
                position.y = Integer.MAX_VALUE;
                vectorPair.setKey(position);

                onMapCollision(position);
            }
            batch.draw(texture, position.x, position.y, texture.getWidth(), texture.getHeight());
        });
    }

    @Override
    public void update(float delta) {
        projectiles.forEach((texture, vectorPair) -> {
            Vector2 position = vectorPair.getKey();
            Vector2 velocity = vectorPair.getValue();

            velocity.y -= gravity * delta;
            position.x += velocity.x * delta;
            position.y += velocity.y * delta;

            vectorPair.setKey(position);
            vectorPair.setValue(velocity);
        });
    }

    protected abstract void onMapCollision(Vector2 position);

    protected boolean checkMapCollision(Vector2 position) {
        return position.y < world.getMap().getMapHeight((int) position.x);
    }

    public void setPosition(Vector2 position) {
        projectiles.forEach((texture, vectorPair) -> {
            vectorPair.setKey(vectorPair.getKey().add(position));
        });
        super.setPosition(position);
    }

    public void setVelocity(Vector2 velocity) {
        projectiles.forEach((texture, vectorPair) -> {
            vectorPair.setValue(vectorPair.getValue().add(velocity));
        });
        super.setVelocity(velocity);
    }
}