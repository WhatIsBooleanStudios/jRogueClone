package jrogueclone.entity;

import java.util.Vector;

import jrogueclone.Global;
import jrogueclone.game.Vector2D;
import jrogueclone.gfx.ui.Inventory.ItemType;
import jrogueclone.item.Item;
import jrogueclone.item.LootBox;
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
                m_Invisible ? 239 : 9,
                m_FrozenDuration == 0 ? Global.terminalHandler.getBackgroundColorAt(getPosition().getX(), getPosition().getY()) : 75,
                true);
    }

    @Override
    public boolean isMonster() {
        return false;
    }

    @Override
    public void handleEntitySpawn() {

        this.getInventory().addItem(new Weapon("Damaged Wooden Sword",
                20, 70, 10));
        this.getInventory().equipItem(this.getInventory().getItems().elementAt(0));
        this.getHealthController().setHealthCapacity(100);
        this.getHealthController().setHealthMax();
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

        if(m_Invisible) {
            Global.terminalHandler.appendTopStatusBarString(" You are now visible!", 255, 232, false);
        }
        this.setInvisible(false);

        if(getFrozenDuration() <= 0) {
            if (activeWeapon.getWeaponDamageChance() >= Math.random() * 99 + 1) {
                boolean leveledUp = false;
                HealthController hc = entity.getHealthController();
                hc.deductHealth(activeWeapon.getWeaponDamage());
                String toPrint = " You dealt " + activeWeapon.getWeaponDamage() + "dmg to " + entity.toString();
                if(hc.getHealth() <= 0) {
                    m_XP += entity.getExperienceReward();
                    if(m_XP >= m_TargetXP) {
                        m_Level++;
                        m_XP = 0;
                        m_TargetXP *= 2.0;
                        getHealthController().setHealthCapacity(getHealthController().getMaxHealth() + 50);
                        getHealthController().setHealthMax();
                        leveledUp = true;
                    }
                    toPrint += " and killed it! ";
                } else {
                    toPrint += ". ";
                }
                
                activeWeapon.setDurability(activeWeapon.getDurability() - 1);
                if(activeWeapon.getDurability() <= 0) {
                    toPrint += "Your weapon broke! ";
                    getInventory().getItems().remove(activeWeapon);
                    getInventory().getEquippedItems().remove(activeWeapon);
                    Weapon newWeapon = null;
                    for(Item w : getInventory().getItems()) {
                        if(w.getClass() == Weapon.class) {
                            newWeapon = (Weapon)w;
                            break;
                        }
                    }
                    if(newWeapon == null) {
                        toPrint += "No weapons to equip! ";
                    } else {
                        getInventory().equipItem(newWeapon);
                    }
                }

                Global.terminalHandler.appendTopStatusBarString(
                        toPrint, 255, 232, false);
                if(leveledUp) {
                    Global.terminalHandler.appendTopStatusBarString(
                        " You leveled up to level " + m_Level + "!", 255, 232, false);
                }
            } else {
                String toDisplay = " You missed the " + entity.toString();
                //toDisplay += " ".repeat(Global.columns - toDisplay.length());

                Global.terminalHandler.appendTopStatusBarString(
                        toDisplay, 255, 232, false);
            }
        }
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
        if(m_FrozenDuration <= 0) {
            m_FrozenDuration = 0;
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
        } else {
            m_FrozenDuration--;
            if(m_FrozenDuration > 0)
                Global.terminalHandler.appendTopStatusBarString(" Frozen " + m_FrozenDuration + " more turns!", 255, 233, false);
        }
        if (this.getHealthController().getHealth() <= 0) {
            Global.terminalHandler.restoreState();
            System.out.println("Game Over! You died a tragic death\nFinal Score: "
                    + (this.getKillCount() + Global.getGameLoop().getCurrentLevel().getDifficulty()));
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

    public void setInvisible(boolean invisible) {
        this.m_Invisible = invisible;
    }

    public boolean isInvisible() {
        return this.m_Invisible;
    }

    public int getKillCount() {
        return this.m_KillCount;
    }

    public void incrementKillCount() {
        this.m_KillCount++;
    }
    
    public void setFrozenDuration(int duration) {
        m_FrozenDuration = duration;
    }
    
    public int getFrozenDuration() {
        return m_FrozenDuration;
    }
    
    @Override
    public int getExperienceReward() {
        return 0;
    }
    
    public int getXP() { return m_XP; }
    public int getTargetXP() { return m_TargetXP; }
    public int getLevel() { return m_Level; }

    private int m_KillCount = 0;
    private int m_FrozenDuration = 0;
    private int m_Level = 1;
    private int m_XP = 0;
    private int m_TargetXP = 10;
    private boolean m_Invisible = false;
    private Vector<Room> m_DiscoveredRooms = new Vector<Room>();
    private Vector<Hallway> m_DiscoveredHallways = new Vector<Hallway>();
}