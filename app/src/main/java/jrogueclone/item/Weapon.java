package jrogueclone.item;

import jrogueclone.gfx.ui.Inventory.ItemType;

public class Weapon extends Item {
    public Weapon(String weaponName, int weaponDamage, int weaponDamageChance, int durability) {
        this.m_ItemType = ItemType.WEAPON;
        this.m_WeaponName = weaponName;
        this.m_WeaponDamage = weaponDamage;
        this.m_WeaponDamageChance = weaponDamageChance;
        this.m_Durability = durability;
        this.m_MaxDurability = durability;
    }

    public Weapon(Weapon weapon) {
        this.m_ItemType = weapon.m_ItemType;
        this.m_WeaponName = weapon.m_WeaponName;
        this.m_WeaponDamage = weapon.m_WeaponDamage;
        this.m_WeaponDamageChance = weapon.m_WeaponDamageChance;
        this.m_MaxDurability = weapon.m_MaxDurability;
        this.m_Durability = weapon.m_Durability;
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

    public int getDurability() {
        return m_Durability;
    }

    public int getMaxDurability() {
        return m_MaxDurability;
    }

    public void setDurability(int durability) {
        m_Durability = durability;
    }

    @Override
    public void draw() {
        if (this.isUseable()) {
            // draw it
        }
    }

    @Override
    public void useItem() {
    }

    @Override
    public String toString() {
        return "\"" + m_WeaponName + "\"" + " (" + m_WeaponDamage + "dmg, " + m_WeaponDamageChance + "% dmg chance "
                + m_Durability + "/" + m_MaxDurability + " durability)";
    }

    private String m_WeaponName = "";
    private int m_WeaponDamage = 0;
    private int m_WeaponDamageChance = 0;
    private final int m_MaxDurability;
    private int m_Durability;
}
