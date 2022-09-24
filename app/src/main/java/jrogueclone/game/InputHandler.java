package jrogueclone.game;

import jrogueclone.Global;

public class InputHandler {
    public void update() {
        Global.terminalHandler.updateKeyPresses();

        if (Global.terminalHandler.keyIsPressed('q'))
            Global.getGameLoop().endGame();
    }

    public boolean updateGame() {
        for (char c : m_KeyMap.toCharArray()) {
            if (Global.terminalHandler.keyIsPressed(c)) {
                if (c == 'e')
                    Global.getGameLoop().setUpdateEntities(false);
                else
                    Global.getGameLoop().setUpdateEntities(true);
                return true;
            }

        }

        return false;
    }

    public static final String m_KeyMap = "wasde ";
}
