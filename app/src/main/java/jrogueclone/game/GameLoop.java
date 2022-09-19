package jrogueclone.game;

import jrogueclone.Global;
import jrogueclone.Global.GameStates;

public class GameLoop {
    public void gameHandler() {
        try {
            Global.terminalHandler.initAlternateScreen();
            Global.setGameState(GameStates.GAME, this.m_CurrentLevel);

            while (this.m_EndGame != true) {
                Global.terminalHandler.begin();
                this.m_InputHandler.update();

                Global.getGameState().update();

                Global.terminalHandler.end();
                Thread.sleep(100);
            }
            Global.terminalHandler.restoreState();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void endGame() {
        this.m_EndGame = true;
    }

    private Level m_CurrentLevel = MapGeneration.generateLevel();
    private InputHandler m_InputHandler = new InputHandler();
    private boolean m_EndGame = false;
}
