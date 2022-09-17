package jrogueclone;

import jrogueclone.gfx.TerminalHandler;

import com.google.common.collect.EnumBiMap;

import jrogueclone.entity.Player;
import jrogueclone.game.GameState;

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
}
