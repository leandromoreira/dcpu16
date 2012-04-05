package br.com.leandromoreira.jdcpu16br;

public interface Instruction {
    void execute(final OpCode opcode);
    int cycles();
    int sumToPC();
}
