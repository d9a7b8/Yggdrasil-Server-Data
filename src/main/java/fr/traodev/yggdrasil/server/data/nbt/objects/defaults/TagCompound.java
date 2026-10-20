package fr.traodev.yggdrasil.server.data.nbt.objects.defaults;

import fr.traodev.yggdrasil.server.data.nbt.TagType;
import fr.traodev.yggdrasil.server.data.nbt.objects.TagObject;
import io.netty.buffer.ByteBuf;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class TagCompound extends TagObject<Map<String, TagObject<?>>> {
    private Map<String, TagObject<?>> tags = new HashMap<>();

    public TagCompound() {
        super(TagType.COMPOUND);
    }

    @Override
    public Map<String, TagObject<?>> get() {
        return tags;
    }

    @Override
    public void set(final Map<String, TagObject<?>> object) {
        this.tags = object;
    }

    @Override
    public void parse(final ByteBuf buffer) {
        final var stringTag = new TagString();

        while (buffer.isReadable()) {
            final byte type = buffer.readByte();
            if (type == 0) {
                break;
            }
            stringTag.parse(buffer);
            final String name = stringTag.get();
            final TagObject<?> tag = TagType.fromId(type).create();
            tag.parse(buffer);
            tags.put(name, tag);
        }
    }

    @Override
    public void write(final ByteBuf buffer) {
        final var stringTag = new TagString();

        tags.forEach((name, tag) -> {
            buffer.writeByte(tag.getType().id());
            stringTag.set(name);
            stringTag.write(buffer);
            tag.write(buffer);
        });
        buffer.writeByte(0);
    }

    public byte getByte(final String key) {
        return ((TagByte) tags.get(key)).get();
    }

    public short getShort(final String key) {
        return ((TagShort) tags.get(key)).get();
    }

    public int getInt(final String key) {
        return ((TagInt) tags.get(key)).get();
    }

    public long getLong(final String key) {
        return ((TagLong) tags.get(key)).get();
    }

    public float getFloat(final String key) {
        return ((TagFloat) tags.get(key)).get();
    }

    public double getDouble(final String key) {
        return ((TagDouble) tags.get(key)).get();
    }

    public byte[] getByteArray(final String key) {
        return ((TagByteArray) tags.get(key)).get();
    }

    public String getString(final String key) {
        return ((TagString) tags.get(key)).get();
    }

    public <T extends TagObject<?>> TagList<T> getList(final String key) {
        return (TagList<T>) tags.get(key);
    }

    public TagCompound getCompound(final String key) {
        return (TagCompound) tags.get(key);
    }

    public int[] getIntArray(final String key) {
        return ((TagIntArray) tags.get(key)).get();
    }

    public long[] getLongArray(final String key) {
        return ((TagLongArray) tags.get(key)).get();
    }

    public <T> TagObject<T> getTag(final String key) {
        return (TagObject<T>) tags.get(key);
    }

    public Object get(final String key) {
        return tags.get(key).get();
    }

    public void setByte(final String name, final byte value) {
        final var tag = new TagByte();
        tag.set(value);

        tags.put(name, tag);
    }

    public void setShort(final String name, final short value) {
        final var tag = new TagShort();
        tag.set(value);

        tags.put(name, tag);
    }

    public void setInt(final String name, final int value) {
        final var tag = new TagInt();
        tag.set(value);

        tags.put(name, tag);
    }

    public void setLong(final String name, final long value) {
        final var tag = new TagLong();
        tag.set(value);

        tags.put(name, tag);
    }

    public void setFloat(final String name, final float value) {
        final var tag = new TagFloat();
        tag.set(value);

        tags.put(name, tag);
    }

    public void setDouble(final String name, final double value) {
        final var tag = new TagDouble();
        tag.set(value);

        tags.put(name, tag);
    }

    public void setByteArray(final String name, final byte[] value) {
        final var tag = new TagByteArray();
        tag.set(value);

        tags.put(name, tag);
    }

    public void setString(final String name, final String value) {
        final var tag = new TagString();
        tag.set(value);

        tags.put(name, tag);
    }

    public <T> void setList(final String name, final List<T> value, final TagType type) {
        final var tag = new TagList<TagObject<T>>(type);
        tag.setValues(value);

        tags.put(name, tag);
    }

    public void setCompound(final String name, final TagCompound value) {
        tags.put(name, value);
    }

    public void setIntArray(final String name, final int[] value) {
        final var tag = new TagIntArray();
        tag.set(value);

        tags.put(name, tag);
    }

    public void setLongArray(final String name, final long[] value) {
        final var tag = new TagLongArray();
        tag.set(value);

        tags.put(name, tag);
    }

}