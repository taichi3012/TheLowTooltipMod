package com.github.taichi3012.thelowtooltipmod.weapon.skill;

import java.util.*;

import org.apache.commons.lang3.StringUtils;

import com.github.taichi3012.thelowtooltipmod.config.TheLowTooltipModConfig;
import com.github.taichi3012.thelowtooltipmod.damagefactor.ResultCategoryType;
import com.github.taichi3012.thelowtooltipmod.util.DamageCalcUtil;
import com.github.taichi3012.thelowtooltipmod.util.MagicStoneUtil;
import com.github.taichi3012.thelowtooltipmod.weapon.WeaponBasic;
import com.github.taichi3012.thelowtooltipmod.weapon.WeaponData;
import com.github.taichi3012.thelowtooltipmod.weapon.skill.unique.WeaponSkillUniqueBase;

public class WeaponSkillBasic extends WeaponSkillUniqueBase {

    public final double defaultCoolTime;
    public final boolean isIncludeUniqueSpecial;
    public final double activeTime;
    public final double multiply;

    public WeaponSkillBasic(String id, String name, List<String> skillSetIds, double defaultCoolTime, double activeTime, boolean isIncludeUniqueSpecial, double multiply) {
        super(id, name, skillSetIds);
        this.defaultCoolTime = defaultCoolTime;
        this.activeTime = activeTime;
        this.isIncludeUniqueSpecial = isIncludeUniqueSpecial;
        this.multiply = multiply;
    }

    public WeaponSkillBasic(String id, String name, String skillSetId, double defaultCoolTime, double activeTime, boolean isIncludeUniqueSpecial, double multiply) {
        this(id, name, Collections.singletonList(skillSetId), defaultCoolTime, activeTime, isIncludeUniqueSpecial, multiply);
    }

    public WeaponSkillBasic(String id, String name, String skillSetId, double defaultCoolTime, double activeTime) {
        this(id, name, skillSetId, defaultCoolTime, activeTime, true, 0.0d);
    }

    public WeaponSkillBasic(String id, String name, String skillSetId, boolean isIncludeUniqueSpecial, double multiply) {
        this(id, name, skillSetId, 0.0d, 0.0d, isIncludeUniqueSpecial, multiply);
    }

    @Override
    public double getCoolTime(WeaponData weaponData) {
        return this.defaultCoolTime * TheLowTooltipModConfig.getQuickSpellTalkMultiply() * MagicStoneUtil.getCasterMultiply(weaponData) + this.activeTime;
    }

    @Override
    public List<String> getResultContext(WeaponData weaponData) {
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
