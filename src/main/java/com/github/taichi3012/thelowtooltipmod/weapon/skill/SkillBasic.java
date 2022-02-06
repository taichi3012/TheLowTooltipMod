package com.github.taichi3012.thelowtooltipmod.weapon.skill;

import com.github.taichi3012.thelowtooltipmod.config.TheLowTooltipModConfig;
import com.github.taichi3012.thelowtooltipmod.damagefactor.ResultCategoryType;
import com.github.taichi3012.thelowtooltipmod.util.DamageCalcUtil;
import com.github.taichi3012.thelowtooltipmod.util.MagicStoneUtil;
import com.github.taichi3012.thelowtooltipmod.weapon.WeaponBasic;
import com.github.taichi3012.thelowtooltipmod.weapon.WeaponData;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class SkillBasic implements IWeaponSkillAble {

    public final String id;
    public final String name;
    public final String skillSetId;
    public final double defaultCoolTime;
    public final boolean isIncludeUniqueSpecial;
    public final double activeTime;
    public final double multiply;

    public SkillBasic(String id, String name, String skillSetId, double defaultCoolTime, double activeTime, boolean isIncludeUniqueSpecial, double multiply) {
        this.id = id;
        this.name = name;
        this.skillSetId = skillSetId;
        this.defaultCoolTime = defaultCoolTime;
        this.activeTime = activeTime;
        this.isIncludeUniqueSpecial = isIncludeUniqueSpecial;
        this.multiply = multiply;
    }

    public SkillBasic(String id, String name, String skillSetId, double defaultCoolTime , double activeTime) {
        this(id, name, skillSetId, defaultCoolTime, activeTime, true, 0.0d);
    }

    public SkillBasic(String id, String name, String skillSetId, boolean isIncludeUniqueSpecial, double multiply) {
        this(id, name, skillSetId, 0.0d, 0.0d, isIncludeUniqueSpecial, multiply);
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getSkillSetId() {
        return this.skillSetId;
    }

    @Override
    public String getName(WeaponData weaponData) {
        return isActive(weaponData) ? this.name : "";
    }

    @Override
    public boolean isActive(WeaponData weaponData) {
        return weaponData.getSkillSetId() != null && this.skillSetId.contains(weaponData.getSkillSetId());
    }

    @Override
    public double getCoolTime(WeaponData weaponData) {
        return this.defaultCoolTime * TheLowTooltipModConfig.getQuickSpellTalkMultiply() * MagicStoneUtil.getCasterMultiply(weaponData) + this.activeTime;
    }

    @Override
    public @NotNull List<String> getResultContext(WeaponData weaponData) {
        List<String> result = new ArrayList<>();

        if (this.multiply > 0) {
            Map<ResultCategoryType, Double> damages =
                    DamageCalcUtil.removeAllRedundancy(new WeaponBasic(weaponData).generateCategorizedDamage(isIncludeUniqueSpecial));

            result.add("§4[ダメージ]");
            damages.keySet().stream()
                    .sorted(Comparator.comparingDouble(damages::get).reversed())
                    .forEach(category -> {
                        double normalDamage = damages.get(category) * this.multiply;

                        result.add(StringUtils.repeat(' ', 2) + category.getDisplayColor() + category.getName() + ":");
                        result.add(String.format(StringUtils.repeat(' ', 4) + "§6+%1$s§c§o(+%2$s)", DamageCalcUtil.roundDamage(normalDamage), DamageCalcUtil.roundCriticalDamage(normalDamage)));
                    });
        }

        return result;
    }
}
