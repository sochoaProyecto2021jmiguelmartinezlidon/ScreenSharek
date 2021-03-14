package com.screensharek.ui;

import javax.swing.*;
import java.awt.*;

public class StartScreen {

    private JFrame frame;
    private JPanel panelButton;
    private JButton connect;
    private JButton shareScreen;
    private Font font;
    private BoxLayout frameLayout;

    /**
     * Initialize the class and configure the components.
     */
    public void init() {
        startComponents();
        configureFrame();
        configurePanelButton();
        configureShareButton();
        configureConnectButton();
        frame.setVisible(true);
    }

    /**
     * Create components.
     */
    public void startComponents() {
        frame = new JFrame();
        connect = new JButton();
        shareScreen = new JButton();
        font = new Font("a Aha Wow", Font.PLAIN, 24);
        panelButton = new JPanel();
        frameLayout = new BoxLayout(panelButton, BoxLayout.Y_AXIS);
    }

    /**
     * Configure the frame.
     */
    public void configureFrame() {
        frame.setSize(new Dimension(935, 436));
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    /**
     * Configure the main panel.
     */
    public void configurePanelButton() {
        panelButton.setLayout(frameLayout);
        panelButton.setBackground(new Color(30, 30, 30));
        frame.add(panelButton);
    }

    /**
     * Configure the button to connect a remote host.
     */
    public void configureConnectButton() {
        Icon iconHover = new ImageIcon("C:\\Users\\Jose\\Desktop\\Proyecto21\\ScreenSharek\\src\\main\\resources\\ButtonMainScreenHover.png");
        Icon icon = new ImageIcon("C:\\Users\\Jose\\Desktop\\Proyecto21\\ScreenSharek\\src\\main\\resources\\ButtonMainScreen.png");

        connect.setIcon(icon);
        connect.setRolloverIcon(iconHover);
        connect.setForeground(new Color(0, 0, 0));
        connect.setPreferredSize(new Dimension(414, 88));
        connect.setFont(font);
        connect.setBorderPainted(false);
        connect.setContentAreaFilled(false);
        connect.setText("CONECTARSE A UN ANFITRION");
        connect.setHorizontalTextPosition(SwingConstants.CENTER);
        connect.setAlignmentX(Component.CENTER_ALIGNMENT);
        connect.setFocusable(false);
        panelButton.add(connect);
        panelButton.add(Box.createRigidArea(new Dimension(0,65)));
    }

    /**
     * Configure the button to start to share the screen.
     */
    public void configureShareButton() {
        Icon iconHover = new ImageIcon("C:\\Users\\Jose\\Desktop\\Proyecto21\\ScreenSharek\\src\\main\\resources\\ButtonMainScreenHover.png");
        Icon icon = new ImageIcon("C:\\Users\\Jose\\Desktop\\Proyecto21\\ScreenSharek\\src\\main\\resources\\ButtonMainScreen.png");
        shareScreen.setIcon(icon);
        shareScreen.setRolloverIcon(iconHover);
        shareScreen.setForeground(new Color(0, 0, 0));
        shareScreen.setPreferredSize(new Dimension(414, 88));
        shareScreen.setFont(font);
        shareScreen.setBorderPainted(false);
        shareScreen.setContentAreaFilled(false);
        shareScreen.setText("COMPARTIR PANTALLA");
        shareScreen.setHorizontalTextPosition(SwingConstants.CENTER);
        shareScreen.setAlignmentX(Component.CENTER_ALIGNMENT);
        shareScreen.setFocusable(false);
        panelButton.add(shareScreen);
    }
}
