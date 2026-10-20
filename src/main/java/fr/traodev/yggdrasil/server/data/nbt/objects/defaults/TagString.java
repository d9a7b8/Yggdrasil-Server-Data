package fr.traodev.yggdrasil.server.data.nbt.objects.defaults;

import fr.traodev.yggdrasil.server.data.nbt.TagType;
import fr.traodev.yggdrasil.server.data.nbt.objects.TagObject;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;

import java.nio.charset.StandardCharsets;

public final class TagString extends TagObject<String> {
    private String value;

    public TagString() {
        super(TagType.STRING);
    }

    @Override
    public String get() {
        return value;
    }

    @Override
    public void set(final String object) {
        this.value = object;
    }

    @Override
    public void parse(final ByteBuf buffer) {
        final int length = buffer.readShort();
        this.value = buffer.readCharSequence(length, StandardCharsets.UTF_8).toString();
    }

    @Override
    public void write(final ByteBuf buffer) {
        buffer.writeShort(ByteBufUtil.utf8Bytes(value));
        buffer.writeCharSequence(value, StandardCharsets.UTF_8);
    }

}