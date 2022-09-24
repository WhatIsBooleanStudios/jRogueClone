package jrogueclone.game;

import java.util.Vector;

import jrogueclone.entity.Player;
import jrogueclone.util.Pair;

public class Level implements GameState {
    public Level(Vector<Room> rooms, Vector<Hallway> hallways, Player player) {

        this.m_Rooms = rooms;
        this.m_Hallways = hallways;
        this.m_Player = player;
        setDifficulty(1);
    }

    public Player getPlayer() {
        return this.m_Player;
    }

    public void setDifficulty(int levelDifficulty) {
        this.m_LevelDifficuty = levelDifficulty;
    }

    public int getDifficulty() {
        return this.m_LevelDifficuty;
    }

    public Vector<Room> getRooms() {
        return this.m_Rooms;
    }

    public Vector<Hallway> getConnectors() {
        return this.m_Hallways;
    }

    public void drawLevel() {
        for (Room room : this.m_Player.getDiscoveredRooms()) {
            room.draw();
        }
        for (Hallway hallway : this.m_Player.getDiscoveredHallways()) {
            hallway.draw();
        }
    }

    @Override
    public void initialize() {
        this.m_Player.clearDiscoveredRooms();
        this.m_Player.clearDiscoveredHallways();
        Room playerSpawnRoom = m_Rooms.get((int) (Math.random() * m_Rooms.size()));
        m_Player.setPosition(new Vector2D(
                playerSpawnRoom.getRoomPosition().getX() + (int) ((double) playerSpawnRoom.getRoomWidth() / 2),
                playerSpawnRoom.getRoomPosition().getY() + (int) ((double) playerSpawnRoom.getRoomHeight() / 2)));

        for (Room room : m_Rooms) {
            room.spawnEntities();
            room.spawnItems();
        }

        this.m_Player.setRoomDiscovered(playerSpawnRoom);
    }

    @Override
    public void update() {
        this.drawLevel();

        m_Player.update();
        for (Room room : m_Player.getDiscoveredRooms())
            room.update();

        m_Player.draw();
        System.out.flush();
    }

    private Vector<Room> m_Rooms = new Vector<Room>();
    private Vector<Hallway> m_Hallways = new Vector<Hallway>();
    private Player m_Player;
    private int m_LevelDifficuty;
}
