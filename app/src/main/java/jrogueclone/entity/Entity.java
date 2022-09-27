package jrogueclone.entity;

import jrogueclone.Global;
import jrogueclone.game.EmptySpace;
import jrogueclone.game.Room;
import jrogueclone.game.Vector2D;
import jrogueclone.gfx.ui.Inventory;
import jrogueclone.gfx.ui.Inventory.ItemType;
import jrogueclone.item.Weapon;

public abstract class Entity {

    public Entity(char entityCharacter) {

        this.m_EntityCharacter = entityCharacter;
        handleEntitySpawn();
    }

    public Entity(char entityCharacter, Vector2D entityPosition) {

        this.m_EntityCharacter = entityCharacter;
        this.m_EntityPosition = entityPosition;
        handleEntitySpawn();
    }

    public Entity(char entityCharacter, Vector2D entityPosition, Room spawnRoom) {
        this.m_SpawnRoom = spawnRoom;
        this.m_EntityCharacter = entityCharacter;
        this.m_EntityPosition = entityPosition;
        handleEntitySpawn();
    }

    public class HealthController {

        public void addHealth(int healthPoints) {
            this.m_HealthPoints += healthPoints;
        }

        public void setHealthMax() {
            this.m_HealthPoints = this.m_MaxHealth;
        }

        public int getHealth() {
            return this.m_HealthPoints;
        }

        public void setHealthCapacity(int maxHealth) {
            this.m_MaxHealth = maxHealth;
        }

        public int getMaxHealth() {
            return this.m_MaxHealth;
        }

        public void deductHealth(int healthDeduction) {
            this.m_HealthPoints -= healthDeduction;
        }

        private int m_HealthPoints = 0, m_MaxHealth = 0;
    }

    public HealthController getHealthController() {
        return this.m_HealthController;
    }

    public Vector2D getPosition() {
        return this.m_EntityPosition;
    }

    public void setPosition(Vector2D position) {
        m_EntityPosition = position;
    }

    public char getEntityCharacter() {
        return this.m_EntityCharacter;
    }

    protected void setEntityCharacter(char entityCharacter) {
        this.m_EntityCharacter = entityCharacter;
    }

    public abstract void draw();

    public Inventory getInventory() {
        return this.m_Inventory;
    }

    protected void handleCombat() {
        Weapon activeWeapon = (Weapon) this.getInventory().getEquippedItem(ItemType.WEAPON);

        if (activeWeapon == null)
            return;

        if (activeWeapon.getWeaponDamageChance() <= Math.random() * 99 + 1) {
            HealthController hc = Global.getGameLoop().getCurrentLevel().getPlayer().getHealthController();
            hc.addHealth(-activeWeapon.getWeaponDamage());
            String toDisplay = this.toString() + " did " + activeWeapon.getWeaponDamage() + "dmg to you. ";
            //toDisplay += " ".repeat(Global.columns - toDisplay.length());

            Global.terminalHandler.appendTopStatusBarString(
                    toDisplay, 255, 232, false);
        } else {
            String toDisplay = this.toString() + " missed. ";
            //toDisplay += " ".repeat(Global.columns - toDisplay.length());

            Global.terminalHandler.appendTopStatusBarString(
                    toDisplay, 255, 232, false);
        }
    }

    protected void handleMovment() {
        if (!this.isMonster() || Global.getGameLoop().getCurrentLevel().getPlayer().isInvisible())
            return;

        Vector2D playerPosition = Global.getGameLoop().getCurrentLevel().getPlayer().getPosition(),
                newPosition = new Vector2D(this.getPosition());

        if (playerPosition.getY() > this.getPosition().getY()) {
            newPosition.setY(newPosition.getY() + 1);
        } else if (playerPosition.getY() < this.getPosition().getY()) {
            newPosition.setY(newPosition.getY() - 1);
        } else if (playerPosition.getX() > this.getPosition().getX()) {
            newPosition.setX(newPosition.getX() + 1);
        } else {
            newPosition.setX(newPosition.getX() - 1);
        }

        if (this.m_SpawnRoom.getRect().contains(playerPosition.getX(), playerPosition.getY())
                && this.m_SpawnRoom.getRect().contains(newPosition.getX(), newPosition.getY())) {

            if (!newPosition.Equals(playerPosition)) {
                Object uData = Global.terminalHandler.getUserDataAt(newPosition.getX(), newPosition.getY());
                if (uData.getClass() == EmptySpace.class) {

                    this.setPosition(newPosition);
                }
            } else
                handleCombat();

        }
    }

    public abstract void handleEntitySpawn();

    public abstract boolean isMonster();

    public abstract void update();

    public abstract String toString();

    private char m_EntityCharacter;
    private Vector2D m_EntityPosition = new Vector2D();
    private HealthController m_HealthController = new HealthController();
    private Inventory m_Inventory = new Inventory();
    protected Room m_SpawnRoom;
}
