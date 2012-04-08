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
        assertThat(memory.getMaximumFilled(), is(0));
    }

    @Test
    public void it_load_memory_from_hexa_string_array() {
        final String[] cells = new String[]{"003A"};
        memory.load(cells);
        assertThat(memory.getMaximumFilled(), is(1));
        assertThat(memory.readFrom(0x0000), is(0x003A));
    }

    @Test
    public void it_load_memory_from_hexa_string_hexa_formatted_array() {
        final String[] cells = new String[]{"0x003A"};
        memory.load(cells);
        assertThat(memory.getMaximumFilled(), is(1));
        assertThat(memory.readFrom(0x0000), is(0x003A));
    }

    @Test
    public void it_null_empty_array() {
        final String[] emptyCell = new String[]{};
        final String[] nullCell = null;
        memory.load(emptyCell);
        assertThat(memory.getMaximumFilled(), is(0));
        memory.load(nullCell);
        assertThat(memory.getMaximumFilled(), is(0));
    }

    @Test
    public void it_ignores_empty_or_null_item_of_array() {
        final String[] emptyCell = new String[]{null, ""};
        memory.load(emptyCell);
        assertThat(memory.getMaximumFilled(), is(0));
    }

    @Test
    public void it_cleans_memory() {
        final String[] cells = new String[]{"003A"};
        memory.load(cells);
        memory.clear();
        assertThat(memory.getMaximumFilled(), is(0));
    }
}
