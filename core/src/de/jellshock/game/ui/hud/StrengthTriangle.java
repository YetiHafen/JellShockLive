package de.jellshock.game.ui.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.bullet.collision.CollisionJNI;
import com.badlogic.gdx.utils.Disposable;
import de.jellshock.game.rendering.IRenderConsumer;
import de.jellshock.game.screen.game.GameScreen;
import de.jellshock.game.ui.HudElement;
import lombok.Getter;

@Getter
public class StrengthTriangle extends HudElement implements IRenderConsumer<SpriteBatch>, Disposable {

    private final Vector2 position;
    private final Pixmap triangle;
    private Texture triangleTexture;

    public static final int DEFAULT_STRENGTH = 40;
    public static final int LENGTH_MULTIPLIER = 50;
    public static final float TRIANGLE_WIDTH = 1F; // in radians

    private int strength = DEFAULT_STRENGTH;
    private double angle = 0;

    public StrengthTriangle(GameScreen gameScreen) {
        super(gameScreen);

        position = new Vector2();

        int strengthLength = strength * LENGTH_MULTIPLIER;

        triangle = new Pixmap(100, 100, Pixmap.Format.RGBA8888);
        triangle.setColor(Color.RED);
        
        triangleTexture = new Texture(triangle);
    }

    private void draw() {
        triangle.setColor(0);
        triangle.fill();

        float angleOffset = TRIANGLE_WIDTH / 2;
        int hWidth = triangle.getWidth() / 2;
        int hHeight = triangle.getHeight() / 2;

        triangle.setColor(0xFF0000FF);
        /*for(double d = angle - angleOffset; d < angle + angleOffset; d += 0.1) {
            double sin = Math.sin(d);
            double cos = Math.cos(d);

            int x = (int) (cos * hWidth + hWidth);
            int y = (int) (sin * hHeight + hHeight);
            System.out.println("y = " + y + " x = " + x);
            triangle.drawPixel(x, y);
        }
        //triangle.fill();*/



        for(int y = -hHeight; y < hHeight; y++) {
            for(int x = -hWidth; x < hWidth; x++) {
                if(x*x + y*y > hWidth * hWidth) continue;

                double currentAngle = (Math.atan2(y, x) + 2 * Math.PI) % (2*Math.PI);

                //if(angle > 0 && boundA < 0)
                //if(boundA < 0 && currentAngle - Math.PI * 2 < boundA) continue;


                //if(currentAngle > boundB && (boundA < boundB)) continue;

                //if(boundA < 0) currentAngle -= Math.PI * 2;

                //if(currentAngle < boundA && boundA > 0) continue;

                //if(centerAngle > this.angle + angleOffset) continue;
                //if(centerAngle < this.angle - angleOffset) continue;

                double boundA = (angle - angleOffset);// + 2 * Math.PI) % (Math.PI * 2);
                double boundB = (angle + angleOffset);// + 2 * Math.PI) % (Math.PI * 2);

                if(boundA < 0 && currentAngle > Math.PI)
                    boundA += Math.PI * 2;

                if(boundB > Math.PI * 2 && currentAngle < Math.PI)
                    boundB -= Math.PI * 2;


                if(currentAngle > boundA && currentAngle < boundB)
                    triangle.drawPixel(x + hWidth, -y + hHeight);
            }
        }


        triangleTexture.dispose();
        triangleTexture = new Texture(triangle);
    }

    public void updateStrength(int strength) {
        if(this.strength != strength) {
            this.strength = strength;
            draw();
        }
    }

    public void updateAngle(double angle) {
        if(this.angle != angle) {
            this.angle = angle;
            draw();
        }
    }

    public void updatePosition(Vector2 position) {
        System.out.println(position);
        this.position.x = position.x;
        this.position.y = position.y;
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.draw(triangleTexture, position.x, position.y);
    }

    @Override
    public void dispose() {
        triangle.dispose();
        triangleTexture.dispose();
    }
}
