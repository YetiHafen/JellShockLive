package de.jellshock.game.screen.menu;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class MenuScreen extends AbstractMenuScreen {

    private Texture texture;
    private Stage stage;
    private TextureRegion buttonRegion;
    private TextButton.TextButtonStyle textButtonStyle;
    private TextButton button;

    public MenuScreen() {
        texture = new Texture("background/background.png");
        stage = new Stage();

        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("neon/skin/neon-ui.atlas"));
        Skin skin = new Skin(Gdx.files.internal("neon/skin/neon-ui.json"));
        skin.addRegions(atlas);

        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = new TextureRegionDrawable(atlas.findRegion("button"));
        textButtonStyle.down = new TextureRegionDrawable(atlas.findRegion("button-pressed"));


        BitmapFont font = new BitmapFont();
        textButtonStyle.font = font;
        button = new TextButton("Play!", textButtonStyle);
        button.setPosition(0, 0);
        button.setSize(100, 80);

        stage.addActor(button);
    }

    @Override
    public void render(float delta) {
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();
        spriteBatch.draw(texture, 0 ,0, width, height);
        /*spriteBatch.draw(buttonRegion, 100, 100);*/
        spriteBatch.end();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        button.setPosition(width / 2, height / 2);
        super.resize(width, height);
    }

    @Override
    public void dispose() {
        texture.dispose();
        stage.dispose();
        super.dispose();
    }
}
