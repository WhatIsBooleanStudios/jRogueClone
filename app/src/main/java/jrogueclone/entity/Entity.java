package jrogueclone.entity;

import java.util.Vector;

import jrogueclone.Global;
import jrogueclone.game.Vector2D;
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

    public class HealthController {
        public void setHealth(int healthPoints) {
            this.m_HealthPoints = healthPoints;
        }

        public int getHealth() {
            return this.m_HealthPoints;
        }

        public void deductHealth(int healthDeduction) {
            this.m_HealthPoints -= healthDeduction;
        }

        private int m_HealthPoints = 0;
    }

    public HealthController getHealthController() {
        return this.m_HealthController;
    }

    public Vector2D getPosition() {
        return this.m_EntityPosition;
    }

    public void setPosition(Vector2D position) { m_EntityPosition = position; }

    public void draw() { Global.terminalHandler.putChar(m_EntityPosition.getX(), m_EntityPosition.getY(), m_EntityCharacter); }

    public char getEntityCharacter() {
        return this.m_EntityCharacter;
    }
    
    protected void setEntityCharacter(char entityCharacter) {
        this.m_EntityCharacter = entityCharacter;
    }

    public Vector<Weapon> getAvailableWeapons() {
        return this.m_AvailableWeapons;
    }

    public abstract void handleEntitySpawn();
    public abstract boolean isMonster();
    public abstract void update();

    private char m_EntityCharacter;
    private Vector2D m_EntityPosition = new Vector2D();
    private HealthController m_HealthController = new HealthController();
    protected Vector<Weapon> m_AvailableWeapons = new Vector<Weapon>();
}
