package de.jellshock.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class AbstractScreen implements Screen {

    protected OrthographicCamera camera;
    protected Viewport viewport;

    protected int width;
    protected int height;

    public AbstractScreen() {
        this.width = Gdx.graphics.getWidth();
        this.height = Gdx.graphics.getHeight();
        camera = new OrthographicCamera(width, height);
        viewport = new StretchViewport(width, height);
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {}

    @Override
    public void resize(int width, int height) {
        this.width = width;
        this.height = height;
        viewport.update(width, height);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {}
}
