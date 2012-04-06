package br.com.leandromoreira.jdcpu16br;

public class Memory {

    private static final int MEMORY_SIZE = 0x10000;
    private final int[] memory = new int[MEMORY_SIZE];

    public int readFrom(final int address) {
        return memory[address];
    }

    public void writeAt(final int address, final int value) {
        memory[address] = value;
    }
}
