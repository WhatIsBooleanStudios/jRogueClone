package jrogueclone;

import jrogueclone.gfx.TerminalHandler;
import com.sun.jna.Native;

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

    // Loads the native c library for use by the terminal handler
    public static final LibC libc = (LibC) Native.loadLibrary("c", LibC.class);
    // Handles input and renders game view
    public static final TerminalHandler terminalHandler = new TerminalHandler();

    public static final int rows = 20;
    public static final int cols = 60;

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
