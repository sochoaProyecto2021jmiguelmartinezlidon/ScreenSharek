package com.screensharek.test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TestCapturaPantalla {

    private BufferedImage image;
    private Robot robot;

    public TestCapturaPantalla(Robot robot) {
        this.robot = robot;
    }

    public BufferedImage takeCapture() {
        image = robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
        return image;
    }

    public void save() {
        try {
            ImageIO.write(image, "png", new File("D:\\PROYECTOtest\\capture.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BufferedImage getImage() {
        return image;
    }
}
