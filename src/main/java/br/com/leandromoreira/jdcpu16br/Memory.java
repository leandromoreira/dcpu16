package br.com.leandromoreira.jdcpu16br;

public class Memory {

    private static final int MEMORY_SIZE = 0x10000;
    private final int[] memory = new int[MEMORY_SIZE];
    private int maximumFilled = 0;

    public int readFrom(final int address) {
        return memory[address];
    }

    public void writeAt(final int address, final int value) {
        memory[address] = value;
    }

    private void clear() {
        if (!isEmpty()) {
            for (int address = 0x0000; address < MEMORY_SIZE; address++) {
                memory[address] = 0x0000;
            }
        }
    }

    public boolean isEmpty() {
        return maximumFilled == 0;
    }

    public void load(String[] hexadecimalCells) {
        if (hexadecimalCells != null) {
            clear();
            int address = 0x0000;
            for (final String oneValue : hexadecimalCells) {
                if (oneValue != null && !"".equals(oneValue.trim())) {
                    memory[address++] = Integer.valueOf(oneValue, 16);
                    ++maximumFilled;
                }
            }
        }
    }
}
