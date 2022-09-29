package jrogueclone.game;

import java.util.Vector;

import jrogueclone.Global;
import jrogueclone.entity.Player;
import jrogueclone.gfx.ui.Inventory.ItemType;
import jrogueclone.item.Weapon;

public class Level implements GameState {
    public Level(Vector<Room> rooms, Vector<Hallway> hallways, Player player) {

        this.m_Rooms = rooms;
        this.m_Hallways = hallways;
        this.m_Player = player;
    }

    public Player getPlayer() {
        return this.m_Player;
    }

    public void setDifficulty(int previousDifficulty) {
        this.m_LevelDifficuty = previousDifficulty + 1;
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

        if (Global.terminalHandler.keyIsPressed('i')) {
            m_Player.toggleInventoryState();
            m_Player.getInventory().zeroCursor();
        }

        if (Global.terminalHandler.keyIsPressed('h')) {
            Global.terminalHandler.putTopStatusBarString(0, "Map unlocked!", 255, 232, true);
            for (Room room : this.m_Rooms) {
                m_Player.setRoomDiscovered(room);
            }
            for (Hallway hallway : this.m_Hallways) {
                m_Player.setHallwayDiscovered(hallway);
            }
        }

        if (!Global.getGameLoop().getInventoryToggled()) {
            this.drawLevel();

            for (Room room : m_Player.getDiscoveredRooms()) {
                room.drawContainedObjects();
                room.update();
            }

            m_Player.update();
            m_Player.draw();

            int margin = 1;
            String statusBarStr = "XP: (" + m_Player.getXP() + "/" + m_Player.getTargetXP() + ")"
                    + " | HP: ("
                    + m_Player.getHealthController().getHealth() + "/"
                    + m_Player.getHealthController().getMaxHealth() + ") " + "▆".repeat(20),
                    statusbarStr2 = "KILLS: " + this.getPlayer().getKillCount()
                            + " | LVL: " + getDifficulty()
                            + " | PLR_LVL: " + m_Player.getLevel();
            Weapon currentWeapon = ((Weapon) m_Player.getInventory().getEquippedItem(ItemType.WEAPON));
            if (currentWeapon != null) {
                statusbarStr2 += " | DURABILTY: [" + currentWeapon.getDurability() + "/"
                        + currentWeapon.getMaxDurability() + "]";
            }

            Global.terminalHandler.putBottomStatusBarString(
                    1,
                    0,
                    statusbarStr2,
                    9,
                    232,
                    true);

            Global.terminalHandler.putBottomStatusBarString(
                    margin,
                    1,
                    statusBarStr,
                    9,
                    232,
                    true);

            Global.terminalHandler.putBottomStatusBarString(
                    margin + statusBarStr.indexOf('▆'),
                    1,
                    "▆".repeat((int) Math.ceil(20 * (double) m_Player.getHealthController().getHealth()
                            / m_Player.getHealthController().getMaxHealth())),
                    34,
                    232,
                    true);
        } else {
            Global.terminalHandler.resetCursor();
            ;
            m_Player.getInventory().updateUI();
            m_Player.getInventory().draw();
        }
    }

    private Vector<Room> m_Rooms = new Vector<Room>();
    private Vector<Hallway> m_Hallways = new Vector<Hallway>();
    private Player m_Player;
    private int m_LevelDifficuty = 1;
}
