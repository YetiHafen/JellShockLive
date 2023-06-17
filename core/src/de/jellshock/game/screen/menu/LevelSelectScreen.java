package de.jellshock.game.screen.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import de.jellshock.Constants;
import de.jellshock.JellShock;

public class LevelSelectScreen extends AbstractMenuScreen {

    private final Skin skin;
    private final Table levelTable;

    public LevelSelectScreen() {
        super(false);
        skin = JellShock.getInstance().getAssetManager().get(Constants.NEON_SKIN_PATH);

        Table table = new Table();
        table.setFillParent(true);

        levelTable = new Table();
        levelTable.center();
        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();
        levelTable.setSize(width * 0.3f, height * 0.7f);
        table.add(levelTable).padTop(20).expand().center().top().row();

        listAllLevels();



        stage.addActor(table);

        stage.setDebugAll(true);
    }

    public void listAllLevels() {

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
