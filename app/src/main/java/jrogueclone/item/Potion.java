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
            case HEALTH: {
                HealthController hc = Global.getGameLoop().getCurrentLevel().getPlayer().getHealthController();
                if (hc.getHealth() + 40 <= hc.getMaxHealth())
                    hc.addHealth(40);
                else
                    hc.setHealthMax();
                break;
            }
            case INVISIBILTY: {
                Global.getGameLoop().getCurrentLevel().getPlayer().setInvisible(true);
                break;
            }
            case MYSTERY: {
                int randomEffect = (int) Math.round(Math.random() * 2 + 1);
                
                if (randomEffect == 1) {

                    HealthController hc = Global.getGameLoop().getCurrentLevel().getPlayer().getHealthController();
                    if (hc.getHealth() + 40 <= hc.getMaxHealth())
                        hc.addHealth(40);
                    else
                        hc.setHealthMax();

                } else if (randomEffect == 2) {
                    Global.getGameLoop().getCurrentLevel().getPlayer().setInvisible(true);
                } else {
                    HealthController hc = Global.getGameLoop().getCurrentLevel().getPlayer().getHealthController();
                    hc.addHealth(-40);
                }
                break;
            }
        }

    }

    @Override
    public String toString() {
        switch (m_PotionType) {
            case HEALTH:
                return "\"" + m_PotionName + "\"" + " (Heals 40HP)";

            case INVISIBILTY:
                return "\"" + m_PotionName + "\"" + " (Makes you invisible until you attack)";

            case MYSTERY:
                return "\"" + m_PotionName + "\"" + " (A Mystery potion! Could be bad, could be good)";

            default:
                return "error";
        }
    }

    @Override
    public void draw() {
    }

    public String getPotionName() {
        return this.m_PotionName;
    }

    public PotionType getPotionType() {
        return this.m_PotionType;
    }

    private String m_PotionName;
    private PotionType m_PotionType;

}
