package jrogueclone.entity;

import jrogueclone.Global;
import jrogueclone.game.Room;
import jrogueclone.game.Vector2D;
import jrogueclone.item.Weapon;

public class Orc extends Entity {

    public Orc(char entityCharacter, Vector2D entityPosition, Room spawnRoom) {
        super(entityCharacter, entityPosition, spawnRoom);
    }

    @Override
    public void draw() {
        Global.terminalHandler.putChar(getPosition().getX(), getPosition().getY(), getEntityCharacter(), 34, 232,
                false, this);
    }

    @Override
    public void handleEntitySpawn() {

        this.getInventory().addItem(new Weapon("Club",
                30, 20));
        this.getInventory().equipItem(this.getInventory().getItems().elementAt(0));
        this.getHealthController().setHealthCapacity(70);
        this.getHealthController().setHealthMax();
    }

    @Override
    public boolean isMonster() {
        return true;
    }

    @Override
    public void update() {
        handleMovment();
    }

    @Override
    public String toString() {
        return "Orc";
    }
}

