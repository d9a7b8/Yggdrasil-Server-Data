package fr.traodev.yggdrasil.server.data.nbt.objects.defaults;

import fr.traodev.yggdrasil.server.data.nbt.TagType;
import fr.traodev.yggdrasil.server.data.nbt.objects.TagObject;
import io.netty.buffer.ByteBuf;

public final class TagLong extends TagObject<Long> {
    private long value;

    public TagLong() {
        super(TagType.LONG);
    }

    @Override
    public Long get() {
        return value;
    }

    @Override
    public void set(final Long object) {
        this.value = object;
    }

    @Override
    public void parse(final ByteBuf buffer) {
        this.value = buffer.readLong();
    }

    @Override
    public void write(final ByteBuf buffer) {
        buffer.writeLong(value);
    }

}