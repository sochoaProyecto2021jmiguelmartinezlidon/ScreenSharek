package com.screensharek.ui;


import com.screensharek.components.JImage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
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
    private GridBagConstraints constraints;
    private ShareScreen.Mode mode;

    /**
     * Initialize the object and configure the components.
     */
    public void init(ShareScreen.Mode mode) {
        this.mode = mode;
        startComponents();
        startFrame();
        configureFont();
        setDescriptionText();
        startPanels();
        startForm();
        startButtons();
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
        constraints = new GridBagConstraints();
    }

    /**
     * Configure the font.
     */
    public void configureFont() {
        try {
            File fontFile = new File("./Fonts/aAhaWow.ttf");
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
        master.setBackground(new Color(30, 30, 30));
        frame.add(master);
    }

    /**
     * Set de text in the top of the window.
     */
    public void setDescriptionText() {
        description.setText(getTextByMode());
        description.setFont(font);
        description.setAlignmentX(Component.CENTER_ALIGNMENT);
        description.setForeground(new Color(158, 14, 86));
        description.setBackground(new Color(30, 30, 30));
        description.setOpaque(true);
        description.setHorizontalAlignment(JLabel.CENTER);
        description.setVerticalAlignment(JLabel.CENTER);
        master.add(Box.createRigidArea(new Dimension(0, 5)));
        master.add(description);
    }

    /**
     * Configure de panels.
     */
    public void startPanels() {
        form.setLayout(new GridBagLayout());
        form.setBackground(new Color(30, 30, 30));
        form.setMaximumSize(new Dimension(934, 250));
        form.setAlignmentX(Component.CENTER_ALIGNMENT);
        master.add(form);

        buttons.setLayout(new BoxLayout(buttons, BoxLayout.X_AXIS));
        buttons.setBackground(new Color(30, 30, 30));
        buttons.setAlignmentX(Component.CENTER_ALIGNMENT);
        master.add(buttons);
    }

    /**
     * Create form of the window.
     */
    public void startForm() {
        JImage img = new JImage(IPScreen.class.getResource("/TextBox.png").getPath());
        img.setFormat(JImage.Format.ORIGINAL);
        img.setPreferredSize(new Dimension(300, 41));
        ipTextField.setBorder(null);
        ipTextField.setBounds(5, 5, 295, 36);
        ipTextField.setBackground(new Color(0, true));
        ipTextField.setOpaque(false);
        ipTextField.setFont(font);
        ipTextField.setForeground(new Color(71, 13, 121));
        img.add(ipTextField);

        ipLabel.setText("ip: ");
        ipLabel.setFont(font);
        ipLabel.setHorizontalAlignment(JLabel.RIGHT);
        ipLabel.setForeground(new Color(158, 14, 86));
        ipLabel.setLabelFor(img);

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.ipady = 0;
        constraints.gridx = 0;
        constraints.gridy = 0;
        form.add(ipLabel, constraints);

        constraints.ipady = 25;
        constraints.gridx = 1;
        constraints.gridy = 0;
        form.add(img, constraints);

        JImage img2 = new JImage(IPScreen.class.getResource("/TextBox.png").getPath());
        img2.setFormat(JImage.Format.ORIGINAL);
        img2.setPreferredSize(new Dimension(300, 41));
        portTextField.setBorder(null);
        portTextField.setBounds(5, 5, 295, 36);
        portTextField.setBackground(new Color(0, true));
        portTextField.setOpaque(false);
        portTextField.setFont(font);
        portTextField.setForeground(new Color(71, 13, 121));
        img2.add(portTextField);

        portLabel.setText("port: ");
        portLabel.setFont(font);
        portLabel.setForeground(new Color(158, 14, 86));
        portLabel.setLabelFor(img);

        constraints.ipady = 0;
        constraints.gridx = 0;
        constraints.gridy = 1;
        form.add(portLabel, constraints);

        constraints.ipady = 25;
        constraints.gridx = 1;
        constraints.gridy = 1;
        form.add(img2, constraints);
    }

    /**
     * Configure buttons
     */
    public void startButtons() {
        Icon button = new ImageIcon(IPScreen.class.getResource("/ButtonChiquito.png"));
        Icon buttonHover = new ImageIcon(IPScreen.class.getResource("/ButtonChiquitoHover.png"));

        cancel.setIcon(button);
        cancel.setRolloverIcon(buttonHover);
        cancel.setForeground(new Color(0, 0, 0));
        cancel.setPreferredSize(new Dimension(234, 52));
        cancel.setFont(font);
        cancel.setBorderPainted(false);
        cancel.setContentAreaFilled(false);
        cancel.setText("cancelar");
        cancel.setHorizontalTextPosition(SwingConstants.CENTER);
        cancel.setAlignmentY(Component.CENTER_ALIGNMENT);
        cancel.setFocusable(false);
        // TODO: 02/04/2021 put in method in controller.
        /*cancel.addActionListener(actionEvent -> {
            StartScreen startScreen = new StartScreen();
            startScreen.init();
            frame.dispose();
        });*/
        buttons.add(cancel);

        accept.setIcon(button);
        accept.setRolloverIcon(buttonHover);
        accept.setForeground(new Color(0, 0, 0));
        accept.setPreferredSize(new Dimension(234, 52));
        accept.setFont(font);
        accept.setBorderPainted(false);
        accept.setContentAreaFilled(false);
        accept.setText("aceptar");
        accept.setHorizontalTextPosition(SwingConstants.CENTER);
        accept.setAlignmentY(Component.CENTER_ALIGNMENT);
        accept.setFocusable(false);
        // TODO: 02/04/2021 put in method in controller.
        /*accept.addActionListener(actionEvent -> {
            ShareScreen shareScreen = new ShareScreen();
            shareScreen.init(mode);
            frame.dispose();
        });*/
        buttons.add(accept);
    }

    private String getTextByMode() {
        switch (mode) {
            case WATCHING:
                return "<html>Introduce la ip y el puerto<br>al que te quieres conectar</html>";
            case SHARING:
                return "<html>Introduce tu ip y el puerto<br>por donde quieres emitir</html>";
        }
        return "";
    }

    public void setIpAndPort(String ip, String port) {
        ipTextField.setText(ip);
        portTextField.setText(port);
    }

    public void setAcceptListener(ActionListener listener) {
        accept.addActionListener(listener);
    }

    public void setCancelListener(ActionListener listener) {
        cancel.addActionListener(listener);
    }

    public ShareScreen.Mode getMode() {
        return mode;
    }

    public String getIp() {
        return ipTextField.getText();
    }

    public int getPort() {
        return Integer.parseInt(portTextField.getText());
    }

    public void dispose() {
        frame.dispose();
    }
}
