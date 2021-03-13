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
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class TestSockets {
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
                    InetSocketAddress isa = new InetSocketAddress("localhost", 9998);
                    Socket client = new Socket();
                    client.bind(isa);
                    byte[] imageArray;
                    BufferedImage image;

                    while (true) {


                        ObjectInputStream input = new ObjectInputStream(client.getInputStream());
                        imageArray = (byte[]) input.readObject();



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
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }).start();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }
}
