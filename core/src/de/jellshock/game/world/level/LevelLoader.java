package de.jellshock.game.world.level;

import com.badlogic.gdx.Gdx;
import de.jellshock.game.world.Map;
import de.jellshock.game.world.MapType;
import de.jellshock.game.world.World;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class LevelLoader {

    private static final String fileExtension = ".level";
    private final List<World> levelWorlds;

    public LevelLoader() {
        levelWorlds = new ArrayList<>();
    }

    /*
     * pixels[width] -> height
     */
    public void createLevel(String name, int width, int height, int[] pixels) {
        String path = Gdx.files.getLocalStoragePath() + "/level/" + name + fileExtension;
        try (FileWriter writer = new FileWriter(path)) {
            long start = System.nanoTime();
            writer.write("name: " + name + "\nwidth: " + width + "\nheight: " + height + "\n");

            for (int i = 0; i < width; i++) {
                writer.write(pixels[i] + ",");
            }
            long stop = System.nanoTime();
            Gdx.app.log("Debug", "Generating the map took " + TimeUnit.NANOSECONDS.toMillis(stop - start) + "ms");
        } catch (IOException e) {
            Gdx.app.error("Level Map Error", "Can't create the level map " + e);
        }
    }

    public void generateLevel(String name, int width, int height) {
        World world = new World(name, new Map(width, MapType.MOUNTAIN));
        world.getMap().generateMap();
        levelWorlds.add(world);
        createLevel(name, width, height * 2, world.getMap().getWorldMap());
    }

    public World loadLevel(File file) {
        try (FileReader reader = new FileReader(file)) {
            Scanner scanner = new Scanner(reader);

            int i = 0;
            String name = null;
            int width = -1;
            int height = -1;
            List<Integer> pixels = new ArrayList<>();
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                if (line.startsWith("name")) name = line.substring(6);
                else if (line.startsWith("width")) width = Integer.parseInt(line.substring(7));
                else if (line.startsWith("height")) height = Integer.parseInt(line.substring(8));
                else {
                    String[] values = line.split(",");

                    for (int j = 0; j < values.length; j++) {
                        pixels.add(i, Integer.parseInt(values[i]));
                        i++;
                    }
                }

            }
            int[] map = pixels.stream().mapToInt(Integer::intValue).toArray();
            World world = new World(name, new Map(map, width, height));
            world.getMap().setMapChanged(true);
            levelWorlds.add(world);
            return world;
        } catch (IOException e) {
            Gdx.app.error("Level Map Error", "Can't load the level map " + file.getName(), e);
        }
        return null;
    }
}
