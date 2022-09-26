package jrogueclone.game;

import java.util.Vector;

import jrogueclone.Global;
import jrogueclone.entity.Player;

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
            room.drawRoomBounds();
        }
        for (Hallway hallway : this.m_Player.getDiscoveredHallways()) {
            hallway.draw();
        }
    }

    @Override
    public void initialize() {
        this.m_Player.clearDiscoveredRooms();
        this.m_Player.clearDiscoveredHallways();
        Room playerSpawnRoom = m_Rooms.get((int) (Math.random() * m_Rooms.size() - 1)),
                staircaseSpawnRoom = m_Rooms.get((int) (Math.random() * m_Rooms.size() - 1));
        m_Player.setPosition(new Vector2D(
                playerSpawnRoom.getRoomPosition().getX() + (int) ((double) playerSpawnRoom.getRoomWidth() / 2),
                playerSpawnRoom.getRoomPosition().getY() + (int) ((double) playerSpawnRoom.getRoomHeight() / 2)));

        for (Room room : m_Rooms) {
            room.spawnEntities();
            room.spawnItems();
        }

        staircaseSpawnRoom.addStaircase();
        this.m_Player.setRoomDiscovered(playerSpawnRoom);
    }

    @Override
    public void update() {
        
        if(Global.terminalHandler.keyIsPressed('i'))
            m_Player.toggleInventoryState();
        
        if(!Global.getGameLoop().getInventoryToggled()) {
            this.drawLevel();

            for (Room room : m_Player.getDiscoveredRooms()) {
                room.drawContainedObjects();
                room.update();
            }

            m_Player.update();
            m_Player.draw();
        } else {
            Global.terminalHandler.clear();
            m_Player.getInventory().draw();
        }
    }

    private Vector<Room> m_Rooms = new Vector<Room>();
    private Vector<Hallway> m_Hallways = new Vector<Hallway>();
    private Player m_Player;
    private int m_LevelDifficuty;
}
