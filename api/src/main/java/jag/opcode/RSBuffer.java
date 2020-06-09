package jag.opcode;

import jag.RSNode;

public interface RSBuffer extends RSNode {

    static int writeEscapedStringBytes(CharSequence sequence, int offset, int length, byte[] buffer, int caret) {
        int size = length - offset;

        for (int i = 0; i < size; ++i) {
            char c = sequence.charAt(i + offset);
            if (c > 0 && c < 128 || c >= 160 && c <= 255) {
                buffer[i + caret] = (byte) c;
            } else if (c == 8364) {
                buffer[i + caret] = -128;
            } else if (c == 8218) {
                buffer[i + caret] = -126;
            } else if (c == 402) {
                buffer[i + caret] = -125;
            } else if (c == 8222) {
                buffer[i + caret] = -124;
            } else if (c == 8230) {
                buffer[i + caret] = -123;
            } else if (c == 8224) {
                buffer[i + caret] = -122;
            } else if (c == 8225) {
                buffer[i + caret] = -121;
            } else if (c == 710) {
                buffer[i + caret] = -120;
            } else if (c == 8240) {
                buffer[i + caret] = -119;
            } else if (c == 352) {
                buffer[i + caret] = -118;
            } else if (c == 8249) {
                buffer[i + caret] = -117;
            } else if (c == 338) {
                buffer[i + caret] = -116;
            } else if (c == 381) {
                buffer[i + caret] = -114;
            } else if (c == 8216) {
                buffer[i + caret] = -111;
            } else if (c == 8217) {
                buffer[i + caret] = -110;
            } else if (c == 8220) {
                buffer[i + caret] = -109;
            } else if (c == 8221) {
                buffer[i + caret] = -108;
            } else if (c == 8226) {
                buffer[i + caret] = -107;
            } else if (c == 8211) {
                buffer[i + caret] = -106;
            } else if (c == 8212) {
                buffer[i + caret] = -105;
            } else if (c == 732) {
                buffer[i + caret] = -104;
            } else if (c == 8482) {
                buffer[i + caret] = -103;
            } else if (c == 353) {
                buffer[i + caret] = -102;
            } else if (c == 8250) {
                buffer[i + caret] = -101;
            } else if (c == 339) {
                buffer[i + caret] = -100;
            } else if (c == 382) {
                buffer[i + caret] = -98;
            } else if (c == 376) {
                buffer[i + caret] = -97;
            } else {
                buffer[i + caret] = 63;
            }
        }

        return size;
    }

    int getCaret();

    void setCaret(int caret);

    default int seek() {
        int caret = getCaret();
        setCaret(caret + 1);
        return caret;
    }

    byte[] getPayload();

    default RSBuffer p1(int value) {
        byte[] data = getPayload();
        data[seek()] = (byte) value;
        return this;
    }

    default RSBuffer p2(int value) {
        byte[] data = getPayload();
        data[seek()] = (byte) (value >> 8);
        data[seek()] = (byte) value;
        return this;
    }

    default RSBuffer ip2(int value) {
        byte[] data = getPayload();
        data[seek()] = (byte) value;
        data[seek()] = (byte) (value >> 8);
        return this;
    }

    default RSBuffer p3(int value) {
        byte[] data = getPayload();
        data[seek()] = (byte) (value >> 16);
        data[seek()] = (byte) (value >> 8);
        data[seek()] = (byte) value;
        return this;
    }

    default RSBuffer p4(int value) {
        byte[] data = getPayload();
        data[seek()] = (byte) (value >> 24);
        data[seek()] = (byte) (value >> 16);
        data[seek()] = (byte) (value >> 8);
        data[seek()] = (byte) value;
        return this;
    }

    default RSBuffer ip4(int value) {
        byte[] data = getPayload();
        data[seek()] = (byte) value;
        data[seek()] = (byte) (value >> 8);
        data[seek()] = (byte) (value >> 16);
        data[seek()] = (byte) (value >> 24);
        return this;
    }

    default RSBuffer p8(long value) {
        byte[] data = getPayload();
        data[seek()] = (byte) (int) (value >> 56);
        data[seek()] = (byte) (int) (value >> 48);
        data[seek()] = (byte) (int) (value >> 40);
        data[seek()] = (byte) (int) (value >> 32);
        data[seek()] = (byte) (int) (value >> 24);
        data[seek()] = (byte) (int) (value >> 16);
        data[seek()] = (byte) (int) (value >> 8);
        data[seek()] = (byte) (int) value;
        return this;
    }

    default RSBuffer pjstr(String value) {
        int end = value.indexOf(0);
        if (end >= 0) {
            throw new IllegalArgumentException("");
        }

        byte[] data = getPayload();
        int caret = getCaret();
        setCaret(writeEscapedStringBytes(value, 0, value.length(), data, caret));
        data[seek()] = 0;
        return this;
    }

    default RSBuffer pdata(byte[] buffer, int caret, int length) {
        byte[] data = getPayload();
        for (int k = caret; k < caret + length; k++) {
            data[seek()] = buffer[k];
        }
        return this;
    }
}