package jrogueclone.item;

import jrogueclone.Global;
import jrogueclone.game.Vector2D;
import jrogueclone.gfx.ui.Inventory.ItemType;

public class LootBox extends Item {
    public LootBox(char itemCharacter, int itemCharacterColor, Vector2D itemPosition) {
        this.setItemData(itemCharacter, itemCharacterColor, itemPosition, ItemType.LOOTBOX);
    }

    @Override
    public void draw() {
        if (this.m_ItemPosition != new Vector2D(-1, -1)) {

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
    public String toString() {
        return "LootBox";
    }

    @Override
    public void useItem() {
        this.m_ItemUsed = true;
        this.setItemCharacter('␣');
        this.draw();
        if (Math.random() * 100 <= 50) { // potion
            Global.getGameLoop().getCurrentLevel().getPlayer().getInventory()
                    .addItem(new Potion((Potion)Global.Items.get((int)(Math.random() * 3))));
        } else { // weapon
            Global.getGameLoop().getCurrentLevel().getPlayer().getInventory()
                    .addItem(new Weapon((Weapon)Global.Items.get((int) (Math.random() * (Global.Items.size() - 3)) + 3)));
        }
        Global.terminalHandler.putTopStatusBarString(
                1,
                "Player got " + Global.getGameLoop().getCurrentLevel().getPlayer().getInventory().getItems().lastElement(),
                255,
                232,
                false);

    }

    public final static int m_SpawnChance = 40;
}
