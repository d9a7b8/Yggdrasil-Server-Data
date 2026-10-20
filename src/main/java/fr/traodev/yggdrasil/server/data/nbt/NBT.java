package fr.traodev.yggdrasil.server.data.nbt;

import fr.traodev.yggdrasil.server.data.nbt.objects.defaults.TagCompound;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import me.steinborn.libdeflate.CompressionType;
import me.steinborn.libdeflate.LibdeflateCompressor;
import me.steinborn.libdeflate.LibdeflateDecompressor;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.zip.DataFormatException;

public final class NBT {
    private static final FileSystem FILE_SYSTEM = FileSystems.getDefault();

    private static final String UNSUPPORTED_INSTANTIATION = "This class cannot be instantiated";
    private static final String MALFORMED_COMPRESSED_DATA = "Malformed NBT Compressed data";
    private static final String INVALID_FILE_DATA = "Invalid file data";

    private NBT() {
        throw new UnsupportedOperationException(UNSUPPORTED_INSTANTIATION);
    }

    private static int getGzipUncompressedSize(final byte[] data) {
        if (data.length < 4) {
            throw new IllegalArgumentException(MALFORMED_COMPRESSED_DATA);
        }

        int len = data.length;
        return (data[len - 4] & 0xFF) |
                ((data[len - 3] & 0xFF) << 8) |
                ((data[len - 2] & 0xFF) << 16) |
                ((data[len - 1] & 0xFF) << 24);
    }

    private static byte[] decompressGzip(final byte[] data) {
        final var uncompressedSize = getGzipUncompressedSize(data);
        final var bytes = new byte[uncompressedSize];

        try (final var decompressor = new LibdeflateDecompressor()) {
            decompressor.decompress(data, bytes, CompressionType.GZIP, uncompressedSize);
        } catch (final DataFormatException e) {
            throw new IllegalArgumentException(MALFORMED_COMPRESSED_DATA);
        }

        return bytes;
    }

    private static ByteBuffer decompressZlib(final byte[] data) {
        final var inBuffer = ByteBuffer.wrap(data);
        final var outBuffer = ByteBuffer.allocateDirect(data.length * 128);

        try (final var decompressor = new LibdeflateDecompressor()) {
            decompressor.decompressUnknown(inBuffer, outBuffer, CompressionType.ZLIB);
        } catch (final DataFormatException e) {
            throw new IllegalArgumentException(MALFORMED_COMPRESSED_DATA);
        }

        outBuffer.flip();
        return outBuffer;
    }

    private static ByteBuf decompress(final byte[] data, final CompressionType compressionType) {
        if (compressionType == null) {
            return Unpooled.wrappedBuffer(data);
        }
        return switch (compressionType) {
            case GZIP -> Unpooled.wrappedBuffer(decompressGzip(data));
            case ZLIB -> Unpooled.wrappedBuffer(decompressZlib(data));
            case DEFLATE -> null;
        };
    }

    private static byte[] compress(final byte[] data, final CompressionType compressionType) {
        final var inBuffer = ByteBuffer.wrap(data);
        final ByteBuffer outBuffer;

        try (final var compressor = new LibdeflateCompressor()) {
            outBuffer = ByteBuffer.allocateDirect((int) compressor.getCompressBound(data.length, compressionType));
            compressor.compress(inBuffer, outBuffer, compressionType);
        }

        outBuffer.flip();
        final var outData = new byte[outBuffer.remaining()];
        outBuffer.get(outData);
        return outData;
    }

    private static TagCompound parse(final ByteBuf buffer) {
        final var compound = new TagCompound();
        compound.parse(buffer);

        return compound;
    }

    private static CompressionType getCompressionType(final byte[] data) {
        if (data.length < 2) {
            return null;
        }
        if (data[0] == (byte) 0x1F && data[1] == (byte) 0x8B) {
            return CompressionType.GZIP;
        }
        if (data[0] == (byte) 0x78 && (data[1] & 0x20) == 0) {
            return CompressionType.ZLIB;
        }
        return null;
    }

    public static TagCompound parse(final byte[] data) {
        if (data.length == 0) {
            return new TagCompound();
        }

        final var compressionType = getCompressionType(data);
        final var buffer = decompress(data, compressionType);
        if (buffer == null) {
            throw new IllegalArgumentException(MALFORMED_COMPRESSED_DATA);
        }

        final var compound = parse(buffer);

        buffer.release();
        return compound;
    }

    public static TagCompound parse(final String file) {
        try {
            return parse(Files.readAllBytes(FILE_SYSTEM.getPath(file)));
        } catch (final IOException e) {
            throw new IllegalArgumentException(INVALID_FILE_DATA);
        }
    }

    public static byte[] write(final TagCompound compound, final CompressionType compressionType) {
        final var buffer = Unpooled.buffer();
        compound.write(buffer);

        final var data = new byte[buffer.readableBytes()];
        buffer.readBytes(data);
        buffer.release();

        return compressionType != null ? compress(data, compressionType) : data;
    }

    public static void write(final TagCompound compound, final String file, final CompressionType compressionType) {
        try {
            Files.write(FILE_SYSTEM.getPath(file), write(compound, compressionType));
        } catch (final IOException e) {
            throw new IllegalArgumentException(INVALID_FILE_DATA);
        }
    }

}