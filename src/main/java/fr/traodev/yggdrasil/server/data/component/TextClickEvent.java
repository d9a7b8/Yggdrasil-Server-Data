package fr.traodev.yggdrasil.server.data.component;

public enum TextClickEvent {
    OPEN_URL,
    OPEN_FILE,
    RUN_COMMAND,
    SUGGEST_COMMAND;

    private final String action = this.name().toLowerCase();

    public String action() {
        return this.action;
    }
}