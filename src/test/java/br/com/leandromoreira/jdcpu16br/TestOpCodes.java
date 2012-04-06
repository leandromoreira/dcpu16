package br.com.leandromoreira.jdcpu16br;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;

public class TestOpCodes {

    @Test
    public void it_returns_string_representation_of_bytecode() {
        assertThat(OpCodes.toString(0x01), is("SET"));
        assertThat(OpCodes.toString(0x04), is("MUL"));
        assertThat(OpCodes.toString(0x06), is("MOD"));
        assertThat(OpCodes.toString(0x0F), is("IFB"));
    }

    @Test(expected=IllegalArgumentException.class)
    public void it_throws_illegal_argument() {
        assertThat(OpCodes.toString(0x1F), is("NO WAY"));
    }
}
