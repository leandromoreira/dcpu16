package br.com.leandromoreira.jdcpu16br.cpu;

public class Word {

    private final int code;
    private final int a;
    private final int b;
    private final int instruction;

    public Word(final int rawInstruction) {
        instruction = rawInstruction;
        code = rawInstruction & 0b11111;
        a = (rawInstruction >> 0xA);
        b = (rawInstruction >> 0x5) & 0b11111;
    }

    public int code() {
        return code;
    }

    public int a() {
        return a;
    }

    public int b() {
        return b;
    }

    public int instruction() {
        return instruction;
    }

    @Override
    public String toString() {
        return OpCodes.toString(code);
    }
}
