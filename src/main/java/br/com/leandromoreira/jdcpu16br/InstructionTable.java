package br.com.leandromoreira.jdcpu16br;

import static br.com.leandromoreira.jdcpu16br.OpCodes.*;

public class InstructionTable {

    private static final int NUMBER_OF_INSTRUCTIONS = 0x10;
    private static final int ZERO = 0;
    private static final int ONE = 1;

    public Instruction[] instructionSet(final CPU cpu) {
        final Instruction[] instruction = new Instruction[NUMBER_OF_INSTRUCTIONS];

        /*
         * instruction[NOT_BASIC] = new DefaultInstruction() {
         *
         * private int cycles = 2;
         *
         * @Override public void execute(final Word parameter) { final int
         * syscall = (parameter.instruction() >> 0x4) & 0x6; final int a =
         * (parameter.instruction() >> (0x4 + 0x6)); switch (syscall) { case
         * SYSCALL_JSR: cpu.setStackPointer(cpu.getProgramCounter() + ONE);
         * cpu.setProgramCounter(cpu.register(a)); defaultSumToNextInstruction =
         * ZERO; break; } }
         *
         * @Override public int cycles() { return cycles + cost; }
        };
         */
        instruction[SET] = new DefaultInstruction() {

            @Override
            public void execute(final Word parameter) {
                cpu.parameterA().write(cpu.parameterB().read());
            }
        };
        instruction[ADD] = new DefaultInstruction() {

            @Override
            public void execute(final Word parameter) {
                cpu.parameterA().write(cpu.parameterA().read() + cpu.parameterB().read());
                final int newOverflow = (cpu.parameterA().read() > 0xFFFF) ? 0x0001 : 0x0000;
                cpu.setOverflow(newOverflow);
            }

            @Override
            public int cycles() {
                return 2 + cost;
            }
        };
        instruction[SUB] = new DefaultInstruction() {

            @Override
            public void execute(final Word parameter) {
                cpu.parameterA().write(cpu.parameterA().read() - cpu.parameterB().read());
                final int newOverflow = (cpu.parameterA().read() < 0x0000) ? 0xFFFF : 0x0000;
                cpu.setOverflow(newOverflow);
            }

            @Override
            public int cycles() {
                return 2 + cost;
            }
        };
        instruction[MUL] = new DefaultInstruction() {

            @Override
            public void execute(final Word parameter) {
                cpu.parameterA().write(cpu.parameterA().read() * cpu.parameterB().read());
                cpu.setOverflow((cpu.parameterA().read() >> 16) & 0xFFF);
            }

            @Override
            public int cycles() {
                return 2 + cost;
            }
        };
        instruction[DIV] = new DefaultInstruction() {

            @Override
            public void execute(final Word parameter) {
                if (cpu.parameterB().read() != ZERO) {
                    cpu.parameterA().write(cpu.parameterA().read() / cpu.parameterB().read());
                    cpu.setOverflow(((cpu.parameterA().read() << 16) / cpu.parameterB().read()) & 0xFFFF);
                } else {
                    cpu.parameterA().write(ZERO);
                    cpu.setOverflow(ZERO);
                }
            }

            @Override
            public int cycles() {
                return 3 + cost;
            }
        };
        instruction[MOD] = new DefaultInstruction() {

            @Override
            public void execute(final Word parameter) {
                final int mod = (cpu.parameterB().read() == 0) ? ZERO : cpu.parameterA().read() % cpu.parameterB().read();
                cpu.parameterA().write(mod);
            }

            @Override
            public int cycles() {
                return 3 + cost;
            }
        };
        instruction[SHL] = new DefaultInstruction() {

            @Override
            public void execute(final Word parameter) {
                cpu.setOverflow(((cpu.parameterA().read() << cpu.parameterB().read()) >> 16) & 0xFFFF);
                cpu.parameterA().write(cpu.parameterA().read() << cpu.parameterB().read());
            }

            @Override
            public int cycles() {
                return 2 + cost;
            }
        };
        instruction[SHR] = new DefaultInstruction() {

            @Override
            public void execute(final Word parameter) {
                cpu.setOverflow(((cpu.parameterA().read() << 16) >> cpu.parameterB().read()) & 0xFFFF);
                cpu.parameterA().write(cpu.parameterA().read() >> cpu.parameterB().read());
            }

            @Override
            public int cycles() {
                return 2 + cost;
            }
        };
        instruction[AND] = new DefaultInstruction() {

            @Override
            public void execute(final Word parameter) {
                cpu.parameterA().write(cpu.parameterA().read() & cpu.parameterB().read());
            }
        };
        instruction[BOR] = new DefaultInstruction() {

            @Override
            public void execute(final Word parameter) {
                cpu.parameterA().write(cpu.parameterA().read() | cpu.parameterB().read());
            }
        };
        instruction[XOR] = new DefaultInstruction() {

            @Override
            public void execute(final Word parameter) {
                cpu.parameterA().write(cpu.parameterA().read() ^ cpu.parameterB().read());

            }
        };/*
        instruction[IFE] = new DefaultInstruction() {

            @Override
            public void execute(final Word parameter) {
                if (cpu.register(parameter.a()] != cpu.register(parameter.b()]) {
                    cost++;
                    defaultSumToNextInstruction = 0;
                }
            }

            @Override
            public int cycles() {
                return 2 + cost;
            }
        };
        instruction[IFN] = new DefaultInstruction() {

            @Override
            public void execute(final Word parameter) {
                if (cpu.register(parameter.a()] == cpu.register(parameter.b()]) {
                    cost++;
                    defaultSumToNextInstruction = 0;
                }
            }

            @Override
            public int cycles() {
                return 2 + cost;
            }
        };
        instruction[IFG] = new DefaultInstruction() {

            @Override
            public void execute(final Word parameter) {
                if (cpu.register(parameter.a()] < cpu.register(parameter.b()]) {
                    cost++;
                    defaultSumToNextInstruction = 0;
                }
            }

            @Override
            public int cycles() {
                return 2 + cost;
            }
        };
        instruction[IFB] = new DefaultInstruction() {

            @Override
            public void execute(final Word parameter) {
                if (cpu.register(parameter.a()] > cpu.register(parameter.b()]) {
                    cost++;
                    defaultSumToNextInstruction = 0;
                }
            }

            @Override
            public int cycles() {
                return 2 + cost;
            }
        };*/

        return instruction ;
}
}
