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
import com.github.taichi3012.thelowtooltipmod.util.JavaUtil;
import com.github.taichi3012.thelowtooltipmod.util.MagicStoneUtil;
import com.github.taichi3012.thelowtooltipmod.util.TheLowUtil;
import com.github.taichi3012.thelowtooltipmod.weapon.WeaponBasic;
import com.github.taichi3012.thelowtooltipmod.weapon.WeaponData;

public class WeaponSkill114 extends WeaponSkillUniqueBase {

    public WeaponSkill114() {
        super("wskill114", "血の代償", "29");
    }

    @Override
    public double getCoolTime(WeaponData weaponData) {
        return 20.0d * TheLowTooltipModConfig.getQuickSpellTalkMultiply() * MagicStoneUtil.getCasterMultiply(weaponData) + 10.0d;
    }

    @Override
    public List<String> getResultContext(WeaponData weaponData) {
        List<String> result = new ArrayList<>();
        Map<ResultCategoryType, Double> damages =
                DamageCalcUtil.removeAllRedundancy(new WeaponBasic(weaponData).generateCategorizedDamage(true));
        double abilityGain = TheLowUtil.getAttackMultiplyAbilityGain(WeaponType.SWORD);
        JobType job = TheLowUtil.getPlayerJobType();

        if (WeaponType.SWORD.equals(weaponData.getWeaponType())) {
            result.add("§4[ダメージ]");
            damages.keySet().stream()
                    .sorted(Comparator.comparingDouble(damages::get).reversed())
                    .forEach(category -> {
                        double normalDamage = damages.get(category);
                        //normalDamage : resultDamage == nowAbility : nowAbility + gain
                        double decDamage = (normalDamage * (abilityGain + 70.0d)) / (abilityGain + 100);
                        double incDamage = (normalDamage * (abilityGain + 120.0d)) / (abilityGain + 100);

                        result.add(StringUtils.repeat(' ', 2) + category.getDisplayColor() + category.getName() + ":");

                        //専用職業だった場合はここで返す
                        if (job.equals(JobType.DARK_BLASTER)) {
                            result.add(String.format(StringUtils.repeat(' ', 4) + "§7Boss20%%上昇:§6+%1$s§c§o(+%2$s)", DamageCalcUtil.roundDamage(incDamage * 1.1d), DamageCalcUtil.roundCriticalDamage(incDamage * 1.1d)));
                            result.add(String.format(StringUtils.repeat(' ', 4) + "§7Mob20%%上昇 :§6+%1$s§c§o(+%2$s)", DamageCalcUtil.roundDamage(incDamage), DamageCalcUtil.roundCriticalDamage(incDamage)));
                            return;
                        }

                        result.add(String.format(StringUtils.repeat(' ', 4) + "§720%%上昇:§6+%1$s§c§o(+%2$s)", DamageCalcUtil.roundDamage(incDamage), DamageCalcUtil.roundCriticalDamage(incDamage)));
                        result.add(String.format(StringUtils.repeat(' ', 4) + "§730%%減少:§6+%1$s§c§o(+%2$s)", DamageCalcUtil.roundDamage(decDamage), DamageCalcUtil.roundCriticalDamage(decDamage)));
                    });
        }

        if (!result.isEmpty()) {
            result.add("");
        }

        result.add(String.format("§c[現在の倍率]§e -> ×%1$s倍", JavaUtil.digitRound((abilityGain + 120.0d) / (abilityGain + 100), 2.0d)));

        return result;
    }
}
