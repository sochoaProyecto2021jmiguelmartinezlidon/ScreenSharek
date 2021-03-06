package com.screensharek.controller;

import com.screensharek.capture.CaptureScreen;
import com.screensharek.net.Receiver;
import com.screensharek.net.Sender;
import com.screensharek.ui.IPScreen;
import com.screensharek.ui.ShareScreen;
import com.screensharek.ui.StartScreen;
import com.screensharek.utils.ImageUtils;

import javax.swing.*;

public class Controller {
    private IPScreen ipScreen;
    private ShareScreen shareScreen;
    private StartScreen startScreen;

    private Receiver receiver;
    private Sender sender;

    private CaptureScreen captureScreen;

    /**
     * Initialize the app.
     */
    public void startApp() {
        startScreen = new StartScreen();
        startScreen.init();
        startScreen.setConnectButtonListener(actionEvent -> {
            if (receiver == null)
                initReceiver();
            ipScreen = new IPScreen();
            ipScreen.init(ShareScreen.Mode.WATCHING);
            configureIpScreenButtons();
            startScreen.dispose();
        });
        startScreen.setShareButtonListener(actionEvent -> {
            if (sender == null)
                initSender();
            ipScreen = new IPScreen();
            ipScreen.init(ShareScreen.Mode.SHARING);
            configureIpScreen();
            configureIpScreenButtons();
            startScreen.dispose();
        });
    }

    /**
     * Initialize the sender.
     */
    public void initSender() {
        sender = new Sender();
        sender.init();
    }

    /**
     * Configure the view IpScreen.
     */
    public void configureIpScreen() {
        ipScreen.setIpAndPort(sender.getIp(), String.valueOf(sender.getPort()));
    }

    /**
     * Configure the receiver with the ip and port passed by parameters.
     * @param ip of the computer receiver.
     * @param port of the computer receiver.
     */
    public void setConfigurationReceiver(String ip, int port) {
        receiver.setIpServer(ip);
        receiver.setPortServer(port);
    }

    /**
     * Configure the address that sender must connect
     * @param ip of the computer to connect.
     * @param port of the computer to connect.
     */
    public void setConfigurationSender(String ip, int port) {
        sender.setIp(ip);
        sender.setPort(port);
    }

    /**
     * Initialize the receiver.
     */
    public void initReceiver() {
        receiver = new Receiver();
    }

    /**
     * Configure the buttons of IpScreen.
     */
    public void configureIpScreenButtons() {
        if (ipScreen.getMode() == ShareScreen.Mode.WATCHING) {
            ipScreen.setAcceptListener(actionEvent -> {
                shareScreen = new ShareScreen();
                shareScreen.init(ShareScreen.Mode.WATCHING);
                setConfigurationReceiver(ipScreen.getIp(), ipScreen.getPort());
                configureButtonsShareScreen();

                viewScreen();
                ipScreen.dispose();
            });
        } else {
            ipScreen.setAcceptListener(actionEvent -> {
                shareScreen = new ShareScreen();
                shareScreen.init(ShareScreen.Mode.SHARING);
                setConfigurationSender(ipScreen.getIp(), ipScreen.getPort());
                configureButtonsShareScreen();
                shareScreen();
                ipScreen.dispose();
            });
        }
        ipScreen.setCancelListener(actionEvent -> {
            startApp();
            ipScreen.dispose();
        });
    }

    /**
     * Configure the buttons of ShareScreen.
     */
    public void configureButtonsShareScreen() {
        shareScreen.setExitListener(actionEvent -> {
            if (receiver != null)
                receiver.disconnect();
            System.exit(0);
        });

        shareScreen.setMaximizeListener(actionEvent -> {
            shareScreen.maximizeMinimize();
        });
    }

    /**
     * Start to share screen in a new thread.
     */
    public void shareScreen() {
        new Thread(() -> {
            sender.startShare();
            int response = 1;
            if (captureScreen == null) {
                captureScreen = new CaptureScreen();
                captureScreen.init();
            }
            do {
                byte[] image = captureScreen.takeCapture();
                shareScreen.putImage(image);
                sender.sendImage(ImageUtils.splitImage(image));
                try {
                    Thread.sleep(16);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //response = sender.getResponse();
                //System.out.println(response);
            } while (response != 2);
        }).start();
    }

    /**
     * Start to show the images are coming from the other computer in a new thread.
     */
    public void viewScreen() {
        new Thread(() -> {
            receiver.connect();
            while (true) {
                byte[][] imageMessy = receiver.getSplitImage();
                shareScreen.putImage(ImageUtils.assembleImage(imageMessy));
                //receiver.checkDisconnect();
            }
        }).start();

    }
}
