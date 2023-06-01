package de.jellshock.game.screen.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import de.jellshock.Constants;

public class ExampleTableMenu extends AbstractMenuScreen {

    private final Stage stage;

    public ExampleTableMenu() {
        super(false);
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        Skin skin = new Skin(Gdx.files.internal(Constants.NEON_SKIN_PATH));
        Table table = new Table(skin);

        // 2 Tables
        /*table.defaults().pad(10);
        table.setFillParent(true);

        Label titleLabel = new Label("SERVER LIST", skin);
        titleLabel.setAlignment(Align.center);

        Table table1 = new Table();
        table1.setDebug(true);
        table1.add(new Label("First Table", skin));

        Table table2 = new Table();
        table2.setDebug(true);
        table2.add(new Label("Second Table", skin));

        table.add(titleLabel).colspan(2).fillX();
        table.row();
        table.add(table1).expand();
        table.add(table2).expand();*/

        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();
        table.setSize(width * 0.3f, height * 0.7f);
        table.setPosition((width - table.getWidth()) / 2, (height - table.getHeight()) / 2);
        table.setTransform(true);
        table.setOrigin(table.getWidth() / 2, table.getHeight() / 2);

        stage.addActor(table);
        stage.setDebugAll(true);
    }

    @Override
    public void update(float delta) {
        stage.act();
        stage.draw();
    }
}
