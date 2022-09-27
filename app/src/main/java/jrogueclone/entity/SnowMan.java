package jrogueclone.entity;

import jrogueclone.Global;
import jrogueclone.game.Room;
import jrogueclone.game.Vector2D;
import jrogueclone.item.Weapon;

public class SnowMan extends Entity {

    public SnowMan(char entityCharacter, Vector2D entityPosition, Room spawnRoom) {
        super(entityCharacter, entityPosition, spawnRoom);
    }

    @Override
    public void draw() {
        Global.terminalHandler.putChar(getPosition().getX(), getPosition().getY(), getEntityCharacter(), 255, 232,
                true, this);
    }

    @Override
    public void handleEntitySpawn() {

        this.getInventory().addItem(new Weapon("Snow Balls",
                35, 35));
        this.getInventory().equipItem(this.getInventory().getItems().elementAt(0));
        this.getHealthController().setHealthCapacity(40);
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
        return "SnowMan";
    }
    
    @Override
    public void handleCombat() {
        int prevPlayerHealth = Global.getGameLoop().getCurrentLevel().getPlayer().getHealthController().getHealth();
        super.handleCombat();
        boolean hit = Global.getGameLoop().getCurrentLevel().getPlayer().getHealthController().getHealth() < prevPlayerHealth;
        // 35% chance of freezing player
        if(hit && Math.random() <= 0.35 && Global.getGameLoop().getCurrentLevel().getPlayer().getFrozenDuration() <= 0) {
            Global.getGameLoop().getCurrentLevel().getPlayer().setFrozenDuration((int)(Math.random() * 3 + 1));
        }
    }
}