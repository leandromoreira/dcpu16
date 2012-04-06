package br.com.leandromoreira.jdcpu16br;

public class CPU {

    private static final int NUMBER_OF_INSTRUCTIONS = 0x10;
    private static final int NUMBER_OF_DECODERS = 0x40;
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
    private int[] register = new int[0x8];
    private int programCounter, stackPointer;
    private int overflow;
    private Instruction[] instruction = new Instruction[NUMBER_OF_INSTRUCTIONS];
    private ParameterDecoder[] decoder;
    private Memory memory = new Memory();
    private ParameterDecoder a, b;

    public CPU() {
        programCounter = stackPointer = overflow = 0x0000;
        instruction = new InstructionTableBuilder().instructionSet(this);
        decoder = new ParameterDecoderBuilder(this).all();
    }

    public ParameterDecoder parameterA() {
        return a;
    }

    public ParameterDecoder parameterB() {
        return b;
    }

    public Memory memory() {
        return memory;
    }

    public int getOverflow() {
        return overflow;
    }

    public void setOverflow(final int overflow) {
        this.overflow = overflow;
    }

    public int getProgramCounter() {
        return programCounter;
    }

    public int getNextProgramCounter() {
        return ++programCounter;
    }

    public void setProgramCounter(final int programCounter) {
        this.programCounter = programCounter;
    }

    public int getStackPointer() {
        return stackPointer;
    }

    public int getStackPointerAndIncrement() {
        final int sp = stackPointer;
        stackPointer++;
        return sp;
    }

    public int getStackPointerAndDecrement() {
        return --stackPointer;
    }

    public void setStackPointer(final int stackPointer) {
        this.stackPointer = stackPointer;
    }

    public int register(final int index) {
        return register[index];
    }

    public void setRegister(final int index, final int value) {
        register[index] = value;
    }

    public void step() {
        final Word word = new Word(memory.readFrom(programCounter));
        final Instruction currentInstruction = instruction[word.code()];
        decodeValuesParameter(word);
        currentInstruction.execute(word);
        programCounter += currentInstruction.sumToPC();
    }

    private void decodeValuesParameter(final Word parameter) {
        a = decoder[parameter.a()];
        b = decoder[parameter.b()];
    }
}