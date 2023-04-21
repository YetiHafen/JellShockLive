package de.jellshock.game.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import de.jellshock.game.rendering.IRenderConsumer;

public class Background implements IRenderConsumer<SpriteBatch>, Disposable {

    private String path;
    private final Texture backgroundTexture;
    private final Map map;

    public Background(Map map) {
        this("background/sky.png", map);
    }

    public Background(String path, Map map) {
        this.path = path;
        this.map = map;
        backgroundTexture = new Texture(path);
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
