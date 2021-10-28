package com.github.taichi3012.thelowtooltipmod.weapon;

import com.github.taichi3012.thelowtooltipmod.damagefactor.ResultCategoryType;
import com.github.taichi3012.thelowtooltipmod.damagefactor.WeaponType;
import com.github.taichi3012.thelowtooltipmod.damagefactor.magicstone.MagicStoneData;
import com.github.taichi3012.thelowtooltipmod.damagefactor.UniqueSpecialType;
import com.github.taichi3012.thelowtooltipmod.damagefactor.magicstone.SpecialMagicStoneType;
import com.github.taichi3012.thelowtooltipmod.util.TheLowNBTUtil;
import com.github.taichi3012.thelowtooltipmod.util.TheLowUtil;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;

public class WeaponData {
    private final ItemStack item;
    private final WeaponType weaponType;
    private final double damage;
    private final Map<UniqueSpecialType, Double> specialDamage;
    private final List<MagicStoneData> magicStone;

    public WeaponData(@Nonnull ItemStack item) {
        if (!TheLowNBTUtil.hasDamage(item)) throw new IllegalArgumentException("指定されたアイテムには攻撃力がセットされていません。");
        this.item = item;
        this.weaponType = TheLowUtil.getWeaponType(item);
        this.damage = TheLowNBTUtil.getDamage(item);
        this.specialDamage = TheLowNBTUtil.getSpecialDamage(item);
        this.magicStone = TheLowNBTUtil.getMagicStoneData(item);
    }

    public ItemStack getItem() {
        return item;
    }

    public WeaponType getWeaponType() {
        return weaponType;
    }

    public double getDamage() {
        return damage;
    }

    public Map<UniqueSpecialType, Double> getSpecialDamageList() {
        return specialDamage;
    }

    public List<MagicStoneData> getMagicStoneList() {
        return magicStone;
    }

    public double getSpecialDamage(ResultCategoryType categoryType) {
        if (specialDamage == null) return 0d;
        return specialDamage.keySet().stream()
                .filter(type -> type.isAvailable(categoryType))
                .mapToDouble(type -> damage * specialDamage.get(type)).sum();
    }

    public double getMSMultiply(ResultCategoryType categoryType) {
        if (magicStone == null) return 1d;
        OptionalDouble op = magicStone.stream()
                .filter(MagicStoneData::isSpecialType)
                .filter(data -> ((SpecialMagicStoneType) data.getMagicStone()).isAvailable(categoryType))
                .mapToDouble(data -> data.getLevel().getDamageMultiply())
                .reduce((v1, v2) -> v1 * v2);

        return op.isPresent() ? op.getAsDouble()
                :1d;
    }

}
