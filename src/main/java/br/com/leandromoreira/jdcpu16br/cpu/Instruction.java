package br.com.leandromoreira.jdcpu16br.cpu;

public interface Instruction {

    void execute();

    int cycles();

    int sumToPC();
}
