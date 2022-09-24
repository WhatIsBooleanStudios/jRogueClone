package jrogueclone.gfx.ui;

import java.util.Vector;

import jrogueclone.item.Item;

public class Inventory {
    public enum ItemType {
        WEAPON,
        FOOD,
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

    public void equipItem(Item newItem) {
        for (Item item : this.m_EquippedItems) {
            if (item.getItemType() == newItem.getItemType())
                m_EquippedItems.remove(item);
        }
        this.m_EquippedItems.add(newItem);
    }

    public Item getEquippedItem(ItemType itemType) {
        for (Item item : this.m_EquippedItems) {
            if (item.getItemType() == itemType) {
                return item;
            }
        }
        return null;
    }

    private Vector<Item> m_Items = new Vector<Item>(), m_EquippedItems = new Vector<Item>();
}
