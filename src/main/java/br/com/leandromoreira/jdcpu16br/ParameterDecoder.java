package br.com.leandromoreira.jdcpu16br;

public abstract class ParameterDecoder {

    protected final int index;
    private int myProgramCounter;

    public ParameterDecoder(final int index) {
        this.index = index;
    }

    public int getMyProgramCounter() {
        return myProgramCounter;
    }

    public void setMyProgramCounter(int myProgramCounter) {
        this.myProgramCounter = myProgramCounter;
    }

    public int size() {
        return 0;
    }

    public abstract void write(final int value);

    public abstract String type();

    public abstract int read();

    @Override
    public String toString() {
        return type();
    }
}