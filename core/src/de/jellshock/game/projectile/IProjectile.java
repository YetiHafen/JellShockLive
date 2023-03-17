package de.jellshock.game.projectile;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import de.jellshock.game.rendering.IRenderConsumer;

public interface IProjectile extends IRenderConsumer<SpriteBatch>, Disposable {

    /**
     * @return if this projectile is usable in the game
     * When you have this projectile, but you can't use it during the game this value is false
     */
    boolean isEnabled();

    /**
     * @return the name of this projectile
     */
    String getName();

    /**
     *
     * @return the color of this projectile which is used in the select screen and as a trail
     */
    Color getColor();


}
