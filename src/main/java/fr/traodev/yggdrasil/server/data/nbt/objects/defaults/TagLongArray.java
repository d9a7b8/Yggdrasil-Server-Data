package fr.traodev.yggdrasil.server.data.nbt.objects.defaults;

import fr.traodev.yggdrasil.server.data.nbt.TagType;
import fr.traodev.yggdrasil.server.data.nbt.objects.TagObject;
import io.netty.buffer.ByteBuf;

public final class TagLongArray extends TagObject<long[]> {
    private long[] value;

    public TagLongArray() {
        super(TagType.LONG_ARRAY);
    }

    @Override
    public long[] get() {
        return value;
    }

    @Override
    public void set(final long[] object) {
        this.value = object;
    }

    @Override
    public void parse(final ByteBuf buffer) {
        final int length = buffer.readInt();
        this.value = new long[length];
        for (int i = 0; i < length; i++) {
            this.value[i] = buffer.readLong();
        }
    }

    @Override
    public void write(final ByteBuf buffer) {
        buffer.writeInt(value.length);
        for (final long l : value) {
            buffer.writeLong(l);
        }
    }

}