package de.jellshock.game.world.level;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import de.jellshock.game.world.Map;
import de.jellshock.game.world.MapType;
import de.jellshock.game.world.World;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class LevelLoader {

    private static final String fileExtension = ".level";

    /*
     * pixels[width] -> height
     */
    public static void createLevel(String name, int width, int height, int[] pixels) {
        String path = Gdx.files.getLocalStoragePath() + "level/" + name + fileExtension;
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

    public static void generateLevel(String name, int width, int height) {
        World world = new World(name, new Map(width, MapType.MOUNTAIN));
        world.getMap().generateMap();
        createLevel(name, width, height * 2, world.getMap().getWorldMap());
    }

    public static void generateCosLevel(String name, int width, int height) {
        int[] pixels = new int[Map.DEFAULT_MAP_SIZE];

        for (int i = 0; i < Map.DEFAULT_MAP_SIZE; i++) {
            pixels[i] = (int) (2 * Math.cos(((double) i / width) * Math.PI * 5) * 100) + 300;
        }

        createLevel(name, width, height, pixels);
    }

    public static World loadLevel(FileHandle file) {
        Scanner scanner = new Scanner(file.read());

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
        return world;
    }
}
