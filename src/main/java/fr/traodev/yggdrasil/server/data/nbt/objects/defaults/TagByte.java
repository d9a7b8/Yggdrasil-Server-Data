package fr.traodev.yggdrasil.server.data.nbt.objects.defaults;

import fr.traodev.yggdrasil.server.data.nbt.TagType;
import fr.traodev.yggdrasil.server.data.nbt.objects.TagObject;
import io.netty.buffer.ByteBuf;

public final class TagByte extends TagObject<Byte> {
    private byte value;

    public TagByte() {
        super(TagType.BYTE);
    }

    @Override
    public Byte get() {
        return value;
    }

    @Override
    public void set(final Byte object) {
        this.value = object;
    }

    @Override
    public void parse(final ByteBuf buffer) {
        this.value = buffer.readByte();
    }

    @Override
    public void write(final ByteBuf buffer) {
        buffer.writeByte(value);
    }

}