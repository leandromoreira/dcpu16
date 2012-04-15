package br.com.leandromoreira.jdcpu16br.cpu;

import br.com.leandromoreira.jdcpu16br.cpu.OpCodes;
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

    @Test(expected = AssertionError.class)
    public void it_throws_illegal_argument_for_higher_values() {
        assertThat(OpCodes.toString(0x1F), is("NO WAY"));
    }

    @Test(expected = AssertionError.class)
    public void it_throws_illegal_argument_for_negative_values() {
        assertThat(OpCodes.toString(-0x01), is("ARE YOU KIDDING?"));
    }

    @Test
    public void it_returns_string_representation_of_bytecode_syscalls() {
        assertThat(OpCodes.syscallToString(0x01), is("SYSCALL_JSR"));
    }

    @Test(expected = AssertionError.class)
    public void it_throws_illegal_argument_for_syscall_higher_values() {
        assertThat(OpCodes.syscallToString(0x1F), is("NO WAY"));
    }

    @Test(expected = AssertionError.class)
    public void it_throws_illegal_argument_for_syscall_negative_values() {
        assertThat(OpCodes.syscallToString(-0x01), is("ARE YOU KIDDING?"));
    }
}
