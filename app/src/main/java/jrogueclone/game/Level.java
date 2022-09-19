package jrogueclone.game;

import java.util.Vector;

import jrogueclone.Global;

public class Level implements GameState{
    public Level(Vector<Room> room, Vector<Vector2D> connector) {
        this.m_Room = room;
        this.m_Connector = connector;
    }

    public Vector<Room> getRooms() {
        return this.m_Room;
    }

    public Vector<Vector2D> getConnectors() {
        return this.m_Connector;
    }

    private void draw() {
        for (Room room : this.m_Room)
            room.draw();

        for (Vector2D connector : this.m_Connector) {
            for (int x = 0; x < connector.getX(); x++) {
                for (int y = 0; y < connector.getY(); y++) {
                    Global.terminalHandler.putChar(x, y, '#');
                }
            }
        }
    }

    @Override public void initialize() {
        
    }

    @Override public void update() {
        this.draw();
    }

    private Vector<Room> m_Room = new Vector<Room>();
    private Vector<Vector2D> m_Connector = new Vector<Vector2D>();
}
