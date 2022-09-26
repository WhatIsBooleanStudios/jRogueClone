package jrogueclone.entity;

import java.util.Vector;

import jrogueclone.Global;
import jrogueclone.game.Vector2D;
import jrogueclone.gfx.ui.Inventory.ItemType;
import jrogueclone.item.LootBox;
import jrogueclone.item.Potion;
import jrogueclone.item.Staircase;
import jrogueclone.item.Weapon;
import jrogueclone.game.EmptySpace;
import jrogueclone.game.Hallway;
import jrogueclone.game.Room;

public class Player extends Entity {
    public Player(char entityCharacter) {
        super(entityCharacter);

    }

    @Override
    public void draw() {
        Global.terminalHandler.putChar(
                getPosition().getX(),
                getPosition().getY(),
                getEntityCharacter(),
                9,
                Global.terminalHandler.getBackgroundColorAt(getPosition().getX(), getPosition().getY()),
                true);
    }

    @Override
    public boolean isMonster() {
        return false;
    }

    @Override
    public void handleEntitySpawn() {

        // Give the player a weapon with a 70% chance to inflict 34 damage
        this.getInventory().addItem(new Weapon("Damaged Wooden Sword",
        34, 70));
        this.getInventory().equipItem(this.getInventory().getItems().elementAt(0));

        this.getHealthController().setHealth(100);

        for(int i = 0; i < 100; i++) {
            this.getInventory().addItem(new Potion(Potion.PotionType.INVISIBILTY));
        }
    }

    public void handleDiscovery(Vector2D newPosition) {
        for (Hallway hallway : Global.getGameLoop().getCurrentLevel().getConnectors()) {
            if (hallway.contains(newPosition)
                    && !Global.getGameLoop().getCurrentLevel().getPlayer().getDiscoveredHallways().contains(hallway)) {
                Global.getGameLoop().getCurrentLevel().getPlayer().setHallwayDiscovered(hallway);
            }
        }
        for (Room room : Global.getGameLoop().getCurrentLevel().getRooms()) {
            if (room.getRect().contains(newPosition.getX(), newPosition.getY())
                    && !Global.getGameLoop().getCurrentLevel().getPlayer().getDiscoveredRooms().contains(room)) {
                Global.getGameLoop().getCurrentLevel().getPlayer().setRoomDiscovered(room);
            }
        }
        Global.getGameLoop().getCurrentLevel().drawLevel();

        for (Room room : getDiscoveredRooms()) {
            room.drawContainedObjects();
        }
    }

    private enum MoveDirection {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }

    private void handleCombat(Object object) {

        if (object.getClass().toString().toLowerCase().indexOf("entity") == -1)
            return;

        Entity entity = (Entity) object;

        Weapon activeWeapon = (Weapon) this.getInventory().getEquippedItem(ItemType.WEAPON);

        if (activeWeapon == null)
            return;

        this.toggleInvisible();

        if (activeWeapon.getWeaponDamageChance() <= Math.random() * 99 + 1) {
            HealthController hc = entity.getHealthController();
            hc.setHealth(hc.getHealth() - activeWeapon.getWeaponDamage());
            Global.terminalHandler.putTopStatusBarString(0,
                    "Dealt " + activeWeapon.getWeaponDamage() + "dm to " + entity.toString(), 255, 235, false);
        } else
            Global.terminalHandler.putTopStatusBarString(0,
                    "You missed the " + entity.toString(), 255, 235, false);
    }

    private void handleMovement(MoveDirection moveDirection) {
        Vector2D newPosition = null;
        Object uData = null;
        switch (moveDirection) {
            case DOWN:
                newPosition = new Vector2D(getPosition().getX(), getPosition().getY() + 1);
                handleDiscovery(newPosition);
                if (getPosition().getY() < Global.rows - 1) {
                    uData = Global.terminalHandler.getUserDataAt(newPosition.getX(), newPosition.getY());
                    if (uData != null)
                        handleCombat(uData);

                    if (uData != null
                            && (uData.getClass() == EmptySpace.class || uData.getClass() == Hallway.class)) {
                        setPosition(newPosition);
                    }
                }
                break;
            case LEFT:
                newPosition = new Vector2D(getPosition().getX() - 1, getPosition().getY());
                handleDiscovery(newPosition);
                uData = Global.terminalHandler.getUserDataAt(newPosition.getX(), newPosition.getY());
                if (uData != null)
                    handleCombat(uData);

                if (getPosition().getX() > 0 && uData != null
                        && (uData.getClass() == EmptySpace.class || uData.getClass() == Hallway.class)) {
                    setPosition(newPosition);
                }
                break;
            case RIGHT:
                newPosition = new Vector2D(getPosition().getX() + 1, getPosition().getY());
                handleDiscovery(newPosition);
                uData = Global.terminalHandler.getUserDataAt(newPosition.getX(), newPosition.getY());
                if (uData != null)
                    handleCombat(uData);

                if (getPosition().getX() < Global.columns - 1 && uData != null
                        && (uData.getClass() == EmptySpace.class || uData.getClass() == Hallway.class)) {
                    setPosition(newPosition);
                }
                break;
            case UP:
                newPosition = new Vector2D(getPosition().getX(), getPosition().getY() - 1);
                handleDiscovery(newPosition);
                if (getPosition().getY() > 0) {
                    uData = Global.terminalHandler.getUserDataAt(newPosition.getX(), newPosition.getY());
                    if (uData != null)
                        handleCombat(uData);

                    if (uData != null
                            && (uData.getClass() == EmptySpace.class || uData.getClass() == Hallway.class)) {
                        setPosition(newPosition);
                    }
                }

                break;
            default:
                break;

        }
    }

    @Override
    public String toString() {
        return "Player";
    }

    private void tryUse() {
        int pX = this.getPosition().getX(), pY = this.getPosition().getY();
        Vector<Object> uData = new Vector<Object>();

        for (int i = -1; i <= 1; i++) {
            if (pX + i <= Global.columns && pX + i >= 0 && pY + i <= Global.rows && pY + i >= 0) {
                if (Global.terminalHandler.getUserDataAt(pX + i, pY) != null)
                    uData.add(Global.terminalHandler.getUserDataAt(pX + i, pY));

                if (Global.terminalHandler.getUserDataAt(pX, pY + i) != null)
                    uData.add(Global.terminalHandler.getUserDataAt(pX, pY + i));
            }
        }

        if (uData != null) {
            for (Object object : uData) {
                if (object.getClass() == LootBox.class) {
                    for (Room room : this.m_DiscoveredRooms) {
                        if (room.getRect().contains(this.getPosition().getX(), this.getPosition().getY())) {
                            LootBox lootBox = (LootBox) object;
                            if (lootBox.isUseable()) {
                                lootBox.useItem();
                            }
                        }
                    }
                } else if (object.getClass() == Staircase.class) {
                    for (Room room : this.m_DiscoveredRooms) {
                        if (room.getRect().contains(this.getPosition().getX(), this.getPosition().getY())) {
                            Staircase staircase = (Staircase) object;
                            if (staircase.isUseable()) {
                                staircase.useItem();
                            }

                        }
                    }
                }
            }
        }
    }

    public void toggleInventoryState() {
        Global.getGameLoop().setInventoryToggled(!Global.getGameLoop().getInventoryToggled());
    }

    @Override
    public void update() {
        if (Global.terminalHandler.keyIsPressed('w'))
            this.handleMovement(MoveDirection.UP);
        if (Global.terminalHandler.keyIsPressed('a'))
            this.handleMovement(MoveDirection.LEFT);
        if (Global.terminalHandler.keyIsPressed('s'))
            this.handleMovement(MoveDirection.DOWN);
        if (Global.terminalHandler.keyIsPressed('d'))
            this.handleMovement(MoveDirection.RIGHT);
        if (Global.terminalHandler.keyIsPressed('e'))
            this.tryUse();
        if (this.getHealthController().getHealth() <= 0) {
            Global.terminalHandler.restoreState();
            System.out.println("Game Over! You died a tragic death");
            System.exit(0);
        }

    }

    public void clearDiscoveredRooms() {
        this.m_DiscoveredRooms.clear();
    }

    public Vector<Room> getDiscoveredRooms() {
        return this.m_DiscoveredRooms;
    }

    public void setRoomDiscovered(Room room) {
        this.m_DiscoveredRooms.add(room);
    }

    public void clearDiscoveredHallways() {
        this.m_DiscoveredHallways.clear();
    }

    public Vector<Hallway> getDiscoveredHallways() {
        return this.m_DiscoveredHallways;
    }

    public void setHallwayDiscovered(Hallway hallway) {
        this.m_DiscoveredHallways.add(hallway);
    }

    public void toggleInvisible() {
        this.m_Invisible = !this.m_Invisible;
    }

    public boolean isInvisible() {
        return this.m_Invisible;
    }

    private boolean m_Invisible = false;
    private Vector<Room> m_DiscoveredRooms = new Vector<Room>();
    private Vector<Hallway> m_DiscoveredHallways = new Vector<Hallway>();
}