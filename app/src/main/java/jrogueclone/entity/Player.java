package jrogueclone.entity;

import jrogueclone.Global;
import jrogueclone.game.Room;
import jrogueclone.game.Vector2D;
import jrogueclone.item.Weapon;

public class Player extends Entity {
    public Player(char entityCharacter) {
        super(entityCharacter);
    }

    public Player(char entityCharacter, Vector2D entityPosition) {
        super(entityCharacter, entityPosition);
    }

    @Override
    public void draw() {
        Global.terminalHandler.putChar(
                getPosition().getX(),
                getPosition().getY(),
                getEntityCharacter(),
                9,
                Global.terminalHandler.getBackgroundColorAt(getPosition().getX(), getPosition().getY()),
                true);
    }

    @Override
    public boolean isMonster() { return false; }

    @Override
    public void handleEntitySpawn() {

        // Give the player a weapon with a 70% chance to enflict 34 damage
        this.m_AvailableWeapons.add(new Weapon("Damaged Wooden Sword",
                34, 70));

        this.getHealthController().setHealth(100);
        this.m_TilesPerSecond = 1;
    }

    public void tryMoveUp() {
        Vector2D newPosition = new Vector2D(getPosition().getX(), getPosition().getY() - 1);
        //System.out.println(Global.terminalHandler.getUserDataAt(getPosition().getX(), getPosition().getY() - 1).getClass());
        Object uData = Global.terminalHandler.getUserDataAt(newPosition.getX(), newPosition.getY());
        if (getPosition().getY() > 0 && (uData == null || uData.getClass() != Room.class))
            setPosition(newPosition);
    }

    public void tryMoveLeft() {
        Vector2D newPosition = new Vector2D(getPosition().getX() - 1, getPosition().getY());
        Object userData = Global.terminalHandler.getUserDataAt(newPosition.getX(), newPosition.getY());
        if (getPosition().getX() > 0 && (userData == null || userData.getClass() != Room.class)) {
            setPosition(newPosition);
        }
    }

    public void tryMoveDown() {
        Vector2D newPosition = new Vector2D(getPosition().getX(), getPosition().getY() + 1);
        Object userData =  Global.terminalHandler.getUserDataAt(newPosition.getX(), newPosition.getY());
        if (getPosition().getY() < Global.rows - 1 && (userData == null || userData.getClass() != Room.class)) {
            setPosition(newPosition);
        }
    }

    public void tryMoveRight() {
        Vector2D newPosition = new Vector2D(getPosition().getX() + 1, getPosition().getY());
        Object userData = Global.terminalHandler.getUserDataAt(newPosition.getX(), newPosition.getY()); 
        if (getPosition().getX() < Global.columns - 1 && (userData == null || userData.getClass() != Room.class)) {
            setPosition(newPosition);
        }
    }
}