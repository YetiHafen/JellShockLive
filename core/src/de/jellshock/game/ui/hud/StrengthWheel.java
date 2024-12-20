package de.jellshock.game.ui.hud;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import de.jellshock.game.rendering.IRenderConsumer;
import de.jellshock.game.screen.game.GameScreen;
import de.jellshock.game.ui.HudElement;

public class StrengthWheel extends HudElement implements IRenderConsumer<SpriteBatch>, Disposable {

    private final Vector2 position;
    private final Pixmap wheel;
    private final Texture wheelTexture;

    public StrengthWheel(GameScreen gameScreen) {
        super(gameScreen);
        position = new Vector2();

        wheel = new Pixmap(600, 600, Pixmap.Format.RGBA8888);
        wheel.setBlending(Pixmap.Blending.None);
        wheel.setColor(1, 1, 1, 0.2f);
        wheel.fillCircle(300, 300, 300);
        wheelTexture = new Texture(wheel);

        updatePosition(gameScreen.getPlayer().getTank().getParentPosition());
    }

    public void updatePosition(Vector2 position) {
        this.position.x = position.x - wheel.getWidth() / 2F;
        this.position.y = position.y - wheel.getHeight() / 2F;
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        Vector2 position = getGameScreen().getPlayer().getTank().getParentPosition();
        if (!position.epsilonEquals(this.position)) updatePosition(position);
        spriteBatch.draw(wheelTexture, this.position.x, this.position.y);
    }

    @Override
    public void dispose() {
        wheel.dispose();
        wheelTexture.dispose();
    }
}
