package jrogueclone.game;

public class Weapon {
    public Weapon(String weaponName, int weaponDamage, int weaponDamageChance) {
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

    private String m_WeaponName = "";
    private int m_WeaponDamage = 0;
    private int m_WeaponDamageChance = 0;
}
