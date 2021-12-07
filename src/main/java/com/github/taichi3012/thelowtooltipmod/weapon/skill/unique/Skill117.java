package com.github.taichi3012.thelowtooltipmod.weapon.skill.unique;

import com.github.taichi3012.thelowtooltipmod.config.TheLowTooltipModConfig;
import com.github.taichi3012.thelowtooltipmod.damagefactor.ResultCategoryType;
import com.github.taichi3012.thelowtooltipmod.util.DamageCalcUtil;
import com.github.taichi3012.thelowtooltipmod.util.MagicStoneUtil;
import com.github.taichi3012.thelowtooltipmod.weapon.WeaponBasic;
import com.github.taichi3012.thelowtooltipmod.weapon.WeaponData;
import com.github.taichi3012.thelowtooltipmod.weapon.skill.IWeaponSkillAble;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class Skill117 implements IWeaponSkillAble {
    @Override
    public double getCoolTime(WeaponData weaponData) {
        return 35.0d * TheLowTooltipModConfig.getQuickSpellTalkMultiply() * MagicStoneUtil.getCasterMultiply(weaponData);
    }

    @Override
    public String getId() {
        return "wskill117";
    }

    @Override
    public String getSkillSetId() {
        return "30,34";
    }

    @Override
    public String getName(WeaponData weaponData) {
        return isActive(weaponData) ? "オーバーシュート" : "";
    }

    @Override
    public boolean isActive(WeaponData weaponData) {
        return weaponData.getSkillSetId() != null && getSkillSetId().contains(weaponData.getSkillSetId());
    }

    @Override
    public List<String> getResultContext(WeaponData weaponData) {
        List<String> result = new ArrayList<>();
        Map<ResultCategoryType, Double> damages =
                DamageCalcUtil.removeAllRedundancy(new WeaponBasic(weaponData).generateCategorizedDamage(false));

        result.add("§4[ダメージ]");
        damages.keySet().stream()
                .sorted(Comparator.comparingDouble(damages::get).reversed())
                .forEach(category -> {
                    double normalDamage = damages.get(category) * 12.5d;

                    result.add(StringUtils.repeat(' ', 2) + category.getDisplayColor() + category.getName() + ":");
                    if ("30".equals(weaponData.getSkillSetId())) {
                        double shadowPowerAbleDamage = normalDamage * 1.5d;
                        result.add(String.format(StringUtils.repeat(' ', 4) + "§7通常:§6+%1$s§c§o(+%2$s)", DamageCalcUtil.roundDamage(normalDamage), DamageCalcUtil.roundCriticalDamage(normalDamage)));
                        result.add(String.format(StringUtils.repeat(' ', 4) + "§7シャドウパワーあり:§6+%1$s§c§o(+%2$s)", DamageCalcUtil.roundDamage(shadowPowerAbleDamage), DamageCalcUtil.roundCriticalDamage(shadowPowerAbleDamage)));
                    } else {
                        result.add(String.format(StringUtils.repeat(' ', 4) + "§6+%1$s§c§o(+%2$s)", DamageCalcUtil.roundDamage(normalDamage), DamageCalcUtil.roundCriticalDamage(normalDamage)));
                    }
                });

        return result;
    }
}
