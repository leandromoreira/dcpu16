package br.com.leandromoreira.jdcpu16br;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;

public class HexaFormatterTest {

    @Test
    public void it_format_as_a_hexa_four_position_given_a_number() {
        final HexaFormatter formatter = new HexaFormatter();
        assertThat(formatter.toHexa4Spaces(0x1000), is("0x1000"));
    }
}
