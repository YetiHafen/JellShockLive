package de.jellshock.game.world;

import lombok.Getter;

@Getter
public class World {

    private String name;
    private Map map;
    private Background background;

    public static final int MAP_SIZE = 3000;

    public World(String name) {
        this.name = name;
        this.map = new Map(MAP_SIZE, MapType.MOUNTAIN);
    }

    public void generateWorld() {
        map.generateMap();
    }

}
