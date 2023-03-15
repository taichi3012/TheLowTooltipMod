package com.github.taichi3012.thelowtooltipmod.weapon;

import com.github.taichi3012.thelowtooltipmod.damagefactor.JobType;
import com.github.taichi3012.thelowtooltipmod.damagefactor.ResultCategoryType;
import com.github.taichi3012.thelowtooltipmod.damagefactor.UniqueSpecialType;
import com.github.taichi3012.thelowtooltipmod.util.DamageCalcUtil;
import com.github.taichi3012.thelowtooltipmod.util.TheLowUtil;
import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class WeaponGekokujo extends WeaponBasic {
    public WeaponGekokujo(WeaponData weaponData) {
        super(weaponData);
    }

    public WeaponGekokujo(ItemStack stack) {
        super(stack);
    }

    @Override
    public List<String> generateResultContext() {
        List<String> result = new ArrayList<>();
        Map<ResultCategoryType, Double> damages = DamageCalcUtil.removeAllRedundancy(generateCategorizedDamage(true));
        Comparator<ResultCategoryType> comparator = Comparator.comparingDouble(damages::get);

        JobType job = TheLowUtil.getPlayerJobType();
        Set<UniqueSpecialType> specials = weaponData.getSpecialDamageList().keySet();
        //武器の固有特攻で聖剣か冥剣かを判断
        boolean isJobApply =
                (job.equals(JobType.SKULL_PIERCER) && specials.contains(UniqueSpecialType.SKELETON_SPECIAL_DAMAGE)) ||
                (job.equals(JobType.DARK_BLASTER) && specials.contains(UniqueSpecialType.ZOMBIE_SPECIAL_DAMAGE));

        result.add("§4[ダメージ]");
        damages.keySet().stream().sorted(comparator.reversed())
                .forEach(category -> {
                    double normalDamage = damages.get(category);
                    double BossDamage = normalDamage * (isJobApply ? 1.3d : 1.07d);
                    double MobDamage = normalDamage * 0.7d;

                    result.add(StringUtils.repeat(' ', 2) + category.getDisplayColor() + category.getName() + ":");
                    result.add(String.format(StringUtils.repeat(' ', 4) + "§7Boss:§6+%1$s§c§o(+%2$s)", DamageCalcUtil.roundDamage(BossDamage), DamageCalcUtil.roundCriticalDamage(BossDamage)));
                    result.add(String.format(StringUtils.repeat(' ', 4) + "§7Mob :§6+%1$s§c§o(+%2$s)", DamageCalcUtil.roundDamage(MobDamage), DamageCalcUtil.roundCriticalDamage(MobDamage)));
                });

        return result;
    }
}
