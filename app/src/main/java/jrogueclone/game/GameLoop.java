package jrogueclone.game;

import jrogueclone.Global;
import jrogueclone.Global.GameStates;

public class GameLoop {
    public static int getLevelDifficulty() {
        return m_CurrentLevel.getDifficulty();
    }

    public boolean updateEntities() {
        return this.m_UpdateEntities;
    }
    public void setUpdateEntities(boolean updateEntities) {
        this.m_UpdateEntities = updateEntities;
    }
    public void gameHandler() {
        Global.setGameState(GameStates.GAME, m_CurrentLevel);

        boolean firstFrame = true;
        while (!this.m_EndGame) {
            Global.terminalHandler.begin();
            this.m_InputHandler.update();
            try {
                if (!firstFrame && !this.m_InputHandler.updateGame()) {
                    Thread.sleep(100);
                    continue;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            firstFrame = false;
            Global.getGameState().update();

            Global.terminalHandler.end();
            Global.terminalHandler.resetCursor();
        }
    }

    public void endGame() {
        this.m_EndGame = true;
    }


    private static Level m_CurrentLevel = MapGeneration.generateLevel();
    private InputHandler m_InputHandler = new InputHandler();
    private boolean m_EndGame = false, m_UpdateEntities = false;
    public boolean m_Inventory = false;
}
