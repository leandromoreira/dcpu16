package br.com.leandromoreira.jdcpu16br.io;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class ROMLoader {

    private ByteBuffer binaryROMBuffer;

    public ROMLoader load(final File rom) {
        checkNotNull(rom);
        checkState(rom.exists());
        final FileChannel fileChannel;
        try {
            fileChannel = new RandomAccessFile(rom, "r").getChannel();
            try {
                binaryROMBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0, (int) fileChannel.size());
            } catch (final IOException ex) {
                throw new IllegalArgumentException("There was an io error while reading " + rom, ex);
            }
        } catch (final FileNotFoundException ex) {
            throw new IllegalArgumentException("File (" + rom + ") not found!", ex);
        }
        return this;
    }

    public void to(final Memory memory) {
        checkNotNull(memory);
        int address = 0x0000;
        while (binaryROMBuffer.hasRemaining()) {
            memory.writeAt(address++, littleEndianWord(binaryROMBuffer.get(), binaryROMBuffer.get()));
        }
    }

    private int littleEndianWord(byte first, byte second) {
        return ((toUnsignedByte(second) << 8) | toUnsignedByte(first));
    }

    private int toUnsignedByte(final byte value) {
        return value & 0xFF;
    }
}
