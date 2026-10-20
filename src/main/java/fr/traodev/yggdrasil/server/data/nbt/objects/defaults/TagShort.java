package fr.traodev.yggdrasil.server.data.nbt.objects.defaults;

import fr.traodev.yggdrasil.server.data.nbt.TagType;
import fr.traodev.yggdrasil.server.data.nbt.objects.TagObject;
import io.netty.buffer.ByteBuf;

public final class TagShort extends TagObject<Short> {
    private short value;

    public TagShort() {
        super(TagType.SHORT);
    }

    @Override
    public Short get() {
        return value;
    }

    @Override
    public void set(final Short object) {
        this.value = object;
    }

    @Override
    public void parse(final ByteBuf buffer) {
        this.value = buffer.readShort();
    }

    @Override
    public void write(final ByteBuf buffer) {
        buffer.writeShort(value);
    }

}