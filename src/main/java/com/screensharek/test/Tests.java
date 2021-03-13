package com.screensharek.test;

import com.screensharek.components.JImage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

public class Tests {
    public static void main(String[] args) {
        try {
            Robot robot = new Robot();
            TestCapturaPantalla captura = new TestCapturaPantalla(robot);
            //captura.takeCapture();
            //captura.save();
            JFrame frame = new JFrame();
            frame.setSize(800, 600);
            JImage jImage = new JImage();
            jImage.setFormat(JImage.Format.STRETCH);
            //jImage.setImage(captura.getImage());
            jImage.setPreferredSize(new Dimension(800, 600));
            frame.add(jImage);
            frame.setVisible(true);
            TestActualizarImagenes actualize = new TestActualizarImagenes(jImage, captura);
            new Thread(() -> {
                try {
                    InetSocketAddress isa = new InetSocketAddress("localhost", 9999);
                    DatagramSocket client = new DatagramSocket();
                    byte[] buffer = "hola".getBytes();
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length, isa);
                    byte[] imageArray;
                    BufferedImage image;
                    client.send(packet);
                    while (true) {
                        List<Byte> byteList = new ArrayList<>();
                        while (true) {
                            packet.setData(new byte[1]);
                            client.receive(packet);
                            byte[] auxBuffer = packet.getData();
                            int num = 0;
                            if (auxBuffer[0] != -1)
                                byteList.add(auxBuffer[0]);
                            else {
                                buffer = new byte[byteList.size()];
                                for (int i = 0; i < buffer.length; i++) {
                                    buffer[i] = byteList.get(i);
                                }
                                byteList = new ArrayList<>();
                                break;
                            }
                        }
                        imageArray = packet.getData();
                        image = ImageIO.read(new ByteArrayInputStream(imageArray));
                        jImage.setImage(image);

//                    actualize.update();
//                    try {
//                        Thread.sleep(16);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }
}
