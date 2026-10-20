package fr.traodev.yggdrasil.server.data.world;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

public final class MCA {
    private static final String UNSUPPORTED_INSTANCIATION = "This class cannot be instantiated";

    private MCA() {
        throw new UnsupportedOperationException(UNSUPPORTED_INSTANCIATION);
    }

    public static List<ChunkSettings> readMcaHeader(final String filePath) throws IOException {
        try (final var file = new RandomAccessFile(filePath, "r")) {
            final var header = new byte[4096];
            file.readFully(header);

            final var chunks = new ArrayList<ChunkSettings>();
            for (int i = 0; i < 1024; i++) {
                final var id = i * 4;
                final var offset = ((header[id] & 0xFF) << 16) | ((header[id + 1] & 0xFF) << 8) | (header[id + 2] & 0xFF);
                final var size = (header[id + 3] & 0xFF);

                if (offset != 0 && size != 0) {
                    chunks.add(new ChunkSettings(offset, size));
                }
            }

            return chunks;
        }
    }

    public static ChunkHeader extractChunkHeader(final String filePath, final ChunkSettings header) throws IOException {
        try (final var file = new RandomAccessFile(filePath, "r")) {
            file.seek(header.offset() * 4096);

            final var compressedSize = file.readInt();
            final var compressionFormat = file.readByte() & 0xFF;

            final var chunkData = new byte[compressedSize * 2];
            file.readFully(chunkData, 0, compressedSize);

            return new ChunkHeader(compressedSize, compressionFormat, chunkData);
        }
    }

}