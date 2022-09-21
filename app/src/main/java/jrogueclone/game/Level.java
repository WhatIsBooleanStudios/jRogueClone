package jrogueclone.game;

import java.util.Vector;

import javafx.util.Pair;

public class Level implements GameState{
    public Level(Vector<Room> room, Vector<Pair<Vector2D,Vector2D>> connector) {
        this.m_Room = room;
        this.m_Connector = connector;
    }

    public Vector<Room> getRooms() {
        return this.m_Room;
    }

    public Vector<Pair<Vector2D,Vector2D>> getConnectors() {
        return this.m_Connector;
    }

    private void draw() {
        for (Room room : this.m_Room)
            room.draw();
    }

    @Override public void initialize() {
        
    }

    @Override public void update() {
        this.draw();
    }

    private Vector<Room> m_Room = new Vector<Room>();
    private Vector<Pair<Vector2D,Vector2D>> m_Connector = new Vector<>();
}
