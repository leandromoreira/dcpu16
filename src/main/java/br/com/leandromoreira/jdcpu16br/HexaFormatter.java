package br.com.leandromoreira.jdcpu16br;

public class HexaFormatter {
    public static final String INFIX = "0";
    public static final String PREFIX = "0x";
    public static final int SIZE_OF_HEXA = 4;

    public String toHexa4Spaces(final int word) {
        final StringBuilder sb = new StringBuilder(PREFIX);
        final String address = Integer.toHexString(word).toUpperCase();
        for (int i = address.length(); i < SIZE_OF_HEXA; i++) {
            sb.append(INFIX);
        }
        return sb.append(address).toString();
    }
}
