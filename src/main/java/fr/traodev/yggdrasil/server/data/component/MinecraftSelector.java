package fr.traodev.yggdrasil.server.data.component;

public enum MinecraftSelector {
    NEAREST_PLAYER("@p"),
    RANDOM_PLAYER("@r"),
    ALL_PLAYERS("@a"),
    ALL_ENTITIES("@e");

    private final String selector;

    MinecraftSelector(final String selector) {
        this.selector = selector;
    }

    public String selector() {
        return this.selector;
    }
}