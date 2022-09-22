package jrogueclone.game;

import java.util.Vector;

import jrogueclone.Global;
import jrogueclone.entity.Entity;
import jrogueclone.entity.Bat;
import jrogueclone.entity.Player;
import jrogueclone.util.Pair;

public class Level implements GameState{
    public Level(Vector<Room> rooms, Vector<Hallway> hallways, Player player) {
        this.m_Rooms = rooms;
        this.m_Hallways = hallways;
        this.m_Player = player;

        Room playerSpawnRoom = m_Rooms.get((int)(Math.random() * m_Rooms.size()));
        m_Player.setPosition(new Vector2D(
            playerSpawnRoom.getRoomPosition().getX() + (int)((double)playerSpawnRoom.getRoomWidth() / 2),
            playerSpawnRoom.getRoomPosition().getY() + (int)((double)playerSpawnRoom.getRoomHeight() / 2)
        ));

        for(Room room : m_Rooms) {
            room.spawnEntities();
        }

    }

    public Vector<Room> getRooms() {
        return this.m_Rooms;
    }

    public Vector<Hallway> getConnectors() {
        return this.m_Hallways;
    }

    private void drawLevel() {
        for (Room room : this.m_Rooms)
            room.draw();

        for (Hallway hallway : m_Hallways) {
            hallway.draw();
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

        for(Room room : m_Rooms) {
            room.update();
        }
        m_Player.update();

        for(Room room : m_Rooms) {
            room.draw();
        }
        m_Player.draw();
        System.out.flush();
    }

    private Vector<Room> m_Rooms = new Vector<Room>();
    private Vector<Hallway> m_Hallways = new Vector<Hallway>();
    private Player m_Player;

}
