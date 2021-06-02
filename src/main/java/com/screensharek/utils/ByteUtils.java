package com.screensharek.utils;

import java.nio.ByteBuffer;

public class ByteUtils {
    private static ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);

    /**
     * Reset the buffer of bytes.
     */
    public static void resetBuffer() {
        buffer = ByteBuffer.allocate(Long.BYTES);
    }

    /**
     * Pass a long to bytes.
     * @param x number long to convert.
     * @return the array of bytes.
     */
    public static byte[] longToBytes(long x) {
        buffer.putLong(0, x);
        return buffer.array();
    }

    /**
     * Pass an array of bytes to long.
     * @param bytes array of bytes to convert.
     * @return the number long.
     */
    public static long bytesToLong(byte[] bytes) {
        buffer.put(bytes, 0, bytes.length);
        buffer.flip();//need flip
        return buffer.getLong();
    }
}