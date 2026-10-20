package fr.traodev.yggdrasil.server.data.nbt.objects.defaults;

import fr.traodev.yggdrasil.server.data.nbt.TagType;
import fr.traodev.yggdrasil.server.data.nbt.objects.TagObject;
import io.netty.buffer.ByteBuf;

public final class TagIntArray extends TagObject<int[]> {
    private int[] value;

    public TagIntArray() {
        super(TagType.INT_ARRAY);
    }

    @Override
    public int[] get() {
        return value;
    }

    @Override
    public void set(final int[] object) {
        this.value = object;
    }

    @Override
    public void parse(final ByteBuf buffer) {
        final int length = buffer.readInt();
        this.value = new int[length];
        for (int i = 0; i < length; i++) {
            this.value[i] = buffer.readInt();
        }
    }

    @Override
    public void write(final ByteBuf buffer) {
        buffer.writeInt(value.length);
        for (int i : value) {
            buffer.writeInt(i);
        }
    }
}