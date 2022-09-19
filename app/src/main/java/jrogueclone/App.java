package jrogueclone;

import jrogueclone.game.*;
import java.util.Vector;

public class App {
    public String getGreeting() {
        return "Hello World!";
    }

    public static void main(String[] args) {
        Global.terminalHandler.initAlternateScreen();
        Global.getGameLoop().gameHandler();
        Global.terminalHandler.restoreState();
    }
}
