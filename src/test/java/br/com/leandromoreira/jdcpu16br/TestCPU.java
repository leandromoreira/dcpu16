package br.com.leandromoreira.jdcpu16br;

import static br.com.leandromoreira.jdcpu16br.CPU.*;
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
        assertThat(cpu.register(A), is(0x5 + 0x5));
    }

    @Test
    public void when_there_is_no_oveflow_at_add() {
        cpu.setRegister(A, 0x5);
        cpu.setRegister(B, 0x5);
        memory.writeAt(0x0000, 0b000001_000000_0010);
        cpu.step();
        assertThat(cpu.getOverflow(), is(0x0000));
    }

    @Test
    public void when_there_is_an_oveflow_at_add() {
        cpu.setRegister(A, 0xFFFF);
        cpu.setRegister(B, 0x5);
        memory.writeAt(0x0000, 0b000001_000000_0010);
        cpu.step();
        assertThat(cpu.getOverflow(), is(0x0001));
    }

    @Test
    public void it_performs_sub_a_to_b() {
        cpu.setRegister(A, 0x10);
        cpu.setRegister(B, 0x5);
        memory.writeAt(0x0000, 0b000001_000000_0011);
        cpu.step();
        assertThat(cpu.register(A), is(0x10 - 0x5));
    }

    @Test
    public void when_there_is_no_underflow_at_sub() {
        cpu.setRegister(A, 0x5);
        cpu.setRegister(B, 0x5);
        memory.writeAt(0x0000, 0b000001_000000_0011);
        cpu.step();
        assertThat(cpu.getOverflow(), is(0x0000));
    }

    @Test
    public void when_there_is_an_underflow_at_sub() {
        cpu.setRegister(A, 0x5);
        cpu.setRegister(B, 0x6);
        memory.writeAt(0x0000, 0b000001_000000_0011);
        cpu.step();
        assertThat(cpu.getOverflow(), is(0xFFFF));
    }

    @Test
    public void it_performs_mul_a_to_b() {
        cpu.setRegister(A, 0x10);
        cpu.setRegister(B, 0x5);
        memory.writeAt(0x0000, 0b000001_000000_0100);
        cpu.step();
        assertThat(cpu.register(A), is(0x10 * 0x5));
    }

    @Test
    public void mul_sets_overflow() {
        cpu.setRegister(A, 0x5);
        cpu.setRegister(B, 0x5);
        memory.writeAt(0x0000, 0b000001_000000_0100);
        cpu.step();
        assertThat(cpu.getOverflow(), is(((0x5 * 0x5) >> 16) & 0xFFFF));
    }

    @Test
    public void it_performs_div_a_to_b() {
        cpu.setRegister(A, 0x10);
        cpu.setRegister(B, 0x5);
        memory.writeAt(0x0000, 0b000001_000000_0101);
        cpu.step();
        assertThat(cpu.register(A), is(0x10 / 0x5));
        assertThat(cpu.getOverflow(), is(((cpu.register(A) << 16) / cpu.register(B)) & 0xFFFF));
    }

    @Test
    public void when_b_is_zero_there_is_no_division() {
        cpu.setRegister(A, 0x10);
        cpu.setRegister(B, 0x0);
        memory.writeAt(0x0000, 0b000001_000000_0101);
        cpu.step();
        assertThat(cpu.register(A), is(0x0));
        assertThat(cpu.getOverflow(), is(0x0));
    }

    @Test
    public void it_performs_mod_a_to_b() {
        cpu.setRegister(A, 0x10);
        cpu.setRegister(B, 0x5);
        memory.writeAt(0x0000, 0b000001_000000_0110);
        cpu.step();
        assertThat(cpu.register(A), is(0x10 % 0x5));
    }

    @Test
    public void when_b_is_zero_there_is_no_mod() {
        cpu.setRegister(A, 0x10);
        cpu.setRegister(B, 0x0);
        memory.writeAt(0x0000, 0b000001_000000_0110);
        cpu.step();
        assertThat(cpu.register(A), is(0x0));
    }

    @Test
    public void it_performs_shl_a_to_b() {
        cpu.setRegister(A, 0x10);
        cpu.setRegister(B, 0x5);
        memory.writeAt(0x0000, 0b000001_000000_0111);
        cpu.step();
        assertThat(cpu.register(A), is(0x10 << 0x5));
        assertThat(cpu.getOverflow(), is(((0x10 << 0x5) >> 16) & 0xFFFF));
    }

    @Test
    public void it_performs_shr_a_to_b() {
        cpu.setRegister(A, 0x10);
        cpu.setRegister(B, 0x5);
        memory.writeAt(0x0000, 0b000001_000000_1000);
        cpu.step();
        assertThat(cpu.register(A), is(0x10 >> 0x5));
        assertThat(cpu.getOverflow(), is(((0x10 << 16) >> 0x5) & 0xFFFF));
    }

    @Test
    public void it_performs_and_a_to_b() {
        cpu.setRegister(A, 0x10);
        cpu.setRegister(B, 0x5);
        memory.writeAt(0x0000, 0b000001_000000_1001);
        cpu.step();
        assertThat(cpu.register(A), is(0x10 & 0x5));
    }

    @Test
    public void it_performs_bor_a_to_b() {
        cpu.setRegister(A, 0x10);
        cpu.setRegister(B, 0x5);
        memory.writeAt(0x0000, 0b000001_000000_1010);
        cpu.step();
        assertThat(cpu.register(A), is(0x10 | 0x5));
    }

    @Test
    public void it_performs_xor_a_to_b() {
        cpu.setRegister(A, 0x10);
        cpu.setRegister(B, 0x5);
        memory.writeAt(0x0000, 0b000001_000000_1011);
        cpu.step();
        assertThat(cpu.register(A), is(0x10 ^ 0x5));
    }

    @Test
    public void it_performs_next_instruction_when_a_equals_to_b() {
        cpu.setRegister(A, 0x5);
        cpu.setRegister(B, 0x5);
        cpu.setRegister(C, 0x3);
        memory.writeAt(0x0000, 0b000001_000000_1100);
        memory.writeAt(0x0001, 0b000010_000000_0001);

        cpu.step();
        cpu.step();
        assertThat(cpu.register(A), is(0x3));
    }

    @Test
    public void it_doesnt_performs_next_instruction_when_a_is_different_from_b() {
        cpu.setRegister(A, 0x1);
        cpu.setRegister(B, 0x5);
        cpu.setRegister(C, 0x3);
        memory.writeAt(0x0000, 0b000001_000000_1100);
        memory.writeAt(0x0001, 0b000010_000000_0010);
        memory.writeAt(0x0002, 0b000001_000000_0010);

        cpu.step();
        cpu.step();
        assertThat(cpu.register(A), is(0x5 + 0x1));
    }

    @Test
    public void it_does_perform_next_instruction_when_a_equals_to_b() {
        cpu.setRegister(A, 0x1);
        cpu.setRegister(B, 0x5);
        cpu.setRegister(C, 0x3);
        memory.writeAt(0x0000, 0b000001_000000_1101);
        memory.writeAt(0x0001, 0b000010_000000_0010);
        memory.writeAt(0x0002, 0b000001_000000_0010);

        cpu.step();
        cpu.step();
        assertThat(cpu.register(A), is(0x3 + 0x1));
    }

    @Test
    public void it_does_perform_next_instruction_when_a_is_greater_than_b() {
        cpu.setRegister(A, 0x6);
        cpu.setRegister(B, 0x5);
        cpu.setRegister(C, 0x3);
        memory.writeAt(0x0000, 0b000001_000000_1110);
        memory.writeAt(0x0001, 0b000010_000000_0010);
        memory.writeAt(0x0002, 0b000001_000000_0010);

        cpu.step();
        cpu.step();
        assertThat(cpu.register(A), is(0x3 + 0x6));
    }

    @Test
    public void it_does_perform_next_instruction_when_a_and_b_different_from_zero() {
        cpu.setRegister(A, 0x6);
        cpu.setRegister(B, 0x5);
        cpu.setRegister(C, 0x3);
        memory.writeAt(0x0000, 0b000001_000000_1111);
        memory.writeAt(0x0001, 0b000010_000000_0010);
        memory.writeAt(0x0002, 0b000001_000000_0010);

        cpu.step();
        cpu.step();
        assertThat(cpu.register(A), is(0x3 + 0x6));
    }

    @Test
    public void it_does_perform_syscall_jsr() {
        cpu.setRegister(A, 0x6);
        memory.writeAt(0x0000, 0b000000_000001_0000);
        cpu.step();
        assertThat(cpu.getProgramCounter(), is(cpu.register(A)));
    }

    @Test
    public void it_runs_properly_instructions_with_different_sizes() {
        int address = 0x0000;
        memory.writeAt(address++, 0x7C01);
        memory.writeAt(address++, 0x0030);
        memory.writeAt(address++, 0x7DE1);
        memory.writeAt(address++, 0x1000);
        memory.writeAt(address++, 0x0020);

        cpu.step();
        cpu.step();
        assertThat(memory.readFrom(0x1000), is(0x20));
    }

    @Test
    public void it_runs_properly_basic_stuff() {
        int address = 0x0000;
        memory.writeAt(address++, 0x7C01);
        memory.writeAt(address++, 0x0030);
        
        memory.writeAt(address++, 0x7DE1);
        memory.writeAt(address++, 0x1000);
        memory.writeAt(address++, 0x0020);
        
        memory.writeAt(address++, 0x7803);
        memory.writeAt(address++, 0x1000);
        
        memory.writeAt(address++, 0xC00D);
        
        memory.writeAt(address++, 0x7DC1);
        memory.writeAt(address++, 0x001A);
        
        memory.writeAt(address++, 0xA861);
        
        for (int steps = 0; steps < 5; steps++) {
            cpu.step();
        }
        assertThat(cpu.register(A), is(0x10));
        assertThat(cpu.register(I), is(10));
        assertThat(memory.readFrom(0x1000), is(0x20));
    }
    
    @Test
    public void it_runs_loopy_code(){
        int address = 0x0000;
        memory.writeAt(address++, 0x7C01);
        memory.writeAt(address++, 0x0030);
        
        memory.writeAt(address++, 0x7DE1);
        memory.writeAt(address++, 0x1000);
        memory.writeAt(address++, 0x0020);
        
        memory.writeAt(address++, 0x7803);
        memory.writeAt(address++, 0x1000);
        
        memory.writeAt(address++, 0xC00D);
        
        memory.writeAt(address++, 0x7DC1);
        memory.writeAt(address++, 0x001A);
        
        memory.writeAt(address++, 0xA861);
        
        memory.writeAt(address++, 0x7C01);
        memory.writeAt(address++, 0x2000);
        
        memory.writeAt(address++, 0x2161);
        memory.writeAt(address++, 0x2000);
        
        memory.writeAt(address++, 0x8463);
        
        memory.writeAt(address++, 0x806D);
     
        memory.writeAt(address++, 0x7DC1);
        memory.writeAt(address++, 0x000D);
        for (int steps = 0; steps < 15; steps++) {
            cpu.step();
        }
    }
}
