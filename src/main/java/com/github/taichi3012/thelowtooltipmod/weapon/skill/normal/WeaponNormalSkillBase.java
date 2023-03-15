package com.github.taichi3012.thelowtooltipmod.weapon.skill.normal;

import java.util.Arrays;

import com.github.taichi3012.thelowtooltipmod.util.TheLowNBTUtil;
import com.github.taichi3012.thelowtooltipmod.weapon.WeaponData;
import com.github.taichi3012.thelowtooltipmod.weapon.skill.WeaponSkillBase;

public abstract class WeaponNormalSkillBase extends WeaponSkillBase {

    protected WeaponNormalSkillBase(String id, String name) {
        super(id, name);
    }

    @Override
    public boolean isActive(WeaponData weaponData) {
        String[] skillList = TheLowNBTUtil.getNormalSkillList(weaponData.getItemStack());
        return Arrays.asList(skillList).contains(this.id);
    }
}
