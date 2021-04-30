package com.screensharek.ui;

import com.screensharek.components.JImage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ShareScreen {

    public enum Mode {
        SHARING, WATCHING
    }

    private JFrame frame;
    private JImage screen;
    private JButton maximize;
    private JButton exit;
    private Mode mode;
    private boolean isMaximized = false;
    private final Icon minimizeIcon = new ImageIcon(ShareScreen.class.getResource("/FullscreenMinimize.png"));
    private final Icon maximizeIcon = new ImageIcon(ShareScreen.class.getResource("/FullscreenMaximize.png"));

    public void init(Mode mode) {
        this.mode = mode;
        startComponents();
        startFrame();
        startImageComponent();
        startButtons();
        frame.setVisible(true);
    }

    private void startComponents() {
        frame = new JFrame();
        screen = new JImage();
        maximize = new JButton();
        exit = new JButton();
    }

    private void startFrame() {
        frame.setSize(1280, 720);
        frame.setMinimumSize(new Dimension(854, 480));
        frame.setLocationRelativeTo(null);
        frame.setBackground(new Color(30, 30, 30));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void startImageComponent() {
        screen.setImage(ShareScreen.class.getResource("/Testeo.png").getPath());
        screen.setLayout(new BorderLayout());
        screen.setFormat(JImage.Format.STRETCH);
        frame.add(screen);
    }

    private void startButtons() {


        Icon exitIcon = new ImageIcon(ShareScreen.class.getResource("/Exit.png"));
        Icon exitIconHover = new ImageIcon(ShareScreen.class.getResource("/ExitHover.png"));
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setOpaque(false);
        panel.setBackground(new Color(64, 222, 59, 0));
        panel.add(Box.createHorizontalGlue());
        exit.setIcon(exitIcon);
        exit.setRolloverIcon(exitIconHover);
        exit.setBorderPainted(false);
        exit.setBorder(null);
        exit.setFocusable(false);
        exit.setContentAreaFilled(false);
        // TODO: 02/04/2021 put in a method of controller.
        /*exit.addActionListener(actionEvent -> {
            StartScreen startScreen = new StartScreen();
            startScreen.init();
            frame.dispose();
        });*/
        maximize.setIcon(maximizeIcon);
        maximize.setBorderPainted(false);
        maximize.setBorder(null);
        maximize.setFocusable(false);
        maximize.setContentAreaFilled(false);
        panel.add(Box.createRigidArea(new Dimension(0, 40)));
        panel.add(exit);
        panel.add(Box.createRigidArea(new Dimension(10, 0)));
        panel.add(maximize);
        panel.add(Box.createRigidArea(new Dimension(5, 0)));
        screen.add(panel, BorderLayout.PAGE_END);
    }

    public void setExitListener(ActionListener listener) {
        exit.addActionListener(listener);
    }

    public void setMaximizeListener(ActionListener listener) {
        maximize.addActionListener(listener);
    }

    public void maximizeMinimize() {
        if (isMaximized) {
            frame.setExtendedState(JFrame.NORMAL);
            //frame.setUndecorated(false);
            maximize.setIcon(maximizeIcon);
            isMaximized = false;
        } else {
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            //frame.setUndecorated(true);
            maximize.setIcon(minimizeIcon);
            isMaximized = true;
        }

    }

    public void dispose() {
        frame.dispose();
    }

    public void putImage(byte[] img) {
        screen.setImage(img);
    }
}
