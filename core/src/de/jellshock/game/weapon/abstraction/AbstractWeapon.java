package de.jellshock.game.weapon.abstraction;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import de.jellshock.game.rendering.IRenderConsumer;
import de.jellshock.game.screen.game.GameScreen;
import de.jellshock.game.world.World;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractWeapon implements IRenderConsumer<SpriteBatch>, Disposable {

    protected GameScreen gameScreen;

    protected String name;

    protected Color color;
    protected World world;

    protected int damage;
    protected int damageRadius;

    protected Vector2 position;
    protected Vector2 velocity;

    protected float gravity = 9.81F;

    public AbstractWeapon(String name, Color color) {
        this.name = name;
        this.color = color;
    }

    public abstract void update(float delta);
}
