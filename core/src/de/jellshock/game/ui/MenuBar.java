package de.jellshock.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import de.jellshock.Constants;
import de.jellshock.JellShock;
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
        skin = new Skin(Gdx.files.internal(Constants.JELLY_SKIN_PATH));

        // Table Background
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.valueOf("#232323"));
        pixmap.fill();
        getTable().setBackground(new TextureRegionDrawable(new Texture(pixmap)));

        AssetManager manager = JellShock.getInstance().getAssetManager();
        texture = manager.get(Constants.CHASSIS_PATH);

        TextButton itemsButton = new TextButton("Items", skin.get("yellow", TextButton.TextButtonStyle.class));

        Table weaponTable = new Table();
        TextButton weaponsButton = new TextButton("Weapons", skin.get("flat-blue", TextButton.TextButtonStyle.class));
        TextButton waa = new TextButton("Weapons", skin.get("flat-blue", TextButton.TextButtonStyle.class));
        weaponTable.add(weaponsButton).row();
        weaponTable.add(waa);

        TextButton fireButton = new TextButton("FIRE", skin.get("green", TextButton.TextButtonStyle.class));
        fireButton.align(Align.topLeft);

        getTable().add(itemsButton).expand().fill().uniform();
        getTable().add(weaponTable).padLeft(15).fill().uniform();
        getTable().add(fireButton).padLeft(10).expand().fill().uniform();
        getTable().setBounds(0, -height, Gdx.graphics.getWidth(), height);
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
