package jrogueclone.item;

import jrogueclone.game.Vector2D;

import java.util.Vector;

import jrogueclone.Global.CharacterAttributes;

public abstract class Item {
    public void setItemData(char itemCharacter, int itemCharacterColor, Vector2D itemPosition) {
        this.m_ItemCharacter = itemCharacter;
        this.m_ItemCharacterColor = itemCharacterColor;
        this.m_ItemPosition = itemPosition;
        this.m_CharacterAttributes.add(CharacterAttributes.BOLD);
    }
    
    public Vector2D getItemPosition() {
        return this.m_ItemPosition;
    }

    public char getItemCharacter() {
        return this.m_ItemCharacter;
    }

    public int getItemCharacterColor() {
        return this.m_ItemCharacterColor;
    }

    public Vector<CharacterAttributes> getCharacterAttributes() {
        return this.m_CharacterAttributes;
    }

    /*
     * default position is -1, -1
     * This means that the item is in some Entities inventory
     * if the items position is on the map(not -1, -1) it will be seen by the player
     */
    private Vector2D m_ItemPosition = new Vector2D(-1, -1);
    private char m_ItemCharacter;
    private int m_ItemCharacterColor;
    private Vector<CharacterAttributes> m_CharacterAttributes = new Vector<>();
}
