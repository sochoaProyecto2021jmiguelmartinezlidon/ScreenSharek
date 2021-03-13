package com.screensharek.test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.*;

public class SendScreenTest {
    public static void main(String[] args) {
        try {
            //TODO: Probar con datagram socket.
            TestCapturaPantalla capture = new TestCapturaPantalla(new Robot());
            DatagramSocket serverDatagram = new DatagramSocket(9999);
            System.out.println("Waiting connection...");
            byte[] buffer = new byte[serverDatagram.getReceiveBufferSize()];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            serverDatagram.receive(packet);
            System.out.println("Sending data...");
            BufferedImage image;
            ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
            while (true) {
                //buffer = new byte[serverDatagram.getReceiveBufferSize()];
                image = capture.takeCapture();
                ImageIO.write(image, "png", byteOutput);
                buffer = byteOutput.toByteArray();
                byte[] auxBuffer = new byte[1];
                for (int i = 0; i < buffer.length; i++) {
                    auxBuffer[0] = buffer[i];
                    packet.setData(auxBuffer);
                    serverDatagram.send(packet);
                }
            }
        } catch (AWTException | IOException e) {
            e.printStackTrace();
        }
    }
}
