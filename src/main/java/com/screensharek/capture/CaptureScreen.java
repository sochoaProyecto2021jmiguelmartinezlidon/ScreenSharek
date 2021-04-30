package com.screensharek.capture;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class CaptureScreen {
    private Robot robot;

    public CaptureScreen() {

    }

    public void init() {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public byte[] takeCapture() {
        BufferedImage bImage = robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(bImage, "jpg", baos);
            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }
}
