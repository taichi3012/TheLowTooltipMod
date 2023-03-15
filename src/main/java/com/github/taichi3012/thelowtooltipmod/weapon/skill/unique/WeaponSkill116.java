package com.github.taichi3012.thelowtooltipmod.weapon.skill.unique;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.github.taichi3012.thelowtooltipmod.config.TheLowTooltipModConfig;
import com.github.taichi3012.thelowtooltipmod.damagefactor.JobType;
import com.github.taichi3012.thelowtooltipmod.damagefactor.ResultCategoryType;
import com.github.taichi3012.thelowtooltipmod.damagefactor.WeaponType;
import com.github.taichi3012.thelowtooltipmod.util.DamageCalcUtil;
import com.github.taichi3012.thelowtooltipmod.util.MagicStoneUtil;
import com.github.taichi3012.thelowtooltipmod.util.TheLowUtil;
import com.github.taichi3012.thelowtooltipmod.weapon.WeaponBasic;
import com.github.taichi3012.thelowtooltipmod.weapon.WeaponData;

public class WeaponSkill116 extends WeaponSkillUniqueBase {

    public WeaponSkill116() {
        super("wskill116", "冥府の審判", "29");
    }

    @Override
    public double getCoolTime(WeaponData weaponData) {
        return 60.0d * TheLowTooltipModConfig.getQuickSpellTalkMultiply() * MagicStoneUtil.getCasterMultiply(weaponData) + 5.0d;
    }

    @Override
    public List<String> getResultContext(WeaponData weaponData) {
        List<String> result = new ArrayList<>();
        Map<ResultCategoryType, Double> damages =
                DamageCalcUtil.removeAllRedundancy(new WeaponBasic(weaponData).generateCategorizedDamage(true));
        double abilityGain = TheLowUtil.getAttackMultiplyAbilityGain(WeaponType.SWORD);
        JobType job = TheLowUtil.getPlayerJobType();

        result.add("§4[ダメージ]");
        damages.keySet().stream()
                .sorted(Comparator.comparingDouble(damages::get).reversed())
                .forEach(category -> {
                    double normalDamage = damages.get(category) * 1.8d;
                    double daisyouAbleDamage = normalDamage * (abilityGain + 120.0d) / (abilityGain + 100);

                    result.add(StringUtils.repeat(' ', 2) + category.getDisplayColor() + category.getName() + ":");

                    if (job.equals(JobType.DARK_BLASTER)) {
                        result.add(String.format(StringUtils.repeat(' ', 4) + "§7Boss:§6+%1$s§c§o(+%2$s)", DamageCalcUtil.roundDamage(normalDamage * 1.1d), DamageCalcUtil.roundCriticalDamage(normalDamage * 1.1d)));
                        result.add(String.format(StringUtils.repeat(' ', 4) + "§7Boss血の代償あり:§6+%1$s§c§o(+%2$s)", DamageCalcUtil.roundDamage(daisyouAbleDamage * 1.1d), DamageCalcUtil.roundCriticalDamage(daisyouAbleDamage * 1.1d)));
                        return;
                    }

                    result.add(String.format(StringUtils.repeat(' ', 4) + "§7通常:§6+%1$s§c§o(+%2$s)", DamageCalcUtil.roundDamage(normalDamage), DamageCalcUtil.roundCriticalDamage(normalDamage)));
                    result.add(String.format(StringUtils.repeat(' ', 4) + "§7血の代償あり:§6+%1$s§c§o(+%2$s)", DamageCalcUtil.roundDamage(daisyouAbleDamage), DamageCalcUtil.roundCriticalDamage(daisyouAbleDamage)));
                });

        return result;
    }
}
