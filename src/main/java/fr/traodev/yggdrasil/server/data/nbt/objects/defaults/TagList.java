package fr.traodev.yggdrasil.server.data.nbt.objects.defaults;

import fr.traodev.yggdrasil.server.data.nbt.TagType;
import fr.traodev.yggdrasil.server.data.nbt.objects.TagObject;
import io.netty.buffer.ByteBuf;

import java.util.ArrayList;
import java.util.List;

public final class TagList<T extends TagObject> extends TagObject<List<T>> {
    private TagType type;
    private List<T> value;

    public TagList() {
        super(TagType.LIST);
    }

    public TagList(final TagType type) {
        super(TagType.LIST);
        this.type = type;
    }

    public TagType getListType() {
        return type;
    }

    public List<?> getValues() {
        return value.stream().map(TagObject::get).toList();
    }

    @Override
    public List<T> get() {
        return value;
    }

    @Override
    public void set(final List<T> object) {
        this.value = object;
    }

    public void setValues(final List<?> values) {
        this.value = values.stream().map(tag -> {
            final var t = (T) type.create();
            t.set(tag);

            return t;
        }).toList();
    }

    @Override
    public void parse(final ByteBuf buffer) {
        this.type = TagType.fromId(buffer.readByte());
        final int size = buffer.readInt();
        this.value = new ArrayList<>(size);

        if (this.type == TagType.END) {
            return;
        }
        for (int i = 0; i < size; i++) {
            final var tag = this.type.create();
            tag.parse(buffer);
            value.add((T) tag);
        }
    }

    @Override
    public void write(final ByteBuf buffer) {
        buffer.writeByte(type.id());
        buffer.writeInt(value.size());

        if (this.type != TagType.END) {
            value.forEach(tag -> tag.write(buffer));
        }
    }

}