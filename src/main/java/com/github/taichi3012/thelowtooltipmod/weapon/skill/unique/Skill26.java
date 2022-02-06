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

public class Skill26 implements IWeaponSkillAble {
    @Override
    public String getId() {
        return "wskill26";
    }

    @Override
    public String getSkillSetId() {
        return "7";
    }

    @Override
    public String getName(WeaponData weaponData) {
        return isActive(weaponData) ? "アイスショット" : "";
    }

    @Override
    public double getCoolTime(WeaponData weaponData) {
        return 25.0d * TheLowTooltipModConfig.getQuickSpellTalkMultiply() * MagicStoneUtil.getCasterMultiply(weaponData) + 10.0d;
    }

    @Override
    public boolean isActive(WeaponData weaponData) {
        return getSkillSetId().equals(weaponData.getSkillSetId());
    }

    @Override
    public @NotNull List<String> getResultContext(WeaponData weaponData) {
        List<String> result = new ArrayList<>();
        boolean isLightning = weaponData.getTheLowId() != null && weaponData.getTheLowId().equals("mainH弓LvEliteC1boss");
        Map<ResultCategoryType, Double> damages =
                DamageCalcUtil.removeAllRedundancy(new WeaponBasic(weaponData).generateCategorizedDamage(true));

        result.add("§4[ダメージ]");
        damages.keySet().stream()
                .sorted(Comparator.comparingDouble(damages::get).reversed())
                .forEach(category -> {
                    double normalDamage = damages.get(category) * 1.5d;
                    double slowAbleDamage = damages.get(category) * 3.0d;

                    result.add(StringUtils.repeat(' ', 2) + category.getDisplayColor() + category.getName() + ":");

                    result.add(String.format(StringUtils.repeat(' ', 4) + "§7鈍足なし:§6+%1$s§c§o(+%2$s)", DamageCalcUtil.roundDamage(normalDamage), DamageCalcUtil.roundCriticalDamage(normalDamage)));
                    result.add(String.format(StringUtils.repeat(' ', 4) + "§7鈍足あり:§6+%1$s§c§o(+%2$s)", DamageCalcUtil.roundDamage(slowAbleDamage), DamageCalcUtil.roundCriticalDamage(slowAbleDamage)));

                    if (isLightning) {
                        result.add(String.format(StringUtils.repeat(' ', 4) + "§7鈍足なし§e(範囲)§7:§6+%1$s§c§o(+%2$s)", DamageCalcUtil.roundDamage(normalDamage / 4.0d), DamageCalcUtil.roundCriticalDamage(slowAbleDamage/ 4.0d)));
                        result.add(String.format(StringUtils.repeat(' ', 4) + "§7鈍足あり§e(範囲)§7:§6+%1$s§c§o(+%2$s)", DamageCalcUtil.roundDamage(slowAbleDamage / 4.0d), DamageCalcUtil.roundCriticalDamage(slowAbleDamage / 4.0d)));
                    }
                });

        return result;
    }
}
