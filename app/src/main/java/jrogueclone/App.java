package jrogueclone;

public class App {
    public static void main(String[] args) {
        Global.initializeItems();
        Global.terminalHandler.initAlternateScreen();
        Global.terminalHandler.hideCursor();
        Global.getGameLoop().gameHandler();
        Global.terminalHandler.restoreState();
    }
}
