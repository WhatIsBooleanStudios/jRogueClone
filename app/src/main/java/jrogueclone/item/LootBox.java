package jrogueclone.item;

import jrogueclone.Global;
import jrogueclone.game.Vector2D;

public class LootBox extends Item {
    public LootBox(char itemCharacter, int itemCharacterColor, Vector2D itemPosition) {
        this.setItemData(itemCharacter, itemCharacterColor, itemPosition);
    }

    @Override
    public void draw() {
        if(this.m_ItemPosition != new Vector2D(-1, -1)) {

            Global.terminalHandler.putChar(
                getItemPosition().getX(),
                getItemPosition().getY(),
                this.getItemCharacter(),
                m_ItemCharacterColor,
                Global.terminalHandler.getBackgroundColorAt(getItemPosition().getX(), getItemPosition().getY()),
                true,
                this);
        }
    }

    @Override
    public void useItem() {
        this.m_ItemUsed = true;
        this.setItemCharacter('␣');
        this.draw();
    }

    public final static int m_SpawnChance = 40;
}
