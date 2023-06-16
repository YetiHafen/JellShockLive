package de.jellshock.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import de.jellshock.Constants;
import de.jellshock.game.screen.game.GameScreen;
import lombok.Getter;

@Getter
public class MenuBar extends HudElement {

    private final Image image;
    private final int width;
    private int height;

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
        table.setBackground(new TextureRegionDrawable(new Texture(pixmap)));

        Texture tankTexture = new Texture(Gdx.files.internal("tank/tank_complete.png"));
        image = new Image(tankTexture);
        TextButton userInfo = new TextButton("", skin.get("rectangle", TextButton.TextButtonStyle.class));
        userInfo.add(image).width(200).height(100).center();

        TextButton tankInfo = new TextButton("Tank", skin.get("rectangle", TextButton.TextButtonStyle.class));

        TextButton itemsButton = new TextButton("Items", skin.get("yellow", TextButton.TextButtonStyle.class));

        Table weaponTable = new Table();
        TextButton weaponsButton = new TextButton("Weapons", skin.get("flat-red", TextButton.TextButtonStyle.class));
        TextButton weaponInfoButton = new TextButton("Strike", skin.get("flat-blue", TextButton.TextButtonStyle.class));
        TextButton test = new TextButton("1", skin.get("box-blue", TextButton.TextButtonStyle.class));
        weaponInfoButton.add(test).width(79).height(70).padRight(10);

        weaponTable.add(weaponsButton).height(84).padBottom(10).row();
        weaponTable.add(weaponInfoButton).height(84);

        TextButton fireButton = new TextButton("FIRE", skin.get("green", TextButton.TextButtonStyle.class));
        fireButton.align(Align.topLeft);

        table.add(userInfo).expandX().uniform();
        table.add(tankInfo).expandX().uniform();
        table.add(itemsButton).expandX().uniform();
        table.add(weaponTable).padLeft(15).uniform();
        table.add(fireButton).padLeft(10).expand().uniform();
        table.setBounds(0, -height, Gdx.graphics.getWidth(), height);
    }

    public void setHeight(int barHeight, float cameraZoom) {
        System.out.println(Gdx.input.getInputProcessor());
        this.height = barHeight;
        table.setBounds(0, -barHeight, Gdx.graphics.getWidth() * cameraZoom, barHeight);
        stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    public void setTankColor(Color color) {
        image.setColor(color);
    }

    @Override
    public void dispose() {
        super.dispose();
        skin.dispose();
    }
}
