package br.com.leandromoreira.jdcpu16br.cpu;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;

public class TestWord {
    
    @Test
    public void ensures_the_instruction_format(){
        final Word instruction = new Word(0b111111_10000_00001);
        assertThat(instruction.a(),is(0b111111));
        assertThat(instruction.b(),is(0b10000));
        assertThat(instruction.code(),is(0b00001));
    }
    
    @Test
    public void it_creates_instruction_based_on_its_operands(){
        final int a = 0b111111;
        final int b = 0b10000;
        final int opCode = 0b00001;
        final Word instruction = new Word(a,b,opCode);
        assertThat(instruction.rawInstruction(), is(0b111111_10000_00001));
    }
}