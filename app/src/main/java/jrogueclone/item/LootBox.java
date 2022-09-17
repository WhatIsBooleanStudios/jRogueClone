package jrogueclone.item;

import jrogueclone.game.Vector2D;

public class LootBox extends Item {
    LootBox(char itemCharacter, int itemCharacterColor, Vector2D itemPosition) {
        this.setItemData(itemCharacter, itemCharacterColor, itemPosition);
    }   
}
