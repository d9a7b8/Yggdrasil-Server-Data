package fr.traodev.yggdrasil.server.data.world.chunk;

import fr.traodev.yggdrasil.server.data.nbt.TagType;
import fr.traodev.yggdrasil.server.data.nbt.objects.defaults.TagCompound;
import fr.traodev.yggdrasil.server.data.nbt.objects.defaults.TagList;

import java.util.List;

public record ChunkData(int xPos, int zPos, long lastUpdate, long inhabitedTime, int terrainPopulated,
                        int lightPopulated, int[] heightMap, byte[] biomes, List<TagCompound> sections,
                        List<TagCompound> tileEntities, List<TagCompound> entities) {

    public ChunkData(TagCompound tag) {
        this(tag.getCompound("").getCompound("Level"),
                tag.getCompound("").getCompound("Level").getList("Sections"),
                tag.getCompound("").getCompound("Level").getList("TileEntities"),
                tag.getCompound("").getCompound("Level").getList("Entities"));
    }

    private ChunkData(TagCompound tag, TagList<TagCompound> compoundSections, TagList<TagCompound> tileEntities,
                      TagList<TagCompound> entities) {
        this(tag.getInt("xPos"), tag.getInt("zPos"), tag.getLong("LastUpdate"), tag.getLong("InhabitedTime"),
                tag.getByte("TerrainPopulated"), tag.getByte("LightPopulated"), tag.getIntArray("HeightMap"),
                tag.getByteArray("Biomes"), compoundSections.get(), tileEntities.get(), entities.get());
    }

    public List<ChunkSection> parsedSections() {
        return sections.stream().map(ChunkSection::new).toList();
    }

    public TagCompound save() {
        final var level = new TagCompound();
        level.setInt("xPos", xPos);
        level.setInt("zPos", zPos);
        level.setLong("LastUpdate", lastUpdate);
        level.setLong("InhabitedTime", inhabitedTime);
        level.setByte("TerrainPopulated", (byte) terrainPopulated);
        level.setByte("LightPopulated", (byte) lightPopulated);
        level.setIntArray("HeightMap", heightMap);
        level.setByteArray("Biomes", biomes);

        level.setList("Sections", sections, TagType.COMPOUND);
        level.setList("TileEntities", tileEntities, TagType.COMPOUND);
        level.setList("Entities", entities, TagType.COMPOUND);

        final var tag = new TagCompound();
        tag.setCompound("Level", level);

        final var root = new TagCompound();
        root.setCompound("", tag);

        return root;
    }
}