package jrogueclone.item;

import jrogueclone.Global;
import jrogueclone.game.Vector2D;

public class LootBox extends Item {
    public LootBox(char itemCharacter, int itemCharacterColor, Vector2D itemPosition) {
        this.setItemData(itemCharacter, itemCharacterColor, itemPosition);
    }

    @Override
    public void draw() {
        if(this.isUseable()) {
            // render it
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

    public final static int m_SpawnChance = 40;
}
