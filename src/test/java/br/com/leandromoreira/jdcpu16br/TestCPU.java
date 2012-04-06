package br.com.leandromoreira.jdcpu16br;

import org.junit.Test;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static br.com.leandromoreira.jdcpu16br.OpCodes.*;
import static br.com.leandromoreira.jdcpu16br.CPU.*;
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
    public void it_performs_set() {
        cpu.writeAtRAM(0x0000, 0x7C01);
        cpu.writeAtRAM(0x0001, 0x0030);
        cpu.step();
        assertThat(cpu.register(A), is(0x0030));
    }
}
