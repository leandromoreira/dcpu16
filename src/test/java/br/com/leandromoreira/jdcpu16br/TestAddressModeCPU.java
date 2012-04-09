package br.com.leandromoreira.jdcpu16br;

import static br.com.leandromoreira.jdcpu16br.CPU.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

public class TestAddressModeCPU {

    private final CPU cpu = new CPU();
    private final Memory memory = cpu.memory();

    @Before
    public void setup() {
        cpu.reset();
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
    public void it_performs_set_a_to_value_of_the_address_pointed_by_next_word_plus_register() {
        cpu.setRegister(A, 0x0005);
        cpu.setRegister(B, 0x0002);
        memory.writeAt(0x0000, 0b010001_000000_0001);
        memory.writeAt(0x0001, 0x0002);
        memory.writeAt(0x0004, 0xC0DE);
        cpu.step();
        assertThat(cpu.register(A), is(0xC0DE));
    }

    @Test
    public void it_performs_push_and_pop() {
        memory.writeAt(0x0000, 0b100010_011010_0001);
        memory.writeAt(0x0001, 0b011000_000011_0001);
        cpu.step();
        cpu.step();
        assertThat(cpu.register(X), is(0x02));
    }

    @Test
    public void it_performs_peek() {
        memory.writeAt(0xFFFF, 0xCAFE);
        memory.writeAt(0x0000, 0b011001_000000_0001);
        cpu.step();
        assertThat(cpu.register(A), is(0xCAFE));
    }

    @Test
    public void it_performs_write_to_sp_o_pc() {
        cpu.setStackPointer(0x1);
        cpu.setOverflow(0x2);

        memory.writeAt(0x0000, 0b011011_000000_0001);
        memory.writeAt(0x0001, 0b011101_000001_0001);
        memory.writeAt(0x0002, 0b011100_000010_0001);

        cpu.step();
        cpu.step();
        cpu.step();
        
        assertThat(cpu.register(A), is(0x1));
        assertThat(cpu.register(B), is(0x2));
        assertThat(cpu.register(C), is(0x2));
    }

    @Test
    public void it_performs_set_a_to_next_word() {
        memory.writeAt(0x0000, 0x7C01);
        memory.writeAt(0x0001, 0x0030);
        cpu.step();
        assertThat(cpu.register(A), is(0x0030));
    }
}
