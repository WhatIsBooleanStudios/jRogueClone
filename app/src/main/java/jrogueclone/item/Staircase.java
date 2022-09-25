package jrogueclone.item;

import jrogueclone.Global;
import jrogueclone.entity.Player;
import jrogueclone.game.MapGeneration;
import jrogueclone.game.Vector2D;
import jrogueclone.gfx.ui.Inventory.ItemType;

public class Staircase extends Item {
    public Staircase(char itemCharacter, int itemCharacterColor, Vector2D itemPosition) {
        this.setItemData(itemCharacter, itemCharacterColor, itemPosition, ItemType.STAIRCASE);
    }

    @Override
    public void useItem() {
        this.m_ItemUsed = true;
        Player curPlayer = Global.getGameLoop().getCurrentLevel().getPlayer();
        Global.getGameLoop().setCurentLevel(MapGeneration.generateLevel(curPlayer));
    }

    @Override
    public void draw() {
        Global.terminalHandler.putChar(this.getItemPosition().getX(), this.getItemPosition().getY(),
                this.getItemCharacter(), this.getItemCharacterColor(), 232, false, this);

    }

    @Override
    public String toString() {
        return "staircase";
    }
}
