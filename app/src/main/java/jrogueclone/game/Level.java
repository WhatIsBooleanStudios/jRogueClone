package jrogueclone.game;

import java.util.Vector;

import jrogueclone.Global;
import jrogueclone.entity.Player;

public class Level implements GameState{
    public Level(Vector<Room> rooms, Vector<Vector2D> connector, Player player) {
        this.m_Rooms = rooms;
        this.m_Connector = connector;
        this.m_Player = player;

        Room playerSpawnRoom = m_Rooms.get((int)(Math.random() * m_Rooms.size()));
        m_Player.setPosition(new Vector2D(
            playerSpawnRoom.getRoomPosition().getX() + (int)((double)playerSpawnRoom.getRoomWidth() / 2),
            playerSpawnRoom.getRoomPosition().getY() + (int)((double)playerSpawnRoom.getRoomHeight() / 2)
        ));
    }

    public Vector<Room> getRooms() {
        return this.m_Rooms;
    }

    public Vector<Vector2D> getConnectors() {
        return this.m_Connector;
    }

    private void drawLevel() {
        for (Room room : this.m_Rooms)
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
        this.drawLevel(); // call this first as player updating depends on the result
        if(Global.terminalHandler.keyIsPressed('w') ) {
            m_Player.tryMoveUp();
        }
        if(Global.terminalHandler.keyIsPressed('a')) {
            m_Player.tryMoveLeft();
        }
        if(Global.terminalHandler.keyIsPressed('s')) {
            m_Player.tryMoveDown();
        }
        if(Global.terminalHandler.keyIsPressed('d')) {
            m_Player.tryMoveRight();
        }
        m_Player.draw();
    }

    private Vector<Room> m_Rooms = new Vector<Room>();
    private Vector<Vector2D> m_Connector = new Vector<Vector2D>();
    private Player m_Player;
}
