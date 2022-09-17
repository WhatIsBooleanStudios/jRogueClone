package jrogueclone.entity;

import jrogueclone.game.Vector2D;
import jrogueclone.item.Weapon;

public class Bat extends Entity {
    public Bat(char entityCharacter, Vector2D entityPosition) {
        super(entityCharacter, entityPosition);
    }

    @Override
    public void handleEntitySpawn() {
        // Give the player a weapon with a 50% chance to enflict 12 damage
        this.m_AvailableWeapons[0] = new Weapon("Fangs",
                12, 50);

        this.getHealthController().setHealth(20);
        this.m_TilesPerSecond = 1;
    }
}
