package com.github.taichi3012.thelowtooltipmod.weapon;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.StringUtils;

import com.github.taichi3012.thelowtooltipmod.damagefactor.JobType;
import com.github.taichi3012.thelowtooltipmod.damagefactor.ResultCategoryType;
import com.github.taichi3012.thelowtooltipmod.util.DamageCalcUtil;
import com.github.taichi3012.thelowtooltipmod.util.TheLowUtil;

public class WeaponAmrudad extends WeaponBasic {

    public WeaponAmrudad(WeaponData weaponData) {
        super(weaponData);
    }

    public WeaponAmrudad(ItemStack stack) {
        super(stack);
    }

    @Override
    public List<String> generateResultContext() {
        List<String> result = new ArrayList<>();
        Map<ResultCategoryType, Double> damages = DamageCalcUtil.removeAllRedundancy(generateCategorizedDamage(true));
        Comparator<ResultCategoryType> comparator = Comparator.comparingDouble(damages::get);

        JobType job = TheLowUtil.getPlayerJobType();
        if (!job.equals(JobType.DARK_BLASTER)) {
            return new WeaponBasic(weaponData).generateResultContext();
        }

        result.add("§4[ダメージ]");
        damages.keySet().stream().sorted(comparator.reversed())
                .forEach(category -> {
                    double normalDamage = damages.get(category);
                    double BossDamage = normalDamage * 1.1d;

                    result.add(StringUtils.repeat(' ', 2) + category.getDisplayColor() + category.getName() + ":");
                    result.add(String.format(StringUtils.repeat(' ', 4) + "§7Boss:§6+%1$s§c§o(+%2$s)", DamageCalcUtil.roundDamage(BossDamage), DamageCalcUtil.roundCriticalDamage(BossDamage)));
                    result.add(String.format(StringUtils.repeat(' ', 4) + "§7Mob :§6+%1$s§c§o(+%2$s)", DamageCalcUtil.roundDamage(normalDamage), DamageCalcUtil.roundCriticalDamage(normalDamage)));
                });

        return result;
    }
}
