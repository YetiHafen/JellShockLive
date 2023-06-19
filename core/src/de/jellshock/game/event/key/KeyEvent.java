package de.jellshock.game.event.key;

import com.badlogic.gdx.Input;
import lombok.Getter;

@Getter
public class KeyEvent {

    public enum EventType {

        MOVE_LEFT(Input.Keys.A),
        MOVE_RIGHT(Input.Keys.D),
        GUN_ROTATION_LEFT(Input.Keys.LEFT),
        GUN_ROTATION_RIGHT(Input.Keys.RIGHT),
        GUN_POWER_UP(Input.Keys.UP),
        GUN_POWER_DOWN(Input.Keys.DOWN),
        ESCAPE(Input.Keys.ESCAPE),
        SHOT(Input.Keys.SPACE);

        private final int keyCode;

        EventType(int keyCode) {
            this.keyCode = keyCode;
        }

        public int getKeyCode() {
            return keyCode;
        }
    }

    private final EventType type;

    public KeyEvent(EventType eventType) {
        this.type = eventType;
    }

}
