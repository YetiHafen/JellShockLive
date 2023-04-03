package de.jellshock.game.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import lombok.Getter;

@Getter
public class Background {

    private String path;
    private Table table;
    private final Texture backgroundTexture;

    public Background() {
        backgroundTexture = new Texture("background/sky.png");
        setBackground();
    }

    public Background(String path) {
        this.path = path;
        backgroundTexture = new Texture(path);
        setBackground();
    }

    private void setBackground() {
        table = new Table();
        table.background(new TextureRegionDrawable(backgroundTexture));
        table.setFillParent(true);
    }

}
