package de.jellshock.game.event.key;

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

    public KeyEvent dispatchEvent(KeyEvent event) {
        listeners.forEach(listener -> listener.handleKeyEvent(event));
        return event;
    }
}
