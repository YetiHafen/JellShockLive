package de.jellshock.game.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import de.jellshock.game.rendering.IRenderConsumer;
import de.jellshock.game.screen.game.GameScreen;
import lombok.Getter;

@Getter
public class MenuBar extends HudElement implements IRenderConsumer<SpriteBatch> {

    private static int WIDTH;
    private static int HEIGHT;

    private final Texture texture;


    public MenuBar(GameScreen gameScreen) {
        super(gameScreen, new Vector2(1, 1));
        WIDTH = gameScreen.getWorld().getMap().getMapWidth();
        HEIGHT = (int) (gameScreen.getWorld().getMap().getMapHeight() * 0.2);

        texture = new Texture(WIDTH, HEIGHT, Pixmap.Format.RGBA8888);
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.setColor(Color.RED);
        batch.draw(texture, 0, -HEIGHT);
        batch.setColor(Color.WHITE);
    }

    @Override
    public void dispose() {
        texture.dispose();
    }
}
