package jrogueclone;

import jrogueclone.gfx.TerminalHandler;
import com.sun.jna.Native;

import jrogueclone.game.GameLoop;
import jrogueclone.game.GameState;

// Game Design Doc: https://docs.google.com/document/d/1yalAcBC2sBIdWUnINqwlQOZyY0MLWz4Xha-l0T2WdQA/edit?usp=sharing
public class Global {
    public static void setGameState(GameStates stateType, GameState gameState) {
        m_GameState = gameState;
        m_StateType = stateType;
        m_GameState.initialize();
    }

    public static GameStates getGameType() {
        return m_StateType;
    }

    public static GameState getGameState() {
        return m_GameState;
    }

    // Loads the native c library for use by the terminal handler
    public static final LibC libc = (LibC) Native.loadLibrary("c", LibC.class);

    // Handles input and renders game view
    public static final TerminalHandler terminalHandler = new TerminalHandler();

    // Level data
    private static GameStates m_StateType = GameStates.STARTMENU;
    private static GameState m_GameState;

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

    // Game loop related vars/methods
    private final static GameLoop m_GameLoop = new GameLoop();

    public final static GameLoop getGameLoop() {
        return m_GameLoop;
    }

    // Render vars
    public static final int rows = 24;
    public static final int columns = 80;
}
