package com.github.taichi3012.thelowtooltipmod.weapon.skill.normal;

import java.util.ArrayList;
import java.util.List;

import com.github.taichi3012.thelowtooltipmod.config.TheLowTooltipModConfig;
import com.github.taichi3012.thelowtooltipmod.util.MagicStoneUtil;
import com.github.taichi3012.thelowtooltipmod.weapon.WeaponData;

public class WeaponNormalSkillBuff extends WeaponNormalSkillBase {

    public double coolTime;

    public WeaponNormalSkillBuff(String id, String name, double coolTime) {
        super(id, name);
        this.coolTime = coolTime;
    }

    @Override
    public List<String> getResultContext(WeaponData weaponData) {
        return new ArrayList<>();
    }

    @Override
    public double getCoolTime(WeaponData weaponData) {
        return coolTime * TheLowTooltipModConfig.getQuickSpellTalkMultiply() * MagicStoneUtil.getCasterMultiply(weaponData);
    }
}
