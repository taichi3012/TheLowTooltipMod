package com.github.taichi3012.thelowtooltipmod.weapon;

import net.minecraft.item.ItemStack;

public class MagicalMixtureWeapon extends Weapon{
    //直撃1.7倍 炸裂0.7倍 サテキャ2.5倍 インフェライズ2倍 ダークサイクロン0.8倍(×5回) グロウ3.2倍
    public MagicalMixtureWeapon(WeaponData weaponData) {
        super(weaponData);
    }

    public MagicalMixtureWeapon(ItemStack item) {
        super(item);
    }
}
