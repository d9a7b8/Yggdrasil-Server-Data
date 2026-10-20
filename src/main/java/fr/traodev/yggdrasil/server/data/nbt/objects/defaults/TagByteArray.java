package fr.traodev.yggdrasil.server.data.nbt.objects.defaults;

import fr.traodev.yggdrasil.server.data.nbt.TagType;
import fr.traodev.yggdrasil.server.data.nbt.objects.TagObject;
import io.netty.buffer.ByteBuf;

public final class TagByteArray extends TagObject<byte[]> {
    private byte[] value;

    public TagByteArray() {
        super(TagType.BYTE_ARRAY);
    }

    @Override
    public byte[] get() {
        return value;
    }

    @Override
    public void set(final byte[] object) {
        this.value = object;
    }

    @Override
    public void parse(final ByteBuf buffer) {
        final int length = buffer.readInt();
        this.value = new byte[length];
        buffer.readBytes(value);
    }

    @Override
    public void write(final ByteBuf buffer) {
        buffer.writeInt(value.length);
        buffer.writeBytes(value);
    }

}