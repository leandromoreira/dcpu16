package br.com.leandromoreira.jdcpu16br;

public class HexaFormatter {

    public static final String INFIX = "0";
    public static final String PREFIX = "0x";

    public String toHexa4Spaces(final int word) {
        return toHexadecimalFormatted(word, 4);
    }

    private String toHexadecimalFormatted(final int value, final int size) {
        final StringBuilder sb = new StringBuilder(PREFIX);
        final String address = Integer.toHexString(value).toUpperCase();
        for (int i = address.length(); i < size; i++) {
            sb.append(INFIX);
        }
        return sb.append(address).toString();
    }

    public String toHexadecimal(final int value) {
        return toHexadecimalFormatted(value, 1);
    }

    public boolean isValidHexadecimal(final String value) {
        if (value != null && !"".equals(value.trim())) {
            try {
                Integer.valueOf(value.replace("x", ""), 16);
                return true;
            } catch (Exception e) {
                return false;
            }
        } else {
            return false;
        }
    }
}
