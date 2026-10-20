package fr.traodev.yggdrasil.server.data.nbt;

import fr.traodev.yggdrasil.server.data.nbt.objects.*;
import fr.traodev.yggdrasil.server.data.nbt.objects.defaults.*;

public enum TagType {
    END(null),
    BYTE(TagByte::new),
    SHORT(TagShort::new),
    INT(TagInt::new),
    LONG(TagLong::new),
    FLOAT(TagFloat::new),
    DOUBLE(TagDouble::new),
    BYTE_ARRAY(TagByteArray::new),
    STRING(TagString::new),
    LIST(TagList::new),
    COMPOUND(TagCompound::new),
    INT_ARRAY(TagIntArray::new),
    LONG_ARRAY(TagLongArray::new);

    private static final TagType[] VALUES = values();

    private final int id = ordinal();
    private final TagCreator<?> creator;

    <T> TagType(TagCreator<T> creator) {
        this.creator = creator;
    }

    public static TagType fromId(final int id) {
        return VALUES[id];
    }

    public int id() {
        return id;
    }

    public <T> TagObject<T> create() {
        return (TagObject<T>) creator.create();
    }
}