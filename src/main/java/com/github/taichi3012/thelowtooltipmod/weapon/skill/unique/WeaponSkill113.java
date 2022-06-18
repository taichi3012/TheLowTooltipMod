package com.github.taichi3012.thelowtooltipmod.weapon.skill.unique;

import com.github.taichi3012.thelowtooltipmod.damagefactor.JobType;
import com.github.taichi3012.thelowtooltipmod.damagefactor.ResultCategoryType;
import com.github.taichi3012.thelowtooltipmod.damagefactor.WeaponType;
import com.github.taichi3012.thelowtooltipmod.util.DamageCalcUtil;
import com.github.taichi3012.thelowtooltipmod.util.TheLowUtil;
import com.github.taichi3012.thelowtooltipmod.weapon.WeaponBasic;
import com.github.taichi3012.thelowtooltipmod.weapon.WeaponData;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class WeaponSkill113 extends WeaponSkillUniqueBase {

    public WeaponSkill113() {
        super("wskill113", "闇の解放", "29");
    }

    @Override
    public double getCoolTime(WeaponData weaponData) {
        return 0.0d;
    }

    @Override
    public @NotNull List<String> getResultContext(WeaponData weaponData) {
        List<String> result = new ArrayList<>();
        Map<ResultCategoryType, Double> damages =
                DamageCalcUtil.removeAllRedundancy(new WeaponBasic(weaponData).generateCategorizedDamage(true));
        double abilityGain = TheLowUtil.getAttackMultiplyAbilityGain(WeaponType.SWORD);
        JobType job = TheLowUtil.getPlayerJobType();

        result.add("§4[ダメージ]");
        damages.keySet().stream()
                .sorted(Comparator.comparingDouble(damages::get).reversed())
                .forEach(category -> {
                    double normalDamage = damages.get(category) * 2.0d;

                    result.add(StringUtils.repeat(' ', 2) + category.getDisplayColor() + category.getName() + ":");
                    result.add(String.format(StringUtils.repeat(' ', 4) + "§7通常:§6+%1$s§c§o(+%2$s)", DamageCalcUtil.roundDamage(normalDamage), DamageCalcUtil.roundCriticalDamage(normalDamage)));
                    if (job.equals(JobType.DARK_BLASTER)) {
                        result.add(String.format(StringUtils.repeat(' ', 4) + "§7Boss:§6+%1$s§c§o(+%2$s)", DamageCalcUtil.roundDamage(normalDamage * 1.1d), DamageCalcUtil.roundCriticalDamage(normalDamage * 1.1d)));
                    }
                });

        return result;
    }
}
