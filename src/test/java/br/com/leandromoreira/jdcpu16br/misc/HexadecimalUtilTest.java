package br.com.leandromoreira.jdcpu16br.misc;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;

public class HexadecimalUtilTest {

    @Test
    public void it_format_as_a_hexa_four_position_given_a_number() {
        final HexadecimalUtil formatter = new HexadecimalUtil();
        assertThat(formatter.toHexa4Spaces(0x1000), is("0x1000"));
    }

    @Test
    public void it_format_as_a_hexadecimal_number() {
        final HexadecimalUtil formatter = new HexadecimalUtil();
        assertThat(formatter.toHexadecimal(10), is("0xA"));
    }

    @Test
    public void it_reject_not_hexadecimal_number() {
        final HexadecimalUtil formatter = new HexadecimalUtil();
        assertThat(formatter.isValidHexadecimal(""), is(false));
        assertThat(formatter.isValidHexadecimal(null), is(false));
        assertThat(formatter.isValidHexadecimal("afaf,G"), is(false));
        assertThat(formatter.isValidHexadecimal("TTG"), is(false));
    }
}
