package com.screensharek.test.splitingpackets;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPImageSend {
    public static void main(String[] args) {
        try {
            Robot robot = new Robot();
            BufferedImage image = robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", baos);
            byte[][] imageSplited = splitImage(baos.toByteArray());
            DatagramSocket socketUDP = new DatagramSocket(9999);
            InetAddress ia = InetAddress.getByName("192.168.18.254");
            for (int i = 0; i < imageSplited.length; i++) {
                DatagramPacket packet = new DatagramPacket(imageSplited[i], imageSplited[i].length, ia, 9998);
                socketUDP.send(packet);
            }
            byte[] finisBytes = createFinishArray();
            DatagramPacket finisPacket = new DatagramPacket(finisBytes, finisBytes.length, ia, 9998);
            socketUDP.send(finisPacket);
        } catch (AWTException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static byte[][] splitImage(byte[] image) {
        double parts = image.length / 59997.0f;
        double numOfPackets = Math.ceil(parts);
        if (numOfPackets > 15) {
            System.out.println("La imagen es demasiado grande.");
            return new byte[0][0];
        }
        byte[][] imageSplit = new byte[(int) numOfPackets][59997];
        for (int i = 0; i < numOfPackets; i++) {
            int k = 3;
            long nPacket = i + 1;
            nPacket = nPacket << 4;
            int startPosition = i * 59997;
            for (int j = startPosition; j < startPosition + 59997; j++, k++) {
                if (j == image.length)
                    break;
                imageSplit[i][k] = image[j];
            }
        }
        return imageSplit;
    }

    public static byte[] createFinishArray() {
        byte[] bytes = new byte[60000];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = Byte.MIN_VALUE;
        }
        return bytes;
    }
}
