package jrogueclone;

public class App {
    public String getGreeting() {
        return "Hello World!";
    }

    public static void main(String[] args) {
        boolean firstFrame = true;
        int atPosX = 0;
        int atPosY = 0;
        System.out.println(new App().getGreeting());
        Global.terminalHandler.initAlternateScreen();
        Global.terminalHandler.hideCursor();
        while (true) {
            Global.terminalHandler.updateKeyPresses();
            if (Global.terminalHandler.keyIsPressed('q')) {
                System.out.println("quit!");
                Global.terminalHandler.restoreState();
                return;
            }
            if (Global.terminalHandler.keyIsPressed('f')) {
                System.out.println("F is pressed");
            }
            if (Global.terminalHandler.keyIsPressed('\n')) {
                System.out.println("Enter is pressed");
            }
            if (Global.terminalHandler.keyIsPressed('c')) {
                Global.terminalHandler.clear();
            }

            if (!(Global.terminalHandler.keyIsPressed('w') || Global.terminalHandler.keyIsPressed('a') || Global.terminalHandler.keyIsPressed('s') || Global.terminalHandler.keyIsPressed('d'))) {
                if(!firstFrame)
                    continue;
                else
                    firstFrame = false;
            }

            // do game updating here
            if(Global.terminalHandler.keyIsPressed('d')) {
                atPosX++;
            }

            // render here
            Global.terminalHandler.clear();

            Global.terminalHandler.begin();
            for (int i = 0; i < Global.rows; i++) {
                for (int j = 0; j < Global.cols; j++) {
                    Global.terminalHandler.putChar(j, i, '#', 160, 0, j % 2 != 0);
                }
            }

            Global.terminalHandler.putChar(atPosX, atPosY, '@', 165, 40, false);
            Global.terminalHandler.end();

        }
    }
}
