package com.github.taichi3012.thelowtooltipmod.weapon.skill.normal;

import com.github.taichi3012.thelowtooltipmod.config.TheLowTooltipModConfig;
import com.github.taichi3012.thelowtooltipmod.util.MagicStoneUtil;
import com.github.taichi3012.thelowtooltipmod.util.TheLowNBTUtil;
import com.github.taichi3012.thelowtooltipmod.weapon.WeaponData;
import com.github.taichi3012.thelowtooltipmod.weapon.skill.IWeaponSkillAble;

import java.util.Arrays;
import java.util.List;

public class SkillBuffNormal implements IWeaponSkillAble {

    public final String id;
    public final String name;
    public final double coolTime;

    public SkillBuffNormal(String id, String name, double coolTime) {
        this.id = id;
        this.name = name;
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
        return skillList != null && Arrays.asList(skillList).contains(getId());
    }

    @Override
    public List<String> getResultContext(WeaponData weaponData) {
        return null;
    }

    @Override
    public double getCoolTime(WeaponData weaponData) {
        return coolTime * TheLowTooltipModConfig.getQuickSpellTalkMultiply() * MagicStoneUtil.getCasterMultiply(weaponData);
    }
}
