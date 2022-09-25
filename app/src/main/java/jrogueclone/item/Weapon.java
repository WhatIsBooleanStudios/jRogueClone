package jrogueclone.item;

import jrogueclone.gfx.ui.Inventory.ItemType;

public class Weapon extends Item {
    public Weapon(String weaponName, int weaponDamage, int weaponDamageChance) {
        this.m_ItemType = ItemType.WEAPON; 
        this.m_WeaponName = weaponName;
        this.m_WeaponDamage = weaponDamage;
        this.m_WeaponDamageChance = weaponDamageChance;
    }

    public String getWeaponName() {
        return this.m_WeaponName;
    }

    public int getWeaponDamage() {
        return this.m_WeaponDamage;
    }

    public int getWeaponDamageChance() {
        return this.m_WeaponDamageChance;
    }

    @Override
    public void draw() {
        if (this.isUseable()) {
            // draw it
        }
    }

    @Override
    public void useItem() {
        // TODO Auto-generated method stub

    }

    @Override
    public String toString() {
        return "\"" + m_WeaponName  + "\"" + " (" + m_WeaponDamage + "dmg, " + m_WeaponDamageChance + "% dmg chance)";
    }

    private String m_WeaponName = "";
    private int m_WeaponDamage = 0;
    private int m_WeaponDamageChance = 0;
}
