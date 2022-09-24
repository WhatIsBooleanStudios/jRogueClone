package jrogueclone.entity;

import java.util.Vector;

import jrogueclone.Global;
import jrogueclone.game.Vector2D;
import jrogueclone.item.LootBox;
import jrogueclone.item.Staircase;
import jrogueclone.item.Weapon;
import jrogueclone.game.Room;

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
    public boolean isMonster() {
        return false;
    }

    @Override
    public void handleEntitySpawn() {

        // Give the player a weapon with a 70% chance to enflict 34 damage
        this.m_AvailableWeapons.add(new Weapon("Damaged Wooden Sword",
                34, 70));

        this.getHealthController().setHealth(100);
    }

    private void tryMoveUp() {
        Vector2D newPosition = new Vector2D(getPosition().getX(), getPosition().getY() - 1);

        Object uData = Global.terminalHandler.getUserDataAt(newPosition.getX(), newPosition.getY());
        if (getPosition().getY() > 0 && uData == null) {
            setPosition(newPosition);
        }
    }

    private void tryMoveLeft() {
        Vector2D newPosition = new Vector2D(getPosition().getX() - 1, getPosition().getY());
        Object uData = Global.terminalHandler.getUserDataAt(newPosition.getX(), newPosition.getY());
        if (getPosition().getX() > 0 && uData == null) {
            setPosition(newPosition);
        }
    }

    private void tryMoveDown() {
        Vector2D newPosition = new Vector2D(getPosition().getX(), getPosition().getY() + 1);
        Object uData = Global.terminalHandler.getUserDataAt(newPosition.getX(), newPosition.getY());
        if (getPosition().getY() < Global.rows - 1 && uData == null) {
            setPosition(newPosition);
        }
    }

    private void tryMoveRight() {
        Vector2D newPosition = new Vector2D(getPosition().getX() + 1, getPosition().getY());
        Object uData = Global.terminalHandler.getUserDataAt(newPosition.getX(), newPosition.getY());
        if (getPosition().getX() < Global.columns - 1 && uData == null) {
            setPosition(newPosition);
        }
    }

    private void tryUse() {
        int pX = this.getPosition().getX(), pY = this.getPosition().getY();
        Vector<Object> uData = new Vector<Object>();

        for (int i = -1; i <= 1; i++) {
            if (Global.terminalHandler.getUserDataAt(pX + i, pY) != null)
                uData.add(Global.terminalHandler.getUserDataAt(pX + i, pY));

            if (Global.terminalHandler.getUserDataAt(pX, pY + i) != null)
                uData.add(Global.terminalHandler.getUserDataAt(pX, pY + i));
        }

        for (Object object : uData) {
            if (object.getClass().getName().toLowerCase().indexOf("lootbox") > 0) {
                for (Room room : this.m_DiscoveredRooms) {
                    if (room.getRect().contains(this.getPosition().getX(), this.getPosition().getY())) {
                        LootBox lootBox = (LootBox) object;
                        if (lootBox.isUseable())
                            lootBox.useItem();
                    }
                }
            } else if (object.getClass().getName().toLowerCase().indexOf("staircase") > 0) {
                for (Room room : this.m_DiscoveredRooms) {
                    if (room.getRect().contains(this.getPosition().getX(), this.getPosition().getY())) {
                        Staircase staircase = (Staircase) object;
                        if (staircase.isUseable()) {
                            staircase.useItem();
                        }
                            
                    }
                }
            }
        }
    }

    @Override
    public void update() {
        if (Global.terminalHandler.keyIsPressed('w')) {
            this.tryMoveUp();
        }
        if (Global.terminalHandler.keyIsPressed('a')) {
            this.tryMoveLeft();
        }
        if (Global.terminalHandler.keyIsPressed('s')) {
            this.tryMoveDown();
        }
        if (Global.terminalHandler.keyIsPressed('d')) {
            this.tryMoveRight();
        }
        if (Global.terminalHandler.keyIsPressed('e')) {
            this.tryUse();
        }
    }

    public void clearDiscoveredRooms() {
        this.m_DiscoveredRooms.clear();
    }

    public Vector<Room> getDiscoveredRooms() {
        return this.m_DiscoveredRooms;
    }

    public void setRoomDiscovered(Room room) {
        this.m_DiscoveredRooms.add(room);
    }

    private Vector<Room> m_DiscoveredRooms = new Vector<Room>();
}