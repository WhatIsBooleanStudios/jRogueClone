package jrogueclone;

import jrogueclone.gfx.TerminalHandler;

import jrogueclone.entity.Player;
import jrogueclone.game.GameState;


// Game Design Doc: https://docs.google.com/document/d/1yalAcBC2sBIdWUnINqwlQOZyY0MLWz4Xha-l0T2WdQA/edit?usp=sharing
public class Global {
    public static void setGameState(GameStates newGameState) {
        switch (newGameState) {
            case STARTMENU: {
                // gameState = startScreen;
            }
            case GAME: {
                // gameState = gameScreen;
            }
        }

        m_CurrentState = newGameState;
        m_GameState.initialize();
    }

    public static GameStates getGaemState() {
        return m_CurrentState;
    }

    // Handles input and renders game view
    private static final TerminalHandler m_TerminalHandler = new TerminalHandler();

    // Our starting player
    private static final Player m_PlayerEntity = new Player('@');

    // Handles game funtions
    private static GameState m_GameState;

    // Level data
    private static GameStates m_CurrentState = GameStates.STARTMENU;

    public enum GameStates {
        STARTMENU,
        GAME
    }

    // Object data
    public enum CharacterAttributes {
        BOLD,
        ITALIC,
        UNDERLINE
    }

    // Render vars
    private static final int Rows = 20;
    private static final int Colomns = 60;
}
