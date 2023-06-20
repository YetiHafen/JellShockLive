package de.jellshock.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import de.jellshock.Constants;
import de.jellshock.JellShock;
import de.jellshock.game.player.Player;
import de.jellshock.game.screen.game.GameScreen;
import de.jellshock.game.weapon.abstraction.AbstractWeapon;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Getter
public class MenuBar extends HudElement {

    private final Player player;

    private final Image tankImage;
    private final int width;
    private int height;

    private final TextButton tankButton;
    private final TextButton fireButton;

    private final Skin skin;

    public MenuBar(GameScreen gameScreen, Player player) {
        super(gameScreen);
        this.player = player;

        width = gameScreen.getWorld().getMap().getMapWidth();
        height = (int) (gameScreen.getWorld().getMap().getMapHeight() * 0.2);
        skin = JellShock.getInstance().getAssetManager().get(Constants.JELLY_SKIN_PATH);

        // Table Background
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.valueOf("#232323"));
        pixmap.fill();
        table.setBackground(new TextureRegionDrawable(new Texture(pixmap)));

        Texture tankTexture = new Texture(Gdx.files.internal("tank/tank_complete.png"));
        tankImage = new Image(tankTexture);
        TextButton userInfo = new TextButton("", skin.get("rectangle", TextButton.TextButtonStyle.class));
        userInfo.add(tankImage).width(200).height(100).center();

/*        Texture fuelCounterTexture = JellShock.getInstance().getAssetManager().get(Constants.FUEL_BUTTON_PATH, Texture.class);
        ImageButton.ImageButtonStyle buttonStyle = new ImageButton.ImageButtonStyle();
        buttonStyle.imageUp = new TextureRegionDrawable(fuelCounterTexture);
        ImageButton fuelButton = new ImageButton(buttonStyle);*/

/*        fuelPointerImage = new Image(JellShock.getInstance().getAssetManager().get(Constants.FUEL_POINTER_PATH, Texture.class));
        fuelPointerImage.setScaling(Scaling.fit);
        fuelButton.add(fuelPointerImage);*/

        tankButton = new TextButton(String.valueOf(player.getFuel()), skin.get("rectangle", TextButton.TextButtonStyle.class));

        TextButton itemsButton = new TextButton("Items", skin.get("yellow", TextButton.TextButtonStyle.class));

        Table weaponTable = new Table();
        TextButton weaponsButton = new TextButton("Weapons", skin.get("flat-red", TextButton.TextButtonStyle.class));

        List<Class<? extends AbstractWeapon>> weapons = player.getWeapons();
        List<String> names = new ArrayList<>(2);
        weapons.forEach(clazz -> {
            try {
                names.add((String) weapons.get(0).getField("name").get(null));
            } catch (IllegalAccessException | NoSuchFieldException e) {
                throw new RuntimeException(e);
            }
        });

        final int[] val = {0};
        TextButton weaponInfoButton = new TextButton("Single Shot", skin.get("flat-blue", TextButton.TextButtonStyle.class));
        weaponsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String[] names = {"Single Shot", "Five Ball"};
                weaponInfoButton.setText(names[val[0]]);
                if (val[0] == 1) val[0] = 0;
                else val[0] = 1;
            }
        });

        Image image = new Image(JellShock.getInstance().getAssetManager().get(Constants.SHOT_PATH, Texture.class));
        TextButton icon = new TextButton("", skin.get("box-blue", TextButton.TextButtonStyle.class));
        icon.add(image).padRight(icon.getWidth() / 2F - (image.getWidth() / 2F)).center();
        weaponInfoButton.add(icon).width(79).height(70).padRight(10);

        weaponTable.add(weaponsButton).height(84).padBottom(10).row();
        weaponTable.add(weaponInfoButton).height(84);

        fireButton = new TextButton("FIRE", skin.get("green", TextButton.TextButtonStyle.class));
        fireButton.align(Align.topLeft);

        table.add(userInfo).uniform();
        table.add(tankButton).uniform();
        table.add(itemsButton).expandX().uniform();
        table.add(weaponTable).padLeft(15).uniform();
        table.add(fireButton).padLeft(10).expand().uniform();
        table.setBounds(0, -height, Gdx.graphics.getWidth(), height);
    }

    /*public static final float FUEL_DEGREES = 1.8f;*/

    public void updateFuel(float fuel) {
        tankButton.setText(String.valueOf(fuel));
        /*fuelPointerImage.rotateBy(- (0.2f * (Player.START_TANK_VALUE - fuel)));*/
    }

    public void setHeight(int barHeight, float cameraZoom) {
        this.height = barHeight;
        table.setBounds(0, -barHeight, Gdx.graphics.getWidth() * cameraZoom, barHeight);
        stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    public void setTankColor(Color color) {
        tankImage.setColor(color);
    }


    @Override
    public void dispose() {
        skin.dispose();
        super.dispose();
    }
}
