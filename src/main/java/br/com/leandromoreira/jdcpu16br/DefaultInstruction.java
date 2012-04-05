package br.com.leandromoreira.jdcpu16br;

public abstract class DefaultInstruction implements Instruction {

    protected int cost;
    protected int defaultSumToNextInstruction = 1;

    public abstract void execute(final OpCode opcode);

    public int cycles() {
        return 1;
    }

    public int sumToPC() {
        return defaultSumToNextInstruction;
    }
}
