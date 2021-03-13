package com.screensharek.capture;

public class CaptureScreen {
    private byte[] videoBuffer;

    public CaptureScreen(int bufferSize) {
        videoBuffer = new byte[bufferSize];
    }
}
