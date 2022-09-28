package jrogueclone.gfx.ui;

import java.util.Vector;

import jrogueclone.Global;
import jrogueclone.entity.Entity.HealthController;
import jrogueclone.item.Item;
import jrogueclone.item.Potion;
import jrogueclone.item.Potion.PotionType;

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
    
    public void zeroCursor() {
        cursorPos = 0;
    }

    Vector<Item> m_RemoveQueue = new Vector<>();

    public void equipItem(Item newItem) {
        for (Item item : this.m_EquippedItems) {
            if (newItem.getItemType() == ItemType.POTION) {
                HealthController hc = Global.getGameLoop().getCurrentLevel().getPlayer().getHealthController();
                Potion potion = (Potion)newItem;
                if (hc.getMaxHealth() == hc.getHealth() && potion.getPotionType() == PotionType.HEALTH) {
                    Global.terminalHandler.putTopStatusBarString(1, "HP is allready full!", 255, 233, false);
                    return;
                }
                    
                Global.terminalHandler.putTopStatusBarString(1, "Drank " + newItem.toString(), 255, 233, false);
                newItem.useItem();
                m_RemoveQueue.add(newItem);
                break;
            } else if (item.getItemType() == newItem.getItemType()) {
                Global.terminalHandler.putTopStatusBarString(1, "Equipped " + newItem.toString(), 255, 233, false);
                m_RemoveQueue.add(item);
            }
        }
        this.m_EquippedItems.add(newItem);
        for (Item item : m_RemoveQueue) {
            m_EquippedItems.remove(item);
            if (item.getItemType() == ItemType.POTION) {
                m_Items.remove(item);
            }
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
    
    public Vector<Item> getEquippedItems() {
        return m_EquippedItems;
    }

    private int cursorPos = 0;
    private int currentPage = 0;

    public void updateUI() {
        if (Global.terminalHandler.keyIsPressed('w') && cursorPos > 0) {
            cursorPos--;
        }
        if(Global.terminalHandler.keyIsPressed('s') && cursorPos < Global.rows - 1 &&
                !(currentPage == Math.ceil((double)m_Items.size() / Global.rows) - 1 && cursorPos >= (m_Items.size() % Global.rows - 1))) {
            cursorPos++;
        }
        if(Global.terminalHandler.keyIsPressed('a') && currentPage > 0) {
            currentPage--;
            cursorPos = 0;
        }
        if(Global.terminalHandler.keyIsPressed('d') && currentPage < ((int)Math.ceil((double)getItems().size() / Global.rows)) - 1) {
            currentPage++;
            cursorPos = 0;
        }
        if(Global.terminalHandler.keyIsPressed('\n')) {
            Item item = getItems().get(cursorPos + currentPage * Global.rows);
            equipItem(getItems().get(cursorPos + currentPage * Global.rows));
//            if(item != getItems().get(cursorPos + currentPage * 24)) {
//                cursorPos--;
//            }
            /*System.out.println(currentPage == Math.ceil((double)m_Items.size() / Global.rows) - 1);
            System.out.println("size: " + m_Items.size());
            System.out.println(cursorPos > m_Items.size() % Global.rows - 1);
            System.out.println(cursorPos + " > " + (m_Items.size() % Global.rows - 1));*/
            if(currentPage == Math.ceil((double)m_Items.size() / Global.rows) - 1 && cursorPos > (m_Items.size() % Global.rows - 1)) {
                cursorPos--;
            }
        }
        if(Global.terminalHandler.keyIsPressed('x')) {
            Item itemUnderCursor = getItems().get(cursorPos + currentPage * Global.rows);
            Global.terminalHandler.putTopStatusBarString(1, "Delete " + itemUnderCursor, 255, 233, false);
            m_Items.remove(itemUnderCursor);
            if(getEquippedItem(itemUnderCursor.getItemType()) == itemUnderCursor) {
                boolean foundEquippedItem = false;
                for(int i = 0; i < m_Items.size(); i++) {
                    if(m_Items.get(i).getItemType() == itemUnderCursor.getItemType()) {
                        equipItem(m_Items.get(i));
                        foundEquippedItem = true;
                        break;
                    }
                }
                if(!foundEquippedItem) {
                    m_EquippedItems.remove(itemUnderCursor);
                }
            }
            if(currentPage == Math.ceil((double)m_Items.size() / Global.rows) - 1 && cursorPos > (m_Items.size() % Global.rows - 1)) {
                cursorPos--;
            }
        }
        if(currentPage > Math.ceil((double)m_Items.size() / Global.rows) - 1) {
            currentPage = (int)Math.ceil((double)m_Items.size() / Global.rows) - 1;
            cursorPos = 23;
            if(currentPage < 0) {currentPage = 0; cursorPos = 0;}
        }
        if(cursorPos < 0 && currentPage > 0) {
            currentPage--;
            cursorPos = 23;
        }
    }

    public void draw() {
        if(m_Items.size() > 0) {
            for(int i = currentPage * Global.rows; i < (currentPage + 1) * Global.rows; i++) {
                if(i > m_Items.size() - 1) {
                    break;
                }
                Item item = getItems().get(i);
                String itemString = item.toString();
                String itemNumberString = String.valueOf(i) + ")";
                for (int it = itemNumberString.length(); it < 4; it++) {
                    itemNumberString = itemNumberString + " ";
                }
                String finalString = itemNumberString + itemString;
                finalString += " ".repeat(Global.columns - finalString.length());

                int bg = (i % Global.rows == cursorPos ? 255 : 232);
                int fg = (i % Global.rows == cursorPos ? 232 : 255);

                for(int j = 0; j < finalString.length(); j++) {
                    Global.terminalHandler.putChar(j, i % Global.rows, finalString.charAt(j), fg, bg, false);
                }
                if(getEquippedItem(item.getItemType()) == item) {
                    Global.terminalHandler.putChar(Global.columns - 1 - 2, i % 24, '✓', fg, bg, true);
                }
            }
        }

        String helpString = "w/s=PRV_ITM/NXT_ITM  a/d=PRV_PG/NXT_PG  <RET>=USE  x=DEL";
        String pageString = "pg=[" + (currentPage + 1) + "/" + (int)Math.ceil((double)m_Items.size() / Global.rows) + "]";
        Global.terminalHandler.putBottomStatusBarString(0, helpString + " ".repeat(Global.columns - helpString.length() - pageString.length()) + pageString, 255, 232, false);
    }

    private Vector<Item> m_Items = new Vector<Item>(), m_EquippedItems = new Vector<Item>();
}
