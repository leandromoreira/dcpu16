package br.com.leandromoreira.jdcpu16br;

import static br.com.leandromoreira.jdcpu16br.OpCodes.SET;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

public class TestWord {

    public static final int WORD = 0b011111_000000_0001;
    private Word opCode;

    @Before
    public void setup() {
        opCode = new Word(WORD);
    }

    @Test
    public void returns_instruction() {
        assertThat(opCode.instruction(), is(WORD));
    }

    @Test
    public void returns_opcode() {
        assertThat(opCode.code(), is(SET));
    }

    @Test
    public void returns_a() {
        assertThat(opCode.a(), is(0b000000));
    }

    @Test
    public void returns_b() {
        assertThat(opCode.b(), is(0b011111));
    }
}
