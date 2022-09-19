package jrogueclone.game;

import jrogueclone.Global;

public class InputHandler {
    public void update() {
        Global.terminalHandler.updateKeyPresses();

        if (Global.terminalHandler.keyIsPressed('q'))
            Global.getGameLoop().endGame();
    }
}
