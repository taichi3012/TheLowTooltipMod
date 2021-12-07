package com.github.taichi3012.thelowtooltipmod.weapon.skill.normal;

import com.github.taichi3012.thelowtooltipmod.config.TheLowTooltipModConfig;
import com.github.taichi3012.thelowtooltipmod.util.MagicStoneUtil;
import com.github.taichi3012.thelowtooltipmod.util.TheLowNBTUtil;
import com.github.taichi3012.thelowtooltipmod.weapon.WeaponData;
import com.github.taichi3012.thelowtooltipmod.weapon.skill.IWeaponSkillAble;

import java.util.Arrays;
import java.util.List;

public class SkillRemoveFire implements IWeaponSkillAble {

    @Override
    public String getId() {
        return "n_skill_r_fire";
    }

    @Override
    public String getSkillSetId() {
        return null;
    }

    @Override
    public String getName(WeaponData weaponData) {
        return "天使の囁き";
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
        return 10.0d * TheLowTooltipModConfig.getQuickSpellTalkMultiply() * MagicStoneUtil.getCasterMultiply(weaponData);
    }
}
