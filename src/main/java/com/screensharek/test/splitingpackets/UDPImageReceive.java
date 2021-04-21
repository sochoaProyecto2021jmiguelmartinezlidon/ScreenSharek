package com.screensharek.test.splitingpackets;

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

        long numLong = 15;
        numLong = numLong << 4;
        byte[] numBytes = longToBytes(numLong);
        long numParsed = bytesToLong(numBytes);
        numParsed = numParsed >> 4;


        byte[] buff = new byte[60000];

        try {
            SocketAddress sa = new InetSocketAddress("192.168.18.254", 9998);
            DatagramSocket socketUDP = new DatagramSocket(null);
            socketUDP.bind(sa);
            DatagramPacket packet = new DatagramPacket(buff, buff.length);
            byte[] bytes;
            do {
                socketUDP.receive(packet);
                bytes = packet.getData();
            } while (!isFinalPacket(bytes));
            FileOutputStream fos = new FileOutputStream("C:\\Users\\Jose\\Desktop\\Proyecto21\\testImages\\image001.png");
            OutputStream os = new BufferedOutputStream(fos);
            ImageReader reader = ImageIO.getImageReadersByFormatName("png").next();
            ImageInputStream iis = new MemoryCacheImageInputStream(new ByteArrayInputStream(image));
            reader.setInput(iis);
            BufferedImage renderImage = reader.read(0);
            ImageIO.write(renderImage, "png", os);
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

    public static byte[] longToBytes(long x) {
        buffer.putLong(0, x);
        return buffer.array();
    }

    public static long bytesToLong(byte[] bytes) {
        buffer.put(bytes, 0, bytes.length);
        buffer.flip();//need flip
        return buffer.getLong();
    }
}
