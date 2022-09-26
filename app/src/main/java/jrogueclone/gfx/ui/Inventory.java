package jrogueclone.gfx.ui;

import java.util.Vector;

import jrogueclone.Global;
import jrogueclone.item.Item;

public class Inventory {
    public enum ItemType {
        WEAPON,
        POTION,
        LOOTBOX,
        STAIRCASE
    }

    public void addItem(Item item) {
        this.m_Items.add(item);
    }

    public Vector<Item> getItems() {
        return this.m_Items;
    }

    Vector<Item> m_RemoveQueue = new Vector<>();
    public void equipItem(Item newItem) {
        for (Item item : this.m_EquippedItems) {
            if(newItem.getItemType() == ItemType.POTION) {
                Global.terminalHandler.putTopStatusBarString(1, "Drank " + newItem.toString(), 255, 233, false);
                newItem.useItem();
                m_RemoveQueue.add(newItem);
            } else if (item.getItemType() == newItem.getItemType()) {
                Global.terminalHandler.putTopStatusBarString(1, "Equipped " + newItem.toString(), 255, 233, false);
                m_RemoveQueue.add(item);
            }
        }
        this.m_EquippedItems.add(newItem);
        for(Item item : m_RemoveQueue) {
            m_EquippedItems.remove(item);
            if(item.getItemType() == ItemType.POTION)
                m_Items.remove(item);
        }
        m_RemoveQueue.clear();
    }

    public Item getEquippedItem(ItemType itemType) {
        for (Item item : this.m_EquippedItems) {
            if (item.getItemType() == itemType) {
                return item;
            }
        }
        return null;
    }

    private int cursorPos = 0;

    public void updateUI() {
        if(Global.terminalHandler.keyIsPressed('w') && cursorPos > 0) {
            cursorPos--;
        }
        if(Global.terminalHandler.keyIsPressed('s') && cursorPos < getItems().size() - 1) {
            cursorPos++;
        }
        if(Global.terminalHandler.keyIsPressed('\n')) {
            Item item = getItems().get(cursorPos);
            equipItem(getItems().get(cursorPos));
            if(cursorPos >= getItems().size() || item != getItems().get(cursorPos)) {
                cursorPos--;
            }
        }
    }

    public void draw() {
        int i = 0;
        for(Item item : m_Items) {
            String itemString = item.toString();
            String itemNumberString = String.valueOf(i) + ")";
            for(int it = itemNumberString.length(); it < 4; it++) {
                itemNumberString = itemNumberString + " ";
            }
            String finalString = itemNumberString + itemString;
            finalString += " ".repeat(Global.columns - finalString.length());

            int bg = (i == cursorPos ? 255 : 232);
            int fg = (i == cursorPos ? 232 : 255);
            for(int j = 0; j < finalString.length(); j++) {
                Global.terminalHandler.putChar(j, i, finalString.charAt(j), fg, bg, false);
            }
            if(getEquippedItem(item.getItemType()) == item) {
                Global.terminalHandler.putChar(Global.columns - 1 - 2, 0, '✓', fg, bg, true);
            }
            i++;
        }
    }

    private Vector<Item> m_Items = new Vector<Item>(), m_EquippedItems = new Vector<Item>();
}
