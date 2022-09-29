package jrogueclone.game;

import jrogueclone.Global;

public class InputHandler {
    public void update() {
        Global.terminalHandler.updateKeyPresses();

        if (Global.terminalHandler.keyIsPressed('q'))
            Global.getGameLoop().endGame();
    }

    public boolean updateGame() {
        if (!Global.getGameLoop().getInventoryToggled()) {
            for (char c : m_KeyMap.toCharArray()) {
                if (Global.terminalHandler.keyIsPressed(c)) {
                    if (c == 'e' || c == 'i') {
                        Global.getGameLoop().setUpdateEntities(false);
                    } else {
                        Global.getGameLoop().setUpdateEntities(true);
                    }
                    return true;
                }

            }
        } else {
            for (char c : m_InventoryKeyMap.toCharArray()) {
                if (Global.terminalHandler.keyIsPressed(c)) {
                    Global.getGameLoop().setUpdateEntities(false);
                    return true;
                }

            }
        }

        return false;
    }

    public static final String m_KeyMap = "iwasdeh ";

    public static final String m_InventoryKeyMap = "xwasd\ni";
}
