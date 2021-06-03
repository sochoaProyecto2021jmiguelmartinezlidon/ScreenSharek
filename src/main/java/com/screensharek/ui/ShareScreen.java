package com.screensharek.ui;

import com.screensharek.components.JImage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

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

    /**
     * Initialize the ShareScreen.
     * @param mode
     */
    public void init(Mode mode) {
        this.mode = mode;
        startComponents();
        startFrame();
        startImageComponent();
        startButtons();
        Image image = new ImageIcon(StartScreen.class.getResource("/aleta.png")).getImage();
        frame.setIconImage(image);
        frame.setTitle("ScreenSharek");
        frame.setVisible(true);
    }

    /**
     * Initialize the components in the screen.
     */
    private void startComponents() {
        frame = new JFrame();
        screen = new JImage();
        maximize = new JButton();
        exit = new JButton();
    }

    /**
     * Launch the frame.
     */
    private void startFrame() {
        frame.setSize(1280, 720);
        frame.setMinimumSize(new Dimension(854, 480));
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(new Color(30, 30, 30));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    /**
     * Initialize the component that contains the images.
     */
    private void startImageComponent() {
        //screen.setImage(ShareScreen.class.getResource("/SinTransmision.png").getPath());
        //Icon disconnect = new ImageIcon(ShareScreen.class.getResource("/SinTransmision.png"));
        Image disconnect = null;
        try {
            disconnect = ImageIO.read(ShareScreen.class.getResource("/SinTransmision.png"));
            screen.setImage(disconnect);
        } catch (IOException e) {
        }
        screen.setLayout(new BorderLayout());
        screen.setFormat(JImage.Format.STRETCH);
        frame.add(screen);
    }

    /**
     * Initialize the buttons of the screen.
     */
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

    /**
     * Set the listener to exit button.
     * @param listener
     */
    public void setExitListener(ActionListener listener) {
        exit.addActionListener(listener);
    }

    /**
     * Set the listener to maximize the screen.
     * @param listener
     */
    public void setMaximizeListener(ActionListener listener) {
        maximize.addActionListener(listener);
    }

    /**
     * Switch the screen state between maximize or minimize.
     */
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

    /**
     * Dispose de frame.
     */
    public void dispose() {
        frame.dispose();
    }

    /**
     * Put the image to display.
     * @param img to display.
     */
    public void putImage(byte[] img) {
        screen.setImage(img);
    }
}
