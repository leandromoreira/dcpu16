package br.com.leandromoreira.jdcpu16br;

import static br.com.leandromoreira.jdcpu16br.CPU.A;
import static br.com.leandromoreira.jdcpu16br.CPU.B;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

public class TestCPU {

    private CPU cpu;

    @Before
    public void setput() {
        cpu = new CPU();
    }

    @Test
    public void it_handles_RAM_io() {
        cpu.writeAtRAM(0x0000, 0x7C01);
        assertThat(cpu.readFromRAM(0x0000), is(0x7C01));
    }
    @Test
    public void it_performs_set_a_to_registers() {
        cpu.setRegister(A,0xF);
        cpu.setRegister(B,0xA);
        cpu.writeAtRAM(0x0000, 0b000001_000000_0001);
        cpu.step();
        assertThat(cpu.register(A), is(cpu.register(B)));
    }
    
    @Test
    public void it_performs_set_a_to_next_word() {
        cpu.writeAtRAM(0x0000, 0x7C01);
        cpu.writeAtRAM(0x0001, 0x0030);
        cpu.step();
        assertThat(cpu.register(A), is(0x0030));
    }
}
