package de.jellshock.game.ui.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import de.jellshock.game.player.Player;
import de.jellshock.game.rendering.IRenderConsumer;
import de.jellshock.game.screen.game.GameScreen;
import de.jellshock.game.ui.HudElement;
import lombok.Getter;

@Getter
public class StrengthTriangle extends HudElement implements IRenderConsumer<SpriteBatch>, Disposable {

    private final Vector2 position;
    private final Pixmap triangle;
    private Texture triangleTexture;
    private final Player player;

    public static final int DEFAULT_STRENGTH = 40;
    public static final int LENGTH_MULTIPLIER = 50;
    public static final float TRIANGLE_WIDTH = 0.5F; // in radians

    public static final int RESOLUTION = 200;
    public static final int DISPLAY_WIDTH = 600;

    private double angle = 0;
    private double power = 0;

    public StrengthTriangle(GameScreen gameScreen, Player player) {
        super(gameScreen);
        this.player = player;

        position = new Vector2();

        triangle = new Pixmap(RESOLUTION, RESOLUTION, Pixmap.Format.RGBA8888);
        triangle.setColor(Color.RED);
        
        triangleTexture = new Texture(triangle);

        Vector2 pos = player.getTank().getParentPosition();
        updatePosition(pos.x, pos.y);
    }

    private void draw() {
        triangle.setColor(0);
        triangle.fill();

        float angleOffset = TRIANGLE_WIDTH / 2;
        int radius = RESOLUTION / 2;


        triangle.setColor(1, 1, 1, 0.4f);

        final double PI2 = Math.PI * 2;

        for(int y = -radius; y < radius; y++) {
            for(int x = -radius; x < radius; x++) {
                if(x*x + y*y > (radius * radius) * power) continue;

                double currentAngle = (Math.atan2(y, x) + PI2) % PI2;

                double boundA = (angle - angleOffset);
                double boundB = (angle + angleOffset);

                boolean isEdgeA = boundA < 0;
                boolean isEdgeB = boundB > PI2;

                boolean normalA = currentAngle > boundA;
                boolean normalB = currentAngle < boundB;

                boolean edgeA = currentAngle - PI2 > boundA;
                boolean edgeB = currentAngle + PI2 < boundB;

                if(normalA && normalB || isEdgeA && edgeA || isEdgeB && edgeB)
                    triangle.drawPixel(x + radius, -y + radius);
            }
        }

        if (triangleTexture == null) triangleTexture = new Texture(triangle);
        triangleTexture.draw(triangle, 0, 0);
    }

    public void setAngle(double angle) {
        if(this.angle != angle) {
            this.angle = angle;
            draw();
        }
    }

    public void setPower(double power) {
        if(this.power != power) {
            this.power = power;
            draw();
        }
    }

    public void updatePosition(float x, float y) {
        this.position.x = x - DISPLAY_WIDTH / 2F;
        this.position.y = y - DISPLAY_WIDTH / 2F;
    }

    public void updatePosition(Vector2 pos) {
        updatePosition(pos.x, pos.y);
    }


    @Override
    public void render(SpriteBatch spriteBatch) {
        updatePosition(player.getTank().getParentPosition());
        spriteBatch.draw(triangleTexture, position.x, position.y, DISPLAY_WIDTH, DISPLAY_WIDTH);
    }

    @Override
    public void dispose() {
        triangle.dispose();
        triangleTexture.dispose();
    }
}
