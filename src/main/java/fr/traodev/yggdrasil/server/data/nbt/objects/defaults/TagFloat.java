package fr.traodev.yggdrasil.server.data.nbt.objects.defaults;

import fr.traodev.yggdrasil.server.data.nbt.TagType;
import fr.traodev.yggdrasil.server.data.nbt.objects.TagObject;
import io.netty.buffer.ByteBuf;

public final class TagFloat extends TagObject<Float> {
    private float value;

    public TagFloat() {
        super(TagType.FLOAT);
    }

    @Override
    public Float get() {
        return value;
    }

    @Override
    public void set(final Float object) {
        this.value = object;
    }

    @Override
    public void parse(final ByteBuf buffer) {
        this.value = buffer.readFloat();
    }

    @Override
    public void write(final ByteBuf buffer) {
        buffer.writeFloat(value);
    }

}