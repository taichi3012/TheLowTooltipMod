package com.github.taichi3012.thelowtooltipmod.weapon;

import com.github.taichi3012.thelowtooltipmod.damagefactor.ResultCategoryType;
import com.github.taichi3012.thelowtooltipmod.util.DamageCalcUtil;
import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class WeaponPlaceLight extends WeaponBasic{
    public WeaponPlaceLight(WeaponData weaponData) {
        super(weaponData);
    }

    public WeaponPlaceLight(ItemStack stack) {
        super(stack);
    }

    @Override
    public @NotNull List<String> generateResultContext() {
        List<String> result = new ArrayList<>();
        Map<ResultCategoryType, Double> damages = DamageCalcUtil.removeAllRedundancy(generateCategorizedDamage(true));
        Comparator<ResultCategoryType> comparator = Comparator.comparingDouble(damages::get);

        result.add("§4[ダメージ]");
        damages.keySet().stream().sorted(comparator.reversed())
                .forEach(category -> {
                    double normalDamage = damages.get(category);
                    double passiveAbleDamage = normalDamage * 1.2d;

                    result.add(StringUtils.repeat(' ', 2) + category.getDisplayColor() + category.getName() + ":");
                    result.add(String.format(StringUtils.repeat(' ', 4) + "§7パッシブ有効:§6+%1$s§c§o(+%2$s)", DamageCalcUtil.roundDamage(passiveAbleDamage), DamageCalcUtil.roundCriticalDamage(passiveAbleDamage)));
                    result.add(String.format(StringUtils.repeat(' ', 4) + "§7通常:§6+%1$s§c§o(+%2$s)", DamageCalcUtil.roundDamage(normalDamage), DamageCalcUtil.roundCriticalDamage(normalDamage)));
                });

        return result;
    }
}
