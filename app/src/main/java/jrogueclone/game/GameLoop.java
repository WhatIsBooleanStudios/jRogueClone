package jrogueclone.game;

import jrogueclone.Global;
import jrogueclone.Global.GameStates;

public class GameLoop {
    public void gameHandler() {
        Global.setGameState(GameStates.GAME, this.m_CurrentLevel);

        boolean firstFrame = true;
        while (!this.m_EndGame) {
            Global.terminalHandler.begin();
            this.m_InputHandler.update();
            try {
                if(!firstFrame && !(Global.terminalHandler.keyIsPressed(' ') ||
                                    Global.terminalHandler.keyIsPressed('w') || 
                                    Global.terminalHandler.keyIsPressed('a') || 
                                    Global.terminalHandler.keyIsPressed('s') || 
                                    Global.terminalHandler.keyIsPressed('d'))) {
                    Thread.sleep(100);
                    continue;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            firstFrame = false;
            System.out.flush();

            Global.getGameState().update();

            Global.terminalHandler.end();
            Global.terminalHandler.resetCursor();
        }
    }

    public void endGame() {
        this.m_EndGame = true;
    }

    private Level m_CurrentLevel = MapGeneration.generateLevel();
    private final InputHandler m_InputHandler = new InputHandler();
    private boolean m_EndGame = false;
}
