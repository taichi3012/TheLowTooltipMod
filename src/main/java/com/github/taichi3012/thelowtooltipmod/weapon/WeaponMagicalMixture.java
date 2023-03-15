package com.github.taichi3012.thelowtooltipmod.weapon;

import com.github.taichi3012.thelowtooltipmod.damagefactor.ResultCategoryType;
import com.github.taichi3012.thelowtooltipmod.util.DamageCalcUtil;
import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class WeaponMagicalMixture extends WeaponBasic {
    public WeaponMagicalMixture(WeaponData weaponData) {
        super(weaponData);
    }

    public WeaponMagicalMixture(ItemStack stack) {
        super(stack);
    }

    @Override
    public List<String> generateResultContext() {
        List<String> result = new ArrayList<>();
        Map<ResultCategoryType, Double> damages = DamageCalcUtil.removeAllRedundancy(generateCategorizedDamage(true));
        Comparator<ResultCategoryType> comparator = Comparator.comparingDouble(damages::get);

        result.add("§4[ダメージ]");
        damages.keySet().stream().sorted(comparator.reversed())
                .forEach(category -> {
                    double normalDamage = damages.get(category);
                    double hitDamage = normalDamage * 1.7;
                    double explosiveDamage = normalDamage * 0.7;

                    result.add(StringUtils.repeat(' ', 2) + category.getDisplayColor() + category.getName() + ":");
                    result.add(String.format(StringUtils.repeat(' ', 4) + "§7直撃:§6+%1$s§c§o(+%2$s)", DamageCalcUtil.roundDamage(hitDamage), DamageCalcUtil.roundCriticalDamage(hitDamage)));
                    result.add(String.format(StringUtils.repeat(' ', 4) + "§7炸裂:§6+%1$s§c§o(+%2$s)", DamageCalcUtil.roundDamage(explosiveDamage), DamageCalcUtil.roundCriticalDamage(explosiveDamage)));
                });

        return result;
    }
}