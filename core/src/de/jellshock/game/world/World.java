package de.jellshock.game.world;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Disposable;
import lombok.Getter;

import java.util.Random;

@Getter
public class World implements Disposable {

    private final Pixmap pixmap;
    private Texture texture;
    private final ShapeRenderer shapeRenderer;
    private final Random random;

    private final int width;
    private final int height;
    private final int waveLength;
    private final float frequency;
    private final int amplitude;

    float z;

    public World(int width, int height, int waveLength, int amplitude) {
        this.width = width;
        this.height = height;
        this.waveLength = waveLength;
        this.frequency = 1.0f / waveLength;
        this.amplitude = amplitude;
        pixmap = new Pixmap(width, height, Pixmap.Format.RGB888);
        shapeRenderer = new ShapeRenderer();
        random = new Random();
    }

    int i;
    int y;

    public void generateWorld() {

        // Random value while using LCG


        texture = new Texture(pixmap);
    }

    public void renderWorld(SpriteBatch batch) {
        /*batch.draw(texture, 0, 0, width, height);*/

        float randomState = random.nextFloat() * 0xFFFFFFFF - 1;
        float y = height / 2f;

        float a = random.nextFloat();
        float b = random.nextFloat();

        for (int i = 0; i < width; i++) {
            if (!(i % waveLength == 0)) {
                y = height / 2f + interpolate(a, b, (i % waveLength) * 1.0f / (float) waveLength) * amplitude;
            } else {
                a = b;
                b = random.nextFloat();
                y = height / 2f + (a * amplitude);
            }
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.rect(i, y, 1, 1);
            shapeRenderer.end();
            /*pixmap.setColor(Color.BLACK);
            pixmap.drawPixel(i, (int) y);*/
        }


    }

    private float random() {
        z = (1664525 * z + 1) % 4294967296f;
        return z / 4294967296f - 0.5f;
    }

    private float interpolate(float leftPosition, float rightPosition, float relativeDistance) {
        // calc the weight of the two texture values (leftPosition, rightPosition)
        float scaledValue = relativeDistance * (float) Math.PI;
        // weight of the two texture values of the relative position
        float weight = (1 - (float) Math.cos(scaledValue)) * 0.5f;
        return leftPosition * (1 - weight) + rightPosition * weight;
    }

    @Override
    public void dispose() {
        texture.dispose();
        pixmap.dispose();
        shapeRenderer.dispose();
    }
}
