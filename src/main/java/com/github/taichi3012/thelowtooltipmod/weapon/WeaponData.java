package com.github.taichi3012.thelowtooltipmod.weapon;

import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;

import net.minecraft.item.ItemStack;

import com.github.taichi3012.thelowtooltipmod.damagefactor.ResultCategoryType;
import com.github.taichi3012.thelowtooltipmod.damagefactor.UniqueSpecialType;
import com.github.taichi3012.thelowtooltipmod.damagefactor.WeaponType;
import com.github.taichi3012.thelowtooltipmod.damagefactor.magicstone.MagicStoneData;
import com.github.taichi3012.thelowtooltipmod.damagefactor.magicstone.SpecialMagicStoneType;
import com.github.taichi3012.thelowtooltipmod.util.TheLowNBTUtil;
import com.github.taichi3012.thelowtooltipmod.util.TheLowUtil;

public class WeaponData {
    private final ItemStack itemStack;
    private final String theLowId;
    private final String skillSetId;
    private final WeaponType weaponType;
    private final double damage;
    private final Map<UniqueSpecialType, Double> specialDamage;
    private final List<MagicStoneData> magicStone;

    public WeaponData(ItemStack stack) {
        if (!TheLowNBTUtil.hasDamage(stack)) {
            throw new IllegalArgumentException("指定されたアイテムには攻撃力がセットされていません。");
        }

        this.itemStack = stack;
        this.theLowId = TheLowNBTUtil.getTheLowID(stack);
        this.skillSetId = TheLowNBTUtil.getSkillSetID(stack);
        this.weaponType = TheLowUtil.generateWeaponType(stack);
        this.damage = TheLowNBTUtil.getDamage(stack);
        this.specialDamage = TheLowNBTUtil.getSpecialDamage(stack);
        this.magicStone = TheLowNBTUtil.getMagicStoneData(stack);
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public String getTheLowId() {
        return theLowId;
    }

    public String getSkillSetId() {
        return skillSetId;
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
        return specialDamage.keySet().stream()
                .filter(type -> type.isAvailable(categoryType))
                .mapToDouble(type -> damage * specialDamage.get(type)).sum();
    }

    public double getMSMultiply(ResultCategoryType categoryType) {
        OptionalDouble op = magicStone.stream()
                .filter(MagicStoneData::isSpecialType)
                .filter(data -> ((SpecialMagicStoneType) data.getMagicStone()).isAvailable(categoryType))
                .mapToDouble(data -> data.getLevel().getDamageMultiply())
                .reduce((v1, v2) -> v1 * v2);

        return op.isPresent() ? op.getAsDouble() : 1.0d;
    }

}
