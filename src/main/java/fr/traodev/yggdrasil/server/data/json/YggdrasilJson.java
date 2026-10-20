package fr.traodev.yggdrasil.server.data.json;

import fr.traodev.yggdrasil.server.data.json.interfaces.Jsonnable;
import fr.traodev.yggdrasil.server.data.json.interfaces.ValueAdder;

import java.util.*;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.stream.Stream;

public final class YggdrasilJson {
    private static final Object NULL_JSON = "null";
    private static final char QUOTE = '"';
    private static final char COLON = ':';
    private static final char COMMA = ',';
    private static final char ARRAY_START = '[';
    private static final char ARRAY_END = ']';
    private static final char JSON_START = '{';
    private static final char JSON_END = '}';
    private static final String JSON_FORMAT = "{%s}";
    private static final String KEY_FORMAT = "\"%s\":";

    private static final String ARRAY_BAD_TERMINATION = "Array not properly terminated";
    private static final String MAP_BAD_TERMINATION = "Map not properly terminated";
    private static final String BAD_STRING = "Input is not a valid JSON string";
    private static final String BAD_STRING_SEQUENCE = "Invalid escape sequence";

    private final StringBuilder builder;
    private boolean putComma;

    public YggdrasilJson() {
        this.builder = new StringBuilder();
        this.putComma = false;
    }

    public YggdrasilJson(final String json) {
        this.builder = new StringBuilder();
        this.putComma = false;
        load(json);
    }

    public void clear() {
        builder.setLength(0);
        putComma = false;
    }

    public void trim() {
        boolean inString = false;
        for (int i = 0; i < builder.length(); i++) {
            final char c = builder.charAt(i);
            if (c == QUOTE) {
                inString = !inString;
            } else if (!inString && (c == ' ' || c == '\n' || c == '\t' || c == '\r')) {
                builder.deleteCharAt(i--);
            }
        }
    }

    public String build() {
        return String.format(JSON_FORMAT, builder);
    }

    private YggdrasilJson add(final String value) {
        builder.append(QUOTE).append(value).append(QUOTE);
        return this;
    }

    private YggdrasilJson add(final char value) {
        builder.append(value);
        return this;
    }

    private <T> void addValue(final T value, final ValueAdder<T> adder) {
        adder.apply(value);
    }

    public void adder(final Object value) {
        putComma = true;
        builder.append(value == null ? NULL_JSON : value);
    }

    public void adder(final String value) {
        putComma = true;
        if (value == null) {
            builder.append(NULL_JSON);
        } else {
            add(value);
        }
    }

    public void adder(final UUID value) {
        adder(value.toString());
    }

    public void adder(final Jsonnable value) {
        if (value == null) {
            putComma = true;
            builder.append(NULL_JSON);
        } else {
            putComma = false;
            builder.append(JSON_START);
            value.toJson(this);
            builder.append(JSON_END);
        }
    }

    public void safeAdder(final String value) {
        putComma = true;
        if (value == null) {
            builder.append(NULL_JSON);
            return;
        }
        builder.append(QUOTE);
        final var valueLength = value.length();
        for (int i = 0; i < valueLength; i++) {
            final var c = value.charAt(i);
            switch (c) {
                case '\\':
                    builder.append("\\\\");
                    break;
                case '"':
                    builder.append("\\\"");
                    break;
                case '/':
                    builder.append("\\/");
                    break;
                case '\b':
                    builder.append("\\b");
                    break;
                case '\f':
                    builder.append("\\f");
                    break;
                case '\n':
                    builder.append("\\n");
                    break;
                case '\r':
                    builder.append("\\r");
                    break;
                case '\t':
                    builder.append("\\t");
                    break;
                default:
                    if (c < 0x20 || c > 0x7E) {
                        builder.append(String.format("\\u%04X", (int) c));
                    } else {
                        builder.append(c);
                    }
                    break;
            }
        }
        builder.append(QUOTE);
    }

    public <T> void put(final String key, final T value, final ValueAdder<T> adder) {
        if (putComma) {
            add(COMMA);
        }
        add(key).add(COLON).addValue(value, adder);
    }

    public <T> void putArray(final String key, final T[] array, final ValueAdder<T> adder) {
        if (putComma) {
            add(COMMA);
        }
        add(key).add(COLON).add(ARRAY_START);
        if (array.length > 0) {
            addValue(array[0], adder);
            for (int i = 1; i < array.length; i++) {
                add(COMMA).addValue(array[i], adder);
            }
        }
        add(ARRAY_END);
    }

    public <T> void putIterator(final String key, final Iterator<T> iterator, final ValueAdder<T> adder) {
        if (putComma) {
            add(COMMA);
        }
        add(key).add(COLON).add(ARRAY_START);
        if (iterator.hasNext()) {
            addValue(iterator.next(), adder);
            while (iterator.hasNext()) {
                add(COMMA).addValue(iterator.next(), adder);
            }
        }
        add(ARRAY_END);
    }

    public <T> void putIterable(final String key, final Iterable<T> iterable, final ValueAdder<T> adder) {
        this.putIterator(key, iterable.iterator(), adder);
    }

    public <T> void putStream(final String key, final Stream<T> stream, final ValueAdder<T> adder) {
        this.putIterator(key, stream.iterator(), adder);
    }

    public <T> void putMap(final String key, final Map<String, T> map, final ValueAdder<T> valueAdder) {
        if (putComma) {
            add(COMMA);
        }
        add(key).add(COLON).add(JSON_START);
        final var iterator = map.entrySet().iterator();
        if (iterator.hasNext()) {
            final var entry = iterator.next();
            add(entry.getKey()).add(COLON).addValue(entry.getValue(), valueAdder);
            while (iterator.hasNext()) {
                final var next = iterator.next();
                add(COMMA).add(next.getKey()).add(COLON).addValue(next.getValue(), valueAdder);
            }
        }
        add(JSON_END);
    }

    public void load(final String json) {
        builder.append(json, 1, json.length() - 1);
    }

    private int getStartIndex(final String key) {
        final var keyFormatted = String.format(KEY_FORMAT, key);
        final var index = builder.indexOf(keyFormatted);
        return index + keyFormatted.length();
    }

    public String stringPopper(final String value) {
        return value.substring(1, value.length() - 1);
    }

    public String safeStringPopper(final String value) {
        if (value == null || value.length() < 2 || value.charAt(0) != '"' || value.charAt(value.length() - 1) != '"') {
            throw new IllegalArgumentException(BAD_STRING);
        }

        StringBuilder result = new StringBuilder();
        final var valueLength = value.length() - 1;

        for (int i = 1; i < valueLength; i++) {
            final var c = value.charAt(i);

            if (c == '\\') {
                if (i + 1 >= valueLength) {
                    throw new IllegalArgumentException(BAD_STRING_SEQUENCE);
                }
                final var next = value.charAt(++i);
                switch (next) {
                    case '"': result.append('"'); break;
                    case '\\': result.append('\\'); break;
                    case '/': result.append('/'); break;
                    case 'b': result.append('\b'); break;
                    case 'f': result.append('\f'); break;
                    case 'n': result.append('\n'); break;
                    case 'r': result.append('\r'); break;
                    case 't': result.append('\t'); break;
                    case 'u':
                        if (i + 4 >= valueLength) {
                            throw new IllegalArgumentException(BAD_STRING_SEQUENCE);
                        }
                        String hex = value.substring(i + 1, i + 5);
                        result.append((char) Integer.parseInt(hex, 16));
                        i += 4;
                        break;
                    default:
                        throw new IllegalArgumentException(BAD_STRING_SEQUENCE);
                }
            } else {
                result.append(c);
            }
        }

        return result.toString();
    }

    public UUID uuidPopper(final String value) {
        return UUID.fromString(stringPopper(value));
    }

    public <T> T pop(final String key, final Function<String, T> mapper) {
        var start = getStartIndex(key);
        var i = start;
        for (; i < builder.length(); i++) {
            final var c = builder.charAt(i);
            if (c == COMMA || c == JSON_END) {
                break;
            }
        }
        final var value = builder.substring(start, i);
        builder.delete(0, i);
        if (!builder.isEmpty()) {
            builder.deleteCharAt(0);
        }
        if (value.equals(NULL_JSON)) {
            return null;
        }
        return mapper.apply(value);
    }

    private <T> T parseElement(String element, Function<String, T> mapper) {
        if (NULL_JSON.equals(element)) {
            return null;
        }
        return mapper.apply(element);
    }

    public <T> T[] popArray(final String key, final IntFunction<T[]> creator, final Function<String, T> mapper) {
        final Collection<T> collection = popCollection(key, mapper);

        return collection.toArray(creator.apply(collection.size()));
    }

    public <T> Collection<T> popCollection(final String key, final Function<String, T> mapper) {
        var start = getStartIndex(key) + 1;
        final var list = new ArrayList<T>();

        if (builder.charAt(start) == ARRAY_END) {
            builder.deleteCharAt(0);
            return list;
        }

        var isString = false;
        var jsonCount = 0;
        var arrayCount = 1;

        for (int i = start; i < builder.length(); i++) {
            final var c = builder.charAt(i);
            switch (c) {
                case QUOTE -> isString = !isString;
                case JSON_START -> {
                    if (!isString) {
                        jsonCount++;
                    }
                }
                case JSON_END -> {
                    if (!isString) {
                        jsonCount--;
                    }
                }
                case ARRAY_START -> {
                    if (!isString) {
                        arrayCount++;
                    }
                }
                case ARRAY_END -> {
                    if (!isString && --arrayCount == 0 && jsonCount == 0) {
                        list.add(parseElement(builder.substring(start, i), mapper));
                        builder.delete(0, i + 1);
                        return list;
                    }
                }
                case COMMA -> {
                    if (!isString && jsonCount == 0 && arrayCount == 1) {
                        list.add(parseElement(builder.substring(start, i), mapper));
                        start = i + 1;
                    }
                }
            }
        }

        throw new IllegalStateException(ARRAY_BAD_TERMINATION);
    }

    public <T> Map<String, T> popMap(final String key, final Function<String, T> mapper) {
        var start = getStartIndex(key) + 1;
        final Map<String, T> map = new LinkedHashMap<>();

        if (builder.charAt(start) == JSON_END) {
            builder.deleteCharAt(0);
            return map;
        }

        var isString = false;
        var jsonCount = 1;
        var arrayCount = 0;
        String currentKey = null;

        for (int i = start; i < builder.length(); i++) {
            final char c = builder.charAt(i);
            switch (c) {
                case QUOTE -> isString = !isString;
                case JSON_END -> {
                    if (!isString) {
                        if (--jsonCount == 0 && arrayCount == 0) {
                            final String valuePart = builder.substring(start, i);
                            map.put(currentKey, parseElement(valuePart, mapper));
                            builder.delete(0, i + 1);
                            return map;
                        }
                    }
                }
                case JSON_START -> {
                    if (!isString) {
                        jsonCount++;
                    }
                }
                case ARRAY_START -> {
                    if (!isString) {
                        arrayCount++;
                    }
                }
                case ARRAY_END -> {
                    if (!isString) {
                        arrayCount--;
                    }
                }
                case COLON -> {
                    if (!isString && currentKey == null && jsonCount == 1 && arrayCount == 0) {
                        currentKey = builder.substring(start + 1, i - 1);
                        start = i + 1;
                    }
                }
                case COMMA -> {
                    if (!isString && currentKey != null && jsonCount == 1 && arrayCount == 0) {
                        final String valuePart = builder.substring(start, i);
                        map.put(currentKey, parseElement(valuePart, mapper));
                        currentKey = null;
                        start = i + 1;
                    }
                }
            }
        }

        throw new IllegalStateException(MAP_BAD_TERMINATION);
    }
}