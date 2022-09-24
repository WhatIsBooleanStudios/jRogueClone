package jrogueclone.game;

import jrogueclone.Global;
import jrogueclone.Global.GameStates;

public class GameLoop {
    public void setCurentLevel(Level currentLevel) {
        m_CurrentLevel = currentLevel;
        this.m_UpdateLevel = true;
    }

    public Level getCurrentLevel() {
        return m_CurrentLevel;
    }

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

            if (this.m_UpdateLevel) {
                Global.setGameState(GameStates.GAME, m_CurrentLevel);
                this.m_UpdateLevel = false;
            }

            Global.terminalHandler.end();
            Global.terminalHandler.resetCursor();
        }
    }

    public void endGame() {
        this.m_EndGame = true;
    }

    private static Level m_CurrentLevel = MapGeneration.generateLevel(null);
    private InputHandler m_InputHandler = new InputHandler();
    private boolean m_EndGame = false, m_UpdateEntities = false, m_UpdateLevel = false;
    public boolean m_Inventory = false;

}
