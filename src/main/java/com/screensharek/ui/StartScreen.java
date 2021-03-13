package com.screensharek.ui;

import javax.swing.*;
import java.awt.*;

public class StartScreen {
    private JFrame frame;
    private JButton connect;
    private JButton shareScreen;

    public void init() {
        startComponents();
        configureFrame();
        configureConnectButton();
        frame.setVisible(true);
    }

    public void startComponents() {
        frame = new JFrame();
        connect = new JButton();
        shareScreen = new JButton();
    }

    public void configureFrame() {
        frame.setSize(new Dimension(935, 436));
        frame.getContentPane().setBackground(new Color(30, 30, 30));
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(null);
    }

    public void configureConnectButton() {
        Icon iconHover = new ImageIcon("C:\\Users\\Jose\\Desktop\\Proyecto21\\ScreenSharek\\src\\main\\resources\\TestButtonHover.png");
        Icon icon = new ImageIcon("C:\\Users\\Jose\\Desktop\\Proyecto21\\ScreenSharek\\src\\main\\resources\\TestButton.png");
        connect.setIcon(icon);
        connect.setRolloverIcon(iconHover);
        connect.setBounds(250, 250, 414, 88);
        connect.setBorderPainted(false);
        connect.setContentAreaFilled(false);
        //connect.setPreferredSize(new Dimension(414, 88));
        frame.add(connect);
    }
}
