package de.jellshock.game.screen.game.offline;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import de.jellshock.game.event.key.KeyEvent;
import de.jellshock.game.player.Bot;
import de.jellshock.game.player.Entity;
import de.jellshock.game.player.Team;
import de.jellshock.game.screen.game.GameScreen;
import de.jellshock.game.world.World;

import java.util.ArrayList;
import java.util.List;

public abstract class OfflineScreen extends GameScreen {

    private int endScreenTimer = 0;
    private boolean end = false;
    private String file;
    List<Entity> deadBots = new ArrayList<>();
    private final int botCount;

    private Texture endTexture;

    public OfflineScreen(World world, int botCount) {
        super(world);
        this.botCount = botCount;
        player.setTeam(Team.CYAN);
        player.getTank().setPosition(50);
        menuBar.setTankColor(getPlayer().getTeam().getColor());
    }

    @Override
    public void render(float delta) {
        entities.forEach(entity -> {
            if (entity.getHealth() == 0) {
                if (entity instanceof Bot) {
                    deadBots.add(entity);
                    if (deadBots.size() == botCount) {
                        end = true;
                        file = "win";
                    }
                } else {
                    end = true;
                    file = "loss";
                }
            }
        });
        super.render(delta);
        if (end) {
            printEndScreen();
        }
    }

    public void printEndScreen() {
        endScreenTimer += Gdx.graphics.getDeltaTime();
        if (endTexture == null) endTexture = new Texture(Gdx.files.internal(file + ".png"));
        if (endScreenTimer <= 5) {
            batch.begin();
            float x = (Gdx.graphics.getWidth() * camera.zoom / 2F) - (endTexture.getWidth() / 2F);
            float y = (Gdx.graphics.getHeight() * camera.zoom / 2F) + (endTexture.getHeight() / 2F);
            batch.draw(endTexture, x, y);
            batch.end();
        }
    }

    @Override
    public void update(float delta, KeyEvent event) {
        if (shotProjectile != null) {
            shotProjectile.render(batch);
        }
    }
}