package br.com.leandromoreira.jdcpu16br;

public interface Instruction {

    void execute();

    int cycles();

    int sumToPC();
}
