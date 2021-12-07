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
import java.util.List;

public class Skill48 implements IWeaponSkillAble {
    @Override
    public double getCoolTime(WeaponData weaponData) {
        return 70.0d * TheLowTooltipModConfig.getQuickSpellTalkMultiply() * MagicStoneUtil.getCasterMultiply(weaponData);
    }

    @Override
    public String getId() {
        return "wskill48";
    }

    @Override
    public String getSkillSetId() {
        return "12";
    }

    @Override
    public String getName(WeaponData weaponData) {
        return isActive(weaponData) ? "神の鉄槌" : "";
    }

    @Override
    public boolean isActive(WeaponData weaponData) {
        return getSkillSetId().equals(weaponData.getSkillSetId());
    }

    @Override
    public List<String> getResultContext(WeaponData weaponData) {
        List<String> result = new ArrayList<>();
        double damage = new WeaponBasic(weaponData).generateCategorizedDamage(false).get(ResultCategoryType.NO_SPECIAL_CATEGORY);
        double passiveAbleDamage = damage * 1.2d;

        result.add("§4[ダメージ]");
        result.add(String.format(StringUtils.repeat(' ', 2) + "§7通常:§6+%1$s§c§o(+%2$s)", DamageCalcUtil.roundDamage(damage), DamageCalcUtil.roundCriticalDamage(damage)));
        result.add(String.format(StringUtils.repeat(' ', 2) + "§7パッシブ有効:§6+%1$s§c§o(+%2$s)", DamageCalcUtil.roundDamage(passiveAbleDamage), DamageCalcUtil.roundCriticalDamage(passiveAbleDamage)));

        return result;
    }
}
