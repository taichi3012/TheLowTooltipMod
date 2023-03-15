package com.github.taichi3012.thelowtooltipmod.weapon.skill.unique;

import java.util.ArrayList;
import java.util.List;

import com.github.taichi3012.thelowtooltipmod.config.TheLowTooltipModConfig;
import com.github.taichi3012.thelowtooltipmod.damagefactor.JobType;
import com.github.taichi3012.thelowtooltipmod.util.MagicStoneUtil;
import com.github.taichi3012.thelowtooltipmod.util.TheLowUtil;
import com.github.taichi3012.thelowtooltipmod.weapon.WeaponData;

public class WeaponSkill158 extends WeaponSkillUniqueBase {

    public WeaponSkill158() {
        super("wskill158", "百火繚乱", "40");
    }

    @Override
    public double getCoolTime(WeaponData weaponData) {
        double ct = TheLowUtil.getPlayerJobType().equals(JobType.BUTTERFLY_SEEKER) ? 105.0d : 120.0d;
        return ct * TheLowTooltipModConfig.getQuickSpellTalkMultiply() * MagicStoneUtil.getCasterMultiply(weaponData) + 10.0d;
    }

    @Override
    public List<String> getResultContext(WeaponData weaponData) {
        return new ArrayList<>();
    }
}
