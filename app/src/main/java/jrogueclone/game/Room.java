package jrogueclone.game;

import java.util.Vector;

import jrogueclone.entity.Entity;
import jrogueclone.item.LootBox;

public class Room {
    public Room(int roomWidth, int roomHeight) {
        this.m_RoomWidth = roomWidth;
        this.m_RoomHeight = roomHeight;
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

    public Vector<LootBox> getLootBox() {
        return this.m_LootBox;
    }

    public Vector<Entity> getEntities() {
        return this.m_Entities;
    }

    private final int m_RoomWidth, m_RoomHeight;
    private Vector<LootBox> m_LootBox = new Vector<>();
    private Vector<Entity> m_Entities = new Vector<>();
    private Vector2D m_RoomPosition = new Vector2D(-1, -1);
}
