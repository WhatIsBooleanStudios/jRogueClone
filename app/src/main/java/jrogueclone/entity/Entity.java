package jrogueclone.entity;

public class Entity {
    public Entity(char entityCharecter) {
        this.m_EntityCharacter = entityCharecter;
    }
    public Entity(char entityCharecter, Vector2D entityPosition) {
        this.m_EntityCharacter = entityCharecter;
        this.m_EntityPosition = entityPosition;
    }

    public class HealthController {
        public void setHealth(int healthPoints) {
            this.m_HealthPoints = healthPoints;
        }

        public int getHealth() {
            return this.m_HealthPoints;
        }

        private int m_HealthPoints = 100;
    }

    public HealthController getHealthController() {
        return this.m_HealthController;
    }

    public Vector2D getPosition() {
        return this.m_EntityPosition;
    }
    
    public char getEntityCharacter() {
        return this.m_EntityCharacter;
    }

    private final char m_EntityCharacter;
    private Vector2D m_EntityPosition = new Vector2D();
    private HealthController m_HealthController = new HealthController();
}
