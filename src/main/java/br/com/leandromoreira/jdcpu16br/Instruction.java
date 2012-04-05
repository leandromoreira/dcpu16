package br.com.leandromoreira.jdcpu16br;

public interface Instruction {
    void execute(final Word opcode);
    int cycles();
    int sumToPC();
}
