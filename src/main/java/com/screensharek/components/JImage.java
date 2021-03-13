package com.screensharek.components;

import javax.swing.*;
import java.awt.*;

// JMML

public class JImage extends JComponent {

    public enum Format {
        STRETCH,
        KEEP,
        ORIGINAL
    }

    private Image image;
    private Format format = Format.ORIGINAL;


    public JImage(Image image) {
        this.image = image;
    }

    // FIXME: 08/06/2020 This has no sense, ..., in a constructor the object is not linked to any UI tree
    //  so the repaint has no effect
    public JImage() {
        repaint();
    }

    public JImage(byte[] img) {
        image = new ImageIcon(img).getImage();
    }

    public JImage(String path) {
        if (path != null) {
            // FIXME: 08/06/2020 Don't this use an exception?
            ImageIcon ii = new ImageIcon(path);
            image = ii.getImage();
        }
    }

    /**
     * Select de format to paint de image in container.
     * @param format enum STRETCH, KEEP or ORIGINAL
     */
    public void setFormat(Format format) {
        this.format = format;
        repaint();
    }

    /**
     * Select the image with an array of bytes obtained by a image and repaint to show thw image.
     * @param img array of bytes
     */
    public void setImage(byte[] img) {
        image = new ImageIcon(img).getImage();
        repaint();
    }

    /**
     * Select the image with the path passed by parameters and repaint to show thw image.
     * @param path String of the path image.
     */
    // FIXME: 08/06/2020 I think this function shoul use the function bellow
    public void setImage(String path) {
        if (path != null) {
            ImageIcon ii = new ImageIcon(path);
            image = ii.getImage();
            repaint();
        }
    }

    /**
     * Select the image with an Image and repaint to show thw image.
     * @param image Image to show
     */
    public void setImage(Image image) {
        this.image = image;
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            if (format == Format.STRETCH) {

                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);

            } else if (format == Format.ORIGINAL) {

                g.drawImage(image, 0, 0, image.getWidth(null), image.getHeight(null), this);


            } else if (format == Format.KEEP) {
                if (image != null) {
                    double widthIMG = image.getWidth(null);
                    double heightIMG = image.getHeight(null);
                    double ratio = widthIMG / heightIMG;
                    double widthContainter = getWidth();
                    double heigthContainer = getHeight();
                    Dimension dimension = getSize();
                    double ratioContainer = widthContainter / heigthContainer;

                    // FIXME: 08/06/2020 This method does not draw the image in the middle of the component
                    //
                    if (ratioContainer <= ratio) {
                        int heigthCalc = (int) (widthContainter / ratio);
                        double posY = (heigthContainer - heightIMG) / 2;
                        g.drawImage(image, 0, 0, (int) widthContainter, heigthCalc, null);

                    } else if (ratioContainer > ratio) {
                        int widthCalc = (int) (heigthContainer * ratio);
                        double posX = (widthContainter / widthIMG) / 2;
                        g.drawImage(image, 0, 0, widthCalc, (int) heigthContainer,null);

                    }

                }
            }
            setOpaque(false);
        } else
            setOpaque(true);


    }
}
