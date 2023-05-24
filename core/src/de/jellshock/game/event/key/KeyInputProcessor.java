package de.jellshock.game.event.key;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class KeyInputProcessor {

    private final KeyEventManager manager;

    public KeyInputProcessor(KeyEventManager manager) {
        this.manager = manager;
    }

    public void keyPressed() {
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            manager.dispatchEvent(new KeyEvent(KeyEvent.EventType.MOVE_LEFT));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            manager.dispatchEvent(new KeyEvent(KeyEvent.EventType.MOVE_RIGHT));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            manager.dispatchEvent(new KeyEvent(KeyEvent.EventType.GUN_ROTATION_LEFT));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            manager.dispatchEvent(new KeyEvent(KeyEvent.EventType.GUN_ROTATION_RIGHT));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            manager.dispatchEvent(new KeyEvent(KeyEvent.EventType.GUN_POWER_UP));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            manager.dispatchEvent(new KeyEvent(KeyEvent.EventType.GUN_POWER_DOWN));
        }
    }

}
