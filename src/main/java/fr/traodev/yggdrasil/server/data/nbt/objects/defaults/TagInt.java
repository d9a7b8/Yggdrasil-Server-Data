package fr.traodev.yggdrasil.server.data.nbt.objects.defaults;

import fr.traodev.yggdrasil.server.data.nbt.TagType;
import fr.traodev.yggdrasil.server.data.nbt.objects.TagObject;
import io.netty.buffer.ByteBuf;

public final class TagInt extends TagObject<Integer> {
    private int value;

    public TagInt() {
        super(TagType.INT);
    }

    @Override
    public Integer get() {
        return value;
    }

    @Override
    public void set(final Integer object) {
        this.value = object;
    }

    @Override
    public void parse(final ByteBuf buffer) {
        this.value = buffer.readInt();
    }

    @Override
    public void write(final ByteBuf buffer) {
        buffer.writeInt(value);
    }

}