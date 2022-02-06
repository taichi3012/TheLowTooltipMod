package com.github.taichi3012.thelowtooltipmod.weapon.skill.unique;

import com.github.taichi3012.thelowtooltipmod.config.TheLowTooltipModConfig;
import com.github.taichi3012.thelowtooltipmod.damagefactor.ResultCategoryType;
import com.github.taichi3012.thelowtooltipmod.util.DamageCalcUtil;
import com.github.taichi3012.thelowtooltipmod.util.MagicStoneUtil;
import com.github.taichi3012.thelowtooltipmod.weapon.WeaponBasic;
import com.github.taichi3012.thelowtooltipmod.weapon.WeaponData;
import com.github.taichi3012.thelowtooltipmod.weapon.skill.IWeaponSkillAble;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class Skill3 implements IWeaponSkillAble {
    @Override
    public double getCoolTime(WeaponData weaponData) {
        return 2.0d * TheLowTooltipModConfig.getQuickSpellTalkMultiply() * MagicStoneUtil.getCasterMultiply(weaponData);
    }

    @Override
    public String getId() {
        return "wskill3";
    }

    @Override
    public String getSkillSetId() {
        return "1,45";
    }

    @Override
    public String getName(WeaponData weaponData) {
        if (weaponData.getSkillSetId() == null) {
            return "";
        }

        switch (weaponData.getSkillSetId()) {
            case "1":
                return "パリィ";
            case "45":
                return "攻撃破壊";
            default:
                return "";
        }
    }

    @Override
    public boolean isActive(WeaponData weaponData) {
        return weaponData.getSkillSetId() != null && getSkillSetId().contains(weaponData.getSkillSetId());
    }

    @Override
    public @NotNull List<String> getResultContext(WeaponData weaponData) {
        List<String> result = new ArrayList<>();

        Map<ResultCategoryType, Double> damages =
                DamageCalcUtil.removeAllRedundancy(new WeaponBasic(weaponData).generateCategorizedDamage(false));

        result.add("§4[ダメージ]");
        damages.keySet().stream()
                .sorted(Comparator.comparingDouble(damages::get).reversed())
                .forEach(category -> {
                    double damage = damages.get(category) * 0.4;

                    result.add(StringUtils.repeat(' ', 2) + category.getDisplayColor() + category.getName() + ":");
                    result.add(String.format(StringUtils.repeat(' ', 4) + "§6+%1$s§c§o(+%2$s)", DamageCalcUtil.roundDamage(damage), DamageCalcUtil.roundCriticalDamage(damage)));
                });

        return result;
    }
}
