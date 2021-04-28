package com.screensharek.test.splitingpackets;

import com.screensharek.ui.ShareScreen;
import com.screensharek.utils.ByteUtils;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.MemoryCacheImageInputStream;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;

public class UDPImageReceive {

    static byte[] image;

    public static void main(String[] args) {

        /*long numLong = 15;
        long num4bits = 15;
        numLong = numLong << 4;
        numLong = numLong | num4bits;
        byte[] numBytes = longToBytes(numLong);
        long numParsed = bytesToLong(numBytes);
        long npacket = numParsed & 240;
        long nofpacket = numParsed & 15;
        npacket = numParsed >> 4;*/

        byte[] buff;

        try {
            SocketAddress sa = new InetSocketAddress("192.168.18.254", 9998);
            DatagramSocket socketUDP = new DatagramSocket(null);
            socketUDP.bind(sa);
            byte[] bytes;
            byte[][] imageMessy = null;
            ShareScreen shareScreen = new ShareScreen();
            shareScreen.init(ShareScreen.Mode.WATCHING);
            InetAddress ia = InetAddress.getByName("192.168.18.254");
            int count = 0;
            int packets = 0;
            while(true) {
                do {
                    buff = new byte[60000];
                    DatagramPacket packet = new DatagramPacket(buff, buff.length);
                    socketUDP.receive(packet);
                    bytes = packet.getData();
                    if (imageMessy == null) {
                        packets = getNumberOfPackets(new byte[]{0, 0, 0, 0, 0, 0, 0, bytes[0]});
                        imageMessy = new byte[packets][];
                    }
                    imageMessy[count] = bytes;
                    count++;
                } while (count < packets);
                count = 0;
                packets = 0;
                System.out.println("Imagen recibida.");
                byte[] response = new byte[1];
                response[0] = 1;
                DatagramPacket packet = new DatagramPacket(response, response.length, ia, 9999);
                socketUDP.send(packet);
                System.out.println("OK! enviado.");
                image = assembleImage(imageMessy);
                //BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(image));
                shareScreen.putImage(image);
                imageMessy = null;
            }
            /*FileOutputStream fos = new FileOutputStream("C:\\Users\\Jose\\Desktop\\Proyecto21\\testImages\\image001.png");
            OutputStream os = new BufferedOutputStream(fos);
            ImageReader reader = ImageIO.getImageReadersByFormatName("png").next();
            ImageInputStream iis = new MemoryCacheImageInputStream(new ByteArrayInputStream(image));
            reader.setInput(iis);
            BufferedImage renderImage = reader.read(0);
            ImageIO.write(renderImage, "png", os);*/
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:campoDePruebas.db");
            Statement statement = connection.createStatement();
            *//*statement.execute("CREATE TABLE IF NOT EXISTS test(" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "nombre TEXT);");
            PreparedStatement prepared = connection.prepareStatement("INSERT INTO test(nombre) values (?);");
            prepared.setString(1, "Jose");
            prepared.executeUpdate();
            connection.close();
            connection = DriverManager.getConnection("jdbc:sqlite:campoDePruebas.db");
            prepared = connection.prepareStatement("INSERT INTO test(nombre) values (?);");
            prepared.setString(1, "Aitana");
            prepared.executeUpdate();*//*
            ResultSet rs = statement.executeQuery("select * from test;");
            while (rs.next()) {
                System.out.println(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
    }

    public static boolean isFinalPacket(byte[] bytes) {
        for (int i = 0; i < bytes.length; i++) {
            if (bytes[i] != Byte.MIN_VALUE) {
                addBytes(bytes);
                return false;
            }
        }
        return true;
    }

    public static byte[] assembleImage(byte[][] imageSplitted) {
        if (imageSplitted == null)
            return new byte[0];
        byte[][] imageSort = new byte[imageSplitted.length][];
        for (int i = 0; i < imageSplitted.length; i++) {
            resetBuffer();
            long packetInfo = bytesToLong(new byte[] {0, 0, 0, 0, 0, 0, 0, imageSplitted[i][0]});
            long numPacket = packetInfo & 240;
            numPacket = numPacket >> 4;
            long numOfPackets = packetInfo & 15;
            /*if (numOfPackets != imageSplitted.length) {
                imageSort = new byte[(int) numOfPackets][];
            }*/
            // TODO: 21/04/2021 Revisar que en el otro lado no se pueda poner un tamaño superior al que se puede indicar en dos bytes.
            resetBuffer();
            byte[] dataRawLength = new byte[] {0, 0, 0, 0, 0, 0, imageSplitted[i][1], imageSplitted[i][2]};
            long dataLength = bytesToLong(dataRawLength);
            int k = 3;
            for (int j = 0; j < dataLength; j++, k++) {
                if (j == 0)
                    imageSort[i] = new byte[(int) dataLength];
                imageSort[i][j] = imageSplitted[(int) (numPacket - 1)][k];
            }
        }
        return matrixToByteArray(imageSort);
    }

    public static int getNumberOfPackets(byte[] packet) {
        resetBuffer();
        long packetInfo = bytesToLong(packet);
        long numOfPackets = packetInfo & 15;
        return (int) numOfPackets;
    }

    public static byte[] matrixToByteArray(byte[][] matrix) {
        int totalLength = 0;
        for (int i = 0; i < matrix.length; i++) {
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

    public static void addBytes(byte[] bytes) {
        if (image == null) {
            image = bytes;
        } else {
            int newLength = bytes.length + image.length;
            byte[] newImage = new byte[newLength];
            for (int i = 0; i < image.length; i++) {
                newImage[i] = image[i];
            }
            int j = image.length;
            for (int i = 0; i < bytes.length; i++, j++) {
                newImage[j] = bytes[i];
            }
            image = newImage;
        }
    }

    private static ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);

    public static void resetBuffer() {
        buffer = ByteBuffer.allocate(Long.BYTES);
    }

    public static byte[] longToBytes(long x) {
        buffer.putLong(0, x);
        return buffer.array();
    }

    public static long bytesToLong(byte[] bytes) {
        buffer.put(bytes, 0, bytes.length);
        buffer.flip();//need flip
        return buffer.getLong();
    }

    public static long bytesToLong(byte bytes) {
        buffer.put(bytes);
        buffer.flip();//need flip
        return buffer.getLong();
    }
}
