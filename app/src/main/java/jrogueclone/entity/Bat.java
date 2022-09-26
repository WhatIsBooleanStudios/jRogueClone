package jrogueclone.entity;

import jrogueclone.Global;
import jrogueclone.game.Room;
import jrogueclone.game.Vector2D;
import jrogueclone.item.Weapon;

public class Bat extends Entity {
    public Bat(char entityCharacter, Vector2D entityPosition, Room spawnRoom) {
        super(entityCharacter, entityPosition, spawnRoom);
    }

    @Override
    public boolean isMonster() {
        return true;
    }

    @Override
    public void handleEntitySpawn() {
        // Give the bat a weapon with a 50% chance to enflict 12 damage
        this.getInventory().addItem(new Weapon("Fangs",
                12, 50));
        this.getInventory().equipItem(this.getInventory().getItems().elementAt(0));
        this.getHealthController().setHealthCapacity(20);
        this.getHealthController().setHealthMax();
    }

    @Override
    public void draw() {
        Global.terminalHandler.putChar(getPosition().getX(), getPosition().getY(), getEntityCharacter(), 94, 232,
                false, this);
    }

    private int m_AnimationFrame = 0;

    @Override
    public void update() {

        m_AnimationFrame++;
        m_AnimationFrame %= 2;
        setEntityCharacter(m_AnimationFrame == 0 ? 'ʌ' : 'v');

        handleMovment();
    }

    @Override
    public String toString() {
        return "Bat";
    }
}
