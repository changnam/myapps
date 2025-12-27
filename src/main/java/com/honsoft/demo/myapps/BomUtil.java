package com.honsoft.demo.myapps;

public final class BomUtil {

    private BomUtil() {}

    /**
     * @return encoding name if BOM is found, otherwise null
     */
    public static String detectBom(byte[] data) {
        if (data == null || data.length < 2) {
            return null;
        }

        // UTF-8 BOM: EF BB BF
        if (data.length >= 3 &&
            (data[0] & 0xFF) == 0xEF &&
            (data[1] & 0xFF) == 0xBB &&
            (data[2] & 0xFF) == 0xBF) {
            return "UTF-8 with BOM";
        }

        // UTF-16 BE BOM: FE FF
        if ((data[0] & 0xFF) == 0xFE &&
            (data[1] & 0xFF) == 0xFF) {
            return "UTF-16BE";
        }

        // UTF-16 LE BOM: FF FE
        if ((data[0] & 0xFF) == 0xFF &&
            (data[1] & 0xFF) == 0xFE) {

            // UTF-32 LE BOM: FF FE 00 00
            if (data.length >= 4 &&
                data[2] == 0x00 &&
                data[3] == 0x00) {
                return "UTF-32LE";
            }
            return "UTF-16LE";
        }

        // UTF-32 BE BOM: 00 00 FE FF
        if (data.length >= 4 &&
            data[0] == 0x00 &&
            data[1] == 0x00 &&
            (data[2] & 0xFF) == 0xFE &&
            (data[3] & 0xFF) == 0xFF) {
            return "UTF-32BE";
        }

        return null; // No BOM
    }
}
