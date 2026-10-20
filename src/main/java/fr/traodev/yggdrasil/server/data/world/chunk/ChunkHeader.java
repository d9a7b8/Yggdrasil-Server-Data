package fr.traodev.yggdrasil.server.data.world.chunk;

public record ChunkHeader(int compressedSize, int compressionFormat, byte[] data) {
}