package com.github.taichi3012.thelowtooltipmod.weapon.skill;

import com.github.taichi3012.thelowtooltipmod.config.TheLowTooltipModConfig;
import com.github.taichi3012.thelowtooltipmod.damagefactor.ResultCategoryType;
import com.github.taichi3012.thelowtooltipmod.util.DamageCalcUtil;
import com.github.taichi3012.thelowtooltipmod.util.JavaUtil;
import com.github.taichi3012.thelowtooltipmod.util.MagicStoneUtil;
import com.github.taichi3012.thelowtooltipmod.weapon.WeaponBasic;
import com.github.taichi3012.thelowtooltipmod.weapon.WeaponData;
import com.github.taichi3012.thelowtooltipmod.weapon.skill.unique.WeaponSkillUniqueBase;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class WeaponSkillHasExplain extends WeaponSkillUniqueBase {

    public final double defaultCoolTime;
    public final double activeTime;

    private final int explainValue;
    private final String[] explain;
    private final boolean[] isIncludeUniqueSpecial;
    private final double[] multiply;

    public WeaponSkillHasExplain(String id, String name, List<String> skillSetIds, double defaultCoolTime, double activeTime, String[] explain, boolean[] isIncludeUniqueSpecial, double[] multiply) {
        super(id, name, skillSetIds);
        if (explain.length != isIncludeUniqueSpecial.length || isIncludeUniqueSpecial.length != multiply.length) {
            throw new IllegalArgumentException();
        }

        this.defaultCoolTime = defaultCoolTime;
        this.activeTime = activeTime;
        this.explainValue = explain.length;
        this.explain = explain;
        this.isIncludeUniqueSpecial = isIncludeUniqueSpecial;
        this.multiply = multiply;
    }

    public WeaponSkillHasExplain(String id, String name, String skillSetId, double defaultCoolTime, double activeTime, String explain, boolean isIncludeUniqueSpecial, double multiply) {
        this(id, name, Collections.singletonList(skillSetId), defaultCoolTime, activeTime, new String[]{explain}, new boolean[]{isIncludeUniqueSpecial}, new double[]{multiply});
    }

    public WeaponSkillHasExplain(String id, String name, String skillSetId, double defaultCoolTime, double activeTime, String[] explain, boolean isIncludeUniqueSpecial, double[] multiply) {
        this(id, name, Collections.singletonList(skillSetId), defaultCoolTime, activeTime, explain, JavaUtil.generateFillArray(explain.length, isIncludeUniqueSpecial), multiply);
    }


    @Override
    public double getCoolTime(WeaponData weaponData) {
        return this.defaultCoolTime * TheLowTooltipModConfig.getQuickSpellTalkMultiply() * MagicStoneUtil.getCasterMultiply(weaponData) + activeTime;
    }

    @Override
    public @NotNull List<String> getResultContext(WeaponData weaponData) {
        List<String> result = new ArrayList<>();
        WeaponBasic weapon = new WeaponBasic(weaponData);

        result.add("§4[ダメージ]");

        List<Map<ResultCategoryType, Double>> skillDamages = new ArrayList<>();
        for (int i = 0; i < this.explainValue; i++) {
            Map<ResultCategoryType, Double> explainDamages = new HashMap<>();
            Map<ResultCategoryType, Double> WPDamages = weapon.generateCategorizedDamage(this.isIncludeUniqueSpecial[i]);

            int finalI = i;
            WPDamages.keySet()
                    .forEach(
                            category -> explainDamages.put(category, WPDamages.get(category) * this.multiply[finalI])
                    );

            DamageCalcUtil.removeAllRedundancy(explainDamages);
            skillDamages.add(explainDamages);
        }

        Map<ResultCategoryType, Double> defaultDamages =
                DamageCalcUtil.removeAllRedundancy(weapon.generateCategorizedDamage(true));

        defaultDamages.keySet().stream()
                .sorted(Comparator.comparingDouble(defaultDamages::get).reversed())
                .forEach(category -> {
                    List<String> categoryResult = new ArrayList<>();
                    boolean hasResult = false;

                    for (int i = 0; i < this.explainValue; i++) {
                        if (skillDamages.get(i).get(category) == null) {
                            continue;
                        }

                        hasResult = true;
                        double normalDamage = skillDamages.get(i).get(category);
                        categoryResult.add(String.format(StringUtils.repeat(' ', 4) + "§7%1$s:§6+%2$s§c§o(+%3$s)", this.explain[i], DamageCalcUtil.roundDamage(normalDamage), DamageCalcUtil.roundCriticalDamage(normalDamage)));
                    }

                    if (hasResult) {
                        result.add(StringUtils.repeat(' ', 2) + category.getDisplayColor() + category.getName() + ":");
                        result.addAll(categoryResult);
                    }
                });

        return result;
    }
}
