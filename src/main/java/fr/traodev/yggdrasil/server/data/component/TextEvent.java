package fr.traodev.yggdrasil.server.data.component;

public enum TextEvent {
    TEXT,
    TRANSLATE,
    SCORE,
    SELECTOR;

    private final String action = this.name().toLowerCase();

    public String action() {
        return this.action;
    }
}