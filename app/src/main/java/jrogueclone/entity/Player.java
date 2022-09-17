package jrogueclone.entity;

import jrogueclone.game.Vector2D;
import jrogueclone.item.Weapon;

public class Player extends Entity {
    public Player(char entityCharacter) {
        super(entityCharacter);
    }

    public Player(char entityCharacter, Vector2D entityPosition) {
        super(entityCharacter, entityPosition);
    }

    @Override
    public void handleEntitySpawn() {

        // Give the player a weapon with a 70% chance to enflict 34 damage
        this.m_AvailableWeapons[0] = new Weapon("Damaged Wooden Sword",
                34, 70);
        
        this.getHealthController().setHealth(100);
        this.m_TilesPerSecond = 1;
    }

}