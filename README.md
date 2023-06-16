# JellShockLive

## Server

### Database

Rename .env.example to .env and implement credentials 

## Map

### Generating Maps

Examples:

Level 1:
```java
for (int i = 0; i < Map.DEFAULT_MAP_SIZE; i++) {
        pixels[i] = (int) (Math.cos(((double) i / width) * 2 * Math.PI) * 200) + 400;
}
```

Level 4:
```java
for (int i = 0; i < Map.DEFAULT_MAP_SIZE; i++) {
    pixels[i] = (int) (2 * Math.cos(((double) i / width) * Math.PI * 5) * 100) + 300;
}
```