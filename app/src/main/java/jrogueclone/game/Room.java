package jrogueclone.game;

import java.awt.*;
import java.util.Vector;

import jrogueclone.Global;
import jrogueclone.entity.Entity;
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

    public void spawnEntities() {
        int levelDifficulty = GameLoop.getLevelDifficulty();

        switch (levelDifficulty) {
            case 1: {
                m_Entities.add(new Bat(' ', new Vector2D(
                        getRoomPosition().getX() + (int) ((double) getRoomWidth() / 2 - 2),
                        getRoomPosition().getY() + (int) ((double) getRoomHeight() / 2))));
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
                    getRoomPosition().getX() + 1,
                    getRoomPosition().getY() + 1)));
        }
    }

    private void drawRoomBounds() {
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
                    Global.terminalHandler.putChar(j, i, '.', 245, 232, false, null);
            }
        }
    }

    public void addStaircase() {
        this.m_Items.add(new Staircase('♯', 255, new Vector2D((int) (getRoomPosition().getX() + m_RoomWidth / 2),
        (int) getRoomPosition().getY() + m_RoomHeight - 2)));
    }

    public void draw() {
        drawRoomBounds();

        for (Entity entity : this.m_Entities) {
            entity.draw();
        }

        for (Item item : this.m_Items) {
            item.draw();
        }
    }

    public void update() {
        if (Global.getGameLoop().updateEntities()) {
            for (Entity entity : this.m_Entities)
                entity.update();
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

    private final int m_RoomWidth, m_RoomHeight;
    private Vector<Item> m_Items = new Vector<Item>();
    private Vector<Entity> m_Entities = new Vector<Entity>();
    private Vector2D m_RoomPosition = new Vector2D(-1, -1);
}
