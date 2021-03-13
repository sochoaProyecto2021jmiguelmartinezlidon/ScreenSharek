package com.screensharek.test;

import com.screensharek.components.JImage;

public class TestActualizarImagenes {

    JImage canvas;
    TestCapturaPantalla capture;

    public TestActualizarImagenes(JImage canvas, TestCapturaPantalla capture) {
        this.canvas = canvas;
        this.capture = capture;
    }

    public void update() {
        capture.takeCapture();
        canvas.setImage(capture.getImage());
    }
}
