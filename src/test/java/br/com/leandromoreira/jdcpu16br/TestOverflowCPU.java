package br.com.leandromoreira.jdcpu16br;

import static br.com.leandromoreira.jdcpu16br.CPU.A;
import static br.com.leandromoreira.jdcpu16br.CPU.B;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

public class TestOverflowCPU {

    private final CPU cpu = new CPU();
    private final Memory memory = cpu.memory();

    @Before
    public void setup() {
        cpu.reset();
    }

    @Test
    public void it_raises_overflow_on_sum() {
        cpu.setRegister(A, 0xFFFF);
        cpu.setRegister(B, 0x1);
        memory.writeAt(0x0000, 0b000001_000000_0010);
        cpu.step();
        assertThat(cpu.getOverflow(), is(0x0001));
        assertThat(cpu.register(A), is(0x0000));
    }

    @Test
    public void it_raises_underflow_on_sub() {
        cpu.setRegister(A, 0x1);
        cpu.setRegister(B, 0x2);
        memory.writeAt(0x0000, 0b000001_000000_0011);
        cpu.step();
        assertThat(cpu.getOverflow(), is(0xFFFF));
        assertThat(cpu.register(A), is(0xFFFF));
    }
}
