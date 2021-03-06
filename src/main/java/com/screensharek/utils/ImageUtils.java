package com.screensharek.utils;

public class ImageUtils {

    /**
     * Split an image passed in parameters in byte array.
     * @param image array of bytes.
     * @return the split image in a matrix of bytes.
     */
    public static byte[][] splitImage(byte[] image) {
        double parts = image.length / 59997.0f;
        double numOfPackets = Math.ceil(parts);
        if (numOfPackets > 14) {
            System.out.println("La imagen es demasiado grande.");
            return new byte[0][0];
        }
        byte[][] imageSplit = new byte[(int) numOfPackets][59997];
        for (int i = 0; i < numOfPackets; i++) {
            int k = 3;
            long nPacket = i + 1;
            nPacket = nPacket << 4;
            nPacket = nPacket | (long) numOfPackets;
            byte[] aux = ByteUtils.longToBytes(nPacket);
            imageSplit[i][0] = aux[7];
            int startPosition = i * 59994;
            int lengthCount = 0;
            for (int j = startPosition; j < startPosition + 59994; j++, k++) {
                if (j == image.length)
                    break;
                imageSplit[i][k] = image[j];
                lengthCount++;
            }
            byte[] length = ByteUtils.longToBytes(lengthCount);
            imageSplit[i][1] = length[6];
            imageSplit[i][2] = length[7];
        }
        return imageSplit;
    }

    /**
     * By an split image rebuild a image in an array of bytes.
     * @param imageSplitted split image messy.
     * @return the image assembled.
     */
    public static byte[] assembleImage(byte[][] imageSplitted) {
        if (imageSplitted == null)
            return new byte[0];
        byte[][] imageSort = new byte[imageSplitted.length][];
        for (int i = 0; i < imageSplitted.length; i++) {
            ByteUtils.resetBuffer();
            long packetInfo = ByteUtils.bytesToLong(new byte[] {0, 0, 0, 0, 0, 0, 0, imageSplitted[i][0]});
            long numPacket = packetInfo & 240;
            numPacket = numPacket >> 4;
            long numOfPackets = packetInfo & 15;
            /*if (numOfPackets != imageSplitted.length) {
                imageSort = new byte[(int) numOfPackets][];
            }*/
            // TODO: 21/04/2021 Revisar que en el otro lado no se pueda poner un tama??o superior al que se puede indicar en dos bytes.
            ByteUtils.resetBuffer();
            byte[] dataRawLength = new byte[] {0, 0, 0, 0, 0, 0, imageSplitted[i][1], imageSplitted[i][2]};
            long dataLength = ByteUtils.bytesToLong(dataRawLength);
            int k = 3;
            for (int j = 0; j < dataLength; j++, k++) {
                if (j == 0)
                    imageSort[(int) (numPacket - 1)] = new byte[(int) dataLength];
                imageSort[(int) (numPacket - 1)][j] = imageSplitted[(int) (numPacket - 1)][k];
            }
        }
        return matrixToByteArray(imageSort);
    }

    /**
     * Rebuild a image in array of bytes by an split image shorted.
     * @param matrix split image sorted
     * @return image in array of bytes.
     */
    public static byte[] matrixToByteArray(byte[][] matrix) {
        if (matrix == null)
            return new byte[0];
        int totalLength = 0;
        for (int i = 0; i < matrix.length; i++) {
            if (matrix[i] == null)
                matrix[i] = new byte[0];
            totalLength += matrix[i].length;
        }
        byte[] array = new byte[totalLength];
        int k = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++, k++) {
                array[k] = matrix[i][j];
            }
        }
        return array;
    }
}
