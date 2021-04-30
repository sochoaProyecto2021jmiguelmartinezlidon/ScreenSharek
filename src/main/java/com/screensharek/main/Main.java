package com.screensharek.main;

import com.screensharek.controller.Controller;
import com.screensharek.ui.StartScreen;

public class Main {
    public static void main(String[] args) {
        /*StartScreen startScreen = new StartScreen();
        startScreen.init();*/
        Controller controller = new Controller();
        controller.startApp();
    }
}
