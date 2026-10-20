package fr.traodev.yggdrasil.server.data.component;

public enum TextHoverEvent {
    SHOW_TEXT,
    SHOW_ACHIEVEMENT,
    SHOW_ITEM;

    private final String action = this.name().toLowerCase();

    public String action() {
        return this.action;
    }
}