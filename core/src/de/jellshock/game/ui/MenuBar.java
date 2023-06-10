package de.jellshock.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.Align;
import de.jellshock.Constants;
import de.jellshock.JellShock;
import de.jellshock.game.rendering.IRenderConsumer;
import de.jellshock.game.screen.game.GameScreen;
import lombok.Getter;

@Getter
public class MenuBar extends HudElement {

    private final int width;
    private int height;

    private final Texture texture;
    private final Skin skin;


    public MenuBar(GameScreen gameScreen) {
        super(gameScreen, new Vector2(1, 1));

        width = gameScreen.getWorld().getMap().getMapWidth();
        height = (int) (gameScreen.getWorld().getMap().getMapHeight() * 0.2);


        skin = new Skin(Gdx.files.internal(Constants.NEON_SKIN_PATH));

        getTable().setDebug(true);

        ImageButton ib = new ImageButton(skin);
        TextButton tb = new TextButton("BUTTON", skin);
        TextField t = new TextField("asdf", skin);
        //tb.setColor(1,0,0,1);
        tb.align(Align.topLeft);
        getTable().add(t, ib, tb);
        getTable().setBounds(0, -height, Gdx.graphics.getWidth(), height);

        AssetManager manager = JellShock.getInstance().getAssetManager();
        texture = manager.get(Constants.CHASSIS_PATH);
    }

    public void setHeight(int barHeight, float cameraZoom) {
        System.out.println(Gdx.input.getInputProcessor());
        this.height = barHeight;
        getTable().setBounds(0, -barHeight, Gdx.graphics.getWidth() * cameraZoom, barHeight);
        getStage().getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public void dispose() {
        super.dispose();
        skin.dispose();
    }
}
