package jrogueclone;

import jrogueclone.gfx.TerminalHandler;
import jrogueclone.item.Item;
import jrogueclone.item.Potion;
import jrogueclone.item.Weapon;
import jrogueclone.item.Potion.PotionType;

import java.util.ArrayList;
import java.util.List;

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
        INVENTORY,
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

    public static final int topStatusBarColumns = 1;
    public static final int bottomStatusBarColumns = 2;


    // Game items
    public static List<Item> Items = new ArrayList<Item>();
    public static void initializeItems () {

        // Add potions to the list of items
        Items.add(new Potion(PotionType.HEALTH));
        Items.add(new Potion(PotionType.INVISIBILTY));
        Items.add(new Potion(PotionType.MYSTERY));
        Items.add(new Potion(PotionType.DOUBLEXP));

        // Add weapons to the list of items
        Items.add(new Weapon("Damaged Wooden Spear", 20, 75, 7));
        Items.add(new Weapon("Sharp Steel Spear", 45, 85, 10));
        Items.add(new Weapon("Weathered Wood Knife", 15, 30, 5));
        Items.add(new Weapon("Unparalleled Steel Scythe", 50, 80, 15));
        Items.add(new Weapon("Steel Axe", 35,80, 10));
        Items.add(new Weapon("Sickle", 30, 70, 7));
        Items.add(new Weapon("Trident", 40, 65, 18));
        Items.add(new Weapon("Long Sword", 45, 85, 20));
        Items.add(new Weapon("Legendary Long Sword", 40, 100, 20));
        Items.add(new Weapon("Short Sword", 35, 75, 15));
        Items.add(new Weapon("Rusty Dagger", 45, 40, 3));
    }

}
