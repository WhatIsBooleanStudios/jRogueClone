package jrogueclone.game;

import java.awt.*;
import java.util.Vector;

import jrogueclone.Global;
import jrogueclone.entity.Entity;
import jrogueclone.item.LootBox;

public class Room {
    public Room(Vector2D position, int roomWidth, int roomHeight) {
        this.m_RoomWidth = roomWidth;
        this.m_RoomHeight = roomHeight;
        this.m_RoomPosition = position;
    }

    public void draw() {
        for(int i = this.getRoomPosition().getY(); i < this.getRoomPosition().getY() + this.getRoomHeight(); i++) {
            for(int j = this.getRoomPosition().getX(); j < this.getRoomPosition().getX() + this.getRoomWidth(); j++) {
                Global.terminalHandler.putChar(j, i, '#');
            }
        }
    }

    public Rectangle getIntersection(Room room) {
        return (new Rectangle(m_RoomPosition.getX(), m_RoomPosition.getY(), m_RoomWidth, m_RoomHeight))
                .intersection(new Rectangle(room.m_RoomPosition.getX(), room.m_RoomPosition.getY(), room.getRoomWidth(), room.getRoomHeight()));
    }
    public boolean intersects(Room room) {
        return (new Rectangle(m_RoomPosition.getX(), m_RoomPosition.getY(), m_RoomWidth, m_RoomHeight))
                .intersects(new Rectangle(room.m_RoomPosition.getX(), room.m_RoomPosition.getY(), room.getRoomWidth(), room.getRoomHeight()));
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
    private Vector<LootBox> m_LootBox = new Vector<LootBox>();
    private Vector<Entity> m_Entities = new Vector<Entity>();
    private Vector2D m_RoomPosition = new Vector2D(-1, -1);
}
