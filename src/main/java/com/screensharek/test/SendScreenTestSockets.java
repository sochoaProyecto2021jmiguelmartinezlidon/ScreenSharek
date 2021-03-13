package com.screensharek.test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;

public class SendScreenTestSockets {
    public static void main(String[] args) {

        try {
            TestCapturaPantalla capture = new TestCapturaPantalla(new Robot());
            DatagramSocket serverDatagram = new DatagramSocket(9999);
            System.out.println("Waiting connection...");
            InetSocketAddress isa = new InetSocketAddress("localhost", 9999);
            ServerSocket server = new ServerSocket();
            server.bind(isa);
            Socket client = server.accept();
            System.out.println("Sending data...");
            BufferedImage image;
            ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
            ObjectOutputStream output = new ObjectOutputStream(client.getOutputStream());
            while (true) {

                image = capture.takeCapture();
                ImageIO.write(image, "png", byteOutput);
                byte[] buffer = byteOutput.toByteArray();
                output.writeObject(buffer);
            }
        } catch (AWTException | IOException e) {
            e.printStackTrace();
        }
    }
}
