package br.com.leandromoreira.jdcpu16br;

public abstract class DefaultInstruction implements Instruction {

    protected int cost;
    protected int defaultSumToNextInstruction = 1;

    @Override
    public abstract void execute(final Word opcode);

    @Override
    public int cycles() {
        return 1;
    }

    @Override
    public int sumToPC() {
        return defaultSumToNextInstruction;
    }
}
