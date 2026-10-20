package fr.traodev.yggdrasil.server.data.nbt.objects.defaults;

import fr.traodev.yggdrasil.server.data.nbt.TagType;
import fr.traodev.yggdrasil.server.data.nbt.objects.TagObject;
import io.netty.buffer.ByteBuf;

public final class TagDouble extends TagObject<Double> {
    private double value;

    public TagDouble() {
        super(TagType.DOUBLE);
    }

    @Override
    public Double get() {
        return value;
    }

    @Override
    public void set(final Double object) {
        this.value = object;
    }

    @Override
    public void parse(final ByteBuf buffer) {
        this.value = buffer.readDouble();
    }

    @Override
    public void write(final ByteBuf buffer) {
        buffer.writeDouble(value);
    }

}