package jrogueclone.entity;

import jrogueclone.Global;
import jrogueclone.game.EmptySpace;
import jrogueclone.game.Room;
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

    public Entity(char entityCharacter, Vector2D entityPosition, Room spawnRoom) {
        this.m_SpawnRoom = spawnRoom;
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

    protected void handleMovment() {
        if(!this.isMonster() || Global.getGameLoop().getCurrentLevel().getPlayer().isInvisible())
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
                && this.m_SpawnRoom.getRect().contains(newPosition.getX(), newPosition.getY())
                && !newPosition.Equals(playerPosition)) {
            Object uData = Global.terminalHandler.getUserDataAt(newPosition.getX(), newPosition.getY());
            if (uData.getClass() == EmptySpace.class) {

                this.setPosition(newPosition);
            }
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
