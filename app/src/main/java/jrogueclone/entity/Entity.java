package jrogueclone.entity;

import jrogueclone.game.Vector2D;
import jrogueclone.gfx.ui.Inventory;

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

    public class HealthController {
        public void setHealth(int healthPoints) {
            this.m_HealthPoints = healthPoints;
            if(m_StartingHealth == Integer.MIN_VALUE) {
                m_StartingHealth = healthPoints;
            }
        }

        public int getHealth() {
            return this.m_HealthPoints;
        }

        public int getStartingHealth() {
            return m_StartingHealth;
        }

        public void deductHealth(int healthDeduction) {
            this.m_HealthPoints -= healthDeduction;
        }

        private int m_HealthPoints = 0;
        private int m_StartingHealth = Integer.MIN_VALUE;
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

    public abstract void handleEntitySpawn();

    public abstract boolean isMonster();

    public abstract void update();

    public abstract void handleDeath();

    private char m_EntityCharacter;
    private Vector2D m_EntityPosition = new Vector2D();
    private HealthController m_HealthController = new HealthController();
    private Inventory m_Inventory = new Inventory();
}
