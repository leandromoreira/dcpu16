package br.com.leandromoreira.jdcpu16br;

import static br.com.leandromoreira.jdcpu16br.OpCodes.*;

public class CPU {

    private static final int NUMBER_OF_INSTRUCTIONS = 0x10;
    private static final int NUMBER_OF_DECODERS = 0x40;
    private static final int MEMORY_SIZE = 0x10000;
    private static final int MASK_16BIT = 0xF;
    private static final int OxFFFF = 0xFFFF;
    private static final int WORD_SIZE = 16;
    private static final int ZERO = 0;
    private static final int ONE = 1;
    public static final int A = 0x0;
    public static final int B = 0x1;
    public static final int C = 0x2;
    public static final int X = 0x3;
    public static final int Y = 0x4;
    public static final int Z = 0x5;
    public static final int I = 0x6;
    public static final int J = 0x7;
    private static final int NEXT_WORD = 0x1F;
    private int[] memory = new int[MEMORY_SIZE];
    private int[] register = new int[0x8];
    private int programCounter, stackPointer;
    private int overflow;
    private Instruction[] instruction = new Instruction[NUMBER_OF_INSTRUCTIONS];
    private ParameterDecoder[] decoder = new ParameterDecoder[NUMBER_OF_DECODERS];

    public CPU() {
        programCounter = stackPointer = overflow = 0x0000;
        fillInstructionTable();
        fillParameterDecoder();
    }

    public int register(final int index) {
        return register[index];
    }

    private void fillParameterDecoder() {
        fillDirectParameter();
        fillIndirectParameter();
        decoder[NEXT_WORD] = new ParameterDecoder(NEXT_WORD) {

            @Override
            public void write(final int value) {
                throw new UnsupportedOperationException();
            }

            @Override
            public int read() {
                return readFromRAM(++programCounter);
            }
        };
    }

    private void fillDirectParameter() {
        for (int registerIndex = A; registerIndex <= J; registerIndex++) {
            decoder[registerIndex] = new ParameterDecoder(registerIndex) {

                @Override
                public void write(int value) {
                    register[index] = value;
                }

                @Override
                public int read() {
                    return register[index];
                }
            };
        }
    }

    private void fillIndirectParameter() {
        for (int registerIndex = 0x08; registerIndex <= 0x0F; registerIndex++) {
            decoder[registerIndex] = new ParameterDecoder(registerIndex) {

                @Override
                public void write(int value) {
                    writeAtRAM(register[index], value);
                }

                @Override
                public int read() {
                    return readFromRAM(register[index]);
                }
            };
        }
    }

    private void fillInstructionTable() {
        instruction[NOT_BASIC] = new DefaultInstruction() {

            private int cycles = 2;

            @Override
            public void execute(final Word parameter) {
                final int syscall = (parameter.instruction() >> 0x4) & 0x6;
                final int a = (parameter.instruction() >> (0x4 + 0x6));
                switch (syscall) {
                    case SYSCALL_JSR:
                        stackPointer = programCounter + ONE;
                        programCounter = register[a];
                        defaultSumToNextInstruction = ZERO;
                        break;
                }
            }

            @Override
            public int cycles() {
                return cycles + cost;
            }
        };
        instruction[SET] = new DefaultInstruction() {

            @Override
            public void execute(final Word parameter) {
                final ParameterDecoder a = decoder[parameter.a()];
                final ParameterDecoder b = decoder[parameter.b()];
                a.write(b.read());
            }
        };
        instruction[ADD] = new DefaultInstruction() {

            @Override
            public void execute(final Word parameter) {
                register[parameter.a()] += register[parameter.b()];
                overflow = ((register[parameter.a()] & MASK_16BIT) > ZERO) ? ONE : ZERO;
            }

            @Override
            public int cycles() {
                return 2 + cost;
            }
        };
        instruction[SUB] = new DefaultInstruction() {

            @Override
            public void execute(final Word parameter) {
                register[parameter.a()] -= register[parameter.b()];
                overflow = (register[parameter.a()] < ZERO) ? OxFFFF : ZERO;
            }

            @Override
            public int cycles() {
                return 2 + cost;
            }
        };
        instruction[MUL] = new DefaultInstruction() {

            @Override
            public void execute(final Word parameter) {
                register[parameter.a()] *= register[parameter.b()];
                overflow = ((register[parameter.a()] * register[parameter.b()]) >> WORD_SIZE) & OxFFFF;
            }

            @Override
            public int cycles() {
                return 2 + cost;
            }
        };
        instruction[DIV] = new DefaultInstruction() {

            @Override
            public void execute(final Word parameter) {
                if (register[parameter.b()] != ZERO) {
                    register[parameter.a()] /= register[parameter.b()];
                    overflow = ((register[parameter.a()] << WORD_SIZE) / register[parameter.b()]) & OxFFFF;
                } else {
                    overflow = ZERO;
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
                register[parameter.a()] = (register[parameter.b()] == ZERO) ? ZERO : register[parameter.a()] % register[parameter.b()];
            }

            @Override
            public int cycles() {
                return 3 + cost;
            }
        };
        instruction[SHL] = new DefaultInstruction() {

            @Override
            public void execute(final Word parameter) {
                register[parameter.a()] <<= register[parameter.b()];
                overflow = ((register[parameter.a()] << register[parameter.b()]) >> WORD_SIZE) & OxFFFF;
            }

            @Override
            public int cycles() {
                return 2 + cost;
            }
        };
        instruction[SHR] = new DefaultInstruction() {

            @Override
            public void execute(final Word parameter) {
                register[parameter.a()] >>= register[parameter.b()];
                overflow = ((register[parameter.a()] << WORD_SIZE) >> register[parameter.b()]) & OxFFFF;
            }

            @Override
            public int cycles() {
                return 2 + cost;
            }
        };
        instruction[AND] = new DefaultInstruction() {

            @Override
            public void execute(final Word parameter) {
                register[parameter.a()] &= register[parameter.b()];
            }
        };
        instruction[BOR] = new DefaultInstruction() {

            @Override
            public void execute(final Word parameter) {
                register[parameter.a()] |= register[parameter.b()];
            }
        };
        instruction[XOR] = new DefaultInstruction() {

            @Override
            public void execute(final Word parameter) {
                register[parameter.a()] ^= register[parameter.b()];
            }
        };
        instruction[IFE] = new DefaultInstruction() {

            @Override
            public void execute(final Word parameter) {
                if (register[parameter.a()] != register[parameter.b()]) {
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
                if (register[parameter.a()] == register[parameter.b()]) {
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
                if (register[parameter.a()] < register[parameter.b()]) {
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
                if (register[parameter.a()] > register[parameter.b()]) {
                    cost++;
                    defaultSumToNextInstruction = 0;
                }
            }

            @Override
            public int cycles() {
                return 2 + cost;
            }
        };
    }

    public void step() {
        final Word word = new Word(readFromRAM(programCounter));
        final Instruction currentInstruction = instruction[word.code()];
        currentInstruction.execute(word);
        programCounter += currentInstruction.sumToPC();
    }

    public int readFromRAM(final int address) {
        return memory[address];
    }

    public void writeAtRAM(final int address, final int value) {
        memory[address] = value;
    }

    public void setRegister(final int index, final int value) {
        register[index] = value;
    }

    private abstract class ParameterDecoder {

        protected final int index;

        public ParameterDecoder(final int index) {
            this.index = index;
        }

        public abstract void write(final int value);

        public abstract int read();
    }
}