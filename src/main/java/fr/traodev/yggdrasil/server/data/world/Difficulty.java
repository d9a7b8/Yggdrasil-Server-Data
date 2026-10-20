package fr.traodev.yggdrasil.server.data.world;

public enum Difficulty {
    PEACEFUL,
    EASY,
    NORMAL,
    HARD;

    private static final Difficulty[] VALUES = values();

    private final int id = this.ordinal();

    public static Difficulty fromId(final int id) {
        return VALUES[id];
    }

    public int id() {
        return id;
    }
}