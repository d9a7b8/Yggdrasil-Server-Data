package fr.traodev.yggdrasil.server.data.world.chunk;

import fr.traodev.yggdrasil.server.data.nbt.objects.defaults.TagCompound;

public record ChunkSection(int y, byte[] blocks, byte[] data, byte[] blockLight, byte[] skyLight) {
    public ChunkSection(final TagCompound tag) {
        this(tag.getByte("Y"), tag.getByteArray("Blocks"), tag.getByteArray("Data"),
                tag.getByteArray("BlockLight"), tag.getByteArray("SkyLight"));
    }
}