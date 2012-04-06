package br.com.leandromoreira.jdcpu16br;

public abstract class ParameterDecoder {

    protected final int index;

    public ParameterDecoder(final int index) {
        this.index = index;
    }

    public abstract void write(final int value);

    public abstract int read();
}