package br.com.leandromoreira.jdcpu16br.cpu;

public class Word {

    private final int code;
    private final int a;
    private final int b;
    private final int rawInstruction;

    public Word(final int rawInstruction) {
        this.rawInstruction = rawInstruction;
        code = rawInstruction & 0b11111;
        a = (rawInstruction >> 0xA);
        b = (rawInstruction >> 0x5) & 0b11111;
    }

    public Word(final int a,final int b,final int opCode) {
        this.a = a;
        this.b = b;
        code = opCode;
        rawInstruction = (a << 10 | (b << 5 | opCode));
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

    public int rawInstruction() {
        return rawInstruction;
    }

    @Override
    public String toString() {
        return OpCodes.toString(code);
    }
}
