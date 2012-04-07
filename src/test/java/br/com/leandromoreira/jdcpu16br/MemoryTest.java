package br.com.leandromoreira.jdcpu16br;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

public class MemoryTest {

    private Memory memory;

    @Before
    public void setup() {
        memory = new Memory();
    }

    @Test
    public void it_handles_io() {
        memory.writeAt(0x0000, 0x7C01);
        assertThat(memory.readFrom(0x0000), is(0x7C01));
    }

    @Test
    public void it_is_empty_when_nothing_is_loaded() {
        assertThat(memory.isEmpty(), is(true));
    }

    @Test
    public void it_load_memory_from_hexa_string_array() {
        final String[] cells = new String[]{"003A"};
        memory.load(cells);
        assertThat(memory.isEmpty(), is(false));
        assertThat(memory.readFrom(0x0000), is(0x003A));
    }

    @Test
    public void it_null_empty_array() {
        final String[] emptyCell = new String[]{};
        final String[] nullCell = null;
        memory.load(emptyCell);
        assertThat(memory.isEmpty(), is(true));
        memory.load(nullCell);
        assertThat(memory.isEmpty(), is(true));
    }

    @Test
    public void it_ignores_empty_or_null_item_of_array() {
        final String[] emptyCell = new String[]{null, ""};
        memory.load(emptyCell);
        assertThat(memory.isEmpty(), is(true));
    }
}
