package de.jellshock.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.Viewport;

public abstract class AbstractScreen implements Screen {

    protected OrthographicCamera camera;
    protected Viewport viewport;

    protected static int width;
    protected static int height;
    protected static boolean fullscreen;

    static {
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();
        fullscreen = Gdx.graphics.isFullscreen();
    }

    public AbstractScreen(Viewport viewport) {
        this.camera = new OrthographicCamera(width, height);
        this.viewport = viewport;
    }

    @Override
    public void show() {}

    @Override
    public void resize(int width, int height) {
        AbstractScreen.width = width;
        AbstractScreen.height = height;
        //viewport.setScreenSize(width, height);
        camera.viewportHeight = height;
        camera.viewportWidth = width;
        camera.update();
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
