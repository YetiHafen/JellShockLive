package de.jellshock.game.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import de.jellshock.Constants;
import de.jellshock.JellShock;
import de.jellshock.game.rendering.IRenderConsumer;
import lombok.Getter;

@Getter
public class Background implements IRenderConsumer<SpriteBatch>, Disposable {

    private final Texture backgroundTexture;
    private final Map map;

    public Background(Map map) {
        this.map = map;
        backgroundTexture = JellShock.getInstance().getAssetManager().get(Constants.BACKGROUND_PATH);
    }

    @Override
    public void render(SpriteBatch batch) {
        int width = map.getMapWidth();
        float zoom = map.getMapWidth() / (float) Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight() * zoom;
        batch.draw(backgroundTexture, 0, 0, width, height);
    }

    @Override
    public void dispose() {
        backgroundTexture.dispose();
    }
}
