package com.screensharek.ui;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class StartScreen {

    private JFrame frame;
    private JPanel panelButton;
    private JButton connect;
    private JButton shareScreen;
    private Font font;
    private BoxLayout panelButtonLayout;

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
        configureFont();
        panelButton = new JPanel();
        panelButtonLayout = new BoxLayout(panelButton, BoxLayout.Y_AXIS);
    }

    /**
     * Configure the font of buttons.
     */
    public void configureFont() {
        try {
            createFontIfNotExists();
            File fontFile = new File("./Fonts/aAhaWow.ttf");
            System.out.println(fontFile.getPath());
            font = Font.createFont(Font.PLAIN, fontFile).deriveFont(24f);
        } catch (FontFormatException | IOException | NullPointerException e) {
            // TODO: Delete print stack trace when develop is end.
            e.printStackTrace();
            font = new Font("Arial", Font.PLAIN, 24);
        }
    }

    /**
     * Check if the folder with the font exists if not exist make the folder and add the font.
     */
    public void createFontIfNotExists() {
        File fonts = new File("./Fonts");
        if (!fonts.exists())
            fonts.mkdir();
        File font = new File("./Fonts/aAhaWow.ttf");
        if (!font.exists()) {
            try {
                font.createNewFile();
                InputStream input = getClass().getResourceAsStream("/Fonts/aAhaWow.ttf");
                BufferedInputStream inputReader = new BufferedInputStream(input); //StartScreen.class.getResource("/Fonts/aAhaWow.ttf").getPath()
                List<Byte> fontList = new ArrayList<>();
                int bte;
                while ((bte = inputReader.read()) > -1) {
                    fontList.add((byte) bte);
                }
                inputReader.close();
                byte[] bytes = new byte[fontList.size()];
                for (int i = 0; i < bytes.length; i++) {
                    bytes[i] = fontList.get(i);
                }
                BufferedOutputStream outputWriter = new BufferedOutputStream(new FileOutputStream("./Fonts/aAhaWow.ttf"));
                for (int i = 0; i < bytes.length; i++) {
                    outputWriter.write(bytes[i]);
                }
                outputWriter.close();
            } catch (IOException e) {
                // TODO: Change auto-generated code.
                e.printStackTrace();
            }
        }
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
        //JPanel panel = new JPanel();
        //panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        //panel.setBackground(new Color(97, 31, 0));
        panelButton.setLayout(panelButtonLayout);
        panelButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelButton.setBackground(new Color(30, 30, 30));
        //panel.add(panelButton);
        frame.add(panelButton);
    }

    /**
     * Configure the button to connect a remote host.
     */
    public void configureConnectButton() {
        Icon iconHover = new ImageIcon(StartScreen.class.getResource("/ButtonMainScreenHover.png"));
        Icon icon = new ImageIcon(StartScreen.class.getResource("/ButtonMainScreen.png"));

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
        connect.setAlignmentY(Component.CENTER_ALIGNMENT);
        connect.setFocusable(false);
        panelButton.add(connect);
        panelButton.add(Box.createRigidArea(new Dimension(0,65)));
    }

    /**
     * Configure the button to start to share the screen.
     */
    public void configureShareButton() {
        Icon iconHover = new ImageIcon(StartScreen.class.getResource("/ButtonMainScreenHover.png"));
        Icon icon = new ImageIcon(StartScreen.class.getResource("/ButtonMainScreen.png"));
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
        shareScreen.setAlignmentY(Component.CENTER_ALIGNMENT);
        shareScreen.setFocusable(false);
        panelButton.add(shareScreen);
    }
}
