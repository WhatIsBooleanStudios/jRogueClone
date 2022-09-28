package jrogueclone.entity;

import jrogueclone.Global;
import jrogueclone.game.Room;
import jrogueclone.game.Vector2D;
import jrogueclone.item.Weapon;

public class Eye extends Entity {

    public Eye(char entityCharacter, Vector2D entityPosition, Room spawnRoom) {
        super(entityCharacter, entityPosition, spawnRoom);
    }

    @Override
    public void draw() {
        Global.terminalHandler.putChar(getPosition().getX(), getPosition().getY(), getEntityCharacter(), 230, 232,
                false, this);
    }

    @Override
    public void handleEntitySpawn() {

        this.getInventory().addItem(new Weapon("Emotional damage",
                10, 55, Integer.MAX_VALUE));
        this.getInventory().equipItem(this.getInventory().getItems().elementAt(0));
        this.getHealthController().setHealthCapacity(30);
        this.getHealthController().setHealthMax();
    }

    @Override
    public boolean isMonster() {
        return true;
    }

    @Override
    public void update() {
        handleMovement();
    }

    @Override
    public String toString() {
        return "Eye";
    }
    
    @Override
    public int getExperienceReward() {
        return 5;
    }
}
