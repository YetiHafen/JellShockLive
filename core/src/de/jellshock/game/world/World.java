package de.jellshock.game.world;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import lombok.Getter;

import java.io.File;
import java.util.Random;

@Getter
public class World implements Disposable {

    private final Pixmap pixmap;
    private Texture texture;
    private final Random random;

    private final int width;
    private final int height;
    private final int waveLength;
    private final float frequency;
    private final int amplitude;

    public World(int width, int height, int waveLength, int amplitude) {
        this.width = width;
        this.height = height;
        this.waveLength = waveLength;
        this.frequency = 1.0f / waveLength;
        this.amplitude = amplitude;
        pixmap = new Pixmap(width, height, Pixmap.Format.RGB888);
        random = new Random();
    }

    public void generateWorld() {
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
            pixmap.setColor(Color.RED);
            pixmap.drawPixel(x, (int) y);

            for (int j = 0; j < height; j++) {
                if (j < y) {
                    pixmap.setColor(Color.LIGHT_GRAY);
                    pixmap.drawPixel(x, j);
                }
            }
        }

        PixmapIO.writePNG(new FileHandle(new File("test.png")), pixmap);
        texture = new Texture(pixmap);
    }

    public void renderWorld(SpriteBatch batch) {
        batch.draw(texture, 0, 0, width, height);
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
