package de.jellshock.network;

public enum Maps {
    FLAT("FLAT"),
    RANDOM("RANDOM");

    private final String name;

    Maps(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Maps getByName(String name) {
        for(Maps map : values()) {
            if(map.name.equalsIgnoreCase(name)) return map;
        }
        return null;
    }
}
