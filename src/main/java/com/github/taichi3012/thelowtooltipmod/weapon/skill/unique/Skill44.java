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

public class Skill44 implements IWeaponSkillAble {
    @Override
    public double getCoolTime(WeaponData weaponData) {
        return ("45".equals(weaponData.getSkillSetId()) ? 70.0d : 60.0d) * TheLowTooltipModConfig.getQuickSpellTalkMultiply() * MagicStoneUtil.getCasterMultiply(weaponData);
    }

    @Override
    public String getId() {
        return "wskill44";
    }

    @Override
    public String getSkillSetId() {
        return "11,45";
    }

    @Override
    public String getName(WeaponData weaponData) {
        if (weaponData.getSkillSetId() == null)
            return "";
        switch (weaponData.getSkillSetId()) {
            case "11":
                return "ショックストーン";
            case "45":
                return "魔力解放";
            default:
                return "";
        }
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

        double multiply = 7.0d;
        if (weaponData.getSkillSetId() != null) {
            switch (weaponData.getSkillSetId()) {
                case "45":
                    multiply = 6.0d;
                    break;
                case "11":
                default:
            }
        }
        final double finalMultiply = multiply;

        result.add("§4[ダメージ]");
        damages.keySet().stream()
                .sorted(Comparator.comparingDouble(damages::get).reversed())
                .forEach(category -> {
                    double damage = damages.get(category) * finalMultiply;

                    result.add(StringUtils.repeat(' ', 2) + category.getDisplayColor() + category.getName() + ":");
                    result.add(String.format(StringUtils.repeat(' ', 4) + "§6+%1$s§c§o(+%2$s)", DamageCalcUtil.roundDamage(damage), DamageCalcUtil.roundCriticalDamage(damage)));
                });

        return result;
    }
}
