package de.jellshock.game.ui.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import de.jellshock.game.rendering.IRenderConsumer;
import de.jellshock.game.screen.game.GameScreen;
import de.jellshock.game.ui.HudElement;
import lombok.Getter;

@Getter
public class StrengthTriangle extends HudElement implements IRenderConsumer<SpriteBatch>, Disposable {

    private final Vector2 position;
    private final Pixmap triangle;
    private final Texture triangleTexture;

    public static final int DEFAULT_STRENGTH = 40;
    public static final int LENGTH_MULTIPLIER = 50;

    private int strength = DEFAULT_STRENGTH;

    public StrengthTriangle(GameScreen gameScreen) {
        super(gameScreen);

        position = new Vector2();

        int strengthLength = strength * LENGTH_MULTIPLIER;

        triangle = new Pixmap(100, 100, Pixmap.Format.RGBA8888);
        triangle.setColor(Color.RED);
        
        triangleTexture = new Texture(triangle);
    }

    public void updateStrength(int strength) {
        this.strength = strength;
    }

    public void updatePosition(Vector2 position) {
        this.position.x = position.x;
        this.position.y = position.y;
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.draw(triangleTexture, position.x, position.y);
    }

    @Override
    public void dispose() {
        triangle.dispose();
        triangleTexture.dispose();
    }
}
