package br.com.leandromoreira.jdcpu16br.cpu;

public abstract class AddressModeDecoder {

    protected final int index;
    protected String representation;
    private int extraCycles;
    private int myProgramCounter;
    private static final String[] defaultMapping = new String[0x40];

    static {
        defaultMapping[0x18] = "POP";
        defaultMapping[0x19] = "PEEK";
        defaultMapping[0x1A] = "PUSH";
        defaultMapping[0x1B] = "SP";
        defaultMapping[0x1C] = "PC";
        defaultMapping[0x1D] = "O";
    }
    

    public AddressModeDecoder(final int index) {
        this.representation = defaultMapping[index];
        this.index = index;
    }

    public int getMyProgramCounter() {
        return myProgramCounter;
    }

    public void setMyProgramCounter(final int myProgramCounter) {
        this.myProgramCounter = myProgramCounter;
    }

    public int size() {
        return 0;
    }
    
    public int extraCycles(){
        return extraCycles;
    }

    public abstract void write(final int value);

    public abstract int read();

    @Override
    public String toString() {
        return representation;
    }
}