package br.com.leandromoreira.jdcpu16br.cpu;

import static br.com.leandromoreira.jdcpu16br.cpu.OpCodes.NOT_BASIC;
import br.com.leandromoreira.jdcpu16br.io.Memory;
import br.com.leandromoreira.jdcpu16br.misc.HexadecimalUtil;

public class CPU {

    public static final int A = 0x0;
    public static final int B = 0x1;
    public static final int C = 0x2;
    public static final int X = 0x3;
    public static final int Y = 0x4;
    public static final int Z = 0x5;
    public static final int I = 0x6;
    public static final int J = 0x7;
    private final HexadecimalUtil formatter;
    private int[] register;
    private int programCounter, stackPointer, extraExcess, interruptAddress;
    private int overflow;
    private final Instruction[] instructions;
    private final AddressModeDecoder[] decoders;
    private final Memory memory;
    private AddressModeDecoder a, b;
    private Word currentWord;
    private Instruction currentInstruction;
    private int currentCycleCost;

    public CPU() {
        formatter = new HexadecimalUtil();
        memory = new Memory();
        instructions = new InstructionTable().instructionSet(this);
        decoders = new AddressModeDecoders(this).all();
        reset();
    }

    public final void reset() {
        stackPointer = programCounter = overflow = 0x0000;
        register = new int[0x8];
        memory.clear();
    }

    public Instruction[] getInstructions() {
        return instructions;
    }

    public Word getCurrentWord() {
        return currentWord;
    }

    public AddressModeDecoder parameterA() {
        return a;
    }

    public AddressModeDecoder parameterB() {
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

    public void setProgramCounter(final int programCounter) {
        this.programCounter = programCounter;
    }

    public int getStackPointer() {
        return stackPointer;
    }

    public int popStackPointer() {
        if (stackPointer != 0) {
            return stackPointer++;
        } else {
            stackPointer = 0xFFFF;
            return stackPointer;
        }
    }

    public int pushStackPointer() {
        if (stackPointer == 0x0000) {
            stackPointer = 0xFFFF;
            return stackPointer;
        }
        return stackPointer-- & 0xFFFF;
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

    public int getCurrentCycleCost() {
        return currentCycleCost;
    }

    public String step() {
        final int currentProgramCounter = programCounter;
        currentWord = new Word(memory.readFrom(programCounter));
        currentInstruction = instructions[currentWord.code()];

        decodeParameters();

        try {
            currentInstruction.execute();
            programCounter += sumToPC(currentInstruction);
        } catch (SkipSumPCException e) {
            //maybe another logic to it!
        }


        return assemblerFor(currentProgramCounter);
    }

    private void decodeParameters() {
        a = decoderFor(currentWord.a());
        b = decoderFor(currentWord.b());
        a.setMyProgramCounter(programCounter);
        b.setMyProgramCounter(programCounter + a.size());
    }

    private String assemblerFor(final int currentProgramCounter) {
        currentCycleCost = currentInstruction.cycles() + a.extraCycles() + b.extraCycles();
        if (currentWord.code() != NOT_BASIC) {
            return String.format("%s: %s %s, %s", formatter.toHexa4Spaces(currentProgramCounter), currentWord, a, b);
        } else {
            return String.format("%s: %s", formatter.toHexa4Spaces(currentProgramCounter), currentInstruction);
        }
    }

    private int sumToPC(final Instruction instruction) {
        return (instruction.sumToPC() != 0) ? instruction.sumToPC() + a.size() + b.size() : 0;
    }

    public AddressModeDecoder decoderFor(final int value) {
        return decoders[value];
    }
}
