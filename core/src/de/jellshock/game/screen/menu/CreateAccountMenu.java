package de.jellshock.game.screen.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import de.jellshock.Constants;

public class CreateAccountMenu extends AbstractMenuScreen {

    private final Stage stage;

    public CreateAccountMenu() {
        super(false);
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        backButtonTexture = new Texture("menu/left-arrow.png");
        ImageButton button = new ImageButton(new TextureRegionDrawable(backButtonTexture));
        button.setSize(30, 30);
        button.setPosition(10, Gdx.graphics.getHeight() - 50);

        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                button.setVisible(false);
                setSlideScreen(new MenuScreen(), Direction.LEFT);
            }
        });
        stage.addActor(button);

        Skin skin = new Skin(Gdx.files.internal(Constants.NEON_SKIN_PATH));
        Table table = new Table(skin);
        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();
        table.setSize(width * 0.3f, height * 0.7f);
        table.setPosition((width - table.getWidth()) / 2, (height - table.getHeight()) / 2);
        table.setTransform(true);
        table.setOrigin(table.getWidth() / 2, table.getHeight() / 2);

        Label title = new Label("Create Account", skin);
        Label userName = new Label("Username: ", skin);
        TextField userField = new TextField("", skin);
        Label password = new Label("Password: ", skin);
        TextField passwordField = new TextField("", skin);
        table.add(title).colspan(2).fillX().row();
        table.add(userName).pad(10);
        table.add(userField).row();
        table.add(password).pad(10);
        table.add(passwordField).row();

        TextButton createButton = new TextButton("Create Account", skin);
        TextButton backMenuButton = new TextButton("Back to Menu", skin);
        table.add(createButton);
        table.add(backMenuButton);

        stage.addActor(table);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        stage.setViewport(viewport);
    }

    @Override
    public void update(float delta) {
        stage.act();
        stage.draw();
    }
}
