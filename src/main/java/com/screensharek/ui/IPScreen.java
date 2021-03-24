package com.screensharek.ui;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class IPScreen {

    private JFrame frame;
    private JLabel description;
    private JLabel ipLabel;
    private JLabel portLabel;
    private JTextField ipTextField;
    private JTextField portTextField;
    private JButton accept;
    private JButton cancel;
    private JPanel form;
    private JPanel buttons;
    private JPanel master;
    private Font font;
    private String text;

    /**
     * Initialize the object and configure the components.
     */
    public void init(String text) {
        this.text = text;
        startComponents();
        startFrame();
        configureFont();
        setDescriptionText();
        startPanels();
        frame.setVisible(true);
    }

    /**
     * Create components.
     */
    public void startComponents() {
        frame = new JFrame();
        description = new JLabel();
        ipLabel = new JLabel();
        portLabel = new JLabel();
        ipTextField = new JTextField();
        portTextField = new JTextField();
        accept = new JButton();
        cancel = new JButton();
        master = new JPanel();
        form = new JPanel();
        buttons = new JPanel();
    }

    /**
     * Configure the font.
     */
    public void configureFont() {
        try {
            File fontFile = new File("./Fonts/aAhaWow.ttf");
            System.out.println(fontFile.getPath());
            font = Font.createFont(Font.PLAIN, fontFile).deriveFont(30f);
        } catch (FontFormatException | IOException | NullPointerException e) {
            // TODO: Delete print stack trace when develop is end.
            e.printStackTrace();
            font = new Font("Arial", Font.PLAIN, 30);
        }
    }

    /**
     * Configure the frame.
     */
    public void startFrame() {
        frame.setSize(new Dimension(935, 436));
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setBackground(new Color(30, 30, 30));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        master.setLayout(new BoxLayout(master, BoxLayout.Y_AXIS));
        frame.add(master);
    }

    /**
     * Set de text in the top of the window.
     */
    public void setDescriptionText() {
        description.setText("<html>Introduce tu ip y el puerto<br>por donde quieres emitir</html>");
        description.setFont(font);
        description.setAlignmentX(Component.CENTER_ALIGNMENT);
        description.setForeground(new Color(158, 14, 86));
        description.setBackground(new Color(30, 30, 30));
        description.setOpaque(true);

        description.setPreferredSize(new Dimension(100, 100));
        master.add(description);
    }

    /**
     * Configure de panels.
     */
    public void startPanels() {
        form.setLayout(new SpringLayout());
        form.setBackground(new Color(30, 30, 30));
        form.setAlignmentX(Component.CENTER_ALIGNMENT);
        master.add(form);

        buttons.setLayout(new BoxLayout(buttons, BoxLayout.X_AXIS));
        buttons.setBackground(new Color(30, 30, 30));
        buttons.setAlignmentX(Component.CENTER_ALIGNMENT);
        master.add(buttons);
    }
}
