package jrogueclone.entity;

import jrogueclone.Global;
import jrogueclone.game.Vector2D;
import jrogueclone.item.Weapon;

public class Bat extends Entity {
    public Bat(char entityCharacter, Vector2D entityPosition) {
        super(entityCharacter, entityPosition);
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

        this.getHealthController().setHealth(20);
    }

    @Override
    public void draw() {
        Global.terminalHandler.putChar(getPosition().getX(), getPosition().getY(), getEntityCharacter(), 255, 232,
                false, this);
    }

    private int m_AnimationFrame = 0;

    @Override
    public void update() {

        m_AnimationFrame++;
        m_AnimationFrame %= 2;
        setEntityCharacter(m_AnimationFrame == 0 ? 'ʌ' : 'v');

        if (this.getHealthController().getHealth() <= 0)
            this.handleDeath();
    }

    @Override
    public void handleDeath() {
        // TODO Auto-generated method stub


    }
}
