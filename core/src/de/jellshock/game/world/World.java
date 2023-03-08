package de.jellshock.game.world;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import lombok.Getter;

import java.util.Random;

@Getter
public class World implements Disposable {

    private final Pixmap pixmap;
    private Texture texture;
    private final Random random;
    private final WorldType worldType;
    private final int[] worldMap;
    private final Color color;

    private final int mapWidth;
    private final int mapHeight;
    private final int waveLength;
    private final float frequency;
    private final int amplitude;

    private boolean mapChanged = false;

    public World(int mapWidth, int waveLength, int amplitude) {
        this.mapWidth = mapWidth;
        this.waveLength = waveLength;
        this.amplitude = amplitude;
        this.mapHeight = amplitude * 2;
        this.frequency = 1.0f / waveLength;
        this.worldType = WorldType.CUSTOM;
        pixmap = new Pixmap(mapWidth, mapHeight, Pixmap.Format.RGB888);
        random = new Random();
        worldMap = new int[mapWidth];
        color = new Color();
    }

    public World(int mapWidth, WorldType worldType) {
        this.mapWidth = mapWidth;
        this.worldType = worldType;
        this.waveLength = worldType.getWaveLength();
        this.amplitude = worldType.getAmplitude();
        this.mapHeight = amplitude * 2;
        this.frequency = 1.0f / waveLength;
        pixmap = new Pixmap(mapWidth, mapHeight, Pixmap.Format.RGB888);
        random = new Random();
        worldMap = new int[mapWidth];
        color = new Color();
    }

    public void generateWorld() {
        mapChanged = true;
        float a = random.nextFloat();
        float b = random.nextFloat();

        for (int x = 0; x < mapWidth; x++) {
            float y;
            if (!(x % waveLength == 0)) {
                y = interpolate(a, b, (x % waveLength) * 1.0f / (float) waveLength) * amplitude;
            } else {
                a = b;
                b = random.nextFloat();
                y = a * amplitude;
            }
            worldMap[x] = (int) (mapHeight - y);
        }
    }

    private void renderWorld() {
        mapChanged = false;

        for (int x = 0; x < mapWidth; x++) {
            for (int y = 0; y < mapHeight; y++) {
                int worldValue = mapHeight - worldMap[x];
                if (y < worldValue) {
                    pixmap.setColor(Color.LIGHT_GRAY);
                    pixmap.drawPixel(x, y);
                } else {
                    float t = x / (float) mapWidth;
                    pixmap.setColor(color.set(Color.BLUE).lerp(Color.CYAN, t));
                    pixmap.drawPixel(x, y);
                }

            }
        }
        if (texture == null) {
            texture = new Texture(pixmap);
        }
        texture.draw(pixmap, 0,0);
    }

    public void setMapHeight(int x, int height) {
        mapChanged = true;
        assert height <= this.mapHeight;
        worldMap[x] = height;
    }

    public int getMapHeight(int x) {
        return worldMap[x];
    }

    public void render(SpriteBatch batch) {
        if (mapChanged) renderWorld();
        batch.draw(texture, 0, 0, mapWidth, mapHeight);
    }

    public void checkScreenBuffer(int width, int height) {
        if (!(width > this.mapWidth)) return;
        int widthDifference = (width - this.mapWidth) / 2;
        Pixmap leftBuffer = new Pixmap(widthDifference, this.mapHeight, Pixmap.Format.RGB888);
        Pixmap rightBuffer = new Pixmap(widthDifference, this.mapHeight, Pixmap.Format.RGB888);

        for (int i = widthDifference; i < 0; i--) {
            for (int j = 0; j < height; j++) {

            }
        }

        for (int i = 0; i < widthDifference; i++) {

        }

    }

    private float interpolate(float aPosition, float bPosition, float relativeDistance) {
        // calc the weight of the two texture values (aPosition, bPosition)
        float scaledValue = relativeDistance * (float) Math.PI;
        // weight of the two texture values of the relative position
        float weight = (1 - (float) Math.cos(scaledValue)) * 0.5f;
        return aPosition * (1 - weight) + bPosition * weight;
    }

    @Override
    public void dispose() {
        texture.dispose();
        pixmap.dispose();
    }
}
