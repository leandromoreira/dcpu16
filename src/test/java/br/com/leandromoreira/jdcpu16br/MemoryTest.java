package br.com.leandromoreira.jdcpu16br;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;

public class MemoryTest {

    @Test
    public void it_handles_io() {
        final Memory memory = new Memory();
        memory.writeAt(0x0000, 0x7C01);
        assertThat(memory.readFrom(0x0000), is(0x7C01));
    }
}
