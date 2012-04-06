package br.com.leandromoreira.jdcpu16br;

import static br.com.leandromoreira.jdcpu16br.CPU.A;
import static br.com.leandromoreira.jdcpu16br.CPU.B;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

public class TestCPU {

    private CPU cpu;
    private Memory memory;

    @Before
    public void setup() {
        cpu = new CPU();
        memory = cpu.memory();
    }

    @Test
    public void it_performs_set_a_to_direct_registers() {
        cpu.setRegister(A, 0xF);
        cpu.setRegister(B, 0xA);
        memory.writeAt(0x0000, 0b000001_000000_0001);
        cpu.step();
        assertThat(cpu.register(A), is(cpu.register(B)));
    }

    @Test
    public void it_performs_set_a_to_indirect_registers() {
        cpu.setRegister(A, 0x0005);
        memory.writeAt(0x0005, 0x4);
        memory.writeAt(0x0000, 0b001000_000000_0001);
        cpu.step();
        assertThat(cpu.register(A), is(0x4));
    }

    @Test
    public void it_performs_set_a_to_next_word() {
        memory.writeAt(0x0000, 0x7C01);
        memory.writeAt(0x0001, 0x0030);
        cpu.step();
        assertThat(cpu.register(A), is(0x0030));
    }

    @Test
    public void it_performs_add_a_to_b() {
        cpu.setRegister(A, 0x5);
        cpu.setRegister(B, 0x5);
        memory.writeAt(0x0000, 0b000001_000000_0010);
        cpu.step();
        assertThat(cpu.register(A), is(0x5+0x5));
    }
}
