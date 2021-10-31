package com.github.taichi3012.thelowtooltipmod.weapon;

import com.github.taichi3012.thelowtooltipmod.damagefactor.ResultCategoryType;
import com.github.taichi3012.thelowtooltipmod.util.DamageCalcUtil;
import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class MagicalMixtureWeapon extends Weapon{
    public MagicalMixtureWeapon(WeaponData weaponData) {
        super(weaponData);
    }

    public MagicalMixtureWeapon(ItemStack item) {
        super(item);
    }

    @Override
    public List<String> getContext() {
        List<String> result = new ArrayList<>();
        Map<ResultCategoryType, Double> damages = DamageCalcUtil.removeAllRedundancy(getCategorizedDamage());
        Comparator<ResultCategoryType> comparator = Comparator.comparingDouble(damages::get);

        damages.keySet().stream().sorted(comparator.reversed())
                .forEach(category -> {
                    double damage = damages.get(category);
                    double hitDmg = DamageCalcUtil.getRoundDamage(damage * 1.7);
                    double explosiveDmg = DamageCalcUtil.getRoundDamage(damage * 0.7);

                    result.add(StringUtils.repeat(' ', 2) + category.getDisplayColor() + category.getName() + ":");
                    result.add(String.format(StringUtils.repeat(' ', 4) + "§6直撃:+%s§c§o(+%s)", hitDmg, DamageCalcUtil.getCriticalDamage(hitDmg)));
                    result.add(String.format(StringUtils.repeat(' ', 4) + "§6炸裂:+%s§c§o(+%s)", explosiveDmg, DamageCalcUtil.getCriticalDamage(explosiveDmg)));
                });

        return result;
    }
}