package com.github.taichi3012.thelowtooltipmod.weapon.skill.normal;

import com.github.taichi3012.thelowtooltipmod.config.TheLowTooltipModConfig;
import com.github.taichi3012.thelowtooltipmod.damagefactor.ResultCategoryType;
import com.github.taichi3012.thelowtooltipmod.util.DamageCalcUtil;
import com.github.taichi3012.thelowtooltipmod.util.MagicStoneUtil;
import com.github.taichi3012.thelowtooltipmod.util.TheLowNBTUtil;
import com.github.taichi3012.thelowtooltipmod.util.TheLowUtil;
import com.github.taichi3012.thelowtooltipmod.weapon.WeaponBasic;
import com.github.taichi3012.thelowtooltipmod.weapon.WeaponData;
import com.github.taichi3012.thelowtooltipmod.weapon.skill.IWeaponSkillAble;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class SkillAttackNormal implements IWeaponSkillAble {

    public final String id;
    public final String name;
    public final double multiply;
    public final double coolTime;

    public SkillAttackNormal(String id, String name, double multiply, double coolTime) {
        this.id = id;
        this.name = name;
        this.multiply = multiply;
        this.coolTime = coolTime;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getSkillSetId() {
        return null;
    }

    @Override
    public String getName(WeaponData weaponData) {
        return name;
    }

    @Override
    public boolean isActive(WeaponData weaponData) {
        String[] skillList = TheLowNBTUtil.getNormalSkillList(weaponData.getItemStack());
        return Arrays.asList(skillList).contains(getId());
    }

    @Override
    public @NotNull List<String> getResultContext(WeaponData weaponData) {
        List<String> result = new ArrayList<>();
        Map<ResultCategoryType, Double> damages =
                DamageCalcUtil.removeAllRedundancy(new WeaponBasic(weaponData).generateCategorizedDamage(false));
        double multiply = this.multiply + TheLowUtil.generateNoiseBySeed(TheLowNBTUtil.getSeed(weaponData.getItemStack()) + 1100) / 100.0d;

        result.add("§4[ダメージ]");
        damages.keySet().stream()
                .sorted(Comparator.comparingDouble(damages::get).reversed())
                .forEach(category -> {
                    double normalDamage = damages.get(category) * multiply;

                    result.add(StringUtils.repeat(' ', 2) + category.getDisplayColor() + category.getName() + ":");
                    result.add(String.format(StringUtils.repeat(' ', 4) + "§6+%1$s§c§o(+%2$s)", DamageCalcUtil.roundDamage(normalDamage), DamageCalcUtil.roundCriticalDamage(normalDamage)));
                });

        return result;
    }

    @Override
    public double getCoolTime(WeaponData weaponData) {
        return ((int) (coolTime * TheLowUtil.generateNoiseBySeed(TheLowNBTUtil.getSeed(weaponData.getItemStack()) + 15)))
                * TheLowTooltipModConfig.getQuickSpellTalkMultiply() * MagicStoneUtil.getCasterMultiply(weaponData);
    }
}
