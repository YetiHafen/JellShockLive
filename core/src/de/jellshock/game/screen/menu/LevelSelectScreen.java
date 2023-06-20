package de.jellshock.game.screen.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Scaling;
import de.jellshock.Constants;
import de.jellshock.JellShock;
import de.jellshock.game.screen.game.offline.level.Level;

public class LevelSelectScreen extends AbstractMenuScreen {

    private final Skin skin;
    private final Table levelTable;

    public LevelSelectScreen() {
        super(false, true);
        skin = JellShock.getInstance().getAssetManager().get(Constants.NEON_SKIN_PATH);

        Table table = new Table();
        table.setFillParent(true);

        levelTable = new Table();
        levelTable.center();
        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();
        levelTable.setSize(width * 0.3f, height * 0.7f);
        table.add(levelTable).padTop(20).expand().top().row();

        String[] columns = {"Nr.", "Image"};
        Label levelLabel = new Label("Levels", skin);
        levelLabel.setAlignment(Align.center);
        levelTable.add(levelLabel).colspan(columns.length + 1).padTop(10).row();

        Label spaceLabel = new Label("", skin);
        levelTable.add(spaceLabel).pad(10).center();

        for (String column : columns) {
            Label levelsLabel = new Label(column, skin);
            levelTable.add(levelsLabel).pad(10).center();
        }
        levelTable.row();

        listAllLevels();

        stage.addActor(table);
    }

    public void listAllLevels() {
        for (Class<? extends Level> clazz : Constants.LEVELS) {
            Image joinImage = new Image(JellShock.getInstance().getAssetManager().get(Constants.PLAY_BUTTON_PATH, Texture.class));
            joinImage.setScaling(Scaling.fit);

            joinImage.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    joinImage.setTouchable(Touchable.disabled);
                    JellShock.getInstance().setScreen(clazz);
                }
            });
            levelTable.add(joinImage).height(30).width(30);

            int level;
            try {
                level = (int) clazz.getField("level").get(null);
            } catch (IllegalAccessException | NoSuchFieldException e) {
                Gdx.app.error("Level", "In Level Class " + clazz.getSimpleName() + " the level field is missing", e);
                return;
            }
            Label idLabel = new Label(String.valueOf(level), skin);
            Image image = new Image(new Texture(Gdx.files.internal(Constants.IMAGE_LEVEL_PATHS.get(level - 1))));
            levelTable.add(idLabel).pad(10).center();
            levelTable.add(image).width(300).height(100).row();
        }
    }

    @Override
    public void resume() {
        super.resume();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        stage.setViewport(viewport);
    }

    @Override
    public void update(float delta) {
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void dispose() {
        skin.dispose();
        super.dispose();
    }
}
