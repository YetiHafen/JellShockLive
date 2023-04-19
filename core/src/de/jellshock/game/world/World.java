package de.jellshock.game.world;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import lombok.Getter;

@Getter
public class World implements Disposable {

    private final String name;
    private final Map map;
    private final Background background;

    public static final int MAP_SIZE = 3000;

    public World(String name, MapType mapType) {
        this.name = name;
        this.map = new Map(MAP_SIZE, mapType);
        this.background = new Background(map);
    }

    public void generateWorld() {
        map.generateMap();
    }

    public void renderWorld(SpriteBatch batch) {
        background.render(batch);
        map.render(batch);
    }

    @Override
    public void dispose() {
        background.dispose();
        map.dispose();
    }
}
