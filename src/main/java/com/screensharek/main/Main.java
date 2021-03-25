package com.screensharek.main;

import com.screensharek.ui.IPScreen;
import com.screensharek.ui.StartScreen;

public class Main {
    public static void main(String[] args) {
        StartScreen startScreen = new StartScreen();
        startScreen.init();
        IPScreen ipScreen = new IPScreen();
        ipScreen.init("<html>Introduce tu ip y el puerto<br>por donde quieres emitir</html>");
    }
}
