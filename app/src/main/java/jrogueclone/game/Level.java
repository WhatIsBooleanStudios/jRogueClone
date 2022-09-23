package jrogueclone.game;

import java.util.Vector;

import jrogueclone.entity.Player;

public class Level implements GameState {
    public Level(Vector<Room> rooms, Vector<Vector2D> connector, Player player) {
        this.m_Rooms = rooms;
        this.m_Connector = connector;
        this.m_Player = player;
        setDifficulty(1);
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

    public Vector<Vector2D> getConnectors() {
        return this.m_Connector;
    }

    private void drawLevel() {
        for (Room room : this.m_Player.getDiscoveredRooms())
            room.draw();
    }

    @Override
    public void initialize() {
        Room playerSpawnRoom = m_Rooms.get((int) (Math.random() * m_Rooms.size()));
        m_Player.setPosition(new Vector2D(
                playerSpawnRoom.getRoomPosition().getX() + (int) ((double) playerSpawnRoom.getRoomWidth() / 2),
                playerSpawnRoom.getRoomPosition().getY() + (int) ((double) playerSpawnRoom.getRoomHeight() / 2)));

        for (Room room : m_Rooms) {
            room.spawnEntities();
            room.spawnItems();

            // remove this when playing real game
            this.m_Player.setRoomDiscovered(room);
        }

        // Uncomment when playing real game
        // this.m_Player.setRoomDiscovered(playerSpawnRoom);
    }

    @Override
    public void update() {
        this.drawLevel();

        m_Player.update();
        for (Room room : m_Player.getDiscoveredRooms())
            room.update();

        m_Player.draw();
    }

    private Vector<Room> m_Rooms = new Vector<Room>();
    private Vector<Vector2D> m_Connector = new Vector<Vector2D>();
    private Player m_Player;
    private int m_LevelDifficuty;
}
