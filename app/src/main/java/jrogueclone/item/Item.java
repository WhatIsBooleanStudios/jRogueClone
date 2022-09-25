package jrogueclone.item;

import jrogueclone.game.Vector2D;
import jrogueclone.gfx.ui.Inventory.ItemType;

import java.util.Vector;

import jrogueclone.Global.CharacterAttributes;

public abstract class Item {
    public void setItemData(char itemCharacter, int itemCharacterColor, Vector2D itemPosition, ItemType itemType) {
        this.m_ItemCharacter = itemCharacter;
        this.m_ItemCharacterColor = itemCharacterColor;
        this.m_ItemPosition = itemPosition;
        this.m_ItemType = itemType;
        this.m_CharacterAttributes.add(CharacterAttributes.BOLD);
    }

    public abstract String toString();

    public ItemType getItemType() {
        return this.m_ItemType;
    }

    public Vector2D getItemPosition() {
        return this.m_ItemPosition;
    }

    public char getItemCharacter() {
        return this.m_ItemCharacter;
    }

    public void setItemCharacter(char itemCharacter) {
        this.m_ItemCharacter = itemCharacter;
    }

    public int getItemCharacterColor() {
        return this.m_ItemCharacterColor;
    }

    public Vector<CharacterAttributes> getCharacterAttributes() {
        return this.m_CharacterAttributes;
    }

    /*
     * Is available on the map to be picked up or used.
     */
    public boolean isUseable() {
        return !this.m_ItemUsed;
    }

    public abstract void useItem();
    public abstract void draw();

    /*
     * default position is -1, -1
     * This means that the item is in some Entities inventory
     * if the items position is on the map(not -1, -1) it will be seen by the player
     */
    protected boolean m_ItemUsed = false;
    protected Vector2D m_ItemPosition = new Vector2D(-1, -1);
    private char m_ItemCharacter;
    protected int m_ItemCharacterColor;
    private Vector<CharacterAttributes> m_CharacterAttributes = new Vector<>();
    private ItemType m_ItemType;
}
