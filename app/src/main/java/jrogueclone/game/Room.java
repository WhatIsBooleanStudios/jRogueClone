package jrogueclone.game;

import java.util.Vector;

import jrogueclone.entity.Entity;
import jrogueclone.item.LootBox;

public class Room {
    public Room(int roomArea, Vector2D roomPosition) {
        this.m_RoomArea = roomArea;
        this.m_RoomPosition = roomPosition;
    }

    public Vector2D getRoomPosition() {
        return this.m_RoomPosition;
    }
    
    public int getRoomArea() {
        return this.m_RoomArea;
    }

    public Vector<LootBox> getLootBox() {
        return this.m_LootBox;
    }

    public Vector<Entity> getEntities() {
        return this.m_Entities;
    }

    private final int m_RoomArea;
    private Vector<LootBox> m_LootBox = new Vector<>();
    private Vector<Entity> m_Entities = new Vector<>();
    private final Vector2D m_RoomPosition;
}
