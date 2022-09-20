package jrogueclone.entity;

import jrogueclone.Global;
import jrogueclone.game.Vector2D;
import jrogueclone.item.Weapon;

public class Player extends Entity {
    public Player(char entityCharacter) {
        super(entityCharacter);
    }

    public Player(char entityCharacter, Vector2D entityPosition) {
        super(entityCharacter, entityPosition);
    }

    @Override
    public void draw() {
        Global.terminalHandler.putChar(
            getPosition().getX(),
            getPosition().getY(),
            getEntityCharacter(),
            9,
            Global.terminalHandler.getBackgroundColorAt(getPosition().getX(), getPosition().getY()),
            true
        );
    }

    @Override
    public void handleEntitySpawn() {

        // Give the player a weapon with a 70% chance to enflict 34 damage
        this.m_AvailableWeapons.add( new Weapon("Damaged Wooden Sword",
                34, 70));
        
        this.getHealthController().setHealth(100);
        this.m_TilesPerSecond = 1;
    }

    public void tryMoveUp() {
        if(getPosition().getY() > 0 && Global.terminalHandler.getCurrentCharacterAt(getPosition().getX(), getPosition().getY() - 1) != '#')
            setPosition(new Vector2D(getPosition().getX(), getPosition().getY() - 1));
    }

    public void tryMoveLeft() {
        if(getPosition().getX() > 0 && Global.terminalHandler.getCurrentCharacterAt(getPosition().getX() - 1, getPosition().getY()) != '#') {
            setPosition(new Vector2D(getPosition().getX() - 1, getPosition().getY()));
        }
    }

    public void tryMoveDown() {
        if(getPosition().getY() < Global.rows - 1 && Global.terminalHandler.getCurrentCharacterAt(getPosition().getX(), getPosition().getY() + 1) != '#') {
            setPosition(new Vector2D(getPosition().getX(), getPosition().getY() + 1));
        }
    }

    public void tryMoveRight() {
        if(getPosition().getX() < Global.columns - 1 && Global.terminalHandler.getCurrentCharacterAt(getPosition().getX() + 1, getPosition().getY()) != '#') {
            setPosition(new Vector2D(getPosition().getX() + 1, getPosition().getY()));
        }
    }
}