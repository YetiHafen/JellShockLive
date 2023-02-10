package de.jellshock.game.world;

public class World {

    private WorldType type;

    public World(WorldType type) {
        this.type = type;
    }

    public void generateWorld() {
        PerlinNoise noise = PerlinNoise.builder()
                .width(50)
                .height(50)
                .octave(5)
                .smooth(true)
                .min(5)
                .max(20)
                .build();
    }



}
