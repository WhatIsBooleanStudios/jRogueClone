package jrogueclone.item;

import jrogueclone.Global;
import jrogueclone.entity.Entity.HealthController;
import jrogueclone.game.Vector2D;
import jrogueclone.gfx.ui.Inventory.ItemType;

public class Potion extends Item {
    public enum PotionType {
        HEALTH,
        INVISIBILTY,
        MYSTERY
    }

    public Potion(PotionType potionType) {
        setItemData('i', 128, new Vector2D(-1, -1), ItemType.POTION);
        this.m_PotionType = potionType;
        switch (this.m_PotionType) {
            case HEALTH:
                m_PotionName = "Health Potion";
                break;
            case INVISIBILTY:
                m_PotionName = "Invisibility Potion";
                break;
            case MYSTERY:
                m_PotionName = "Mystery Potion";
                break;
        }
    }

    @Override
    public void useItem() {
        switch (this.m_PotionType) {
            case HEALTH:
                break;
            case INVISIBILTY:
                break;
            case MYSTERY:
                int randomEffect = (int) Math.round(Math.random() * 2 + 1);
                if (randomEffect == 1) {

                    HealthController hc = Global.getGameLoop().getCurrentLevel().getPlayer().getHealthController();
                    if (hc.getHealth() <= 60) // heal 40 damage
                        hc.setHealth(hc.getHealth() + 40);

                } else if (randomEffect == 2) {
                    Global.getGameLoop().getCurrentLevel().getPlayer().setInvisible();
                } else {
                    HealthController hc = Global.getGameLoop().getCurrentLevel().getPlayer().getHealthController();
                    hc.setHealth(hc.getHealth() - 40);
                }
                break;

        }

    }

    @Override
    public void draw() {
    }

    public String getPotionName() {
        return this.m_PotionName;
    }

    private String m_PotionName;
    private PotionType m_PotionType;
}
