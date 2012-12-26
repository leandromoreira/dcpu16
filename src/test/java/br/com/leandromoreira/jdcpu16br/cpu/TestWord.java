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
}