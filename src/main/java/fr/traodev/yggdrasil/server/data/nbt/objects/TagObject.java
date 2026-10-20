package fr.traodev.yggdrasil.server.data.nbt.objects;

import fr.traodev.yggdrasil.server.data.nbt.TagType;
import io.netty.buffer.ByteBuf;

public abstract class TagObject<T> {
    private final TagType type;

    protected TagObject(final TagType type) {
        this.type = type;
    }

    public final TagType getType() {
        return type;
    }

    public abstract T get();

    public abstract void set(final T object);

    public abstract void parse(final ByteBuf buffer);

    public abstract void write(final ByteBuf buffer);

    @Override
    public String toString() {
        return get().toString();
    }
}