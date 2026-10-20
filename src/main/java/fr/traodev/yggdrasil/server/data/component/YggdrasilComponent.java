package fr.traodev.yggdrasil.server.data.component;

import fr.traodev.yggdrasil.server.data.json.YggdrasilJson;
import fr.traodev.yggdrasil.server.data.json.interfaces.Jsonnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class YggdrasilComponent implements Jsonnable {
    private static final String EMPTY_STRING = "";

    private final List<YggdrasilComponent> children;

    private TextEvent event;
    private String baseText;
    private String baseObjective;

    private TextColor color;

    private boolean bold;
    private boolean italic;
    private boolean underlined;
    private boolean strikethrough;
    private boolean obfuscated;

    private String insertion;

    private TextHoverEvent hoverEvent;
    private String hoverValue;

    private TextClickEvent clickEvent;
    private String clickValue;

    public YggdrasilComponent() {
        this(EMPTY_STRING);
    }

    public YggdrasilComponent(final String baseText) {
        this.event = TextEvent.TEXT;
        this.baseText = baseText;
        this.children = new ArrayList<>();
    }

    public YggdrasilComponent text(final String text) {
        this.event = TextEvent.TEXT;
        this.baseText = text;
        return this;
    }

    public YggdrasilComponent translate(final String key) {
        this.event = TextEvent.TRANSLATE;
        this.baseText = key;
        return this;
    }

    public YggdrasilComponent score(final String playerName, final String objectiveName) {
        this.event = TextEvent.SCORE;
        this.baseText = playerName;
        this.baseObjective = objectiveName;
        return this;
    }

    public YggdrasilComponent selector(final MinecraftSelector selector) {
        this.event = TextEvent.SELECTOR;
        this.baseText = selector.selector();
        return this;
    }

    public YggdrasilComponent addExtra(final YggdrasilComponent component) {
        this.children.add(component);
        return this;
    }

    public YggdrasilComponent bold(final boolean bold) {
        this.bold = bold;
        return this;
    }

    public YggdrasilComponent italic(final boolean italic) {
        this.italic = italic;
        return this;
    }

    public YggdrasilComponent underlined(final boolean underlined) {
        this.underlined = underlined;
        return this;
    }

    public YggdrasilComponent strikethrough(final boolean strikethrough) {
        this.strikethrough = strikethrough;
        return this;
    }

    public YggdrasilComponent obfuscated(final boolean obfuscated) {
        this.obfuscated = obfuscated;
        return this;
    }

    public YggdrasilComponent color(final TextColor color) {
        this.color = color;
        return this;
    }

    public YggdrasilComponent insertion(final String insertion) {
        this.insertion = insertion;
        return this;
    }

    public YggdrasilComponent hoverEvent(final TextHoverEvent event, final String value) {
        this.hoverEvent = event;
        this.hoverValue = value;
        return this;
    }

    public YggdrasilComponent clickEvent(final TextClickEvent event, final String value) {
        this.clickEvent = event;
        this.clickValue = value;
        return this;
    }

    @Override
    public void toJson(final YggdrasilJson json) {
        switch (this.event) {
            case TEXT -> json.put("text", this.baseText, json::safeAdder);
            case TRANSLATE -> json.put("translate", this.baseText, json::adder);
            case SCORE -> json.putMap("score", Map.of("name", this.baseText, "objective", this.baseObjective), json::adder);
            case SELECTOR -> json.put("selector", this.baseText, json::adder);
        }

        if (!this.children.isEmpty()) {
            json.putIterable("extra", children, json::adder);
        }

        if (this.bold) {
            json.put("bold", true, json::adder);
        }

        if (this.italic) {
            json.put("italic", true, json::adder);
        }

        if (this.underlined) {
            json.put("underlined", true, json::adder);
        }

        if (this.strikethrough) {
            json.put("strikethrough", true, json::adder);
        }

        if (this.obfuscated) {
            json.put("obfuscated", true, json::adder);
        }

        if (this.color != null) {
            json.put("color", this.color.color(), json::adder);
        }

        if (this.insertion != null) {
            json.put("insertion", this.insertion, json::adder);
        }

        if (this.hoverEvent != null && this.hoverValue != null) {
            json.putMap("hoverEvent", Map.of("action", this.hoverEvent.action(), "value", this.hoverValue), json::adder);
        }

        if (this.clickEvent != null && this.clickValue != null) {
            json.putMap("clickEvent", Map.of("action", this.clickEvent.action(), "value", this.clickValue), json::adder);
        }
    }

    public String build() {
        final var json = new YggdrasilJson();
        this.toJson(json);

        return json.build();
    }

}