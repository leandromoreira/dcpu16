package br.com.leandromoreira.jdcpu16br.cpu;

import br.com.leandromoreira.jdcpu16br.cpu.Word;
import static br.com.leandromoreira.jdcpu16br.cpu.OpCodes.SET;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

public class TestWord {

    public static final int WORD = 0b011111_000000_0001;
    private Word instruction;

    @Before
    public void setup() {
        instruction = new Word(WORD);
    }

    @Test
    public void returns_instruction() {
        assertThat(instruction.instruction(), is(WORD));
    }

    @Test
    public void returns_opcode() {
        assertThat(instruction.code(), is(SET));
    }

    @Test
    public void returns_a() {
        assertThat(instruction.a(), is(0b000000));
    }

    @Test
    public void returns_b() {
        assertThat(instruction.b(), is(0b011111));
    }
}
