package de.jellshock.game.event.key;

import com.badlogic.gdx.Gdx;

import java.util.ArrayList;
import java.util.List;

public class KeyEventManager {

    private final List<KeyEventListener> listeners;

    public KeyEventManager() {
        listeners = new ArrayList<>();
    }

    public void registerKeyListener(KeyEventListener listener) {
        listeners.add(listener);
    }

    public void unregisterKeyListener(KeyEventListener listener) {
        listeners.remove(listener);
    }

    public void dispatchEvent(KeyEvent event) {
        listeners.forEach(listener -> listener.handleKeyEvent(event));
        Gdx.app.log("Key Event", event.getType() + " just pressed");
    }
}
