package de.jellshock.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import lombok.Getter;

@Getter
public abstract class AbstractScreen implements Screen {

    protected OrthographicCamera camera;
    protected Viewport viewport;

    public AbstractScreen(Viewport viewport) {
        this.camera = (OrthographicCamera) viewport.getCamera();
        this.viewport = viewport;
    }

    public AbstractScreen() {
        this(new ScreenViewport(new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight())));
    }

    @Override
    public void show() {}

    @Override
    public void resize(int width, int height) {
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
