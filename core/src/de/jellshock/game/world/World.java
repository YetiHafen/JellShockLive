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

    private final int width;
    private final int height;
    private final int waveLength;
    private final float frequency;
    private final int amplitude;

    private boolean mapChanged = false;

    public World(int width, int height, int waveLength, int amplitude) {
        this.width = width;
        this.height = height;
        this.waveLength = waveLength;
        this.frequency = 1.0f / waveLength;
        this.amplitude = amplitude;
        this.worldType = WorldType.CUSTOM;
        pixmap = new Pixmap(width, height, Pixmap.Format.RGB888);
        random = new Random();
        worldMap = new int[width];
        color = new Color();
    }

    public World(int width, int height, WorldType worldType) {
        this.width = width;
        this.height = height;
        this.worldType = worldType;
        this.waveLength = worldType.getWaveLength();
        this.frequency = 1.0f / waveLength;
        this.amplitude = worldType.getAmplitude();
        pixmap = new Pixmap(width, height, Pixmap.Format.RGB888);
        random = new Random();
        worldMap = new int[width];
        color = new Color();
    }

    public void generateWorld() {
        mapChanged = true;
        float a = random.nextFloat();
        float b = random.nextFloat();

        for (int x = 0; x < width; x++) {
            float y;
            if (!(x % waveLength == 0)) {
                y = height / 2f + interpolate(a, b, (x % waveLength) * 1.0f / (float) waveLength) * amplitude;
            } else {
                a = b;
                b = random.nextFloat();
                y = height / 2f + (a * amplitude);
            }
            worldMap[x] = (int) (height - y);
        }
    }

    private void renderWorld() {
        mapChanged = false;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int worldValue = height - worldMap[x];
                if (y < worldValue) {
                    pixmap.setColor(Color.LIGHT_GRAY);
                    pixmap.drawPixel(x, y);
                } else {
                    float t = x / (float) width;
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

    public void setHeight(int x, int height) {
        mapChanged = true;
        assert height <= this.height;
        worldMap[x] = height;
    }

    public int getHeight(int x) {
        return worldMap[x];
    }

    public void render(SpriteBatch batch) {
        if (mapChanged) renderWorld();
        batch.draw(texture, 0, 0, width, height);
    }

    public void checkScreenBuffer(int width, int height) {
        if (!(width > this.width)) return;
        int widthDifference = (width - this.width) / 2;
        Pixmap leftBuffer = new Pixmap(widthDifference, this.height, Pixmap.Format.RGB888);
        Pixmap rightBuffer = new Pixmap(widthDifference, this.height, Pixmap.Format.RGB888);

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
