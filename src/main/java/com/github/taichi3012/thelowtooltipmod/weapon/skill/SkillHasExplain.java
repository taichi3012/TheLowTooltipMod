package com.github.taichi3012.thelowtooltipmod.weapon.skill;

import com.github.taichi3012.thelowtooltipmod.config.TheLowTooltipModConfig;
import com.github.taichi3012.thelowtooltipmod.damagefactor.ResultCategoryType;
import com.github.taichi3012.thelowtooltipmod.util.DamageCalcUtil;
import com.github.taichi3012.thelowtooltipmod.util.JavaUtil;
import com.github.taichi3012.thelowtooltipmod.util.MagicStoneUtil;
import com.github.taichi3012.thelowtooltipmod.weapon.WeaponBasic;
import com.github.taichi3012.thelowtooltipmod.weapon.WeaponData;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class SkillHasExplain implements IWeaponSkillAble {

    public final String id;
    public final String name;
    public final String skillSetId;
    public final double defaultCoolTime;
    public final double activeTime;
    public final int explainValue;
    public final String[] explain;
    public final boolean[] isIncludeUniqueSpecial;
    public final double[] multiply;

    public SkillHasExplain(String id, String name, String skillSetId, double defaultCoolTime, double activeTime, String[] explain, boolean[] isIncludeUniqueSpecial, double[] multiply) {
        if (explain.length != isIncludeUniqueSpecial.length || isIncludeUniqueSpecial.length != multiply.length)
            throw new IllegalArgumentException();

        this.id = id;
        this.name = name;
        this.skillSetId = skillSetId;
        this.defaultCoolTime = defaultCoolTime;
        this.activeTime = activeTime;
        this.explainValue = explain.length;
        this.explain = explain;
        this.isIncludeUniqueSpecial = isIncludeUniqueSpecial;
        this.multiply = multiply;
    }

    public SkillHasExplain(String id, String name, String skillSetId, double defaultCoolTime, double activeTime, String explain, boolean isIncludeUniqueSpecial, double multiply) {
        this(id, name, skillSetId, defaultCoolTime, activeTime, new String[]{explain}, new boolean[]{isIncludeUniqueSpecial}, new double[]{multiply});
    }

    public SkillHasExplain(String id, String name, String skillSetId, double defaultCoolTime, double activeTime, String[] explain, boolean isIncludeUniqueSpecial, double[] multiply) {
        this(id, name, skillSetId, defaultCoolTime, activeTime, explain, JavaUtil.generateFillArray(explain.length, isIncludeUniqueSpecial), multiply);
    }


    @Override
    public double getCoolTime(WeaponData weaponData) {
        return this.defaultCoolTime * TheLowTooltipModConfig.getQuickSpellTalkMultiply() * MagicStoneUtil.getCasterMultiply(weaponData) + activeTime;
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
    public List<String> getResultContext(WeaponData weaponData) {
        List<String> result = new ArrayList<>();

        result.add("§4[ダメージ]");

        WeaponBasic weapon = new WeaponBasic(weaponData);
        List<Map<ResultCategoryType, Double>> skillDamages = new ArrayList<>();
        for (int i = 0; i < this.explainValue; i++) {
            int finalI = i;
            Map<ResultCategoryType, Double> damage = weapon.generateCategorizedDamage(this.isIncludeUniqueSpecial[i]);
            damage.keySet().forEach(category -> damage.put(category, damage.get(category) * this.multiply[finalI]));
            DamageCalcUtil.removeAllRedundancy(damage);
            skillDamages.add(damage);
        }

        Map<ResultCategoryType, Double> defaultDamage =
                DamageCalcUtil.removeAllRedundancy(weapon.generateCategorizedDamage(true));
        defaultDamage.keySet().stream()
                .sorted(Comparator.comparingDouble(defaultDamage::get).reversed())
                .forEach(category -> {
                    List<String> categoryResult = new ArrayList<>();
                    boolean hasResult = false;

                    for (int i = 0; i < this.explainValue; i++) {
                        if (skillDamages.get(i).get(category) == null)
                            continue;
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
