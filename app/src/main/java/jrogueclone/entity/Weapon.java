package jrogueclone.entity;

public class Weapon {
    public Weapon(String weaponName, int weaponDamage) {
        this.m_WeaponName = weaponName;    
        this.m_WeaponDamage = weaponDamage;
    }
    
    public String getWeaponName() {
        return this.m_WeaponName;
    }

    public int getWeaponDamage() {
        return this.m_WeaponDamage;
    }

    private String m_WeaponName = "";
    private int m_WeaponDamage = 0; 
}
