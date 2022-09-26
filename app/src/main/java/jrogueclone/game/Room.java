package jrogueclone.game;

import java.awt.*;
import java.util.Vector;

import jrogueclone.Global;
import jrogueclone.entity.Entity;
import jrogueclone.entity.Orc;
import jrogueclone.entity.Skeleton;
import jrogueclone.item.Item;
import jrogueclone.item.LootBox;
import jrogueclone.item.Staircase;
import jrogueclone.entity.Bat;

public class Room {
    public Room(Vector2D position, int roomWidth, int roomHeight) {
        this.m_RoomWidth = roomWidth;
        this.m_RoomHeight = roomHeight;
        this.m_RoomPosition = position;
    }

    public void generateHallwayConnectionPoints() {
        m_HallwayConnections.add(new Vector2D(
                m_RoomPosition.getX(),
                m_RoomPosition.getY() + (int) (Math.random() * (m_RoomHeight - 2) + 1)));
        m_HallwayConnections.add(new Vector2D(
                m_RoomPosition.getX() + (int) (Math.random() * (m_RoomWidth - 2) + 1),
                m_RoomPosition.getY()));
    }

    public void addExtraHallwayConnectionPoint() {
        m_HallwayConnections.add(new Vector2D(
                m_RoomPosition.getX() + (int) (Math.random() * (m_RoomWidth - 2) + 1),
                m_RoomPosition.getY() + m_RoomHeight - 1));
    }

    public Vector<Vector2D> getHallwayConnectionPoints() {
        return m_HallwayConnections;
    }

    public void spawnEntities() {
        int levelDifficulty = Global.getGameLoop().getCurrentLevel().getDifficulty();
        Vector<Vector2D> spawnPositions = new Vector<>();
        for (int x = 2; x < 10; x++) {
            for (int y = 1; y < 3; y++) {
                spawnPositions.add(new Vector2D(getRoomPosition().getX() + x, getRoomPosition().getY() + y));
            }
        }
        switch (levelDifficulty) {
            case 1: {
                int positionElement = (int)(Math.random() * spawnPositions.size());
                m_Entities.add(new Bat('ʌ', spawnPositions.elementAt(positionElement), this));
                break;
            }
            case 2: {
                int positionElement = (int)(Math.random() * spawnPositions.size());
                m_Entities.add(new Skeleton('☠', spawnPositions.elementAt(positionElement), this));
                break;
            }
            case 3: {
                int usedPositions = 1, positionElement = (int)(Math.random() * spawnPositions.size() - usedPositions);
                m_Entities.add(new Bat('ʌ', spawnPositions.elementAt(positionElement), this));
                spawnPositions.remove(positionElement);
                usedPositions++;

                positionElement = (int)(Math.random() * (spawnPositions.size() - usedPositions)) ;

                m_Entities.add(new Skeleton('☠', spawnPositions.elementAt(positionElement), this));
                spawnPositions.remove(positionElement);
                usedPositions++;
                break;
            }
            default: {

                int usedPositions = 0, positionElement = (int)(Math.random() * (spawnPositions.size() - usedPositions));
                for(int i = 1; i < levelDifficulty; i++) {
                    if(this.getRect().contains(spawnPositions.elementAt(positionElement).getX(), spawnPositions.elementAt(positionElement).getY())) {
                        positionElement = (int)(Math.random() * (spawnPositions.size() - usedPositions));
                        int mobIndex = (int)(Math.random() * 3);
                        switch(mobIndex) {
                            case 0: {
                                m_Entities.add(new Bat('ʌ', spawnPositions.elementAt(positionElement), this));
                                break;
                            }
                            case 1: {
                                m_Entities.add(new Skeleton('☠', spawnPositions.elementAt(positionElement), this));
                                break;
                            }
                            case 2: {
                                m_Entities.add(new Orc('᧠', spawnPositions.elementAt(positionElement), this));
                                break;
                            }
                        }
                        usedPositions++;
                    }
                }
                break;
            }
        }

    }

    public void removeItem(Item item) {
        this.m_Items.remove(item);
    }

    public void spawnItems() {
        if (Math.round(Math.random() * 99) <= LootBox.m_SpawnChance) {
            this.m_Items.add(new LootBox('=', 214, new Vector2D(
                    getRoomPosition().getX() + getRoomWidth() - 2,
                    getRoomPosition().getY() + getRoomHeight() / 2)));
        }
    }

    public void drawRoomBounds() {
        for (int i = this.getRoomPosition().getY(); i < this.getRoomPosition().getY() + this.getRoomHeight(); i++) {
            for (int j = this.getRoomPosition().getX(); j < this.getRoomPosition().getX() + this.getRoomWidth(); j++) {
                if (j == this.getRoomPosition().getX() || j == this.getRoomPosition().getX() + this.getRoomWidth() - 1
                        || i == this.getRoomPosition().getY()
                        || i == this.getRoomPosition().getY() + this.getRoomHeight() - 1) {
                    if (j == this.getRoomPosition().getX()) {
                        if (i == this.getRoomPosition().getY()) {
                            Global.terminalHandler.putChar(j, i, '╔', 255, 232, false, this);
                        } else if (i == this.getRoomPosition().getY() + this.getRoomHeight() - 1) {
                            Global.terminalHandler.putChar(j, i, '╚', 255, 232, false, this);
                        } else {
                            Global.terminalHandler.putChar(j, i, '║', 255, 232, false, this);
                        }
                    } else if (j == this.getRoomPosition().getX() + this.getRoomWidth() - 1) {
                        if (i == this.getRoomPosition().getY()) {
                            Global.terminalHandler.putChar(j, i, '╗', 255, 232, false, this);
                        } else if (i == this.getRoomPosition().getY() + this.getRoomHeight() - 1) {
                            Global.terminalHandler.putChar(j, i, '╝', 255, 232, false, this);
                        } else {
                            Global.terminalHandler.putChar(j, i, '║', 255, 232, false, this);
                        }
                    } else {
                        Global.terminalHandler.putChar(j, i, '═', 255, 232, false, this);
                    }
                } else
                    Global.terminalHandler.putChar(j, i, '.', 245, 232, false, this.m_EmptySpace);
            }

            for (Vector2D connection : m_HallwayConnections) {
                Global.terminalHandler.putChar(connection.getX(), connection.getY(), '╬', 255, 232, false,
                        this.m_EmptySpace);
            }
        }
    }

    public void addStaircase() {
        this.m_Items.add(new Staircase('♯', 255, new Vector2D((int) (getRoomPosition().getX() + m_RoomWidth / 2),
                (int) getRoomPosition().getY() + m_RoomHeight - 2)));
    }

    public void drawContainedObjects() {
        for (Item item : this.m_Items) {
            item.draw();
        }

        for (Entity entity : this.m_Entities) {
            entity.draw();
        }
    }

    public void update() {
        if (Global.getGameLoop().updateEntities()) {
            for (Entity entity : this.m_Entities) {
                if (entity.getHealthController().getHealth() <= 0) {
                    Global.getGameLoop().getCurrentLevel().getPlayer().incrementKillCount();
                    m_EntityRemoveQue.add(entity);
                    continue;
                }
                entity.update();
            }

            for (Entity entity : this.m_EntityRemoveQue) {
                this.m_Entities.remove(entity);
            }

            m_EntityRemoveQue.clear();
        }
    }

    public Rectangle getRect() {
        return new Rectangle(m_RoomPosition.getX(), m_RoomPosition.getY(), m_RoomWidth, m_RoomHeight);
    }

    public void setRoomPosition(Vector2D roomPosition) {
        this.m_RoomPosition = roomPosition;
    }

    public Vector2D getRoomPosition() {
        return this.m_RoomPosition;
    }

    public int getRoomHeight() {
        return this.m_RoomHeight;
    }

    public int getRoomWidth() {
        return this.m_RoomWidth;
    }

    public Vector<Entity> getEntities() {
        return this.m_Entities;
    }

    public void removeEntity(Entity entity) {
        this.m_Entities.remove(entity);
    }

    private final int m_RoomWidth, m_RoomHeight;
    private Vector<Item> m_Items = new Vector<Item>();
    private Vector<Entity> m_Entities = new Vector<Entity>(), m_EntityRemoveQue = new Vector<Entity>();
    private Vector<Vector2D> m_HallwayConnections = new Vector<Vector2D>();
    private EmptySpace m_EmptySpace = new EmptySpace();
    private Vector2D m_RoomPosition = new Vector2D(-1, -1);
}
