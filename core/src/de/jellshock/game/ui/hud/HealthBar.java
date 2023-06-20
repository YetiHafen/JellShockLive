package de.jellshock.game.ui.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import de.jellshock.Constants;
import de.jellshock.JellShock;
import de.jellshock.game.player.Entity;
import de.jellshock.game.screen.game.GameScreen;
import de.jellshock.game.ui.HudElement;
import de.jellshock.game.vehicle.Tank;
import lombok.Getter;

@Getter
public class HealthBar extends HudElement {

    private final Entity entity;

    private int health = 100;
    private static final int HEALTH_OFFSET = 4;
    private static final int HEALTH_HEIGHT = 8;

    private Texture texture;
    private final Pixmap basePixmap;
    private Pixmap pixmap;

    private final Vector2 position;

    public HealthBar(GameScreen gameScreen, Entity entity) {
        super(gameScreen);
        this.entity = entity;
        position = new Vector2();

        Texture baseTexture = JellShock.getInstance().getAssetManager().get(Constants.HEALTH_BAR_PATH);
        baseTexture.getTextureData().prepare();
        basePixmap = baseTexture.getTextureData().consumePixmap();
        pixmap = basePixmap;
        baseTexture.dispose();

        updatePosition(entity.getTank().getParentPosition());
        updateHealth(health);
    }

    public void updatePosition(Vector2 position) {
        float scaling  = Tank.SCALE;
        int offset = (int) ((entity.getTank().getChassisTexture().getHeight() * scaling) + (entity.getTank().getTrackTexture().getHeight() * scaling)) + 20;
        this.position.x = position.x - ((entity.getTank().getChassisTexture().getWidth() * scaling) / 2);
        this.position.y = position.y + offset;
    }

    public void updateHealth(int health) {
        if (health > 100 || health < 0) return;
        this.health = health;

        for (int i = 0; i < health; i++) {
            for (int j = 0; j < HEALTH_HEIGHT; j++) {
                pixmap.setColor(Color.GREEN);
                pixmap.drawPixel(HEALTH_OFFSET + i, HEALTH_OFFSET + j);
            }
        }

        for (int i = 0; i < 100 - health; i++) {
            for (int j = 0; j < HEALTH_HEIGHT; j++) {
                pixmap.setColor(Color.RED);
                pixmap.drawPixel((HEALTH_HEIGHT + health) + i, HEALTH_OFFSET + j);
            }
        }

        if (texture == null) texture = new Texture(pixmap);
        texture.draw(pixmap, 0, 0);
    }

    @Override
    public void render(SpriteBatch batch) {
        Vector2 position = entity.getTank().getParentPosition();
        if (!position.epsilonEquals(this.position)) updatePosition(position);
        batch.draw(texture, this.position.x, this.position.y, texture.getWidth(), texture.getHeight());
    }

    @Override
    public void dispose() {
        texture.dispose();
        basePixmap.dispose();
        pixmap.dispose();
        super.dispose();
    }
}
