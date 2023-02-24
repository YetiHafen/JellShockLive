package de.jellshock.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import de.jellshock.game.vehicles.Tank;

public class TestScreen implements Screen {

    private SpriteBatch batch;
    private BitmapFont font;
    private Tank tank = new Tank(new Color(Color.CYAN));


    public TestScreen() {
        batch = new SpriteBatch();
        font = new BitmapFont();
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        if(Gdx.input.isKeyPressed(Input.Keys.A)) {
            tank.moveX(-100 * delta);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D)) {
            tank.moveX(100 * delta);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.R)) {
            tank.setRotation(tank.getRotation() + 100 * delta);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.F)) {
            tank.setRotation(tank.getRotation() - 100 * delta);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            tank.setGunRotation(tank.getGunRotation() + 100 * delta);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            tank.setGunRotation(tank.getGunRotation() - 100 * delta);
        }
        ScreenUtils.clear(0.5F,0.5F,0.5F,0);
        batch.begin();
        font.draw(batch, "asdf", 1 , font.getLineHeight());
        tank.render(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }
}
