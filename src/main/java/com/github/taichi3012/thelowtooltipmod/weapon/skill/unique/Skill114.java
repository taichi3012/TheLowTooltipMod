package com.github.taichi3012.thelowtooltipmod.weapon.skill.unique;

import com.github.taichi3012.thelowtooltipmod.config.TheLowTooltipModConfig;
import com.github.taichi3012.thelowtooltipmod.damagefactor.ResultCategoryType;
import com.github.taichi3012.thelowtooltipmod.damagefactor.WeaponType;
import com.github.taichi3012.thelowtooltipmod.util.DamageCalcUtil;
import com.github.taichi3012.thelowtooltipmod.util.JavaUtil;
import com.github.taichi3012.thelowtooltipmod.util.MagicStoneUtil;
import com.github.taichi3012.thelowtooltipmod.util.TheLowUtil;
import com.github.taichi3012.thelowtooltipmod.weapon.WeaponBasic;
import com.github.taichi3012.thelowtooltipmod.weapon.WeaponData;
import com.github.taichi3012.thelowtooltipmod.weapon.skill.IWeaponSkillAble;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class Skill114 implements IWeaponSkillAble {
    @Override
    public double getCoolTime(WeaponData weaponData) {
        return 20.0d * TheLowTooltipModConfig.getQuickSpellTalkMultiply() * MagicStoneUtil.getCasterMultiply(weaponData) + 10.0d;
    }

    @Override
    public String getId() {
        return "wskill114";
    }

    @Override
    public String getSkillSetId() {
        return "29";
    }

    @Override
    public String getName(WeaponData weaponData) {
        return isActive(weaponData) ? "血の代償" : "";
    }

    @Override
    public boolean isActive(WeaponData weaponData) {
        return getSkillSetId().equals(weaponData.getSkillSetId());
    }

    @Override
    public @NotNull List<String> getResultContext(WeaponData weaponData) {
        List<String> result = new ArrayList<>();
        Map<ResultCategoryType, Double> damages =
                DamageCalcUtil.removeAllRedundancy(new WeaponBasic(weaponData).generateCategorizedDamage(true));
        double abilityGain = TheLowUtil.getAttackMultiplyAbilityGain(WeaponType.SWORD);

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
